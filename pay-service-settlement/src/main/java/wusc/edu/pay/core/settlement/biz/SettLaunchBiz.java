package wusc.edu.pay.core.settlement.biz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.core.settlement.biz.sub.AccountBiz;
import wusc.edu.pay.core.settlement.biz.sub.FeeBiz;
import wusc.edu.pay.core.settlement.biz.sub.RemitBiz;
import wusc.edu.pay.core.settlement.biz.sub.SettAmountCheckBiz;
import wusc.edu.pay.core.settlement.biz.sub.SettBiz;
import wusc.edu.pay.core.settlement.biz.sub.SettCalculateBiz;
import wusc.edu.pay.core.settlement.biz.sub.SettErrorRecordBiz;
import wusc.edu.pay.core.settlement.biz.sub.TradeLimitBiz;
import wusc.edu.pay.core.settlement.dao.SettDailyCollectDao;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.core.settlement.dao.SettRuleDao;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.account.vo.DailyCollectAccountHistoryVo;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.settlement.DTO.LaunchSettleVo;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettRuleStatusEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;


/**
 * @title 普通结算核心业务biz
 * @company 中益智正(gzzyzz)
 * @author jialong.shen
 * @since 2014-8-13
 */
@Component("settLaunchBiz")
public class SettLaunchBiz {
	private static final Log log = LogFactory.getLog(SettLaunchBiz.class);
	@Autowired
	private SettQueryBiz settQueryBiz;
	@Autowired
	private SettBiz settBiz;
	@Autowired
	private FeeBiz feeBiz;
	@Autowired
	private AccountBiz accountBiz;
	@Autowired
	private SettErrorRecordBiz settErrorRecordBiz;
	@Autowired
	private SettCalculateBiz settCalculateBiz;
	@Autowired
	private SettRuleDao settRuleDao;
	@Autowired
	private SettDailyCollectDao settDailyCollectDao;
	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private TradeLimitBiz tradeLimitBiz;
	@Autowired
	private SettCheckBiz settCheckBiz;
	@Autowired
	private SettAmountCheckBiz settAmountCheckBiz;
	@Autowired
	private RemitBiz remitBiz;
	@Autowired
	private SettRecordDao settRecordDao;

	/**
	 * @描述: 结算发起核心方法.
	 * @作者: Along.shen .
	 * @创建时间:2015-3-11,下午3:20:38.
	 * @版本:
	 */
	@Transactional(rollbackFor = { Exception.class })
	public SettRecord launchSettle(LaunchSettleVo launchSettleVo, BigDecimal balance, boolean isSelfHelpSettle) {
		long startTime = System.currentTimeMillis();
		log.info("===============开始时间============" + startTime + "================================");

		/** step1： 查出商户的可用余额 **/
		balance = accountBiz.getAccountBalance(launchSettleVo, balance);

		/** step2： 获取结算配置信息 **/
		SettRule settleRule = querySettRule(launchSettleVo);
		log.info("===>(" + settleRule.getUserName() + "),开始结算。");

		/** step3： 验证是否符合结算规则 **/
		canLaunchSettle(launchSettleVo);

		/** step4： 封装settRecord **/
		SettRecord record = settBiz.populateSettRecord(launchSettleVo);

		/** step5： 统计汇总记录 **/
		List<SettDailyCollect> totalDailys = calculateSettleAmount(record);

		/** step6： 结算金额和账户余额比对 **/
		Long lastId = checkAccountAmount(record, balance, totalDailys);

		/** step7： 限制包结算金额的限制 **/
		tradeLimitBiz.validateTradeAmount(LimitTrxTypeEnum.SETTLEMENT, null, null, null, record.getSettAmount(), settleRule.getUserNo());

		/** step8： 结算处理，计算手续费 ,创建settRecord **/
		settCalculateBiz.doProcessSettle(totalDailys, record, settleRule);

		/** step9： 更新所有的待汇总记录 **/
		settBiz.updateDailyCollectList(record.getDailys(), record.getBatchNo(), isSelfHelpSettle);

		/** step10：验证风控 **/

		/** step11：验证结算金额 **/
		settAmountCheckBiz.checkSettAmount(record);

		/** step12：确认结算 **/
		confirmSettleProcess(launchSettleVo, isSelfHelpSettle, record, lastId);

		/** step14：把多个计费订单一起保存 **/
		for (final FeeCalculateResultDTO calculateResult : record.getCalculateResultDTOList()) {
			feeBiz.caculate(calculateResult);
		}

		log.debug("============程序运行时间： " + (System.currentTimeMillis() - startTime) + "ms==============");
		return record;
	}

	/** 结算确认 **/
	private void confirmSettleProcess(LaunchSettleVo launchSettleVo, boolean isSelfHelpSettle, final SettRecord record, long lastId) {
		if (isSelfHelpSettle) {

			/** step1： 更新结算记录 **/
			settCheckBiz.autoConfirmSettle(record);

			/** step2： 账户处理 **/
			accountBiz.settCreate(record, lastId);

			/** step3：发起出款 **/
			remitBiz.launchRemit(record);

		} else {
			/** step1： 更新结算规则 **/
			updateSettRule(record);

			/** step2： 账户处理 **/
			accountBiz.settCreate(record, lastId);
		}
	}

