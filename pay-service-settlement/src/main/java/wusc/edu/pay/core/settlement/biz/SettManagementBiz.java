package wusc.edu.pay.core.settlement.biz;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.settlement.dao.SettControlDao;
import wusc.edu.pay.core.settlement.dao.SettDayAdjustSettingDao;
import wusc.edu.pay.core.settlement.dao.SettHolidaySettingDao;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.core.settlement.dao.SettRuleDao;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.entity.SettDayAdjustSetting;
import wusc.edu.pay.facade.settlement.entity.SettHolidaySetting;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettTypeEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;


/**
 * 
 * @描述: 结算管理业务逻辑实现 .
 * @作者: WuShuicheng.
 * @创建: 2014-9-19,上午11:38:22
 * @版本: V1.0
 *
 */
@Component("settManagementBiz")
public class SettManagementBiz {

	@Autowired
	private SettRuleDao settRuleDao;
	@Autowired
	private SettHolidaySettingDao settHolidaySettingDao;
	@Autowired
	private SettDayAdjustSettingDao settDayAdjustSettingDao;
	@Autowired
	private SettControlDao settControlDao;
	@Autowired
	private SettRecordDao settRecordDao;
	@Autowired
	private SettHandleBiz settHandleBiz;
	@Autowired
	private SettCheckBiz settCheckBiz;

	private static final Log log = LogFactory.getLog(SettManagementBiz.class);

	/**
	 * 创建结算规则<br>
	 */
	public long createSettlementRule(SettRule param) {

		// 验证是否存在对应的，一个结算账户唯一对应一个结算规则
		SettRule settRule = settRuleDao.getSettRuleByAccountNo(param.getAccountNo());
		log.info("The account number of settle rule is : " + param.getAccountNo());

		if (null != settRule) {
			throw new SettBizException(SettBizException.SETTLEMENT_RULE_SINGLE_ACCOUNT, "账号：{0}已设置结算规则！", param.getAccountNo());
		}

		// 验证结算规则中相关参数
		this.validatorSettleParam(param);

		// 根据结算规则中周期设置来计算下次结算处理日期
		if (SettTypeEnum.AUTO_SETTLEMENT.getValue() == param.getSettType()) {
			param.setNextProcessDate(param.calculateNextSettleDay(new Date()));
		}else{
			param.setNextProcessDate(new Date());
		}
		// 初次创建结算规则，上次结算截止日期默认为新建日期的前一天
		if (null != param.getLastSettDate()) {
			param.setLastSettDate(DateUtils.addDay(param.getLastSettDate(), -1));
		} else {
			param.setLastSettDate(DateUtils.addDay(new Date(), -1));
			param.setLastSumDate(DateUtils.addDay(new Date(), -7));
		}
		return settRuleDao.insert(param);
	}

	/**
	 * 修改建结算规则<br>
	 */
	public void updateSettRule(SettRule param) {

		// 验证结算规则中数据限制
		this.validatorSettleParam(param);

//		// 结算规则修改，当修改了结算周期、结算数据时，需要修改下次结算处理时间
//		if (param.getSettType() != null) {
//			if (SettTypeEnum.AUTO_SETTLEMENT.getValue() == param.getSettType()) {
//				param.setNextProcessDate(param.calculateNextSettleDay(new Date()));
//			}
//			// 手动结算，结算周期和结算周期数据为null
//			else {
//				param.setSettCycleType(null);
//				param.setSettCycleData(null);
//			}
//		}
		settRuleDao.update(param);
	}

