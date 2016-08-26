package wusc.edu.pay.core.cost.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.core.cost.dao.CalFeeRateFormulaDao;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;


/**
 * 
 * @描述: 计费公式信息表业务实现类 .
 * @作者: huqian .
 * @创建时间: 2014-7-1, 上午11:35:52
 */
@Component("calFeeRateFormulaBiz")
public class CalFeeRateFormulaBiz extends BaseBizImpl<CalFeeRateFormula> {

	@Autowired
	private CalFeeRateFormulaDao calFeeRateFormulaDao;

	protected BaseDao<CalFeeRateFormula> getDao() {
		return calFeeRateFormulaDao;
	}
	
	public List<CalFeeRateFormula> getRateFormulaByFeeWayId(long  feeWayId){
		return calFeeRateFormulaDao.getRateFormulaByFeeWayId(feeWayId);
	}
}