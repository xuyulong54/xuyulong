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
 * 计费方式：事后计算
 * @author yzyue
 *
 */
public class AfterBankCost extends AbstractBankCost {

	public AfterBankCost(CalCostOrder order) {
		super(order);
	}

	@Override
	public boolean calculate(List<CalFeeRateFormula> formulas)
			throws CostBizException {
		this.fee = BigDecimal.ZERO;
		order.setCalExpression("事后:0");
		return true;
	}

	@Override
	public List<CalFeeRateFormula> getFormula(CalFeeRateFormulaBiz formulaBiz,
			CalFeeWay calFeeWay) {
		return null;
	}

}
