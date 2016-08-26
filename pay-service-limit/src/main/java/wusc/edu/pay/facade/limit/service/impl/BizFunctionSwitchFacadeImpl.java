package wusc.edu.pay.facade.limit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.dao.BizFunctionSwitchDao;
import wusc.edu.pay.facade.limit.entity.BizFunctionSwitch;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;
import wusc.edu.pay.facade.limit.service.BizFunctionSwitchFacade;


/**
 * 
 * @描述: 业务功能开关接口实现类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午5:38:05 .
 * @版本: V1.0 .
 */
@Component("bizFunctionSwitchFacade")
public class BizFunctionSwitchFacadeImpl implements BizFunctionSwitchFacade {
	@Autowired
	private BizFunctionSwitchDao bizFunctionSwitchDao;

	/****
	 * 查询列表方法
	 */
	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException {
		return bizFunctionSwitchDao.listPage(pageParam, paramMap);
	}

	/***
	 * 增加业务功能开关
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long saveBizFunctionSwitch(BizFunctionSwitch entity) throws LimitBizException {
		return bizFunctionSwitchDao.insert(entity);
	}

	/***
	 * 修改业务功能开关
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long updateBizFunctionSwitch(BizFunctionSwitch entity) throws LimitBizException {
		return bizFunctionSwitchDao.update(entity);
	}

	/***
	 * 删除业务功能开关
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long deleteBizFunctionSwitch(Long id) throws LimitBizException {
		return bizFunctionSwitchDao.deleteById(id);
	}

	/***
	 * 根据ID查
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public BizFunctionSwitch getById(Long id) throws LimitBizException {
		return bizFunctionSwitchDao.getById(id);
	}

	/***
	 * 删除业务功能开关
	 * 
	 * @param switchLimitPackId
	 * @param bizFunction
	 * @return
	 */
	public long deleteBizFunctionSwitchByPackIdAndFunction(Long switchLimitPackId, String bizFunction) {
		return bizFunctionSwitchDao.deleteBizFunctionSwitchByPackIdAndFunction(switchLimitPackId, bizFunction);
	}

	/**
	 * 根据商户编号查询业务功能
	 * 
	 * @param merchantNo
	 * @return
	 */
	public List<BizFunctionSwitch> getBizFunctionSwitchByMerchantNo(String merchantNo) {
		return bizFunctionSwitchDao.getBizFunctionSwitchByMerchantNo(merchantNo);
	}

}
