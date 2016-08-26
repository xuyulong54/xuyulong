package wusc.edu.pay.facade.fee.vo;

import wusc.edu.pay.facade.fee.entity.FeeFormula;

/**
 * 阶梯流量累加计费金额VO
 * 
 * @author zws
 * 
 */
public class CalculateFeeAmountVO {
	/**
	 * 待计费金额
	 */
	private Double amount;
	/**
	 * 计费公式
	 */
	private FeeFormula formula;

	public CalculateFeeAmountVO(Double amount, FeeFormula formula) {
		this.amount = amount;
		this.formula = formula;
	}

	public CalculateFeeAmountVO() {
	};

	@Override
	public String toString() {
		return "CalculateFeeAmountVO [amount=" + amount + ", formula=" + formula + "]";
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public FeeFormula getFormula() {
		return formula;
	}

	public void setFormula(FeeFormula formula) {
		this.formula = formula;
	}

}
