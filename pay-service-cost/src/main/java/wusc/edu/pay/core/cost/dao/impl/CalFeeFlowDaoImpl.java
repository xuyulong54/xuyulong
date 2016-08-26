package wusc.edu.pay.core.cost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.cost.dao.CalFeeFlowDao;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;


@Repository("calFeeFlowDao")
public class CalFeeFlowDaoImpl extends BaseDaoImpl<CalFeeFlow> implements
		CalFeeFlowDao {

	@Override
	public List<CalFeeFlow> getNumOfDataByFeeWayId(long feeWayId) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("feeWayId", feeWayId);
		return super.listBy(paramMap);
	}

}
