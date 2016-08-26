package wusc.edu.pay.facade.report.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 
 * @描述: 在线会员报表数据表（统计会员金钱有关的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-19, 上午1:08:21 .
 * @版本: V1.0.
 * 
 */
public class OnlineMemberReport extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -430941723215322207L;
	private String realName;// 会员真实姓名
	private String longinName;// 会员登陆名
	private Integer trxType;// 交易类型
	private Double transactionAmount;// 交易金额
	private Integer transactionNumber;// 交易笔数
	private Double totalFee;// 总手续费
	private Date acountDate;// 统计日期

	public Integer getTrxType() {
		return trxType;
	}

	public void setTrxType(Integer trxType) {
		this.trxType = trxType;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLonginName() {
		return longinName;
	}

	public void setLonginName(String longinName) {
		this.longinName = longinName;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Integer getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Date getAcountDate() {
		return acountDate;
	}

	public void setAcountDate(Date acountDate) {
		this.acountDate = acountDate;
	}

}
