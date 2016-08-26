package wusc.edu.pay.core.settlement.biz;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.settlement.biz.sub.RemitBiz;
import wusc.edu.pay.core.settlement.dao.SettDailyCollectDao;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.core.settlement.dao.SettRuleDao;
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
 * 
 * @描述: 结算审核业务Biz.
 * @作者: Along.shen .
 * @创建时间:2015-3-17,下午1:54:44.
 * @版本:
 */
@Component("settCheckBiz")
public class SettCheckBiz {

	@Autowired
	private SettDailyCollectDao settDailyCollectDao;
	@Autowired
	private SettRuleDao settRuleDao;
	@Autowired
	private SettRecordDao settRecordDao;
	@Autowired
	private RemitBiz remitBiz;
	@Autowired
	private SettHandleBiz settHandleBiz;

	private static final Log log = LogFactory.getLog(SettCheckBiz.class);

	/**
	 * * 结算处理:确认结算并发起打款 <br/>
	 * 只有待确认状态的结算记录才能进行操作<br/>
	 * 账户冻结资金，发起打款等
	 * 
	 * @param settRecord
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
	public void confirmSettleProcess(SettRecord settRecord) {

		// 结算确认,修改为已确认
		final SettRecord record = settRecord;
		if (!record.isCanConfirmProcess()) {
			log.error("结算记录状态不是待确认，不能进行结算处理");
			throw new SettBizException(SettBizException.SETTLE_STATUS_ERROR, record.getBatchNo() + "结算记录状态不是待确认，不能进行结算处理");
		}
		// 更新结算状态为
		record.setSettStatus(SettRecordStatusEnum.REMITTING.getValue());
		record.setRemitConfirmTime(new Date());
		record.setRemitRequestTime(new Date());
		record.setRemark("确认结算");
		settRecordDao.update(record);

		// 修改结算规则信息
		processSettleRule(record);

		// 根据批次号，更新每日汇总状态为已结算
		settHandleBiz.updateDailyCollectStatus(record.getBatchNo(), SettDailyCollectStatusEnum.SETTLLED.getValue());
		// 发起打款
		remitBiz.launchRemit(settRecord);
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

	/** 自动结算确认 **/
	public void autoConfirmSettle(SettRecord settRecord) {
		// 更新结算状态为
		settRecord.setVersion(0);
		settRecord.setSettStatus(SettRecordStatusEnum.REMITTING.getValue());
		settRecord.setRemitConfirmTime(new Date());
		settRecord.setRemitRequestTime(new Date());
		settRecord.setRemark("确认结算");
		settRecordDao.update(settRecord);
		// 修改结算规则信息
		processSettleRule(settRecord);
		// 根据批次号，更新每日汇总状态为已结算
		settHandleBiz.updateDailyCollectStatus(settRecord.getBatchNo(), SettDailyCollectStatusEnum.SETTLLED.getValue());
	}

}
