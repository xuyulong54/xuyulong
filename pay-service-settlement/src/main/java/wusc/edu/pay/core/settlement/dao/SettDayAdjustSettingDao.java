package wusc.edu.pay.core.settlement.dao;

import java.util.Date;
import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.settlement.entity.SettDayAdjustSetting;


public interface SettDayAdjustSettingDao extends BaseDao<SettDayAdjustSetting> {

	/**
	 * 结算日调整记录中结算日(oldDate)的记录
	 * @param startDate
	 * @return
	 */
	SettDayAdjustSetting querySettDayAdjustSettingBySettleDay(Date startDate);

	/**
	 * 结算日调整记录中调整到结算日(newDate)的记录
	 * @param startDate
	 * @return
	 */
	List<SettDayAdjustSetting> queryAllSettDayAdjustSettingBySettleDay(Date startDate);

	/**
	 * 根据原日期获取记录.<br/>
	 * @param oldDate 原日期.<br/>
	 * @return SettDayAdjustSetting .
	 */
	SettDayAdjustSetting getByOldDate(Date oldDate);


}
