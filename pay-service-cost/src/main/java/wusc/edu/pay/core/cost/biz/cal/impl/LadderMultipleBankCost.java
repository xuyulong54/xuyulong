package wusc.edu.pay.core.cost.biz.cal.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import wusc.edu.pay.core.cost.biz.CalFeeFlowBiz;
import wusc.edu.pay.core.cost.biz.cal.abs.LadderBankCost;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.enums.CalModelEnum;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;

/**
 * 计费方式：多阶梯费率
 * @author yzyue
 *
 */
public class LadderMultipleBankCost extends LadderBankCost {

	public LadderMultipleBankCost(CalCostOrder order,
			CalFeeFlowBiz calFeeFlowBiz, CalFeeWay calFeeWay, Date date) {
		super(order, calFeeFlowBiz, calFeeWay, date);
	}

	@Override
	public boolean calculate(List<CalFeeRateFormula> formulas)
			throws CostBizException {
		try{
			this.inital();
			if(this.calFeeFlow != null && this.calFeeFlow.getTotalAmount() != null){
				this.fee = this.calculate(formulas, this.calFeeFlow.getTotalAmount().add(amount));
				this.fee = this.fee.subtract(this.calculate(formulas, this.calFeeFlow.getTotalAmount()));
			}else{
				this.fee = this.calculate(formulas, amount);
			}
			return true;
		}catch(CostBizException e){
			logger.error("计算单阶梯成本出现异常", e);
			return false;
		}
	}
	
	/**
	 * 计算多阶梯的手续费
	 * @param formula 计算公式集合
	 * @param amount 总交易金额
	 * @return
	 */
	private BigDecimal calculate(List<CalFeeRateFormula> formulas, BigDecimal totalAmount) throws CostBizException{
		if(formulas == null || formulas.size() == 0){
			throw CostBizException.CAL_FEE_ERROR.newInstance("计费约束中找不到计费公式");
		}
		BigDecimal fee = BigDecimal.ZERO;
		for(int i = 0; i < formulas.size(); i++){
			CalFeeRateFormula formula = formulas.get(i);
			if(formula.getMinLadderAmount() == null){
				throw CostBizException.CAL_FEE_ERROR.newInstance("计费公式中，阶梯下限为空");
			}
			if(formula.getMaxLadderAmount() == null){
				throw CostBizException.CAL_FEE_ERROR.newInstance("计费公式中，阶梯上限为空");
			}
			if(formula.getMinLadderAmount().compareTo(formula.getMaxLadderAmount()) == 1){
				throw CostBizException.CAL_FEE_ERROR.newInstance("计费公式中阶梯上限[%s]和下限[%s]设置有有误", formula.getMinLadderAmount(), formula.getMaxLadderAmount());
			}
			if(totalAmount.compareTo(formula.getMinLadderAmount()) == -1){
				continue;
			}
			if(totalAmount.compareTo(formula.getMaxLadderAmount()) == 1){
				if(formula.getModel().intValue() == CalModelEnum.PERCENT.getValue()){
					if(formula.getPercentFee() == null){
						throw CostBizException.CAL_FEE_ERROR.newInstance("计费公式中，费率比例为空");
					}
					fee = fee.add(formula.getMaxLadderAmount().subtract(formula.getMinLadderAmount()).multiply(formula.getPercentFee()));
				}else{
					if(formula.getFixedFee() == null){
						throw CostBizException.CAL_FEE_ERROR.newInstance("计费公式中，固定手续费为空");
					}
					fee = fee.add(formula.getFixedFee());
				}
			}else{
				if(formula.getModel().intValue() == CalModelEnum.PERCENT.getValue()){
					if(formula.getPercentFee() == null){
						throw CostBizException.CAL_FEE_ERROR.newInstance("计费公式中，费率比例为空");
					}
					fee = fee.add(totalAmount.subtract(formula.getMinLadderAmount()).multiply(formula.getPercentFee()));
				}else{
					if(formula.getFixedFee() == null){
						throw CostBizException.CAL_FEE_ERROR.newInstance("计费公式中，固定手续费为空");
					}
					fee = fee.add(formula.getFixedFee());
				}
			}
		}
		return fee;
	}

}
