package wusc.edu.pay.facade.report.entity;

import java.math.BigDecimal;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 商户交易汇总报表
 * 代理商利润统计报表
 * 
 * @author Administrator
 * 
 */
public class MerchTradeSummary extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1737538247665496460L;

	private String statDate ; // 
	private Integer reportType; // 报表类型
	private String trxDate; // 交易时间
	private String merchantNo; // 商户编号
	private String merchantName; // 商户名称
	private String firstAgentNo; // 一级代理商编号
	private String firstAgentName; // 一级代理商名称
	private String secondAgentNo; // 二级代理商编号
	private String secondAgentName; // 二级代理商名称
	private String thirdAgentNo; // 三级代理商编号
	private String thirdAgentName; // 三级代理商名称
	private Integer debitCount; // 借记总笔数
	private BigDecimal debitAmount; // 借记总金额
	private Integer creditCount; // 贷记总笔数
	private BigDecimal creditAmount; // 贷记总金额
	private Integer totalCount; // 总笔数
	private BigDecimal totalAmount; // 总金额
	private BigDecimal totalFee; // 总手续费
	private BigDecimal settAmount; // 清算金额
	private BigDecimal bankCost; // 银行成本
	private BigDecimal platProfit; // 平台利润
	private BigDecimal agentProfit; // 代理商利润
	private BigDecimal firstAgentCost; // 一级代理商成本
	private BigDecimal firstAgentProfit; // 一级代理商分润
	private BigDecimal secondAgentProfit; // 二级代理商分润
	private BigDecimal thridAgentProfit; // 三级代理商分润
	
	private BigDecimal t0Fee;  // T + 0 手续费
	private Integer settType;	// 结算方式，（1=t0,2=t1,3=t5）
	
	private String bankAccountNo; // 银行收款账号
	private String bankAccountName; // 银行收款账户名
	private String bankChannelNo; // 银行联行号
	private String bankName; // 银行名称
	private String bankAccountType;//银行卡类型


	private BigDecimal income; // 收入
	private BigDecimal expend; // 支出
	private BigDecimal profit; // 利润
	
	private String salesNo; 	// 销售人员编号
	private String salesName; 	// 销售人员姓名
	private int agentNum;		// 销售人员拓展的代理商个数
	private int agentLevel;     //代理商级别
	private String parentAgentNo;     //父级代理商编号
	private String parentAgentName;     //父级代理商名称
	private String parentUpAgentNo;     //父父级代理商编号
	private String parentUpAgentName;     //父父级代理商名称
	
	private String channelNo;//渠道编号
	private String channelName;//渠道名称
	
	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getExpend() {
		return expend;
	}

	public void setExpend(BigDecimal expend) {
		this.expend = expend;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

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

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getFirstAgentNo() {
		return firstAgentNo;
	}

	public void setFirstAgentNo(String firstAgentNo) {
		this.firstAgentNo = firstAgentNo;
	}

	public String getFirstAgentName() {
		return firstAgentName;
	}

	public void setFirstAgentName(String firstAgentName) {
		this.firstAgentName = firstAgentName;
	}

	public String getSecondAgentNo() {
		return secondAgentNo;
	}

	public void setSecondAgentNo(String secondAgentNo) {
		this.secondAgentNo = secondAgentNo;
	}

	public String getSecondAgentName() {
		return secondAgentName;
	}

	public void setSecondAgentName(String secondAgentName) {
		this.secondAgentName = secondAgentName;
	}

	public String getThirdAgentNo() {
		return thirdAgentNo;
	}

	public void setThirdAgentNo(String thirdAgentNo) {
		this.thirdAgentNo = thirdAgentNo;
	}

	public String getThirdAgentName() {
		return thirdAgentName;
	}

	public void setThirdAgentName(String thirdAgentName) {
		this.thirdAgentName = thirdAgentName;
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

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getSettAmount() {
		return settAmount;
	}

	public void setSettAmount(BigDecimal settAmount) {
		this.settAmount = settAmount;
	}

	public BigDecimal getBankCost() {
		return bankCost;
	}

	public void setBankCost(BigDecimal bankCost) {
		this.bankCost = bankCost;
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

	public BigDecimal getFirstAgentCost() {
		return firstAgentCost;
	}

	public void setFirstAgentCost(BigDecimal firstAgentCost) {
		this.firstAgentCost = firstAgentCost;
	}

	public BigDecimal getFirstAgentProfit() {
		return firstAgentProfit;
	}

	public void setFirstAgentProfit(BigDecimal firstAgentProfit) {
		this.firstAgentProfit = firstAgentProfit;
	}

	public BigDecimal getSecondAgentProfit() {
		return secondAgentProfit;
	}

	public void setSecondAgentProfit(BigDecimal secondAgentProfit) {
		this.secondAgentProfit = secondAgentProfit;
	}

	public BigDecimal getThridAgentProfit() {
		return thridAgentProfit;
	}

	public void setThridAgentProfit(BigDecimal thridAgentProfit) {
		this.thridAgentProfit = thridAgentProfit;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankChannelNo() {
		return bankChannelNo;
	}

	public void setBankChannelNo(String bankChannelNo) {
		this.bankChannelNo = bankChannelNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSalesNo() {
		return salesNo;
	}

	public void setSalesNo(String salesNo) {
		this.salesNo = salesNo;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public int getAgentNum() {
		return agentNum;
	}

	public void setAgentNum(int agentNum) {
		this.agentNum = agentNum;
	}

	public BigDecimal getT0Fee() {
		return t0Fee;
	}

	public void setT0Fee(BigDecimal t0Fee) {
		this.t0Fee = t0Fee;
	}

	public Integer getSettType() {
		return settType;
	}

	public void setSettType(Integer settType) {
		this.settType = settType;
	}

	public int getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(int agentLevel) {
		this.agentLevel = agentLevel;
	}

	public String getParentAgentNo() {
		return parentAgentNo;
	}

	public void setParentAgentNo(String parentAgentNo) {
		this.parentAgentNo = parentAgentNo;
	}

	public String getParentAgentName() {
		return parentAgentName;
	}

	public void setParentAgentName(String parentAgentName) {
		this.parentAgentName = parentAgentName;
	}

	public String getParentUpAgentNo() {
		return parentUpAgentNo;
	}

	public void setParentUpAgentNo(String parentUpAgentNo) {
		this.parentUpAgentNo = parentUpAgentNo;
	}

	public String getParentUpAgentName() {
		return parentUpAgentName;
	}
	public void setParentUpAgentName(String parentUpAgentName) {
		this.parentUpAgentName = parentUpAgentName;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
