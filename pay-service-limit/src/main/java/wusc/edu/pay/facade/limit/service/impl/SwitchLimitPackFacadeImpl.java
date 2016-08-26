package wusc.edu.pay.facade.limit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.dao.SwitchLimitPackDao;
import wusc.edu.pay.facade.limit.entity.SwitchLimitPack;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;
import wusc.edu.pay.facade.limit.service.SwitchLimitPackFacade;


/**
 * 
 * @描述: 开关限制包接口实现类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-7, 上午9:57:57 .
 * @版本: V1.0 .
 */
@Component("switchLimitPackFacade")
public class SwitchLimitPackFacadeImpl implements SwitchLimitPackFacade {
	@Autowired
	private SwitchLimitPackDao switchLimitPackDao;

	/****
	 * 查询列表方法
	 */
	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException {
		return switchLimitPackDao.listPage(pageParam, paramMap);
	}

	/***
	 * 增加开关限制
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long saveSwitchLimitPack(SwitchLimitPack entity) throws LimitBizException {
		return switchLimitPackDao.insert(entity);
	}

	/***
	 * 修改开关限制
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long updateSwitchLimitPack(SwitchLimitPack entity) throws LimitBizException {
		return switchLimitPackDao.update(entity);
	}

	/***
	 * 删除开关限制
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public long deleteSwitchLimitPack(Long id) throws LimitBizException {
		return switchLimitPackDao.deleteById(id);
	}

	/***
	 * 根据ID查
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public SwitchLimitPack getById(Long id) throws LimitBizException {
		return switchLimitPackDao.getById(id);
	}

	@Override
	public List<SwitchLimitPack> querySwitchLimitPackList() {
		return switchLimitPackDao.listBy(new HashMap<String, Object>());
	}

	/***
	 * 根据名称查
	 * 
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	@Override
	public SwitchLimitPack getByName(String name) throws LimitBizException {
		return switchLimitPackDao.getByName(name);
	}

}
