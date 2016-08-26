package wusc.edu.pay.facade.limit.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.dao.BizFunctionParamConfigDao;
import wusc.edu.pay.facade.limit.entity.BizFunctionParamConfig;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;
import wusc.edu.pay.facade.limit.service.BizFunctionParamConfigFacade;


/**
 * 
 * @描述: 业务功能参数配置接口实现类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午9:00:23 .
 * @版本: V1.0 .
 */
@Component("bizFunctionParamConfigFacade")
public class BizFunctionParamConfigFacadeImpl implements BizFunctionParamConfigFacade {
	@Autowired
	private BizFunctionParamConfigDao bizFunctionParamConfigDao;

	/****
	 * 查询列表方法
	 */
	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException {
		return bizFunctionParamConfigDao.listPage(pageParam, paramMap);
	}

	/***
	 * 增加业务功能参数配置
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long saveBizFunctionParamConfig(BizFunctionParamConfig entity) throws LimitBizException {
		return bizFunctionParamConfigDao.insert(entity);
	}

	/***
	 * 修改业务功能参数配置
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long updateBizFunctionParamConfig(BizFunctionParamConfig entity) throws LimitBizException {
		return bizFunctionParamConfigDao.update(entity);
	}

	/***
	 * 删除业务功能参数配置
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long deleteBizFunctionParamConfig(Long id) throws LimitBizException {
		return bizFunctionParamConfigDao.deleteById(id);
	}

	/***
	 * 根据ID查
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public BizFunctionParamConfig getById(Long id) throws LimitBizException {
		return bizFunctionParamConfigDao.getById(id);
	}

}
