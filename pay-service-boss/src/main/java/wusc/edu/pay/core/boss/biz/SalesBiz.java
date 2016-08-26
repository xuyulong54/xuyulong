package wusc.edu.pay.core.boss.biz; 

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.boss.dao.SalesDao;
import wusc.edu.pay.facade.boss.entity.Sales;


/**
 *类描述：销售人员Biz接口
 *@author: huangbin
 *@date： 日期：2013-11-25 时间：下午5:27:45
 *@version 1.0
 */
@Component("salesBiz")
public class SalesBiz {
	@Autowired
	private SalesDao salesDao;
	
	public long create(Sales sales){
		return salesDao.insert(sales);
	}
	
	public long update(Sales sales){
		return salesDao.update(sales);
	}
	
	public Sales getById(long salesId){
		return salesDao.getById(salesId);
	}
	
	/***
	 * 查询列表方法-分页
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean querySalesPage(PageParam pageParam, Map<String, Object> paramMap) {
		return salesDao.listPage(pageParam, paramMap);
	}
	
	/***
	 * 根据条件查询列表
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByCondition(Map<String, Object> paramMap){
		return salesDao.listByCondition(paramMap);
	}
}
 