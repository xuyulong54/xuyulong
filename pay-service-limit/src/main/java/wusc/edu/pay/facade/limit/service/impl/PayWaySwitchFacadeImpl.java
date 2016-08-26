package wusc.edu.pay.facade.limit.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.dao.PayWaySwitchDao;
import wusc.edu.pay.facade.limit.entity.PayWaySwitch;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;
import wusc.edu.pay.facade.limit.service.PayWaySwitchFacade;


/**
 * 
 * @描述: 支付方式开关接口实现类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午2:20:32 .
 * @版本: V1.0 .
 */
@Component("payWaySwitchFacade")
public class PayWaySwitchFacadeImpl implements PayWaySwitchFacade {
	@Autowired
	private PayWaySwitchDao payWaySwitchDao;

	/****
	 * 查询列表方法
	 */
	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException {
		return payWaySwitchDao.listPage(pageParam, paramMap);
	}

	/***
	 * 增加支付方式开关
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long savePayWaySwitch(PayWaySwitch entity) throws LimitBizException {
		return payWaySwitchDao.insert(entity);
	}

	/***
	 * 修改支付方式开关
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long updatePayWaySwitch(PayWaySwitch entity) throws LimitBizException {
		return payWaySwitchDao.update(entity);
	}

	/***
	 * 删除支付方式开关
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long deletePayWaySwitch(Long id) throws LimitBizException {
		return payWaySwitchDao.deleteById(id);
	}

	/***
	 * 根据ID查
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public PayWaySwitch getById(Long id) throws LimitBizException {
		return payWaySwitchDao.getById(id);
	}

	/***
	 * 删除支付方式开关
	 * 
	 * @param switchLimitPackId
	 * @param payProduct
	 * @param payWay
	 * @return
	 */
	public long deletePayProductSwitchByPackIdAndProductAndWay(Long switchLimitPackId, String payProduct, String payWay) {
		return payWaySwitchDao.deletePayProductSwitchByPackIdAndProductAndWay(switchLimitPackId, payProduct, payWay);
	}

}
