/**
 * wusc.edu.pay.facade.limit.params.AmountCumulate.java
 */
package wusc.edu.pay.facade.limit.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * 
 * <ul>
 * <li>Title: 交易金额累计实体记录</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-4
 */
public class AmountCumulate extends BaseEntity {

	private static final long serialVersionUID = 831786519740598014L;

	/**
	 * 交易金额限制规则ID
	 * 
	 * <pre>
	 * 关联“交易金额限制规则实体”
	 * </pre>
	 */
	private Long amountLimitId;

	/**
	 * 商户号
	 */
	private String merchantNo;

	/**
	 * 累计金额
	 */
	private BigDecimal cumulateAmount;

	/**
	 * 开始时间
	 */
	private Date beginTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;

	public AmountCumulate() {
		super();
	}

	/**
	 * 是否为新生成对象
	 * 
	 * @return
	 */
	public boolean isNew() {
		return this.getId() == null;
	}

	public Long getAmountLimitId() {
		return amountLimitId;
	}

	public void setAmountLimitId(Long amountLimitId) {
		this.amountLimitId = amountLimitId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public BigDecimal getCumulateAmount() {
		return cumulateAmount;
	}

	public void setCumulateAmount(BigDecimal cumulateAmount) {
		this.cumulateAmount = cumulateAmount;
	}
	
	/**
	 * 增加累计金额
	 * 
	 * @param amount
	 */
	public void increaseCumulateAmount(BigDecimal amount) {
		this.cumulateAmount = this.cumulateAmount.add(amount);
	}

}
