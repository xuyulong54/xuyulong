package wusc.edu.pay.core.cost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.cost.dao.CalFeeRateFormulaDao;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;


@Repository("calFeeRateFormulaDao")
public class CalFeeRateFormulaDaoImpl extends BaseDaoImpl<CalFeeRateFormula>
		implements CalFeeRateFormulaDao {

	@Override
	public List<CalFeeRateFormula> getRateFormulaByFeeWayId(long  feeWayId){
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("feeWayId", feeWayId);
		return super.listBy(paramMap);
	}
}
