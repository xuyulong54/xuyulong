package wusc.edu.pay.core.boss.dao.impl; 

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.boss.dao.SalesDao;
import wusc.edu.pay.facade.boss.entity.Sales;


/**
 *类描述：销售人员Dao实现类
 *@author: huangbin
 *@date： 日期：2013-11-25 时间：下午5:20:06
 *@version 1.0
 */
@Repository("salesDao")
public class SalesDaoImpl extends BaseDaoImpl<Sales> implements SalesDao {

	/***
	 * 根据条件查询列表
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByCondition(Map<String, Object> paramMap) {
		
		return super.listBy(paramMap);
	}
	
	/**
	 * 生成销售人员编号
	 * 
	 * @param bizType
	 * @return
	 */
	@Override
	public String buildSalesNo(){
		return super.getSeqNextValue("BOSS_SALES_NO_SEQ");
	}
}
 