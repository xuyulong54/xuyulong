package wusc.edu.pay.facade.report.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 
 * @描述: POS银行报表数据表.
 * @作者: Lanzy.
 * @创建时间: 2014-4-19, 下午10:31:08 .
 * @版本: V1.0.
 * 
 */
public class PosBankReport extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 675926227941411948L;
	private String bankName;// 银行名称
	// private String bankCode;//银行编号
	private String bankChannelCode;// 银行渠道编号
	private String bankChannelName;// 银行渠道名称
	private Double transactionAmount;// 交易金额
	private Integer transactionNumber;// 交易笔数
	private Double cost;// 成本
	private Double totalFee;// 总手续费
	private Double grossProfit;// 毛利
	private Date acountDate;// 统计日期
	private Integer trxType;

	public Integer getTrxType() {
		return trxType;
	}

	public void setTrxType(Integer trxType) {
		this.trxType = trxType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	// public String getBankCode() {
	// return bankCode;
	// }
	// public void setBankCode(String bankCode) {
	// this.bankCode = bankCode;
	// }
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

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(Double grossProfit) {
		this.grossProfit = grossProfit;
	}

	public Date getAcountDate() {
		return acountDate;
	}

	public void setAcountDate(Date acountDate) {
		this.acountDate = acountDate;
	}

}
