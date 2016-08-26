package wusc.edu.pay.facade.limit.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.limit.entity.SwitchLimitPack;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;


/**
 * 
 * @描述: 开关限制包接口.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-7, 上午9:56:59 .
 * @版本: V1.0 .
 */
public interface SwitchLimitPackFacade {
	/****
	 * 查询列表方法
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException;
	
	/***
	 * 增加开关限制
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long saveSwitchLimitPack(SwitchLimitPack entity) throws LimitBizException;
	/***
	 * 修改开关限制
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long updateSwitchLimitPack(SwitchLimitPack entity) throws LimitBizException;
	/***
	 * 删除开关限制
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long deleteSwitchLimitPack(Long id) throws LimitBizException;

	/***
	 * 根据ID查
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public SwitchLimitPack getById (Long id) throws LimitBizException;
	
	/**
	 * 加载所有开关限制
	 * 
	 * @param entity
	 */
	public List<SwitchLimitPack> querySwitchLimitPackList();
	
	/***
	 * 根据名称查
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public SwitchLimitPack getByName (String name) throws LimitBizException;

	
}
