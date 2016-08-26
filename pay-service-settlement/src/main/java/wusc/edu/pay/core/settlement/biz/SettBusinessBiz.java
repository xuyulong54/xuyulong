package wusc.edu.pay.core.settlement.biz;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.settlement.biz.sub.AccountBiz;
import wusc.edu.pay.core.settlement.biz.sub.RemitBiz;
import wusc.edu.pay.core.settlement.dao.SettControlDao;
import wusc.edu.pay.core.settlement.dao.SettDailyCollectDao;
import wusc.edu.pay.core.settlement.dao.SettDayAdjustSettingDao;
import wusc.edu.pay.core.settlement.dao.SettHolidaySettingDao;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.account.vo.DailyCollectAccountHistoryVo;
import wusc.edu.pay.facade.fee.service.FeeManagerFacade;
import wusc.edu.pay.facade.remit.enums.RemitUrgentEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.enums.TradeTypeEnum;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettDayAdjustSetting;
import wusc.edu.pay.facade.settlement.entity.SettHolidaySetting;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;


/**
 * 
 * @描述: 常规业务设置biz.
 * @作者: Along.shen .
 * @创建时间:2015-3-17,下午2:06:00.
 * @版本:
 */
@Component("settBusinessBiz")
public class SettBusinessBiz {

	@Autowired
	private SettControlDao settControlDao;
	@Autowired
	private SettDayAdjustSettingDao settDayAdjustSettingDao;
	@Autowired
	private SettHandleBiz settHandleBiz;
	@Autowired
	private SettRecordDao settRecordDao;
	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private SettDailyCollectDao settDailyCollectDao;
	@Autowired
	private SettManagementBiz settManagementBiz;
	@Autowired
	private FeeManagerFacade feeManagerFacade;
	@Autowired
	private AccountBiz accountBiz;
	@Autowired
	private RemitBiz remitBiz;
	@Autowired
	private SettHolidaySettingDao settHolidaySettingDao;

	private static final Log LOG = LogFactory.getLog(SettBusinessBiz.class);

