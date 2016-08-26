package wusc.edu.pay.core.cost.biz.cal.impl;

import java.math.BigDecimal;
import java.util.List;

import wusc.edu.pay.core.cost.biz.cal.abs.AbstractBankCost;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.enums.CalModelEnum;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;

/**
 * 计费方式：区间计费
 * @author yzyue
 *
 */
public class SectionBankCost extends AbstractBankCost {

	public SectionBankCost(CalCostOrder order) {
		super(order);
	}

	@Override
	public boolean calculate(List<CalFeeRateFormula> formulas)
			throws CostBizException {
		int formulaCount = (formulas == null) ? 0 : formulas.size();
		if(formulaCount == 0){
			logger.warn("计费约束中找不到计费公式");
			return false;
		}
		BigDecimal fee = BigDecimal.ZERO;
		for(int i = 0; i < formulaCount; i++){
			CalFeeRateFormula formula = formulas.get(i);
			logger.info(String.format("判断当前交易金额[%s]是否在该区间[%s - %s]之内", amount, formula.getMinAmount(), formula.getMaxAmount()));
			if(formula.getMinAmount() == null){
				logger.error("计费公式中，区间下限为空");
				continue;
			}
			if(formula.getMaxAmount() == null){
				logger.error("计费公式中，区间上限为空");
				continue;
			}
			if(formula.getMinAmount().compareTo(formula.getMaxAmount()) == 1){
				logger.error("计费公式中区间上下限设置有误");
				continue;
			}
			if(amount.compareTo(formula.getMinAmount()) == -1 || amount.compareTo(formula.getMaxAmount()) == 1){
				logger.info("当前交易金额不在此区间之内");
				continue;
			}
			if(formula.getModel().intValue() == CalModelEnum.PERCENT.getValue()){
				logger.info(String.format("验证计费公式[%d/%d],模式=[比例],费率=[%s],最低手续费=[%s],最高手续费=[%s]", i + 1, formulaCount, formula.getPercentFee(), formula.getSingleMinFee(), formula.getSingleMaxFee()));
				if(formula.getSingleMinFee() == null){
					logger.error("计费公式中，最低手续费为空");
					continue;
				}
				if(formula.getSingleMaxFee() == null){
					logger.error("计费公式中，最高手续费为空");
					continue;
				}
				if(formula.getPercentFee() == null){
					logger.error("计费公式中，费率比例为空");
					continue;
				}
				if(formula.getSingleMinFee().compareTo(formula.getSingleMaxFee()) == 1){
					logger.error(String.format("计费公式中最低手续费[%s]和最高手续费[%s]设置有误", formula.getSingleMinFee(), formula.getSingleMaxFee()));
				}
				this.fee = amount.multiply(formula.getPercentFee());					
				if(this.fee.compareTo(formula.getSingleMinFee()) == -1){
					this.fee = formula.getSingleMinFee();
					order.setCalExpression("区间比例:" + amount + " * " + formula.getPercentFee() + " ;取最低: " + formula.getSingleMinFee());
				}else if(this.fee.compareTo(formula.getSingleMaxFee()) == 1){
					this.fee = formula.getSingleMaxFee();
					order.setCalExpression("区间比例:" + amount + " * " + formula.getPercentFee() + " ;取最高: " + formula.getSingleMaxFee());
				}else{
					order.setCalExpression("区间比例:" + amount + " * " + formula.getPercentFee());	
				}
				return true;
			}else{
				logger.info(String.format("验证计费公式[%d/%d],模式=[固定金额],固定手续费=[%s]", i + 1, formulaCount, formula.getFixedFee()));
				if(formula.getFixedFee() == null){
					logger.error("计费公式中，固定手续费为空");
					continue;
				}else{
					this.fee = formula.getFixedFee();
					order.setCalExpression("区间固定:" + formula.getFixedFee());
					return true;
				}
			}
		}
		logger.warn("计费约束中找不到有效的计费公式");
		return false;
	}

}
