package wusc.edu.pay.core.cost.biz.cal.impl;

import java.math.BigDecimal;
import java.util.List;

import wusc.edu.pay.core.cost.biz.CalFeeRateFormulaBiz;
import wusc.edu.pay.core.cost.biz.cal.abs.AbstractBankCost;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;

/**
 * 计费方式：包年
 * @author yzyue
 *
 */
public class YearBankCost extends AbstractBankCost {

	public YearBankCost(CalCostOrder order) {
		super(order);
	}

	@Override
	public boolean calculate(List<CalFeeRateFormula> formulas)
			throws CostBizException {
		this.fee = BigDecimal.ZERO;
		order.setCalExpression("包年:0");
		return true;
	}

	@Override
	public List<CalFeeRateFormula> getFormula(CalFeeRateFormulaBiz formulaBiz,
			CalFeeWay calFeeWay) {
		return null;
	}
	
}
