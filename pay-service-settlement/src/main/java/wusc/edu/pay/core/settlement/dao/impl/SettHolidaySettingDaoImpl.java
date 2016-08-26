package wusc.edu.pay.core.settlement.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.settlement.dao.SettHolidaySettingDao;
import wusc.edu.pay.facade.settlement.entity.SettHolidaySetting;


/**
 * 
 * @描述: 节假日设置表数据访问层实现.
 * @作者: WuShuicheng.
 * @创建: 2014-8-12,上午10:20:01
 * @版本: V1.0
 *
 */
@Repository("settHolidaySettingDao")
public class SettHolidaySettingDaoImpl extends BaseDaoImpl<SettHolidaySetting> implements SettHolidaySettingDao {

	/**
	 * 根据日期查找结算节假日对象
	 * @param holiday
	 * @return
	 */
	public SettHolidaySetting getByHoliday(Date holiday){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("holiday", holiday);
		return this.getBy(paramMap);
	}

}
