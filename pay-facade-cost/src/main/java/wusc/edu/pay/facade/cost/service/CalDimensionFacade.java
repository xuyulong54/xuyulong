package wusc.edu.pay.facade.cost.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.cost.entity.CalDimension;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


/**
 * 
 * @描述: 计费维度信息，Dubbo服务接口.
 * @作者: 李安国 .
 * @创建时间: 2014-4-15, 下午2:30:29
 */
public interface CalDimensionFacade {
	/**
	 * 创建计费维度信息
	 * 
	 * @param entity
	 * @return
	 */
	public long create(CalDimension entity) throws CostBizException;

	/**
	 * 修改计费维度信息
	 * 
	 * @param entity
	 * @return
	 */
	public long update(CalDimension entity) throws CostBizException;
	/**
	 * 获取所有的计费维度
	 * @return
	 * @throws CostBizException
	 */
	public List<CalDimension> listAll() throws CostBizException;
	/**
	 * 分页查询计费维度信息
	 * 
	 * @param pageParam
	 *            分页实体对象
	 * @param paramMap
	 *            查询条件
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws CostBizException;

	/**
	 * 根据ID查找计费维度信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public CalDimension getById(long id) throws CostBizException;
	
	/***
	 * 根据计费产品名称查询计费维度信息
	 * @param productName 计费产品名称
	 * @return
	 */
	public List<CalDimension> getByProductName(String productName) throws CostBizException;

	/**
	 * 根据Id删除计费维度信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public void deleteById(long id) throws CostBizException;
	
	/**
	 * 根据计费接口编码查询计费维度信息
	 */
	public List<CalDimension> getByCostInterfaceCode(String calCostInterfaceCode) throws CostBizException;
}
