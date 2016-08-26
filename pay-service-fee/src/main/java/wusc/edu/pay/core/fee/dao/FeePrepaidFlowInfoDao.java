package wusc.edu.pay.core.fee.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.fee.entity.FeePrepaidFlowInfo;


public interface FeePrepaidFlowInfoDao extends BaseDao<FeePrepaidFlowInfo> {

	/**
	 * 根据支付方式id 查找费率包量包笔信息
	 * @param wayId
	 * @return
	 */
	FeePrepaidFlowInfo getFlowInfoByWayId(Long wayId);

}
