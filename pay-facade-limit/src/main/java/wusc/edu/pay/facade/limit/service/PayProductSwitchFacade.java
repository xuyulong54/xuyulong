package wusc.edu.pay.facade.limit.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.limit.entity.PayProductSwitch;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;


/**
 * 
 * @描述: 支付产品开关接口.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-8, 上午9:54:24 .
 * @版本: V1.0 .
 */
public interface PayProductSwitchFacade {
	/****
	 * 查询列表方法
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException;
	
	/***
	 * 增加支付产品开关
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long savePayProductSwitch(PayProductSwitch entity) throws LimitBizException;
	/***
	 * 修改支付产品开关
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long updatePayProductSwitch(PayProductSwitch entity) throws LimitBizException;
	/***
	 * 删除支付产品开关
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long deletePayProductSwitch(Long id) throws LimitBizException;

	/***
	 * 根据ID查
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public PayProductSwitch getById (Long id) throws LimitBizException;
	
	/***
	 * 删除支付产品开关
	 * @param switchLimitPackId
	 * @param payProduct
	 * @return
	 * @throws LimitBizException
	 */
	public long deletePayProductSwitchByPackIdAndProduct(Long switchLimitPackId, String payProduct) throws LimitBizException;
}
