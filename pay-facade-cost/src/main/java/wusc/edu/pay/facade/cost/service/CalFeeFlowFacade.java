package wusc.edu.pay.facade.cost.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


/**
 * 
 * @描述: 计费流量信息，Dubbo服务接口.
 * @作者: 李安国 .
 * @创建时间: 2014-4-15, 下午2:30:29
 */
public interface CalFeeFlowFacade {
	/**
	 * 创建计费流量信息
	 * 
	 * @param entity
	 * @return
	 */
	public long create(CalFeeFlow entity) throws CostBizException;

	/**
	 * 修改计费流量信息
	 * 
	 * @param entity
	 * @return
	 */
	public long update(CalFeeFlow entity) throws CostBizException;

	/**
	 * 分页查询计费流量信息
	 * 
	 * @param pageParam
	 *            分页实体对象
	 * @param paramMap
	 *            查询条件
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap)
			throws CostBizException;

	/**
	 * 根据ID查找计费流量信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public CalFeeFlow getById(long id) throws CostBizException;

	/***
	 * 根据计费约束ID查询计费流量信息
	 * 
	 * @param formulaId
	 *            计费约束ID
	 * @return
	 */
	public List<CalFeeFlow> getByFormulaId(int formulaId)
			throws CostBizException;
}
