package wusc.edu.pay.facade.boss.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.boss.entity.Sales;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;


/**
 * 类描述：
 * 
 * @author: huangbin
 * @date： 日期：2013-11-25 时间：下午5:33:23
 * @version 1.0
 */
public interface SalesFacade {

	public long create(Sales sales) throws BossBizException;

	public long update(Sales sales) throws BossBizException;

	public Sales getById(long salesid) throws BossBizException;

	/***
	 * 查询列表方法-分页
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean querySalesPage(PageParam pageParam, Map<String, Object> paramMap) throws BossBizException;

	/***
	 * 根据条件查询列表
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByCondition(Map<String, Object> paramMap) throws BossBizException;
	
	/**
	 * 生成机具编号
	 * 
	 * @param bizType
	 * @return
	 */
	public String buildSalesNo() throws BossBizException;

}
