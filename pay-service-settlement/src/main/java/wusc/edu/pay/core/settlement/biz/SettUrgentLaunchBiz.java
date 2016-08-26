package wusc.edu.pay.core.settlement.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.settlement.biz.sub.AccountBiz;
import wusc.edu.pay.core.settlement.biz.sub.FeeBiz;
import wusc.edu.pay.core.settlement.biz.sub.RemitBiz;
import wusc.edu.pay.core.settlement.biz.sub.SettAmountCheckBiz;
import wusc.edu.pay.core.settlement.biz.sub.SettBiz;
import wusc.edu.pay.core.settlement.dao.SettDailyCollectDao;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.core.settlement.dao.SettRuleDao;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.settlement.DTO.LaunchSettleVo;
import wusc.edu.pay.facade.settlement.DTO.UserBankAccountVo;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettRuleStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettTypeEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;


/**
 * @title 紧急结算核心业务biz
 * @company 中益智正(gzzyzz)
 * @author jialong.shen
 * @since 2014-8-13
 */
@Component("settUrgentLaunchBiz")
public class SettUrgentLaunchBiz {
	private static final Log log = LogFactory.getLog(SettUrgentLaunchBiz.class);
	@Autowired
	private SettRecordDao settRecordDao;
	@Autowired
	private SettRuleDao settRuleDao;
	@Autowired
	private SettDailyCollectDao settDailyCollectDao;

	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private AccountBiz accountBiz;
	@Autowired
	private RemitBiz remitBiz;
	@Autowired
	private SettAmountCheckBiz settAmountCheckBiz;
	@Autowired
	private SettQueryBiz settQueryBiz;
	@Autowired
	private SettBiz settBiz;
	@Autowired
	private FeeBiz feeBiz;

	/**
	 * @描述: 发起紧急结算（T+0）,需先运营审核才能发起.
	 * @作者: Along.shen .
	 * @创建时间:2015-3-3,下午1:53:09.<br/>
	 * @param merchantNo
	 *            商户编号<br/>
	 * @param trxNo
	 *            支付记录的流水号<br/>
	 * @param amount
	 *            结算金额<br/>
	 * @param bankAccount
	 *            结算账户信息
	 */

	@Transactional(rollbackFor = { Exception.class })
	public void launchUrgentSettle(String merchantNo, String trxNo, double amount, UserBankAccountVo bankAccount) {
		/** step1： 查找出账户 **/
		Account account = accountQueryFacade.getAccountByUserNo(merchantNo);
		String accountNo = account.getAccountNo();

		/** step2： 得到可结算的金额 **/
		BigDecimal settAmount = BigDecimal.valueOf(amount);
		log.info(String.format("账户编号：[%s],可结算金额为：[%s]", accountNo, settAmount));

		/** step3： 得到可用的余额 **/
		BigDecimal availableBalance = BigDecimal.valueOf(account.getAvailableBalance());
		log.info(String.format("账户编号：[%s],可用余额金额为：[%s]", accountNo, availableBalance));

		/** step4： 查找出结算规则 **/
		SettRule settRule = settQueryBiz.getSettRuleByAccountNo(accountNo);
		if (settRule == null) {
			throw new SettBizException(SettBizException.SETT_RULE_NOT_EXIST, "结算账户的结算规则不存在，不允许结算.");
		}
		/** step5： 判断金额是否符合条件 **/
		if (settAmount.compareTo(availableBalance) == 1) {
			log.info(String.format("账户编号：[%s],可用余额金额为：[%s]，需结算金额为：[%s]，余额不足不能结算", accountNo, availableBalance, settAmount));
			throw new SettBizException(SettBizException.SETTLE_AMOUNT_ACCOUNT_CHECKERR, "需结算金额大于账户余额");
		}

		/** step6： 保存账户历史日汇总记录 **/
		SettDailyCollect dailyCollect = saveDailyCollect(accountNo, settAmount);

		/** step7： 封装结算记录 **/
		LaunchSettleVo launchSettleVo = new LaunchSettleVo();
		SettRecord settRecord = populateSettRecord(bankAccount, settRule, launchSettleVo);

		/** step8： 修改结算批次号 **/
		settRuleDao.update(launchSettleVo.getSettRule());

		/** step9： 填充结算记录交易金额 **/
		settRecord.addTradeAmount(settAmount, 1);

		/** step10： 计算手续费 **/
		double settFee = feeBiz.calculateFee(merchantNo, amount, settRecord);

		/** step11： 判断结算金额是否小于0.01 如果不足一分 不生成结算记录 && 分以后部分遗留处理 **/
		settAmountCheckBiz.checkSettAmount(settRecord);
		log.info("settleAmount after check:" + settAmount + " ,结算手续费:" + settFee);

		/** step12： 保存结算记录 **/
		saveSettRecord(dailyCollect, settRecord);

		/** step13： 更新所有的待汇总记录 **/
		settBiz.updateDailyCollectList(settRecord.getDailys(), settRecord.getBatchNo(),true);

		/** step14： 验证风控 **/
		
		/** step15： 紧急结算账户处理 **/
		accountBiz.settSuccessT0(settRecord, trxNo);
		
		/** step16： 保存计费订单 **/
		for (final FeeCalculateResultDTO calculateResult : settRecord.getCalculateResultDTOList()) {
			feeBiz.caculate(calculateResult);
		}
		/** step17： 调remit队列打款 **/
		remitBiz.launchRemit(settRecord);
	}

	

