package wusc.edu.pay.core.cost.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;


/**
 * 计费约束DAO
 */
public interface CalFeeWayDao extends BaseDao<CalFeeWay> {
	
	/**
	 * 根据维度Id获取计费约束列表
	 * @param demensionId
	 * @return
	 */
	public List<CalFeeWay> getCalFeeWayByDimensionID(long dimensionId);
}
