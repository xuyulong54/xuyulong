package wusc.edu.pay.facade.report.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 
 * @描述: 在线银行报表数据表.
 * @作者: Lanzy.
 * @创建时间: 2014-4-18, 下午11:57:08 .
 * @版本: V1.0.
 * 
 */
public class OnlineBankReport extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5476636969516669482L;
	private String bankName;// 银行名称
	private String bankCode;// 银行编号
	private Integer trxType;// 交易类型
	private String bankChannelCode;// 银行渠道编号
	private String bankChannelName;// 银行渠道名称
	private Double transactionAmount;// 交易金额
	private Integer transactionNumber;// 交易笔数
	private Double refundAmount;// 退款金额
	private Integer refundNumber;// 退款笔数
	private Double totalFee;// 总手续费
	private Date acountDate;// 统计日期


	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Integer getTrxType() {
		return trxType;
	}

	public void setTrxType(Integer trxType) {
		this.trxType = trxType;
	}

	public String getBankChannelCode() {
		return bankChannelCode;
	}

	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}

	public String getBankChannelName() {
		return bankChannelName;
	}

	public void setBankChannelName(String bankChannelName) {
		this.bankChannelName = bankChannelName;
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

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getRefundNumber() {
		return refundNumber;
	}

	public void setRefundNumber(Integer refundNumber) {
		this.refundNumber = refundNumber;
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
