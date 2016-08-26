package wusc.edu.pay.facade.limit.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.biz.TradeLimitRouterBiz;
import wusc.edu.pay.facade.limit.entity.TradeLimitRouter;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;
import wusc.edu.pay.facade.limit.service.TradeLimitRouterFacade;

/**
 * 
 * @描述: 商户关联开关限制和额度限制接口实现类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-10, 下午2:27:49 .
 * @版本: V1.0 .
 */
@Component("tradeLimitRouterFacade")
public class TradeLimitRouterFacadeImpl implements TradeLimitRouterFacade{
	@Autowired
	private TradeLimitRouterBiz tradeLimitRouterBiz;
	/****
	 * 查询列表方法
	 */
	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap)
			throws LimitBizException {
		return tradeLimitRouterBiz.listPage( pageParam, paramMap);
	}
	/***
	 * 增加商户关联开关限制和额度限制
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long saveTradeLimitRouter(TradeLimitRouter entity)
			throws LimitBizException {
		return tradeLimitRouterBiz.saveTradeLimitRouter( entity);
	}
	/***
	 * 修改商户关联开关限制和额度限制
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long updateTradeLimitRouter(TradeLimitRouter entity)
			throws LimitBizException {
		return tradeLimitRouterBiz.updateTradeLimitRouter( entity);
	}
	/***
	 * 删除商户关联开关限制和额度限制
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long deleteTradeLimitRouter(Long id) throws LimitBizException {
		return tradeLimitRouterBiz.deleteTradeLimitRouter( id);
	}
	/***
	 * 根据ID查
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public TradeLimitRouter getById(Long id) throws LimitBizException {
		return tradeLimitRouterBiz.getById( id);
	}
	
	@Override
	public Map<String, Object> queryDataBySwitchLimitPackId(Long switchLimitPackId) throws LimitBizException {
		return tradeLimitRouterBiz.queryDataBySwitchLimitPackId(switchLimitPackId);
	}
	
	/**
	 * 根据商户编号查询数据
	 */
	@Override
	public TradeLimitRouter getTradeLimitRouterByMerchantNo(String merchantNo) {
		return tradeLimitRouterBiz.getTradeLimitRouterByMerchantNo(merchantNo);
	}
	
}
