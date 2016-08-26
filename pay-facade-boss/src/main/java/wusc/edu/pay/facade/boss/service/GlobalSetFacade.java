package wusc.edu.pay.facade.boss.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.boss.entity.GlobalSet;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;


/**
 * 
 * @描述: 运营全局设置.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-29,下午4:23:20 .
 * @版本: 1.0 .
 */
public interface GlobalSetFacade {

	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BossBizException;

	GlobalSet getById(long id) throws BossBizException;

	long update(GlobalSet entity) throws BossBizException;
	
	/***
	 * 根据键名查询全局设置表数据
	 * @param keyValue 键名
	 * @return
	 * @throws BossBizException
	 */
	GlobalSet getBySetKey(String keyValue) throws BossBizException;
}
