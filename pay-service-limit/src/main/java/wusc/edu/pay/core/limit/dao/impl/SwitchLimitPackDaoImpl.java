package wusc.edu.pay.core.limit.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.SwitchLimitPackDao;
import wusc.edu.pay.facade.limit.entity.SwitchLimitPack;

/**
 * 
 * @描述: 开关限制DAO.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 上午11:52:08 .
 * @版本: V1.0 .
 */
@Repository(value="switchLimitPackDao")
public class SwitchLimitPackDaoImpl extends BaseDaoImpl<SwitchLimitPack> implements SwitchLimitPackDao{
	/**
	 * 根据名称查
	 */
	@Override
	public SwitchLimitPack getByName(String name) {
		return this.getSqlSession().selectOne(this.getStatement("getByName"), name);
	}

}
