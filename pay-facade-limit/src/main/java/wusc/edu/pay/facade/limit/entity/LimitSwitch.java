package wusc.edu.pay.facade.limit.entity;

import java.io.Serializable;
import java.util.List;

import wusc.edu.pay.common.entity.BaseEntity;


public class LimitSwitch extends BaseEntity implements Serializable {

	/**
	 * 限制开关树形展现实体
	 */
	private static final long serialVersionUID = -6854394410210712818L;
	private String payProductCode; // 支付产品编号
	private String payProductName; // 支付产品名称
	private String payWayCode; // 支付方式编号
	private String selectPayProductCode; // 绑定的支付产品编号
	private Long payRuleId; // 支付规则编号
	private String selectPayWayCode; // 选中的支付方式编号
	private String bizFunctionCode;//业务功能编号
	private String bizFunctionName;//业务功能名称
	
	private boolean isSelectPayProductCode;
	private boolean isSelectPayWayCode;
	private boolean isSelectBizFunctionCode;

	public String getBizFunctionCode() {
		return bizFunctionCode;
	}

	public void setBizFunctionCode(String bizFunctionCode) {
		this.bizFunctionCode = bizFunctionCode;
	}

	public String getBizFunctionName() {
		return bizFunctionName;
	}

	public void setBizFunctionName(String bizFunctionName) {
		this.bizFunctionName = bizFunctionName;
	}

	public boolean getIsSelectBizFunctionCode() {
		return isSelectBizFunctionCode;
	}

	public void setIsSelectBizFunctionCode(boolean isSelectBizFunctionCode) {
		this.isSelectBizFunctionCode = isSelectBizFunctionCode;
	}

	private List<LimitSwitch> payWayCodeList ;
	
	public List<LimitSwitch> getPayWayCodeList() {
		return payWayCodeList;
	}

	public void setPayWayCodeList(List<LimitSwitch> payWayCodeList) {
		this.payWayCodeList = payWayCodeList;
	}

	public boolean getIsSelectPayProductCode() {
		return isSelectPayProductCode;
	}

	public void setIsSelectPayProductCode(boolean isSelectPayProductCode) {
		this.isSelectPayProductCode = isSelectPayProductCode;
	}

	public boolean getIsSelectPayWayCode() {
		return isSelectPayWayCode;
	}

	public void setIsSelectPayWayCode(boolean isSelectPayWayCode) {
		this.isSelectPayWayCode = isSelectPayWayCode;
	}

	public String getPayProductCode() {
		return payProductCode;
	}

	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode;
	}

	public String getPayProductName() {
		return payProductName;
	}

	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName;
	}

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

	public String getSelectPayProductCode() {
		return selectPayProductCode;
	}

	public void setSelectPayProductCode(String selectPayProductCode) {
		this.selectPayProductCode = selectPayProductCode;
	}

	public Long getPayRuleId() {
		return payRuleId;
	}

	public void setPayRuleId(Long payRuleId) {
		this.payRuleId = payRuleId;
	}

	public String getSelectPayWayCode() {
		return selectPayWayCode;
	}

	public void setSelectPayWayCode(String selectPayWayCode) {
		this.selectPayWayCode = selectPayWayCode;
	}

	public boolean equals(Object obj) {
		LimitSwitch s = (LimitSwitch) obj;
		return payProductCode.equals(s.payProductCode) && payProductName.equals(s.getPayProductName());
	}

	public int hashCode() {
		String in = payProductCode + payProductName;
		return in.hashCode();
	}

}
