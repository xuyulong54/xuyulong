package wusc.edu.pay.core.cost.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;


/**
 * 计费流量DAO
 */
public interface CalFeeFlowDao extends BaseDao<CalFeeFlow> {
	
	/**
	 * 根据计费约束Id获取流量列表
	 * @param feeWayId
	 * @return
	 */
	public List<CalFeeFlow> getNumOfDataByFeeWayId(long feeWayId);
}
