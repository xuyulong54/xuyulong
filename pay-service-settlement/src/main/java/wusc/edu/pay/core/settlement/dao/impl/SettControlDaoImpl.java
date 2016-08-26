package wusc.edu.pay.core.settlement.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.settlement.dao.SettControlDao;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;


/**
 * 
 * @描述: 结算控制表数据访问层接口实现 .
 * @作者: WuShuicheng.
 * @创建: 2014-7-31,上午10:18:35
 * @版本: V1.0
 *
 */
@Repository("settControlDao")
public class SettControlDaoImpl extends BaseDaoImpl<SettControl> implements SettControlDao {

	/**
	 * 根据结算发起方式获取结算控制信息.<br/>
	 * @param settModeTypeEnum 结算发起方式.<br/>
	 * @return SettControl.
	 */
	@Override
	public SettControl getBySettModeType(SettModeTypeEnum settModeTypeEnum) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("settModeType", settModeTypeEnum.getValue());
		
		return super.getBy(paramMap);
	}


}
