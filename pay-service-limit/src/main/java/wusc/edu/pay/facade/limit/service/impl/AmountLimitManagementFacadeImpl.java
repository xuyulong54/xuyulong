/**
 * wusc.edu.pay.facade.limit.service.impl.AmountLimitManagementFacadeImpl.java
 */
package wusc.edu.pay.facade.limit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.biz.AmountLimitBiz;
import wusc.edu.pay.core.limit.dao.AmountLimitPackDao;
import wusc.edu.pay.facade.limit.entity.AmountLimit;
import wusc.edu.pay.facade.limit.entity.AmountLimitPack;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;
import wusc.edu.pay.facade.limit.service.AmountLimitManagementFacade;


/**
 * 
 * <ul>
 * <li>Title: 交易金额限制API实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
@Component("amountLimitManagementFacade")
public class AmountLimitManagementFacadeImpl implements AmountLimitManagementFacade {

	/**
	 * 金额限制
	 */
	@Autowired
	private AmountLimitBiz amountLimitBiz;

	/**
	 * 金额限制包
	 */
	@Autowired
	private AmountLimitPackDao amountLimitPackDao;

	/**
	 * 分页查询金额限制
	 */
	@Override
	public PageBean queryAmountLimitPage(PageParam pageParam, Map<String, Object> params) throws LimitBizException {
		return amountLimitPackDao.listPage(pageParam, params);
	}

	/**
	 * 新增金额限制
	 */
	@Override
	public void addAmountLimit(AmountLimit amountLimit) throws LimitBizException {
		amountLimitBiz.addAmountLimit(amountLimit);
	}

	/**
	 * 修改金额限制
	 */
	@Override
	public void updateAmountLimit(AmountLimit amountLimit) throws LimitBizException {
		amountLimitBiz.updateAmountLimit(amountLimit);

	}

	/**
	 * 分页查询交易限制包
	 */
	@Override
	public PageBean queryAmountLimitPackPage(PageParam pageParam, Map<String, Object> params) throws LimitBizException {
		return amountLimitPackDao.listPage(pageParam, params);
	}

	/**
	 * 新增金额限制包
	 */
	@Override
	public void addAmountLimitPack(AmountLimitPack entity) {
		amountLimitPackDao.insert(entity);

	}

	/**
	 * 修改金额限制包
	 */
	@Override
	public void updateAmountLimitPack(AmountLimitPack entity) {
		amountLimitPackDao.update(entity);
	}

	/**
	 * 根据金额限制包的主键，查询金额限制包
	 */
	@Override
	public AmountLimitPack getAmountLimitPackById(Long amountLimitPackId) {
		return amountLimitPackDao.getById(amountLimitPackId);
	}

	/**
	 * 获取所有的金额限制包
	 */
	@Override
	public List<AmountLimitPack> queryAmountLimitPackAll() {
		return amountLimitPackDao.listBy(new HashMap<String, Object>());
	}

	/**
	 * 根据主键查询金额限制
	 */
	@Override
	public AmountLimit getAmountLimitById(Long id) {
		return amountLimitBiz.getAmountLimitById(id);
	}

	/**
	 * 获取金额列表
	 */
	@Override
	public List<AmountLimit> queryAmountLimit(Long amountLimitPackId) {
		return amountLimitBiz.queryAmountLimit(amountLimitPackId);
	}

	/**
	 * 根据金额包名称查询
	 */
	@Override
	public AmountLimitPack getAmountLimitPackByName(String name) {
		return amountLimitPackDao.getAmountLimitPackByName(name);
	}

}
