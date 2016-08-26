package wusc.edu.pay.core.settlement.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;


/**
 * 
 * @描述: 结算控制表数据访问层接口 .
 * @作者: WuShuicheng.
 * @创建: 2014-7-31,上午11:02:39
 * @版本: V1.0
 *
 */
public interface SettControlDao extends BaseDao<SettControl> {

	/**
	 * 根据结算发起方式获取结算控制信息.<br/>
	 * @param settModeTypeEnum 结算发起方式.<br/>
	 * @return SettControl.
	 */
	SettControl getBySettModeType(SettModeTypeEnum settModeTypeEnum);


}
