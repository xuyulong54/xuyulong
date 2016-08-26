package wusc.edu.pay.core.fee.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.fee.dao.FeeNodeDao;
import wusc.edu.pay.facade.fee.entity.FeeNode;


@Repository("feeNodeDao")
public class FeeNodeDaoImpl extends BaseDaoImpl<FeeNode> implements FeeNodeDao {

	@Override
	public FeeNode getFeeNodeByIdAndCalFeeItem(Long feeNodeId,
			Integer calculateFeeItem) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", feeNodeId);
		param.put("calculateFeeItem", calculateFeeItem);
		return super.getSqlSession().selectOne("getFeeNodeByIdAndCalFeeItem", param);
	}
	
}
