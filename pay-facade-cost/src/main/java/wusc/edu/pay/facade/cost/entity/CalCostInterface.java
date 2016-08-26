package wusc.edu.pay.facade.cost.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;



public class CalCostInterface extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7868170322740219486L;

	private Date modifyTime;

	private String interfaceCode;

	private String interfaceName;

	private Integer chargeType;

	private Integer billCycle;

	private Integer customBillCycle;

	private Date customBillDay;

	private Integer status;

	private Integer policy;

	private String remark;

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode == null ? null : interfaceCode.trim();
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName == null ? null : interfaceName.trim();
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public Integer getBillCycle() {
		return billCycle;
	}

	public void setBillCycle(Integer billCycle) {
		this.billCycle = billCycle;
	}

	public Integer getCustomBillCycle() {
		return customBillCycle;
	}

	public void setCustomBillCycle(Integer customBillCycle) {
		this.customBillCycle = customBillCycle;
	}

	public Date getCustomBillDay() {
		return customBillDay;
	}

	public void setCustomBillDay(Date customBillDay) {
		this.customBillDay = customBillDay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPolicy() {
		return policy;
	}

	public void setPolicy(Integer policy) {
		this.policy = policy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

}