package wusc.edu.pay.facade.limit.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.limit.entity.PayWaySwitch;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;


/**
 * 
 * @描述: 支付方式开关接口.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午1:49:05 .
 * @版本: V1.0 .
 */
public interface PayWaySwitchFacade {
	/****
	 * 查询列表方法
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException;	
	/***
	 * 增加支付方式开关
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long savePayWaySwitch(PayWaySwitch entity) throws LimitBizException;
	/***
	 * 修改支付方式开关
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long updatePayWaySwitch(PayWaySwitch entity) throws LimitBizException;
	/***
	 * 删除支付方式开关
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long deletePayWaySwitch(Long id) throws LimitBizException;

	/***
	 * 根据ID查
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public PayWaySwitch getById (Long id) throws LimitBizException;
	
	/***
	 * 删除支付方式开关
	 * @param switchLimitPackId
	 * @param payProduct
	 * @param payWay
	 * @return
	 * @throws LimitBizException
	 */
	public long deletePayProductSwitchByPackIdAndProductAndWay(Long switchLimitPackId, String payProduct, String payWay) throws LimitBizException;
}
