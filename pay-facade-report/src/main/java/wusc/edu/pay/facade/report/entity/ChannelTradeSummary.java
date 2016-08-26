package wusc.edu.pay.facade.report.entity;

import java.math.BigDecimal;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 渠道交易汇总报表
 * 
 * @author Administrator
 * 
 */
public class ChannelTradeSummary extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1644499552068158313L;

	private String statDate ; // 
	private Integer reportType; // 报表类型
	private String trxDate; // 交易时间
	private String channelCode; // 渠道编号
	private String channelName; // 渠道名称
	private String channelMerchNo; // 渠道商户编号
	private String channelMerchName; // 渠道商户名称
	private Integer debitCount; // 借记总笔数
	private BigDecimal debitAmount; // 借记总金额
	private Integer creditCount; // 贷记总笔数
	private BigDecimal creditAmount; // 贷记总金额
	private Integer totalCount; // 总笔数
	private BigDecimal totalAmount; // 总金额
	private BigDecimal channelCost; // 渠道成本
	private BigDecimal platProfit; // 平台利润
	private BigDecimal agentProfit; // 代理商利润
	private BigDecimal totalFee; // 总手续费

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getStatDate() {
		return statDate;
	}

	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	public String getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelMerchNo() {
		return channelMerchNo;
	}

	public void setChannelMerchNo(String channelMerchNo) {
		this.channelMerchNo = channelMerchNo;
	}

	public String getChannelMerchName() {
		return channelMerchName;
	}

	public void setChannelMerchName(String channelMerchName) {
		this.channelMerchName = channelMerchName;
	}

	public Integer getDebitCount() {
		return debitCount;
	}

	public void setDebitCount(Integer debitCount) {
		this.debitCount = debitCount;
	}

	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}

	public Integer getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getChannelCost() {
		return channelCost;
	}

	public void setChannelCost(BigDecimal channelCost) {
		this.channelCost = channelCost;
	}

	public BigDecimal getPlatProfit() {
		return platProfit;
	}

	public void setPlatProfit(BigDecimal platProfit) {
		this.platProfit = platProfit;
	}

	public BigDecimal getAgentProfit() {
		return agentProfit;
	}

	public void setAgentProfit(BigDecimal agentProfit) {
		this.agentProfit = agentProfit;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

}
