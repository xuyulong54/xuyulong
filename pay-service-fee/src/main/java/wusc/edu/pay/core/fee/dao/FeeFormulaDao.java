package wusc.edu.pay.core.fee.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.fee.entity.FeeFormula;


public interface FeeFormulaDao extends BaseDao<FeeFormula> {

	List<FeeFormula> getFeeFormulas(Date transferDate);

	/**
	 * 根据支付方式id查找公式
	 * 
	 * @param id
	 * @return
	 */
	List<FeeFormula> queryFeeFormulaByCalculateWayId(Long calculateWayId);

	/**
	 * 根据计费方式列出所有计费公式
	 * 
	 * @param calWayId
	 * @return
	 */
	List<FeeFormula> listByCalWayId(Long calWayId);

	/**
	 * 检查公式是否唯一
	 * @param paramMap
	 * @return
	 */
	boolean checkUnique(Map<String, Object> paramMap);

}
