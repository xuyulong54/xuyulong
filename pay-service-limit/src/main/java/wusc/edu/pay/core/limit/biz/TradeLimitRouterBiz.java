package wusc.edu.pay.core.limit.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.dao.BizFunctionSwitchDao;
import wusc.edu.pay.core.limit.dao.PayProductSwitchDao;
import wusc.edu.pay.core.limit.dao.PayWaySwitchDao;
import wusc.edu.pay.core.limit.dao.TradeLimitRouterDao;
import wusc.edu.pay.facade.limit.entity.BizFunctionSwitch;
import wusc.edu.pay.facade.limit.entity.TradeLimitRouter;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;


/**
 * 
 * @描述: 商户关联开关限制和额度限制BIZ.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-10, 下午2:32:43 .
 * @版本: V1.0 .
 */
@Component(value = "tradeLimitRouterBiz")
public class TradeLimitRouterBiz {
	@Autowired
	private TradeLimitRouterDao tradeLimitRouterDao;

	// 业务功能
	@Autowired
	private BizFunctionSwitchDao bizFunctionSwitchDao;
	
	//支付产品
	@Autowired
	private PayProductSwitchDao payProductSwitchDao;
	
	//支付方式
	@Autowired
	private PayWaySwitchDao payWaySwitchDao;

	/****
	 * 查询列表方法
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return tradeLimitRouterDao.listPage(pageParam, paramMap);
	}

	/***
	 * 增加商户关联开关限制和额度限制
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long saveTradeLimitRouter(TradeLimitRouter entity) {
		return tradeLimitRouterDao.insert(entity);
	}

	/***
	 * 修改商户关联开关限制和额度限制
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long updateTradeLimitRouter(TradeLimitRouter entity) {
		return tradeLimitRouterDao.update(entity);
	}

	/***
	 * 删除商户关联开关限制和额度限制
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long deleteTradeLimitRouter(Long id) {
		return tradeLimitRouterDao.deleteById(id);
	}

	/***
	 * 根据ID查
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public TradeLimitRouter getById(Long id) {
		return tradeLimitRouterDao.getById(id);
	}

	/**
	 * 根据开关限制包加载数据
	 * 
	 * @param switchLimitPackId
	 * @return
	 */
	public Map<String, Object> queryDataBySwitchLimitPackId(Long switchLimitPackId) {
		
		Map<String,Object>  resultMap = new HashMap<String, Object>();
		
		//用于页面回显POS业务
		List<BizFunctionSwitch> list = bizFunctionSwitchDao.queryBizFunctionSwitch(switchLimitPackId, null);
		List<BizFunctionSwitch> bizFunctionList = new ArrayList<BizFunctionSwitch>();
		for (int i = 0; i < list.size(); i++) {
			BizFunctionSwitch bizFunctionSwitch = list.get(i);
			String bizFunction = bizFunctionSwitch.getBizFunction();
			@SuppressWarnings("rawtypes")
			List posLimitTrxTypeEnumList = LimitTrxTypeEnum.toListForPosSwitchLimit();//POS的业务			
			for (int j = 0; j < posLimitTrxTypeEnumList.size(); j++) {
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) posLimitTrxTypeEnumList.get(j);
				if (bizFunction.equals(map.get("name"))) {
					bizFunctionList.add(bizFunctionSwitch);
				}				
			}
			
			@SuppressWarnings("rawtypes")
			List onlineSwitchLimitEnumList = LimitTrxTypeEnum.toListForOnlineSwitchLimit();//在线的业务
			for (int j = 0; j < onlineSwitchLimitEnumList.size(); j++) {
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) onlineSwitchLimitEnumList.get(j);
				if (bizFunction.equals(map.get("name"))) {
					bizFunctionList.add(bizFunctionSwitch);
				}			
			}
			
		}
		
		//业务功能
		resultMap.put("BizFunctionList", bizFunctionList);
		
		//支付产品
		resultMap.put("PayProductList", payProductSwitchDao.queryPayProductSwitch(switchLimitPackId, null));
		
		//支付方式
		resultMap.put("PayWayList", payWaySwitchDao.queryPayWaySwitch(switchLimitPackId, null));
 		
		return resultMap;
	}

	/**
	 * 根据商户查询数据
	 * @param merchantNo
	 * @return
	 */
	public TradeLimitRouter getTradeLimitRouterByMerchantNo(String merchantNo) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("merchantNo", merchantNo);
		return tradeLimitRouterDao.getBy(params);
	}
}
