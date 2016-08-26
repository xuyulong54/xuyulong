package wusc.edu.pay.facade.cost.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.cost.entity.CalCostInterface;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


/**
 * 
 * @描述:  成本计费接口的Dubbo服务接口.
 * @作者: WuShuicheng.
 * @创建: 2014-7-9,下午5:12:49
 * @版本: V1.0
 *
 */
public interface CalCostInterfaceFacade {
	/**
	 * 创建计费接口
	 */
	public long create(CalCostInterface entity) throws CostBizException;

	/**
	 * 修改计费接口
	 */
	public long update(CalCostInterface entity) throws CostBizException;
	
	/**
	 * 根据ID删除计费接口
	 */
	public void deleteById(long id) throws CostBizException;
	
	/**
	 * 根据计费接口编码删除计费接口
	 */
	public void deleteByCalCostInterfaceCode(String calCostInterfaceCode) throws CostBizException;
	
	/**
	 * 根据计费接口编码查找计费接口
	 */
	public CalCostInterface getByCalCostInterfaceCode(String calCostInterfaceCode) throws CostBizException;

	/**
	 * 分页查询计费接口
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws CostBizException;

	/**
	 * 根据ID查找计费接口
	 */
	public CalCostInterface getById(long id) throws CostBizException;
	
	/**
	 * 获取所有计费接口
	 */
	public List<CalCostInterface> listAll() throws CostBizException;
}