	/**
	 * 修改结算规则
	 */
	private void updateSettRule(SettRecord settRecord) {
		//update settRecord
		settRecord.setVersion(0);
		settRecordDao.update(settRecord);
		// update settRule
		SettRule settRule = settQueryBiz.getSettRuleByAccountNo(settRecord.getAccountNo());
		if (settRule.getCurrentSettStatus() == SettRuleStatusEnum.SETTLING.getValue()) {
			throw new SettBizException(SettBizException.SETTLE_ACCOUNT_SETTLEING, "账户在结算中");
		}
		// 更新账户结算规则状态为结算中
		settRule.setCurrentSettStatus(SettRuleStatusEnum.SETTLING.getValue());
		settRule.setLastBatchNo(settRecord.getBatchNo());
		settRuleDao.update(settRule);
	}

	/**
	 * 查询结算规则
	 * 
	 * @param accountNo
	 * @return
	 */
	private SettRule querySettRule(LaunchSettleVo settleParamVo) {
		// 获取结算配置信息
		if (settleParamVo.getSettRule() == null) {
			SettRule settRule = settQueryBiz.getSettRuleByAccountNo(settleParamVo.getAccountNo());
			if (settRule == null) {
				throw new SettBizException(SettBizException.SETT_RULE_NOT_EXIST, "结算账户的结算规则不存在，不允许结算.");
			}
			settleParamVo.setSettRule(settRule);
			return settRule;
		}
		return settleParamVo.getSettRule();
	}

	/**
	 * 验证是否符合结算规则
	 * 
	 * @param settRule
	 * @param settleParamVo
	 */
	private void canLaunchSettle(LaunchSettleVo settleParamVo) {

		// 判断结算银行账户信息
		if (settleParamVo.getBankAccount() == null) {
			throw new SettBizException(SettBizException.SETT_BANK_ACCOUNT_NOT_EXIT, "账户,结算银行信息不存在");
		}

		// 商户是否允许结算
		SettRule settRule = settleParamVo.getSettRule();

		// 是否可结算
		if (settRule.getIsSett() == PublicStatusEnum.INACTIVE.getValue()) {
			// 更新结算失败日志
			settErrorRecordBiz.addSettErrorRecord("账户不可结算.", settleParamVo);
			throw new SettBizException(SettBizException.SETT_RULE_NOTSETTLE, "账户,结算规则为不可结算");
		}
		// 当前结算状态
		if (settRule.getCurrentSettStatus() == (SettRuleStatusEnum.SETTLING.getValue())) {
			// 结算中， 更新结算失败日志SETTLE_ACCOUNT_SETTLEING
			settErrorRecordBiz.addSettErrorRecord("账户在结算中.", settleParamVo);
			throw new SettBizException(SettBizException.SETTLE_ACCOUNT_SETTLEING, "账户在结算中");
		}
	}

	/**
	 * 
	 * @return
	 * @描述: 统计所有的汇总记录.
	 * @作者: Along.shen .
	 * @创建时间:2015-2-4,上午11:12:45.
	 * @版本:
	 */
	private List<SettDailyCollect> calculateSettleAmount(SettRecord record) {
		List<SettDailyCollect> totalDailys = new ArrayList<SettDailyCollect>();
		// 得到汇总记录list----查出所有的汇总记录
		List<SettDailyCollect> queryDailys = settDailyCollectDao.listUnSettDailyCollectByAccountNo(record.getAccountNo(), null, null);
		for (SettDailyCollect daily : queryDailys) {
			totalDailys.add(daily);
			record.addTradeAmount(daily.getTotalAmount(), daily.getTotalCount());
		}
		if (record.getTradeAmount().compareTo(BigDecimal.ZERO) != 1) {
			throw new SettBizException(SettBizException.SETTAMONT_LESS_THAN_ZERO, record.getUserName() + "的结算金额不足一分钱,不可以结算");
		}
		return totalDailys;
	}

	/**
	 * 检查 结算金额 是否大于 账户余额<br>
	 * 若 大于余额，则进行下列步骤<br>
	 * 检查帐务历史，是否存在退款的记录。
	 * 
	 * @param record
	 * @param totalDailys
	 * @return
	 */
	private Long checkAccountAmount(SettRecord record, BigDecimal balance, List<SettDailyCollect> totalDailys) {

		log.info("===>结算条件,accountNo:" + record.getAccountNo() + ",settleStartDate:" + record.getBeginDate() + ",settleEndDate" + record.getEndDate() + " ,settAmount:" + record.getSettAmount() + " ,balance:" + balance);

		if (record.getSettAmount().compareTo(BigDecimal.ZERO) <= 0)
			return 0L;
		log.info("结算金额：" + record.getSettAmount() + ",可结余额：" + balance);
		if (record.getSettAmount().compareTo(balance) <= 0) {
			return 0L;
		}

		// 计算该账户的当日的退款或消费金额(账务历史金额流向为321)
		Long lastId = calculateCurrentDateRefundAmount(record, balance, totalDailys);
		return lastId;
	}

