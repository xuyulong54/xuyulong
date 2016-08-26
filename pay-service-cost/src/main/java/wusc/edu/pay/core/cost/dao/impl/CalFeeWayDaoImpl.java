package wusc.edu.pay.core.cost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.cost.dao.CalFeeWayDao;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;


@Repository("calFeeWayDao")
public class CalFeeWayDaoImpl extends BaseDaoImpl<CalFeeWay> implements
		CalFeeWayDao {
	
	/**
	 * 根据维度Id获取计费约束列表
	 * @param demensionId
	 * @return
	 */
	@Override
	public List<CalFeeWay> getCalFeeWayByDimensionID(long dimensionId) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("dimensionId", dimensionId);
		return super.listBy(paramMap);
	}


}
