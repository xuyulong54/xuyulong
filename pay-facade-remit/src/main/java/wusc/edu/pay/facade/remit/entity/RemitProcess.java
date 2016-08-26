package wusc.edu.pay.facade.remit.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @Title:打款处理记录实体
 * @Description:
 * @author zzh
 * @date 2014-7-22 上午11:56:10
 */
public class RemitProcess extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String requestNo; // 打款请求号

	private String flowNo; // 打款流水号

	private String channelCode; // 打款通道编号

	private String batchNo; // 打款批次号

	private Integer tradeInitiator; // 业务发起方

	private Integer accountType; // 帐户类型 :1-对公，2-对私

	private String accountName; // 收款人银行账户名称

	private String accountNo; // 收款人银行卡号

	private String bankChannelNo; // 收款银行行号

	private String bankName; // 收款银行名称

	private String province; // 省份

	private String city; // 城市

	private String currency; // 币种

	private String clearBankName; // 清算行名称

	private String clearBankChannelNo; // 清算行行号

	/** RemitProcessStatusEnum */
	private Integer status; // 状态:1-待打款,2-处理中,3-打款成功,4-打款失败,5-已撤销,6-已重出,7-平台原因造成的打款失败

	private Integer isAutoProcess; // 是否自动处理

	private Integer isUrgent; // 是否加急

	private BigDecimal amount = BigDecimal.ZERO; // 金额

	private String orderId; // 银行订单

	private BigDecimal calCost = BigDecimal.ZERO; // 计费成本

	private Integer isReconciled; // 是否已对账:100-是，101-否

	private String userNo; // 用户编号

	private Integer businessType; // 业务类型

	private String creator; // 创建人

	private Date createDate; // 创建时间

	private String confirm; // 审核人

	private Date confirmDate; // 审核时间

	private Date processDate; // 打款处理时间

	private String reason; // 退回原因

	private String bankRemark; // 银行备注信息

	private String exportBatchNo; // 打款导出批次号

	/**
	 * @return 打款请求号
	 */
	public String getRequestNo() {
		return requestNo;
	}

	/**
	 * @param 打款请求号
	 */
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	/**
	 * @return 打款流水号
	 */
	public String getFlowNo() {
		return flowNo;
	}

	/**
	 * @param 打款流水号
	 */
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	/**
	 * @return 打款通道编号
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @param 打款通道编号
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * @return 打款批次号
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param 打款批次号
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return 业务发起方
	 */
	public Integer getTradeInitiator() {
		return tradeInitiator;
	}

	/**
	 * @param 业务发起方
	 */
	public void setTradeInitiator(Integer tradeInitiator) {
		this.tradeInitiator = tradeInitiator;
	}

	/**
	 * @return 账户类型：1-对公，2-对私
	 */
	public Integer getAccountType() {
		return accountType;
	}

	/**
	 * @param 账户类型
	 *            ：1-对公，2-对私
	 */
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return 开户名
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param 开户名
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return 收款帐户编号
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param 收款帐户编号
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/** 银行行号 */
	public String getBankChannelNo() {
		return bankChannelNo;
	}

	/** 银行行号 */
	public void setBankChannelNo(String bankChannelNo) {
		this.bankChannelNo = bankChannelNo;
	}

	/**
	 * @return 开户银行名称
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param 开户银行名称
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return 省份
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param 省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return 城市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param 城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return 币种
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param 币种
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return 清算行名称
	 */
	public String getClearBankName() {
		return clearBankName;
	}

	/**
	 * @param 清算行名称
	 */
	public void setClearBankName(String clearBankName) {
		this.clearBankName = clearBankName;
	}

	/** 清算行行号 */
	public String getClearBankChannelNo() {
		return clearBankChannelNo;
	}

	/** 清算行行号 */
	public void setClearBankChannelNo(String clearBankChannelNo) {
		this.clearBankChannelNo = clearBankChannelNo;
	}

	/**
	 * @return 状态：1-待打款,2-处理中,3-打款成功,4-打款失败,5-已撤销,6-已重出,7-平台原因造成的打款失败
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param 状态
	 *            ：1-待打款,2-处理中,3-打款成功,4-打款失败,5-已撤销,6-已重出,7-平台原因造成的打款失败
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return 是否自动处理：100-是，101-否
	 */
	public Integer getIsAutoProcess() {
		return isAutoProcess;
	}

	/**
	 * @param 是否自动处理
	 *            ：100-是，101-否
	 */
	public void setIsAutoProcess(Integer isAutoProcess) {
		this.isAutoProcess = isAutoProcess;
	}

	/**
	 * @return 是否加急：100-是，101-否
	 */
	public Integer getIsUrgent() {
		return isUrgent;
	}

	/**
	 * @param 是否加急
	 *            ：100-是，101-否
	 */
	public void setIsUrgent(Integer isUrgent) {
		this.isUrgent = isUrgent;
	}

	/**
	 * @return 金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param 金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return 银行订单
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param 银行订单
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 是否已对账：100-是，101-否
	 */
	public Integer getIsReconciled() {
		return isReconciled;
	}

	/**
	 * @param 是否已对账
	 *            ：100-是，101-否
	 */
	public void setIsReconciled(Integer isReconciled) {
		this.isReconciled = isReconciled;
	}

	/**
	 * @return 创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return 打款处理时间
	 */
	public Date getProcessDate() {
		return processDate;
	}

	/**
	 * @param 打款处理时间
	 */
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	/**
	 * @return 退回原因
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param 退回原因
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return 银行备注信息
	 */
	public String getBankRemark() {
		return bankRemark;
	}

	/**
	 * @param 银行备注信息
	 */
	public void setBankRemark(String bankRemark) {
		this.bankRemark = bankRemark;
	}

	/**
	 * @return 用户编号
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * @param 用户编号
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/**
	 * @return 业务类型
	 */
	public Integer getBusinessType() {
		return businessType;
	}

	/**
	 * @param 业务类型
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	/**
	 * @return 创建人
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param 创建人
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return 审核人
	 */
	public String getConfirm() {
		return confirm;
	}

	/**
	 * @param 审核人
	 */
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	/**
	 * @return 审核时间
	 */
	public Date getConfirmDate() {
		return confirmDate;
	}

	/**
	 * @param 审核时间
	 */
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	/**
	 * @return 计费成本
	 */
	public BigDecimal getCalCost() {
		if (calCost == null) {
			return new BigDecimal(0);
		}
		return calCost;
	}

	/**
	 * @param 计费成本
	 */
	public void setCalCost(BigDecimal calCost) {
		this.calCost = calCost;
	}

	/**
	 * @return 打款导出批次号
	 */
	public String getExportBatchNo() {
		return exportBatchNo;
	}

	/**
	 * @param 打款导出批次号
	 */
	public void setExportBatchNo(String exportBatchNo) {
		this.exportBatchNo = exportBatchNo;
	}

}