	/**
	 * 创建不可结算日（节假日），该日内不做结算
	 * 
	 * @param startDate
	 *            不可结算日开始日期
	 * @param endDate
	 *            不可结算日结束日期
	 * @param desc
	 *            对不可结算日的描述信息
	 */
	public void addSettHoliday(Date startDate, Date endDate, String desc) {
		SettHolidaySetting settHoliday = new SettHolidaySetting();

		log.info("Add settlement holiday, start date is : " + startDate + ", end date is : " + endDate);

		// 节假日区间，startDate < endDate, 一次新增多条节假日记录
		if (DateUtils.dateFormat(startDate).before(DateUtils.dateFormat(endDate))) {
			log.info("Add multiple settlement holiday.");
			int i = 1;
//			SettDayAdjustSetting settDayAdjustSetting = null;
			List<SettDayAdjustSetting> list = null;

			while (!startDate.after(endDate)) {
				// 查询startDate = 结算日调整记录中结算日的记录(startDate = oldDate)
//				settDayAdjustSetting = settDayAdjustSettingDao.querySettDayAdjustSettingBySettleDay(startDate);
				// 查询startDate = 结算日调整记录中调整到结算日的记录(startDate = newDate)
				list = settDayAdjustSettingDao.queryAllSettDayAdjustSettingBySettleDay(startDate);
				
				// 查询startDate = 节假日的记录(startDate = holiday)
				if(settHolidaySettingDao.getByHoliday(startDate) != null){
					startDate = DateUtils.addDay(startDate, 1);
					i++;
					continue;
				}
				

				// 如果已设置为结算日调整到日期，则该天不可设置为节假日
//				if (settDayAdjustSetting != null || (null != list && !list.isEmpty())) {
				if (null != list && !list.isEmpty()) {
					throw new SettBizException(SettBizException.SETTLEMENT_DAY_ADJUST_EXIST, "已设置为结算日调整记录，不可设置为不可结算日！");
				} else {
					settHoliday.setHoliday(startDate);
					settHoliday.setRemark(desc + "第" + i + "天");

					settHolidaySettingDao.insert(settHoliday);
				}
				startDate = DateUtils.addDay(startDate, 1);
				log.info("Start date after add is : " + startDate);
				i++;
			}
		}
		// 新增单条节假日记录
		else if (DateUtils.dateFormat(startDate).equals(DateUtils.dateFormat(endDate))) {
			log.info("Add single settlement holiday");
			SettDayAdjustSetting SettDayAdjustSetting = settDayAdjustSettingDao.querySettDayAdjustSettingBySettleDay(startDate);
			List<SettDayAdjustSetting> list = settDayAdjustSettingDao.queryAllSettDayAdjustSettingBySettleDay(startDate);

			// 如果已设置为结算日调整到日期，则该天不可设置为节假日
			if (SettDayAdjustSetting != null || (list != null && !list.isEmpty())) {
				throw new SettBizException(SettBizException.SETTLEMENT_DAY_ADJUST_EXIST, "已设置为结算日调整记录，不可设置为不可结算日！");
			} else {
				settHoliday.setHoliday(startDate);
				settHoliday.setRemark(desc);
				settHolidaySettingDao.insert(settHoliday);
			}
		}
		// 结假日截止日期不能在开始日期之前，否则为非法
		else {
			throw new SettBizException(SettBizException.SETTLEMENT_HOLIDAY_ERROR_END, "不可结算日开始日期必须在截止日期之前！");
		}

	}

	/**
	 * 删除结算日调整
	 * 
	 * @param String
	 *            [] ids
	 */
	public void delSettlementHoliday(String[] ids) {
		Long holidayId;
		for (int i = 0; i < ids.length; i++) {
			holidayId = Long.parseLong(ids[i]);
			log.info("Holiday id is : " + holidayId);
			settHolidaySettingDao.deleteById(holidayId);
		}

	}

	/**
	 * 增加结算日调整。如国庆假、春节假休息，需要调整工作日，导致周六或周日工作
	 * 
	 * @param settleDay
	 *            结算日
	 * @param toDay
	 *            调整后的结算日
	 * @param desc
	 *            描述
	 */
	public void addSettlementAdjustDay(Date settleDay, Date toDay, String desc) {

		log.info("Add settlement day adjust, settle date is : " + settleDay + ", to date is : " + toDay);
		Calendar calendar = Calendar.getInstance();
		// 结算日和结算日调整到日期不能为同一天判断
		if (DateUtils.dateFormat(settleDay).equals(DateUtils.dateFormat(toDay))) {
			throw new SettBizException(SettBizException.SETTLEMENT_DAY_ADJUST_DAY_SAME_ERROR, "结算日和结算日调整到日期不能为同一天！");
		} else if (!DateUtils.dateFormat(toDay).after(DateUtils.dateFormat(calendar.getTime()))) {
			throw new SettBizException(SettBizException.SETTLEMENT_DAY_ADJUST_DAY_LESS_CURRENTDAY, "结算日调整到日期必须在当前日期之后！");
		}

		// 如果结算日调整到日期为不可结算日，则不能进行此设置; 结算日调整记录中的结算日不能存在于不可结算记录中
		SettHolidaySetting settHolidaySetting = settHolidaySettingDao.getByHoliday(toDay);
//		SettHolidaySetting noSettle = settHolidaySettingDao.getByHoliday(settleDay);
//		if (null != settHolidaySetting || null != noSettle) {
		if (null != settHolidaySetting) {
			throw new SettBizException(SettBizException.SETTLEMENT_HOLIDAY_SETTING_EXIST, "已设置为不可结算日，不能进行此设置！");
		}

		// 结算日在结算日调整表中唯一存在
		SettDayAdjustSetting settDayAdjustSetting = settDayAdjustSettingDao.querySettDayAdjustSettingBySettleDay(settleDay);
		if (null != settDayAdjustSetting) {
			throw new SettBizException(SettBizException.SETTLEMENT_DAY_ADJUST_SINGLE, "已进行了结算日调整！");
		}

		// 新增满足条件的结算日调整日期
		SettDayAdjustSetting settlementDayAdjust = new SettDayAdjustSetting();
		settlementDayAdjust.setOldDate(settleDay);
		settlementDayAdjust.setNewDate(toDay);
		settlementDayAdjust.setRemark(desc);

		settDayAdjustSettingDao.insert(settlementDayAdjust);

	}

