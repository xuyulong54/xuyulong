package wusc.edu.pay.core.cost.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;


/**
 * 计费公式DAO
 */
public interface CalFeeRateFormulaDao extends BaseDao<CalFeeRateFormula> {
	
	/**
	 * 根据约束Id获取计费公式列表
	 * @param feeWayId
	 * @return
	 */
	public List<CalFeeRateFormula> getRateFormulaByFeeWayId(long  feeWayId);
}
