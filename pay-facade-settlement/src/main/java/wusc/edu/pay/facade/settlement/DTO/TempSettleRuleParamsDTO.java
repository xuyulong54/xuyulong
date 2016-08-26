package wusc.edu.pay.facade.settlement.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

import wusc.edu.pay.facade.settlement.enums.RemitFeeSetEnum;
import wusc.edu.pay.facade.settlement.enums.RemitFeeTypeEnum;


 /**
  * @title  临时结算规则参数
  * @company 中益智正(gzzyzz)
  * @author jialong.shen
  * @since 2014-8-15
  */
public class TempSettleRuleParamsDTO implements Serializable {

	private static final long serialVersionUID = -1492017715948046815L;

	/**
	 * 账户账号
	 */
	private String accountNo;

	/**
	 * 最低结算金额
	 */
	private BigDecimal minAmount = BigDecimal.ZERO;
	/**
	 * 风险预存期
	 */
	private Integer riskDay;
	
	/**
	 * 最长结算天数
	 */
	private Long maxDays;
	
	/**
	 * 临时结算时，额外加收的手续费，每笔收多少钱，单位元，和结算规则手续费同时使用
	 */
	private BigDecimal extraFee;
	/**
	 * 是否计算手续费，如果为NOT，只收取额外加收的手续费，不再对结算金额进行手续费的计算 TEMP使用临时结算的费率规则
	 * RULE使用结算规则的费率规则
	 */
	private RemitFeeSetEnum needRemitFee;
	/**
	 * 手续费类型 固定手续费 百分比
	 */
	private RemitFeeTypeEnum remitFeeType;

	/**
	 * 客户承担手续费
	 */
	private BigDecimal remitFee;
	/**
	 * 客户不承担手续费限额
	 */
	private BigDecimal limitRemitFee;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public Integer getRiskDay() {
		return riskDay;
	}

	public void setRiskDay(Integer riskDay) {
		this.riskDay = riskDay;
	}

	public Long getMaxDays() {
		return maxDays;
	}

	public void setMaxDays(Long maxDays) {
		this.maxDays = maxDays;
	}

	public BigDecimal getExtraFee() {
		return extraFee;
	}

	public void setExtraFee(BigDecimal extraFee) {
		this.extraFee = extraFee;
	}

	public BigDecimal getRemitFee() {
		return remitFee;
	}

	public void setRemitFee(BigDecimal remitFee) {
		this.remitFee = remitFee;
	}

	public BigDecimal getLimitRemitFee() {
		return limitRemitFee;
	}

	public void setLimitRemitFee(BigDecimal limitRemitFee) {
		this.limitRemitFee = limitRemitFee;
	}

	public RemitFeeTypeEnum getRemitFeeType() {
		return remitFeeType;
	}

	public void setRemitFeeType(RemitFeeTypeEnum remitFeeType) {
		this.remitFeeType = remitFeeType;
	}

	public RemitFeeSetEnum getNeedRemitFee() {
		return needRemitFee;
	}

	public void setNeedRemitFee(RemitFeeSetEnum needRemitFee) {
		this.needRemitFee = needRemitFee;
	}

	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("账户账号=" + this.getAccountNo());
		sbf.append(";最低结算金额=" + this.getMinAmount());
		sbf.append(";风险预存期=" + this.getRiskDay());
		sbf.append(";最长结算天数=" + this.getMaxDays());
		sbf.append(";临时结算时，额外加收的手续费=" + this.getExtraFee());
		sbf.append(";是否计算手续费=" + this.getNeedRemitFee());
		sbf.append(";手续费类型=" + this.getRemitFeeType());
		sbf.append(";客户承担手续费=" + this.getRemitFee());
		return sbf.toString();

	}
}