	/**
	 * 判断是否可以发起结算、汇总(查询结算控制表).<br/>
	 * 
	 * @param settModeTypeEnum
	 * @param nowDateTime
	 * @return
	 */
	public boolean isSettModeLaunchable(SettModeTypeEnum settModeTypeEnum, Date nowDateTime) {

		SettControl settControl = settControlDao.getBySettModeType(settModeTypeEnum);
		if (settControl == null) {
			LOG.error("===>(" + settModeTypeEnum.getDesc() + ")的结算控制设置为空，不能执行自动结算");
			return true;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			Date nowTime = sdf.parse(sdf.format(nowDateTime));
			Date beginTime = settControl.getBeginTime();
			Date endTime = settControl.getEndTime();

			// 每日待结算汇总只有在时间区间内才可以操作
			if (SettModeTypeEnum.DAILY_COLLECT.equals(settModeTypeEnum)) {
				// 时间区间外，禁止进行每日待结算汇总
				if (nowTime.before(beginTime) || nowTime.after(endTime)) {
					return false;
				} else {
					return true;
				}

			} else {
				// 只有状态为每日待结算汇总之外，时间且在汇总时间区间段外才可以发起结算
				if (nowTime.before(beginTime) || nowTime.after(endTime)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			LOG.info(e.getMessage());
		}
		return false;
	}

	/**
	 * 判断该日期是否进行过结算日期调整（如:因节假日调整）.<br/>
	 * 
	 * @param date
	 *            日期.<br/>
	 * @return true or false.
	 */
	public boolean isSettDayAdjust(Date date) {
		LOG.info("===>检查今天是否为结算调整日:" + date);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date oldDate = new Date();
		try {
			oldDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			LOG.error("isSettDayAdjust method ParseException", e);
		}

		SettDayAdjustSetting settlementDayAdjust = settDayAdjustSettingDao.getByOldDate(oldDate);
		if (settlementDayAdjust != null) {
			// 结算日已进行了调整
			return true;
		}
		return false;
	}

	/**
	 * 创建结算记录.<br/>
	 * 
	 * @param settRecord
	 *            结算记录对象.<br/>
	 */
	public void createSettRecord(SettRecord settRecord) {
		settRecordDao.insert(settRecord);
	}

	/**
	 * 批量创建结算记录.<br/>
	 * 
	 * @param settRecordList
	 *            结算记录对象列表.<br/>
	 */
	public void createSettRecord(List<SettRecord> settRecordList) {
		for (SettRecord settRecord : settRecordList) {
			settRecordDao.insert(settRecord);
		}
	}

	/**
	 * 更新日汇总记录为已结算，并回写结算批次号（创建结算记录成功后调用）.<br/>
	 * 
	 * @param collectList
	 *            要更新的汇总记录（必填）.<br/>
	 * @param batchNo
	 *            结算批次号（必填）.<br/>
	 */
	public void updateSettDailyCollectToSetted(List<SettDailyCollect> collectList, String batchNo) {
		for (SettDailyCollect collect : collectList) {
			collect.setBatchNo(batchNo);
			collect.setSettStatus(SettDailyCollectStatusEnum.SETTLLED.getValue());
			settDailyCollectDao.update(collect);
		}
	}

	/**
	 * 会员提现
	 * 
	 * @param settRecord
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void memberWithdraw(SettRecord settRecord) {

		// 手续费暂时为0，上面代码为不为0的手续费
		settRecord.setSettFee(new BigDecimal(0)); // 结算手续费
		settRecord.setRemitAmount(settRecord.getSettAmount()); // 结算打款金额
		settRecord.setSettStatus(SettRecordStatusEnum.REMITTING.getValue());
		settRecordDao.insert(settRecord);

		// step.1 账户处理
		accountBiz.settCreate(settRecord, 0l);

		// step.2 异步请求打款
		remitBiz.requestRemit(settRecord, TradeSourcesEnum.MEMBER_CASH, TradeTypeEnum.MEMBER_CASH, RemitUrgentEnum.UN_URGENT);
	}

	/**
	 * 判断是否为节假日.<br/>
	 * 
	 * @param date
	 *            要验证的日期.<br/>
	 * @return true or false.
	 */
	public boolean isHoliday(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date oldDate = new Date();
		try {
			oldDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			LOG.error("isHoliday method ParseException", e);
		}

		SettHolidaySetting holiday = settHolidaySettingDao.getByHoliday(oldDate);
		if (holiday == null) {
			return false; // 非节假日
		}
		return true; // 节假日
	}

	/**
	 * 根据结算批次号，更新结算记录状态.<br/>
	 * 
	 * @param batchNo
	 * @param statusEnum
	 */
	public void updateSettRecordSettStatus(String batchNo, SettRecordStatusEnum statusEnum) {
		settRecordDao.updateSettStatusByBatchNo(batchNo, statusEnum);

	}

	/**
	 * 按单个商户的结算规则发起每日待结算数据统计汇总.<br/>
	 * 
	 * @param settRule
	 *            结算规则.
	 * @param endDate
	 *            汇总结束日期.
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void dailySettlementCollect(SettRule settRule, Date endDate) {

		String endDateStr = DateUtils.formatDate(endDate, "yyyy-MM-dd");
		List<DailyCollectAccountHistoryVo> accountHistoryList = accountQueryFacade.listDailyCollectAccountHistoryVo(settRule.getAccountNo(), endDateStr, settRule.getRiskDay(), null);
		for (DailyCollectAccountHistoryVo collectVo : accountHistoryList) {

			// 保存账户历史日汇总记录
			SettDailyCollect dailyCollect = new SettDailyCollect();
			dailyCollect.setAccountNo(collectVo.getAccountNo());
			dailyCollect.setCollectDate(collectVo.getCollectDate());
			dailyCollect.setCollectType(SettDailyCollectTypeEnum.ALL.getValue()); // TODO
			dailyCollect.setTotalAmount(BigDecimal.valueOf(collectVo.getTotalAmount()));
			dailyCollect.setTotalCount(collectVo.getTotalNum());
			dailyCollect.setSettStatus(SettDailyCollectStatusEnum.UNSETTLE.getValue());
			dailyCollect.setRiskDay(collectVo.getRiskDay());
			settDailyCollectDao.insert(dailyCollect);
		}

		// 更新结算规则
		settRule.setLastSumDate(endDate); // 上次汇总日期
		settManagementBiz.updateSettRule(settRule);

		// 更新账户历史中的结算状态
		accountBiz.settCollectSuccess(settRule.getUserNo(), endDateStr, settRule.getRiskDay());
	}

	/**
	 * 调接口把结算记录的状态设置为 REMIT_FAIL (打款确定失败)，并解冻资金 confirmFail()
	 * 
	 * @param settRecord
	 * @since 1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	public void confirmFail(SettRecord settRecord) {
		if (!settRecord.isCanResendRemit()) {
			throw new SettBizException(SettBizException.SETTLE_RESEND_ERROR, "结算记录不是失败退回状态，不能标记为结算失败");
		}
		settRecord.setSettStatus(SettRecordStatusEnum.REMIT_FAIL.getValue());// 改为失败
		settRecordDao.update(settRecord);
		if (settRecord.getUserType() != UserTypeEnum.CUSTOMER.getValue()) {
			// 对冲未结算遗留汇总
			hedgeLeaveDailyCollect(settRecord);
			settHandleBiz.updateDailyCollectStatus(settRecord.getBatchNo(), SettDailyCollectStatusEnum.UNSETTLE.getValue()); // 把结算每日汇总改成未结算
		}
		// 账户处理
		accountBiz.settFail(settRecord);

		// 删除计费订单
		if (settRecord.getUserType() != UserTypeEnum.CUSTOMER.getValue()) {
			this.deleteFeeOrder(settRecord.getBatchNo());
		}
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
	 * 删除计费订单（结算特有，只有结算业务可以调用）
	 * 
	 * @param trxFlowNo
	 *            流水号
	 */
	public void deleteFeeOrder(String trxFlowNo) {
		feeManagerFacade.deleteFeeOrder(trxFlowNo);
	}

}
