package wusc.edu.pay.core.fee.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.fee.dao.FeeFormulaDao;
import wusc.edu.pay.facade.fee.entity.FeeFormula;


@Repository("feeFormulaDao")
public class FeeFormulaDaoImpl extends BaseDaoImpl<FeeFormula> implements FeeFormulaDao {
	/**
	 * 根据交易开始时间取费率计算公式
	 * 
	 * @param transferDate
	 *            交易开始时间
	 * @return
	 */
	@Override
	public List<FeeFormula> getFeeFormulas(Date transferDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("transferDate", transferDate);
		return getSessionTemplate().selectList(getStatement("getFeeFormulas"), paramMap);
	}

	/**
	 * 根据支付方式id查找公式
	 * 
	 * @param id
	 * @return
	 */
	public List<FeeFormula> queryFeeFormulaByCalculateWayId(Long calculateWayId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("calculateWayId", calculateWayId);
		return this.listBy(paramMap);
	}

	@Override
	public List<FeeFormula> listByCalWayId(Long calWayId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("calWayId", calWayId);
		return getSessionTemplate().selectList(getStatement("listByCalWayId"), paramMap);
	}

	@Override
	public boolean checkUnique(Map<String, Object> paramMap) {
		List<FeeFormula> list = getSessionTemplate().selectList(getStatement("checkUnique"), paramMap);
		if(list.isEmpty()){
			return true;
		}else{
			return false;
		}
		
	}


	

}
