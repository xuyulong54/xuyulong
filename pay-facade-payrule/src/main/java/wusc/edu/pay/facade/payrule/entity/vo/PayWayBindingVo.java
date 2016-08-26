package wusc.edu.pay.facade.payrule.entity.vo;

import java.io.Serializable;

/***
 * 支付规则下的支付产品和支付规则实体
 * @author Administrator
 *
 */
public class PayWayBindingVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1666952338531878736L;
	// 支付规则
	private Long payRuleId;
	// 支付产品编号
	private String payProductCode;
	// 支付规则编号
	private String payWayCode;
	// 是否被选中
	private boolean use ;
	
	
	public Long getPayRuleId() {
		return payRuleId;
	}
	public void setPayRuleId(Long payRuleId) {
		this.payRuleId = payRuleId;
	}
	public String getPayProductCode() {
		return payProductCode;
	}
	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode;
	}
	public String getPayWayCode() {
		return payWayCode;
	}
	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}
	public boolean isUse() {
		return use;
	}
	public void setUse(boolean use) {
		this.use = use;
	}
}
