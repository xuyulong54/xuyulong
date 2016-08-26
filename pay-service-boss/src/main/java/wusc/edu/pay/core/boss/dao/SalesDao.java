package wusc.edu.pay.core.boss.dao; 

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.boss.entity.Sales;


/**
 *类描述：销售人员Dao接口
 *@author: huangbin
 *@date： 日期：2013-11-25 时间：下午5:18:30
 *@version 1.0
 */
public interface SalesDao extends BaseDao<Sales> {

	/***
	 * 根据条件查询列表
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List listByCondition(Map<String, Object> paramMap);
	
	/**
	 * 生成销售人员编号
	 * 
	 * @param bizType
	 * @return
	 */
	String buildSalesNo();

}
 