	/** 封装结算记录 **/
	private SettRecord populateSettRecord(UserBankAccountVo bankAccount, SettRule settRule, LaunchSettleVo launchSettleVo) {
		launchSettleVo.setAccountNo(settRule.getAccountNo());
		launchSettleVo.setBankAccount(bankAccount);
		launchSettleVo.setSettleEndDate(new Date());
		launchSettleVo.setSettType(SettModeTypeEnum.URGENT_SETTLE.getValue());
		launchSettleVo.setOperatorLoginname("平台");
		launchSettleVo.setOperatorRealname("平台");
		launchSettleVo.setSettRule(settRule);
		SettRecord settRecord = settBiz.populateSettRecord(launchSettleVo);
		return settRecord;
	}

	/** 保存账户历史日汇总记录 **/
	private SettDailyCollect saveDailyCollect(String accountNo, BigDecimal settAmount) {
		SettDailyCollect dailyCollect = new SettDailyCollect();
		dailyCollect.setAccountNo(accountNo);
		dailyCollect.setCollectDate(new Date());
		dailyCollect.setCollectType(SettDailyCollectTypeEnum.ALL.getValue());
		dailyCollect.setTotalAmount(settAmount);
		dailyCollect.setTotalCount(1);
		dailyCollect.setSettStatus(SettDailyCollectStatusEnum.UNSETTLE.getValue());
		dailyCollect.setRiskDay(0);
		dailyCollect.setRemark("T+0 结算汇总");
		settDailyCollectDao.insert(dailyCollect);
		return dailyCollect;
	}

	/** 保存结算记录 **/
	private void saveSettRecord(SettDailyCollect dailyCollect, SettRecord settRecord) {
		// 紧急结算不用审核：更新结算状态为
		settRecord.setSettStatus(SettRecordStatusEnum.REMITTING.getValue());
		settRecord.setRemitConfirmTime(new Date());
		settRecord.setRemitRequestTime(new Date());
		Long id = settRecordDao.insert(settRecord);
		
		settRecord.setId(id);
		settRecord.setVersion(0);
		List<SettDailyCollect> dailys = new ArrayList<SettDailyCollect>();
		dailys.add(dailyCollect);
		settRecord.setDailys(dailys);
	}

	/**
	 * 结算完成后，更新结算规则信息
	 * 
	 * @param record
	 */
	public void processSettleRule(SettRecord record) {
		SettRule settRule = settRuleDao.getSettRuleByAccountNo(record.getAccountNo());

		if (settRule.getSettType() == SettTypeEnum.AUTO_SETTLEMENT.getValue() && StringUtil.isBlank(settRule.getSettCycleData())) {
			throw new SettBizException(SettBizException.SETTLEMENT_RULE_CYCLE_DATA_ILLEGAL, "结算周期数据为空！");
		}
		// 如果是自动结算，则需计算下次结算日期，否则不需要计算
		if (SettModeTypeEnum.REGULAR_SETTLE.getValue() == record.getSettMode()) {
			settRule.setNextProcessDate(settRule.calculateNextSettleDay(record.getSettDate()));
		}

		settRule.setLastSettDate(record.getEndDate());
		settRule.setLastBatchNo(record.getBatchNo());
		// 设置上次结算处理日期
		settRule.setLastProcessDate(record.getSettDate());
		settRule.setCurrentSettStatus(SettRuleStatusEnum.SETTLEABLE.getValue());
		settRuleDao.update(settRule);
	}

}
