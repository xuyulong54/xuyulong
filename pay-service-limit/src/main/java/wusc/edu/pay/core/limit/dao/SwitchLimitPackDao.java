package wusc.edu.pay.core.limit.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.SwitchLimitPack;

/**
 * 
 * @描述: 开关限制DAO.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 上午11:50:56 .
 * @版本: V1.0 .
 */
public interface SwitchLimitPackDao extends BaseDao<SwitchLimitPack>{

	SwitchLimitPack getByName(String name);

}
