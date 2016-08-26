package wusc.edu.pay.facade.boss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.boss.biz.SalesBiz;
import wusc.edu.pay.core.boss.dao.SalesDao;
import wusc.edu.pay.facade.boss.entity.Sales;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.facade.boss.service.SalesFacade;


/**
 * 类描述：
 * 
 * @author: huangbin
 * @date： 日期：2013-11-25 时间：下午5:33:08
 * @version 1.0
 */
@Component("salesFacade")
public class SalesFacadeImpl implements SalesFacade {
	@Autowired
	private SalesBiz salesBiz;
	@Autowired
	private SalesDao salesDao;

	/***
	 * 创建
	 */
	public long create(Sales sales) throws BossBizException {
		return salesBiz.create(sales);
	}

	/***
	 * 创建
	 */
	public long update(Sales sales) throws BossBizException {
		return salesBiz.update(sales);
	}

	/***
	 * 根据ID查询
	 */
	public Sales getById(long salesid) throws BossBizException {
		return salesBiz.getById(salesid);
	}

	/***
	 * 根据条件查询分页
	 */
	public PageBean querySalesPage(PageParam pageParam, Map<String, Object> paramMap) throws BossBizException {
		return salesBiz.querySalesPage(pageParam, paramMap);
	}

	/***
	 * 根据条件查询列表
	 */
	@SuppressWarnings("rawtypes")
	public List listByCondition(Map<String, Object> paramMap) throws BossBizException {
		return salesBiz.listByCondition(paramMap);
	}
	
	/**
	 * 生成机具编号
	 * 
	 * @param bizType
	 * @return
	 */
	@Override
	public String buildSalesNo() {
		return salesDao.buildSalesNo();
	}
}
