package wusc.edu.pay.facade.report.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 
 * @描述: POS商户报表数据表.
 * @作者: Lanzy.
 * @创建时间: 2014-4-19, 下午10:32:11 .
 * @版本: V1.0.
 * 
 */
public class PosMerchantReport extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8583158396180996582L;
	private String fullName;// 公司全称
	private String merchantNo;// 商户编号
	private Integer merchantType;// 商户类型：1、企业，2、个体工商，3、个人
	private Integer trxType;// 交易类型
	private String channelName;// 渠道名称
	private String channelNo;// 渠道编号
	private Double transactionAmount;// 交易金额
	private Integer transactionNumber;// 交易笔数
	private Double income;// 收入
	private Double cost;// 成本
	private Double grossProfit;// 毛利
	private Date acountDate;// 统计日期

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Integer getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
	}

	public Integer getTrxType() {
		return trxType;
	}

	public void setTrxType(Integer trxType) {
		this.trxType = trxType;
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

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
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

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

}
