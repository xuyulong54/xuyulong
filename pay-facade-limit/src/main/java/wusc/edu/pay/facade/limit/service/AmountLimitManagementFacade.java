/**
 * wusc.edu.pay.facade.limit.service.AmountLimitManagementFacade.java
 */
package wusc.edu.pay.facade.limit.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.limit.entity.AmountLimit;
import wusc.edu.pay.facade.limit.entity.AmountLimitPack;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;


/**
 * 
 * <ul>
 * <li>Title:金额限制接口</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
public interface AmountLimitManagementFacade {

	/**
	 * 页面查询金额限制列表
	 * 
	 * @param pageParam
	 * @param params
	 * @return
	 */
	public PageBean queryAmountLimitPage(PageParam pageParam, Map<String, Object> params) throws LimitBizException;

	/**
	 * 新增金额限制
	 * 
	 * @param amountLimit
	 * @throws LimitBizException
	 */
	public void addAmountLimit(AmountLimit amountLimit) throws LimitBizException;

	/**
	 * 修改金额限制
	 * 
	 * @param amountLimit
	 * @throws LimitBizException
	 */
	public void updateAmountLimit(AmountLimit amountLimit) throws LimitBizException;

	/**
	 * 金额限制包查询分页
	 * 
	 * @param pageParam
	 * @param params
	 * @return
	 * @throws LimitBizException
	 */
	public PageBean queryAmountLimitPackPage(PageParam pageParam, Map<String, Object> params) throws LimitBizException;

	/**
	 * 新增金额限制包
	 * 
	 * @param entity
	 */
	public void addAmountLimitPack(AmountLimitPack entity);

	/**
	 * 修改金额限制包
	 * 
	 * @param entity
	 */
	public void updateAmountLimitPack(AmountLimitPack entity);

	/**
	 * 根据金额限制包主键，查询金额限制包
	 * 
	 * @param amountLimitPackId
	 * @return
	 */
	public AmountLimitPack getAmountLimitPackById(Long amountLimitPackId);

	/**
	 * 获取所有的金额限制包
	 * @return
	 */
	public List<AmountLimitPack> queryAmountLimitPackAll();

	/**
	 * 根据主键查询金额限制
	 * @param id
	 * @return
	 */
	public AmountLimit getAmountLimitById(Long id);
	
	/**
	 * 根据金额包查询
	 * @param amountLimitPackId
	 * @return
	 */
	public List<AmountLimit> queryAmountLimit(Long amountLimitPackId);
	
	/**
	 * 根据金额包名称查询
	 * @param amountLimitPackId
	 * @return
	 */
	public AmountLimitPack getAmountLimitPackByName(String name);

}
