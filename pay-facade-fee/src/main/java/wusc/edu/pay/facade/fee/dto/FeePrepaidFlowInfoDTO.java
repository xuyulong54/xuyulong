package wusc.edu.pay.facade.fee.dto;

import java.io.Serializable;

/**
 * 包量信息DTO
 * @desc  
 * @author shenjialong
 * @date  2014-7-1,下午2:43:26
 */
@SuppressWarnings("serial")
public class FeePrepaidFlowInfoDTO implements Serializable {
	/**
	 * 包量周期类型 LadderCycleTypeEnum
	 */
	private Integer flowCycleType;
	/**
	 * 自定义包量周期类型
	 */
	private Integer customizeFlowCycleType;
	/**
	 * 自定义周期日
	 */
	private Integer customizeFlowDay;
	/**
	 * 预付费金额
	 */
	private Double prepaidAmount;
	/**
	 * 流量笔数
	 */
	private Double flowAmount;
	/**
	 * 流量金额
	 */
	private Long flowQuantity;
	/**
	 * 流量类型 FeePrepaidFlowTypeEnum
	 */
	private Integer flowType;
	public Integer getFlowCycleType() {
		return flowCycleType;
	}
	public void setFlowCycleType(Integer flowCycleType) {
		this.flowCycleType = flowCycleType;
	}
	public Integer getCustomizeFlowCycleType() {
		return customizeFlowCycleType;
	}
	public void setCustomizeFlowCycleType(Integer customizeFlowCycleType) {
		this.customizeFlowCycleType = customizeFlowCycleType;
	}
	public Integer getCustomizeFlowDay() {
		return customizeFlowDay;
	}
	public void setCustomizeFlowDay(Integer customizeFlowDay) {
		this.customizeFlowDay = customizeFlowDay;
	}
	public Double getPrepaidAmount() {
		return prepaidAmount;
	}
	public void setPrepaidAmount(Double prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}
	public Double getFlowAmount() {
		return flowAmount;
	}
	public void setFlowAmount(Double flowAmount) {
		this.flowAmount = flowAmount;
	}
	public Long getFlowQuantity() {
		return flowQuantity;
	}
	public void setFlowQuantity(Long flowQuantity) {
		this.flowQuantity = flowQuantity;
	}
	public Integer getFlowType() {
		return flowType;
	}
	public void setFlowType(Integer flowType) {
		this.flowType = flowType;
	}
	
	
	
	
}