	/**
	 * 删除结算日调整
	 * 
	 * @param String
	 *            [] ids
	 */
	public void delAdjustSettlementDay(String[] ids) {
		Long dayAdjust;
		for (int i = 0; i < ids.length; i++) {
			dayAdjust = Long.parseLong(ids[i]);
			log.info("Delete settlement day adjust, id is : " + dayAdjust);
			settDayAdjustSettingDao.deleteById(dayAdjust);
		}
	}

	/**
	 * 修改结算系统控制数据，只修改每日汇总时间段的起止时间
	 * 
	 * @param param
	 */
	public void updateSettControl(SettControl param) {
		settControlDao.update(param);
	}

	/**
	 * 验证结算规则中的部分限制参数
	 * 
	 * @param param
	 */
	private void validatorSettleParam(SettRule param) {
		// 风险预存期
		if (param.getRiskDay() != null) {
			if (param.getRiskDay().longValue() < 0) {
				throw new SettBizException(SettBizException.SETTLEMENT_RULE_RISK_DAYS_LESS_ZERO, "结算规则风险预存期不能小于0！");
			}
		}
		// 结算周期
		if (param.getSettType() != null) {
			if (param.getSettType().equals(SettTypeEnum.AUTO_SETTLEMENT)) {
				if (null == param.getSettCycleType()) {
					throw new SettBizException(SettBizException.SETTLEMENT_RULE_CYCLE_TYPE_ILLEGAL, "定期结算，结算周期类型不能为空！");
				} else if (null == param.getSettCycleData() || "".equals(param.getSettCycleData())) {
					throw new SettBizException(SettBizException.SETTLEMENT_RULE_CYCLE_DATA_ILLEGAL, "定期结算，结算周期数据不能为空！");
				}
			}
		}

	}


	public void createSettControl(SettControl sc) {
		settControlDao.insert(sc);
	}

	/**
	 * 根据批次号和账户编号
	 * 更新结算状态和备注
	 * @param remark 
	 * @param settStatus 
	 * @param accountNo 
	 * @param batchNo 
	 */
	public void updateSettRecordByBatchNoAccountNo(String batchNo, String accountNo, Integer settStatus, String remark) {
		SettRecord param = settRecordDao.getSettRecordByBatchNoAndAccountNo(batchNo, accountNo);
		//确认结算
		if(settStatus == SettRecordStatusEnum.CONFIRMED.getValue()){
			settCheckBiz.confirmSettleProcess(param);
		}
		//取消结算
		else if(settStatus == SettRecordStatusEnum.CANCEL.getValue()){
			settHandleBiz.cancelSettleProcess(param);
		}
	}

	/***
	 * 重新绑定商户编号
	 * @param accountNo 旧商户编号
	 * @param newMerchNo 新商户编号
	 * @return
	 */
	public long reBindUserNo(String accountNo, String newMerchNo) {
		SettRule settRule = settRuleDao.getSettRuleByAccountNo(accountNo);
		if(settRule == null){
			log.error(" settRule info is null , accountNo is : " + accountNo);
			throw new SettBizException(SettBizException.SETT_RULE_NOT_EXIST, "账户结算信息为空.");
		}
		settRule.setUserNo(newMerchNo);
		settRule.setLastModifyTime(new Date());
		
		long ruleId = settRuleDao.update(settRule);
		
		log.info("==> change SettRule's UserNo, accountNo:" + accountNo + ", userNo:" + newMerchNo);
		
		return ruleId;
	}

}
