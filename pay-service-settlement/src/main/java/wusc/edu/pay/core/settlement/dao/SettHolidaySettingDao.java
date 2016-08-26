package wusc.edu.pay.core.settlement.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.settlement.entity.SettHolidaySetting;


/**
 * 
 * @描述: 节假日设置表数据访问层接口.
 * @作者: WuShuicheng.
 * @创建: 2014-8-12,上午10:20:41
 * @版本: V1.0
 *
 */
public interface SettHolidaySettingDao extends BaseDao<SettHolidaySetting> {

	/**
	 * 根据日期查找结算节假日对象
	 * @param holiday
	 * @return
	 */
	SettHolidaySetting getByHoliday(Date holiday);


}
