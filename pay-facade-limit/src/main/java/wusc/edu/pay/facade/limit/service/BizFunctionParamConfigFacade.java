package wusc.edu.pay.facade.limit.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.limit.entity.BizFunctionParamConfig;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;


/**
 * 
 * @描述: 业务功能参数配置接口.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午8:57:47 .
 * @版本: V1.0 .
 */
public interface BizFunctionParamConfigFacade {
	/****
	 * 查询列表方法
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException;	
	/***
	 * 增加业务功能参数配置
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long saveBizFunctionParamConfig(BizFunctionParamConfig entity) throws LimitBizException;
	/***
	 * 修改业务功能参数配置
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long updateBizFunctionParamConfig(BizFunctionParamConfig entity) throws LimitBizException;
	/***
	 * 删除业务功能参数配置
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long deleteBizFunctionParamConfig(Long id) throws LimitBizException;

	/***
	 * 根据ID查
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public BizFunctionParamConfig getById (Long id) throws LimitBizException;
}
