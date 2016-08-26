package wusc.edu.pay.facade.fee.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 流量累加器
 * @desc  
 * @author shenjialong
 * @date  2014-6-27,上午10:51:07
 */
public class FeeFlowAccumulator extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 计费方式ID
	 */
	private Long calculateWayId;
	
	/**
	 * 商户编号
	 */
	private String merchantNo;
	
	/**
	 * 总金额
	 */
	private Double totalAmount = 0D;
	
	/**
	 * 本次累加金额
	 */
	private Double thisAmount = 0D;
	
	/**
	 * 累计区间起
	 */
	private Date accIntervalStart;
	
	/**
	 * 累计区间止
	 */
	private Date accIntervalEnd;
	
	/**
	 * 最近累加日期
	 */
	private Date modifyDate;

	public Long getCalculateWayId() {
		return calculateWayId;
	}

	public void setCalculateWayId(Long calculateWayId) {
		this.calculateWayId = calculateWayId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getThisAmount() {
		return thisAmount;
	}

	public void setThisAmount(Double thisAmount) {
		this.thisAmount = thisAmount;
	}

	public Date getAccIntervalStart() {
		return accIntervalStart;
	}

	public void setAccIntervalStart(Date accIntervalStart) {
		this.accIntervalStart = accIntervalStart;
	}

	public Date getAccIntervalEnd() {
		return accIntervalEnd;
	}

	public void setAccIntervalEnd(Date accIntervalEnd) {
		this.accIntervalEnd = accIntervalEnd;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}
