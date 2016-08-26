/**
 * wusc.edu.pay.facade.limit.params.PayWaySwitch.java
 */
package wusc.edu.pay.facade.limit.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.limit.enums.SwitchLimitStatusEnum;


/**
 * 
 * 
 * <ul>
 * <li>Title: 支付方式开关</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-8
 */
public class PayWaySwitch extends BaseEntity {

	private static final long serialVersionUID = 8123619256315303658L;

	/**
	 * 开关限制包Id
	 */
	private Long switchLimitPackId;

	/**
	 * 业务功能
	 */
	private String bizFunction;

	/**
	 * 支付产品
	 */
	private String payProduct;

	/**
	 * 支付方式
	 */
	private String payWay;

	/**
	 * 状态
	 */
	private SwitchLimitStatusEnum status;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;
	
	/**
	 * 用于列表页面展示
	 * 
	 * @return
	 */
	public String getStatusDesc() {
		return this.getStatus() == null ? "" : this.getStatus().getLabel();
	}
	
	
	public Long getSwitchLimitPackId() {
		return switchLimitPackId;
	}

	public void setSwitchLimitPackId(Long switchLimitPackId) {
		this.switchLimitPackId = switchLimitPackId;
	}

	public String getBizFunction() {
		return bizFunction;
	}

	public void setBizFunction(String bizFunction) {
		this.bizFunction = bizFunction;
	}

	public String getPayProduct() {
		return payProduct;
	}

	public void setPayProduct(String payProduct) {
		this.payProduct = payProduct;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public SwitchLimitStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SwitchLimitStatusEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