	/**
	 * 计算该账户的当日的退款或消费金额
	 * 
	 * @param record
	 *            结算记录
	 * @param balance
	 *            可用余额
	 * @param totalDailys
	 *            每日汇总记录
	 * @return
	 */
	private Long calculateCurrentDateRefundAmount(SettRecord record, BigDecimal balance, List<SettDailyCollect> totalDailys) {

		// 根据账户编号获取账户历史(资金流向为321)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		List<DailyCollectAccountHistoryVo> dailyAccountHistoryList = accountQueryFacade.listDailyCollectAccountHistoryVo(record.getAccountNo(), date, 0, 321);

		if (dailyAccountHistoryList == null || dailyAccountHistoryList.isEmpty()) {
			settErrorRecordBiz.addSettErrorRecord("结算金额大于账户余额.", record);
			throw new SettBizException(SettBizException.SETTLE_AMOUNT_ACCOUNT_CHECKERR, " 结算金额大于账户余额【结算金额：" + record.getSettAmount() + "-余额：" + balance + "】.");
		}

		// 把账户历史里面金额方向为321统计到一个临时的汇总记录中
		SettDailyCollect temp = popluateDailyCollectVo(dailyAccountHistoryList);
		// 累加到结算记录中
		record.addTradeAmount(temp.getTotalAmount(), temp.getTotalCount());

		log.info("结算金额变更为：" + record.getSettAmount() + ",需结算金额：" + balance);

		if (record.getSettAmount().compareTo(balance) <= 0) {
			settBiz.popluateTempDailyCollect(record, temp);
			totalDailys.add(temp);
		} else {
			// 结算金额大于账户余额，不能结算
			settErrorRecordBiz.addSettErrorRecord("结算金额大于账户余额.", record);
			throw new SettBizException(SettBizException.SETTLE_AMOUNT_ACCOUNT_CHECKERR, "结算金额大于账户余额【结算金额：" + record.getSettAmount() + "-余额：" + balance + "】.");
		}
		return dailyAccountHistoryList.get(0).getLastId();
	}

	/**
	 * 填充 当日待计算汇总
	 * 
	 * @param qryHistorys
	 * @return
	 */
	private SettDailyCollect popluateDailyCollectVo(List<DailyCollectAccountHistoryVo> qryHistorys) {
		SettDailyCollect collectVo = new SettDailyCollect();
		for (DailyCollectAccountHistoryVo history : qryHistorys) {

			collectVo.setTotalCount(history.getTotalNum());
			collectVo.setTotalAmount(BigDecimal.valueOf(history.getTotalAmount()));
		}
		return collectVo;
	}

	/**
	 * 查询结算金额 getSettAmount()
	 * 
	 * @param launchSettleVo
	 * @param balance
	 * @return
	 * @since 1.0
	 */
	public SettRecord getSettAmount(LaunchSettleVo settleVo, BigDecimal balance) {
		// 获取一段日期内结算金额
		SettRecord record = new SettRecord();
		record.setBeginDate(settleVo.getSettRule().getLastSettDate());
		record.setEndDate(settleVo.getSettleEndDate());
		record.setAccountNo(settleVo.getSettRule().getAccountNo());
		record.setUserNo(settleVo.getSettRule().getUserNo());

		List<SettDailyCollect> totalDailys = new ArrayList<SettDailyCollect>();
		// 得到汇总记录list
		List<SettDailyCollect> queryDailys = settDailyCollectDao.listUnSettDailyCollectByAccountNo(record.getAccountNo(), null, null);
		for (SettDailyCollect daily : queryDailys) {
			totalDailys.add(daily);
			record.addTradeAmount(daily.getTotalAmount(), daily.getTotalCount());
		}
		// 可结算金额不大于0
		if (record.getTradeAmount().compareTo(BigDecimal.ZERO) != 1) {
			return record;
		}

		log.info("结算条件,accountNo:" + record.getAccountNo() + ",settleStartDate:" + record.getBeginDate() + ",settleEndDate" + record.getEndDate() + " ,settAmount:" + record.getSettAmount());

		// 结算金额和账户余额比对(如果结算金额比可用余额大这继续查找累加退款或者消费的金额)
		if (record.getSettAmount().compareTo(balance) > 0) {
			// 判断结算金额和可用余额
			if (record.getSettAmount().compareTo(balance) > 0) {
				// 计算该账户的当日的退款或消费金额(账务历史金额流向为321)
				calculateCurrentDateRefundAmount(record, balance, totalDailys);
			}
		}
		return record;
	}

}
