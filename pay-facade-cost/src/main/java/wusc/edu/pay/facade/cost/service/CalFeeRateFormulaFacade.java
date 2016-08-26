package wusc.edu.pay.facade.cost.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


/**
 * 
 * @描述: 计费费率公式信息，Dubbo服务接口.
 * @作者: 李安国 .
 * @创建时间: 2014-4-15, 下午2:30:29
 */
public interface CalFeeRateFormulaFacade {
	/**
	 * 创建计费费率公式信息
	 * 
	 * @param entity
	 * @return
	 */
	public long create(CalFeeRateFormula entity) throws CostBizException;

	/**
	 * 修改计费费率公式信息
	 * 
	 * @param entity
	 * @return
	 */
	public long update(CalFeeRateFormula entity) throws CostBizException;

	/**
	 * 分页查询计费费率公式信息
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
	 * 根据ID查找计费费率公式信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public CalFeeRateFormula getById(long id) throws CostBizException;
	
	/**
	 * 根据计费约束ID查找计费费率公式信息
	 */
	public List<CalFeeRateFormula> getByFeeWayId(long feeWayId) throws CostBizException;

}
