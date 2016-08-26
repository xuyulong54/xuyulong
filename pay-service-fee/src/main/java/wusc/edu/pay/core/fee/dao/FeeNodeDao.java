package wusc.edu.pay.core.fee.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.fee.entity.FeeNode;


public interface FeeNodeDao extends BaseDao<FeeNode>{

	FeeNode getFeeNodeByIdAndCalFeeItem(Long feeNodeId, Integer calculateFeeItem);

}
