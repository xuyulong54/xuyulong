package wusc.edu.pay.core.settlement.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.settlement.dao.SettDayAdjustSettingDao;
import wusc.edu.pay.facade.settlement.entity.SettDayAdjustSetting;


@Repository("settDayAdjustSettingDao")
public class SettDayAdjustSettingDaoImpl extends BaseDaoImpl<SettDayAdjustSetting> implements SettDayAdjustSettingDao {
	/**
	 * 结算日调整记录中结算日(oldDate)的记录
	 * @param startDate
	 * @return
	 */
	public SettDayAdjustSetting querySettDayAdjustSettingBySettleDay(Date startDate){
		Map<String, Object>  paramMap = new HashMap<String, Object>();
		paramMap.put("oldDate", startDate);
		return super.getBy(paramMap);
	}

	/**
	 * 结算日调整记录中调整到结算日(newDate)的记录
	 * @param startDate
	 * @return
	 */
	public List<SettDayAdjustSetting> queryAllSettDayAdjustSettingBySettleDay(Date startDate){
		Map<String, Object>  paramMap = new HashMap<String, Object>();
		paramMap.put("newDate", startDate);
		return super.listBy(paramMap);
	}

	/**
	 * 根据原日期获取记录.<br/>
	 * @param oldDate 原日期.<br/>
	 * @return SettDayAdjustSetting .
	 */
	@Override
	public SettDayAdjustSetting getByOldDate(Date oldDate) {
		Map<String, Object>  paramMap = new HashMap<String, Object>();
		paramMap.put("oldDate", oldDate);
		return super.getBy(paramMap);
	}
}
;