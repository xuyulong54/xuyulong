package wusc.edu.pay.facade.limit.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.dao.PayProductSwitchDao;
import wusc.edu.pay.facade.limit.entity.PayProductSwitch;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;
import wusc.edu.pay.facade.limit.service.PayProductSwitchFacade;


/**
 * 
 * @描述: 支付产品开关实现类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-8, 上午10:02:28 .
 * @版本: V1.0 .
 */
@Component("payProductSwitchFacade")
public class PayProductSwitchFacadeImpl implements PayProductSwitchFacade {
	@Autowired
	private PayProductSwitchDao payProductSwitchDao;

	/****
	 * 查询列表方法
	 */
	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException {
		return payProductSwitchDao.listPage(pageParam, paramMap);
	}

	/***
	 * 增加支付产品开关
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long savePayProductSwitch(PayProductSwitch entity) throws LimitBizException {
		return payProductSwitchDao.insert(entity);
	}

	/***
	 * 修改支付产品开关
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long updatePayProductSwitch(PayProductSwitch entity) throws LimitBizException {
		return payProductSwitchDao.update(entity);
	}

	/***
	 * 删除支付产品开关
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long deletePayProductSwitch(Long id) throws LimitBizException {
		return payProductSwitchDao.deleteById(id);
	}

	/***
	 * 根据ID查
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public PayProductSwitch getById(Long id) throws LimitBizException {
		return payProductSwitchDao.getById(id);
	}

	/***
	 * 删除支付产品开关
	 * 
	 * @param switchLimitPackId
	 * @param payProduct
	 * @return
	 */
	public long deletePayProductSwitchByPackIdAndProduct(Long switchLimitPackId, String payProduct) {
		return payProductSwitchDao.deletePayProductSwitchByPackIdAndProduct(switchLimitPackId, payProduct);
	}
}
