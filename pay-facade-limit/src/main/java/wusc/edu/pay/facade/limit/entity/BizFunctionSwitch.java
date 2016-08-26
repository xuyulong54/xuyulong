/**
 * wusc.edu.pay.facade.limit.params.BizFunctionSwitch.java
 */
package wusc.edu.pay.facade.limit.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.enums.SwitchLimitStatusEnum;


/**
 * 
 * 
*<ul>
*<li>Title: 业务功能</li>
*<li>Description: </li>
*<li>Copyright: www.gzzyzz.com</li>
*<li>Company:</li>
*</ul>
*
 * @author Hill
 * @version 2014-7-16
 */
public class BizFunctionSwitch extends BaseEntity {

	private static final long serialVersionUID = -7909588551891798154L;

	private Long switchLimitPackId;

	/**
	 * 业务功能
	 */
	private String bizFunction;

	/**
	 * 状态
	 */
	private SwitchLimitStatusEnum status;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;	
	
	/**
	 * 用于展现
	 * @return
	 */
	public String getBizFunctionDesc(){
		return this.getBizFunction()==null?"":LimitTrxTypeEnum.valueOf(this.getBizFunction()).getDesc();
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
	
	public String getStatusDesc() {
		return this.getStatus() == null ? "" : this.getStatus().getLabel();
	}

}
