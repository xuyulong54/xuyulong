package wusc.edu.pay.facade.remit.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.common.enums.PublicStatusEnum;


/**
 * 
 * @Title: 打款请求实体
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午1:39:13
 */
public class RemitRequest extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/* 打款请求号 */
	private String requestNo;
	/* 打款流水号 */
	private String flowNo;
	/* 业务发起方 1-会员提现，2-商户结算 */
	private Integer tradeInitiator;
	/* 帐户类型 1-对公，2-对私 */
	private Integer accountType;
	/* 是否加急 100-是，101-否 */
	private Integer isUrgent;
	/* 开户名 */
	private String accountName;
	/* 收款帐户编号 */
	private String accountNo;
	/* 银行行号 */
	private String bankChannelNo;
	/* 开户银行名称 */
	private String bankName;
	/* 省份 */
	private String province;
	/* 城市 */
	private String city;
	/* 币种 */
	private String currency;
	/* 结果通知地址 */
	private String notifyAddress;
	/* 状态 */
	private Integer status;
	/* 请求金额 */
	private BigDecimal amount = BigDecimal.ZERO;
	/* 是否自动处理 */
	private Integer isAutoProcess;
	/* 用户编号 */
	private String userNo;
	/* 业务类型 */
	private Integer businessType;
	/* 创建人 */
	private String creator;
	/* 创建时间 */
	private Date createDate;
	/* 审核人 */
	private String confirm;
	/* 审核时间 */
	private Date confirmDate;
	/* 撤销原因 */
	private String cancelReason;
	/* 银行备注信息 */
	private String bankRemark;
	/* 原打款ID */
	private Long orialId;
	/* 是否已重新制单 */
	private Integer copied = Integer.valueOf(PublicStatusEnum.INACTIVE.getValue());
	/*批次号*/
	private String batchNo;
	
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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
	 * @return 业务发起方:1-会员提现，2-商户结算
	 */
	public Integer getTradeInitiator() {
		return tradeInitiator;
	}

	/**
	 * @param 业务发起方
	 *            :1-会员提现，2-商户结算
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
	 * @return 结果通知地址
	 */
	public String getNotifyAddress() {
		return notifyAddress;
	}

	/**
	 * @param 结果通知地址
	 */
	public void setNotifyAddress(String notifyAddress) {
		this.notifyAddress = notifyAddress;
	}

	/**
	 * @return 状态：1-待审核,2-已审核,3-打款中,4-打款成功,5-打款失败,6-已撤销
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param 状态
	 *            ：1-待审核,2-已审核,3-打款中,4-打款成功,5-打款失败,6-已撤销
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return 请求金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param 请求金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	 * @return 撤销原因
	 */
	public String getCancelReason() {
		return cancelReason;
	}

	/**
	 * @param 撤销原因
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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
	 * @param 原打款ID
	 */
	public Long getOrialId() {
		return orialId;
	}

	/**
	 * @param 原打款ID
	 */
	public void setOrialId(Long orialId) {
		this.orialId = orialId;
	}

	/**
	 * @param 是否已重新制单
	 */
	public Integer getCopied() {
		return copied;
	}

	/**
	 * @param 是否已重新制单
	 */
	public void setCopied(Integer copied) {
		this.copied = copied;
	}

}