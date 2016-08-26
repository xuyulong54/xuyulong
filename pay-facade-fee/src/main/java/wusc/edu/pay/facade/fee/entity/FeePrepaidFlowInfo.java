package wusc.edu.pay.facade.fee.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 
 * Desc: 费率包量包笔信息实体
 * @author lichao
 * Date: 2014-7-1
 *
 */
public class FeePrepaidFlowInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 费率计费方式Id
	 */
	private Long feeCalWayId;
	/**
	 * 包量金额
	 */
	private double amount;
	/**
	 * 包量笔数
	 */
	private Long quantity;
	/**
	 * 包量类型-----枚举：FeePrepaidFlowTypeEnum
	 */
	private Integer flowType;
	/**
	 * 阶梯周期类型------枚举：LadderCycleTypeEnum
	 */
	private Integer cycleType;
	/**
	 * 自定义阶梯周期类型------枚举：LadderCycleTypeEnum
	 */
	private Integer customizeCycleType;
	/**
	 * 自定义阶梯周期日
	 */
	private Integer customizeDay;
	/**
	 * 预付金额
	 */
	private double prepaidAmount;
	
	public Long getFeeCalWayId() {
		return feeCalWayId;
	}
	public void setFeeCalWayId(Long feeCalWayId) {
		this.feeCalWayId = feeCalWayId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Integer getFlowType() {
		return flowType;
	}
	public void setFlowType(Integer flowType) {
		this.flowType = flowType;
	}
	public Integer getCycleType() {
		return cycleType;
	}
	public void setCycleType(Integer cycleType) {
		this.cycleType = cycleType;
	}
	public Integer getCustomizeCycleType() {
		return customizeCycleType;
	}
	public void setCustomizeCycleType(Integer customizeCycleType) {
		this.customizeCycleType = customizeCycleType;
	}
	public Integer getCustomizeDay() {
		return customizeDay;
	}
	public void setCustomizeDay(Integer customizeDay) {
		this.customizeDay = customizeDay;
	}
	public double getPrepaidAmount() {
		return prepaidAmount;
	}
	public void setPrepaidAmount(double prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}
	
	
}
