package wusc.edu.pay.core.settlement.biz;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.core.settlement.biz.sub.AccountBiz;
import wusc.edu.pay.core.settlement.biz.sub.RemitBiz;
import wusc.edu.pay.core.settlement.dao.SettDailyCollectDao;
import wusc.edu.pay.core.settlement.dao.SettErrorResendRecordDao;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.core.settlement.dao.SettRuleDao;
import wusc.edu.pay.facade.fee.service.FeeManagerFacade;
import wusc.edu.pay.facade.remit.enums.RemitUrgentEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.enums.TradeTypeEnum;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettErrorResendRecord;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettRuleStatusEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;


/**
 * @title 结算记录处理biz（重新发起，取消等...）
 * @company 中益智正(gzzyzz)
 * @author jialong.shen
 * @since 2014-8-15
 */
@Component("settHandleBiz")
public class SettHandleBiz {
	private final static Log log = LogFactory.getLog(SettHandleBiz.class);
	@Autowired
	private SettBusinessBiz settBusinessBiz;
	@Autowired
	private SettRuleDao settRuleDao;
	@Autowired
	private SettRecordDao settRecordDao;
	@Autowired
	private SettDailyCollectDao settDailyCollectDao;
	@Autowired
	private SettErrorResendRecordDao settErrorResendRecordDao;
	@Autowired
	private RemitRequestFacade remitRequestFacade;
	@Autowired
	private FeeManagerFacade feeManagerFacade;
	@Autowired
	private UserBankAccountFacade userBankAccountFacade;
	@Autowired
	private RemitBiz remitBiz;
	@Autowired
	private AccountBiz accountBiz;

	/**
	 * 结算预处理 判断是否在允许的时间段，是否节假日，是否调整日 modetype 参考SettModeTypeEnum settleDate 结算日期
	 */
	public void settlePreprocess(Integer modeType, Date settDate) {
		// 在某个时间段，不能发起结算判断
		if (!settBusinessBiz.isSettModeLaunchable(SettModeTypeEnum.getEnum(modeType), settDate)) {
			throw new SettBizException(SettBizException.SETTLEMENT_DAY_COLLECT, "日汇总中，不能进行结算");
		}
		// 是否结算日调整，结算日被调整的，本日不在进行结算
		if (settBusinessBiz.isSettDayAdjust(settDate)) {
			throw new SettBizException(SettBizException.SETTLEMENT_DAY_ADJUST, "结算日已调整，不能进行结算");
		}
	}

	/**
	 * 结算处理：取消结算记录 <br/>
	 * 只有待确认状态的结算记录才能进行操作 <br/>
	 * 
	 * @param SettRecord
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void cancelSettleProcess(SettRecord record) {
		// 结算确认,修改为已取消
		if (!record.isCanCancelProcess()) {
			throw new SettBizException(SettBizException.SETTLE_STATUS_ERROR, "结算记录状态不是待确认，不能进行结算处理");
		}
		record.setSettStatus(SettRecordStatusEnum.CANCEL.getValue());
		settRecordDao.update(record);
		log.info("Update settlement rule, the account number is : " + record.getAccountNo());
		SettRule settRule = settRuleDao.getSettRuleByAccountNo(record.getAccountNo());
		settRule.setCurrentSettStatus(SettRuleStatusEnum.SETTLEABLE.getValue());
		settRuleDao.update(settRule);
		// 对冲未结算遗留汇总
		this.hedgeLeaveDailyCollect(record);

		// 解冻资金
		accountBiz.settFail(record);
		// 删掉计费订单
		this.deleteFeeOrder(record.getBatchNo());
	}

	/**
	 * //对冲未结算遗留汇总
	 */
	private void hedgeLeaveDailyCollect(SettRecord record) {
		BigDecimal settAmount = record.getSettAmount();
		BigDecimal tradeAmount = record.getTradeAmount();
		BigDecimal subtract = tradeAmount.subtract(settAmount);// 以前遗留的资金金额（要对冲掉）
		SettDailyCollect tempLeave = null;
		// 如果不为0
		if (subtract.compareTo(BigDecimal.ZERO) == 1) {

			tempLeave = new SettDailyCollect();
			tempLeave.setTotalAmount(BigDecimal.ZERO.subtract(subtract));// 取负
			tempLeave.setTotalCount(1);
			tempLeave.setAccountNo(record.getAccountNo());
			tempLeave.setCollectType(SettDailyCollectTypeEnum.LEAVE.getValue());
			tempLeave.setCollectDate(new Date());
			tempLeave.setSettStatus(SettDailyCollectStatusEnum.UNSETTLE.getValue());
			tempLeave.setRemark("因结算取消或失败对冲结算遗留汇总金额");
			settDailyCollectDao.insert(tempLeave);
		}
	}

