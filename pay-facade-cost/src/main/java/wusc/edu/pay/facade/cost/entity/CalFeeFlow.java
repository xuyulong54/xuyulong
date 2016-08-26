package wusc.edu.pay.facade.cost.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 计费流量实体
 */
public class CalFeeFlow extends BaseEntity {
	private static final long serialVersionUID = -8510441516898844425L;

	private Long feeWayId; // 计费约束ID

	private Date beginDate; // 开始日期

	private Date endDate; // 结束日期

	private BigDecimal totalAmount = BigDecimal.ZERO; // 总金额

	private BigDecimal thisAmount = BigDecimal.ZERO; // 本次金额

	private Date modifyTime; // 修改时间

	/**
	 * 计费约束ID
	 * 
	 * @return
	 */
	public Long getFeeWayId() {
		return feeWayId;
	}

	/**
	 * 计费约束ID
	 * 
	 * @param feeWayId
	 */
	public void setFeeWayId(Long feeWayId) {
		this.feeWayId = feeWayId;
	}

	/**
	 * 开始日期
	 * 
	 * @return
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 开始日期
	 * 
	 * @param beginDate
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 结束日期
	 * 
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 结束日期
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 总金额
	 * 
	 * @return
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 总金额
	 * 
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 本次金额
	 * 
	 * @return
	 */
	public BigDecimal getThisAmount() {
		return thisAmount;
	}

	/**
	 * 本次金额
	 * 
	 * @param thisAmount
	 */
	public void setThisAmount(BigDecimal thisAmount) {
		this.thisAmount = thisAmount;
	}

	/**
	 * 修改时间
	 * 
	 * @return
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 修改时间
	 * 
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}