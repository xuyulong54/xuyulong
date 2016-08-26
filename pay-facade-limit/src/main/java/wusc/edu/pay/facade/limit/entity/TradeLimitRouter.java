/**
 * wusc.edu.pay.facade.limit.params.TradeLimitRouter.java
 */
package wusc.edu.pay.facade.limit.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * 
 * <ul>
 * <li>Title: 商户关联开关限制和额度限制的中间关联表</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-8
 */
public class TradeLimitRouter extends BaseEntity {

	private static final long serialVersionUID = -1230772407286863981L;

	/**
	 * 商户号
	 */
	private String merchantNo;

	/**
	 * 商户关联的开关限制包Id
	 */
	private Long switchLimitPackId;

	/**
	 * 商户关联的额度限制包Id
	 */
	private Long amountLimitPackId;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;

	public TradeLimitRouter() {
		super();
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Long getSwitchLimitPackId() {
		return switchLimitPackId;
	}

	public void setSwitchLimitPackId(Long switchLimitPackId) {
		this.switchLimitPackId = switchLimitPackId;
	}

	public Long getAmountLimitPackId() {
		return amountLimitPackId;
	}

	public void setAmountLimitPackId(Long amountLimitPackId) {
		this.amountLimitPackId = amountLimitPackId;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