	/**
	 * 发起重新结算
	 */
	@Transactional(rollbackFor = { Exception.class })
	public SettRecord resendSettleRemit(SettRecord settRecord) {
		if (!settRecord.isCanResendRemit()) {
			throw new SettBizException(SettBizException.SETTLE_RESEND_ERROR, "结算记录不是失败退回状态，不能重新发起结算");
		}

		// 生成结算请求号
		String remitRequestNo = remitRequestFacade.buildRemitRequestNo(); // 获取打款请求号

		// 获取结算配置信息
		UserBankAccount bankAccount = userBankAccountFacade.getSettlementBankAccountByMerchantUserNo(settRecord.getUserNo());

		if (settRecord.getUserType() != UserTypeEnum.CUSTOMER.getValue()) {
			// 更新收款人信息
			populateCardInfoForResend(settRecord, bankAccount);
		}

		// 保存结算记录重发表
		saveSettlementResendLog(settRecord);

		settRecord.setRemitNo(remitRequestNo);
		settRecord.setSettStatus(SettRecordStatusEnum.REMITTING.getValue());
		settRecordDao.update(settRecord);

		if (settRecord.getSettMode() == SettModeTypeEnum.URGENT_SETTLE.getValue()) {
			remitBiz.requestRemit(settRecord, TradeSourcesEnum.MERCHANT_SETTLEMENT, TradeTypeEnum.MERCHANT_AUTO_SETTLEMENT_URGENT, RemitUrgentEnum.URGENT);
		} else {
			// 判断区分商户结算和代理商结算
			TradeTypeEnum tradeType = null;
			if (settRecord.getUserType() != null && settRecord.getUserType() == UserTypeEnum.POSAGENT.getValue()) {
				tradeType = TradeTypeEnum.AGENT_AUTO_SETTLEMENT;
			} else {
				tradeType = TradeTypeEnum.MERCHANT_AUTO_SETTLEMENT;
			}
			remitBiz.requestRemit(settRecord, TradeSourcesEnum.MERCHANT_SETTLEMENT, tradeType, RemitUrgentEnum.UN_URGENT);
		}

		return settRecord;
	}

	/**
	 * 保存结算重发日志实体
	 * 
	 * @param record
	 */
	private void saveSettlementResendLog(SettRecord record) {
		SettErrorResendRecord log = new SettErrorResendRecord();
		log.setBatchNo(record.getBatchNo());
		log.setRemitNo(record.getRemitNo());
		log.setCurrencyType(record.getCurrencyType());
		log.setBankCode(record.getBankCode());
		log.setBankAccountName(record.getBankAccountName());
		log.setBankAccountNo(record.getBankAccountNo());
		log.setBankAccountType(record.getBankAccountType());
		log.setCountry(record.getCountry());
		log.setCity(record.getCity());
		log.setProvince(record.getProvince());
		log.setBankAccountAddress(record.getBankAccountAddress());
		settErrorResendRecordDao.insert(log);
	}

	// 更新收款人信息
	private void populateCardInfoForResend(SettRecord settRecord, UserBankAccount bankAccount) {
		settRecord.setBankAccountAddress(bankAccount.getBankAccountAddress());
		settRecord.setBankAccountName(bankAccount.getBankAccountName());
		settRecord.setBankAccountNo(bankAccount.getBankAccountNo());
		settRecord.setBankAccountType(bankAccount.getBankAccountType());
		settRecord.setBankChannelNo(bankAccount.getBankChannelNo());
		settRecord.setBankCode(bankAccount.getBankCode());
		settRecord.setCountry("");
		settRecord.setProvince(bankAccount.getProvince());
		settRecord.setCity(bankAccount.getCity());
	}

	/**
	 * 根据批次号，更新每日汇总状态为已结算
	 * 
	 * @param batchNo
	 */
	public void updateDailyCollectStatus(String batchNo, Integer status) {
		settDailyCollectDao.updateDailyCollectStatus(batchNo, status);
	}

	/**
	 * 删除计费订单（结算特有，只有结算业务可以调用）
	 * 
	 * @param trxFlowNo
	 *            流水号
	 */
	public void deleteFeeOrder(String trxFlowNo) {
		feeManagerFacade.deleteFeeOrder(trxFlowNo);
	}

}
