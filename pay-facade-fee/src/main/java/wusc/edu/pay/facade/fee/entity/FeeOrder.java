package wusc.edu.pay.facade.fee.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


public class FeeOrder extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 计费方式ID
	 */
	private Long calculateWayId;
	
	/**
	 * 交易流水号
	 */
	private String trxFlowNo;
	
	/**
	 * 父交易流水号（退款订单有值）
	 */
	private String parentFlowNo;
	
	/**
	 * 交易发生日期
	 */
	private Date trxDate;
	
	/**
	 * 商户编号
	 */
	private String merchantNo;
	
	/**
	 * 商户名称
	 */
	private String merchantName;
	
	/**
	 * 商户订单编号
	 */
	private String merchantOrderNo;
	
	/**
	 * * 用户类型
	 */
	private Integer userType;
	
	/**
	 * 业务类型
	 */
	private Integer trxType;
	
	/**
	 * 计费项 CalculateFeeItemEnum
	 */
	private Integer calculateFeeItem;
	
	/**
	 * 支付产品（计费使用）
	 */
	private String payProduct;
	
	/**
	 * 支付方式（计费使用）
	 */
	private String frpCode;
	
	/**
	 * 原支付产品
	 */
	private String olPayProduct;
	
	/**
	 * 原支付方式
	 */
	private String olFrpCode;
	
	/**
	 * 支付接口
	 */
	private String bankChannelCode;
	
	/**
	 * 计费角色 FeeRoleTypeEnum
	 */
	private Integer feeRole;
	
	/**
	 * 费率计算方式 FeeCalculateTypeEnum
	 */
	private Integer calculateType;
	
	/**
	 * 费率基数
	 */
	private String feeBase;
	
	/**
	 * 交易金额
	 */
	private Double amount;
	
	/**
	 * 总手续费
	 */
	private Double payAllFee;
	
	/**
	 * 收款方实际手续费
	 */
	private Double payeeFee;
	
	/**
	 * 收款方收入
	 */
	private Double payeeUnBackFee;
	
	/**
	 * 付款方实际手续费
	 */
	private Double payerFee;
	
	/**
	 * 付款方收入
	 */
	private Double payerUnBackFee;
	
	/**
	 * 收费类型（实收、后收） FeeChargeTypeEnum
	 */
	private Integer chargeType;
	
	/**
	 * 收费周期 LadderCycleTypeEnum
	 */
	private Integer chargePeriodic;

	/**
	 * 订单类型 FeeOrderTypeEnum
	 */
	private Integer orderType;
	

	/**
	 * 费率订单状态 FeeOrderStatusEnum.java
	 */
	private Integer status;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 计费时间
	 */
	private Date calculateDate;

	/**
	 * 实收时间
	 */
	private Date confirmDate;

	public Long getCalculateWayId() {
		return calculateWayId;
	}

	public void setCalculateWayId(Long calculateWayId) {
		this.calculateWayId = calculateWayId;
	}

	public String getTrxFlowNo() {
		return trxFlowNo;
	}

	public void setTrxFlowNo(String trxFlowNo) {
		this.trxFlowNo = trxFlowNo;
	}

	public String getParentFlowNo() {
		return parentFlowNo;
	}

	public void setParentFlowNo(String parentFlowNo) {
		this.parentFlowNo = parentFlowNo;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
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

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}


	public Integer getTrxType() {
		return trxType;
	}

	public void setTrxType(Integer trxType) {
		this.trxType = trxType;
	}

	public Integer getCalculateFeeItem() {
		return calculateFeeItem;
	}

	public void setCalculateFeeItem(Integer calculateFeeItem) {
		this.calculateFeeItem = calculateFeeItem;
	}

	public String getPayProduct() {
		return payProduct;
	}

	public void setPayProduct(String payProduct) {
		this.payProduct = payProduct;
	}

	public String getFrpCode() {
		return frpCode;
	}

	public void setFrpCode(String frpCode) {
		this.frpCode = frpCode;
	}

	public String getOlPayProduct() {
		return olPayProduct;
	}

	public void setOlPayProduct(String olPayProduct) {
		this.olPayProduct = olPayProduct;
	}

	public String getOlFrpCode() {
		return olFrpCode;
	}

	public void setOlFrpCode(String olFrpCode) {
		this.olFrpCode = olFrpCode;
	}

	public String getBankChannelCode() {
		return bankChannelCode;
	}

	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}

	public Integer getFeeRole() {
		return feeRole;
	}

	public void setFeeRole(Integer feeRole) {
		this.feeRole = feeRole;
	}

	public Integer getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(Integer calculateType) {
		this.calculateType = calculateType;
	}

	public String getFeeBase() {
		return feeBase;
	}

	public void setFeeBase(String feeBase) {
		this.feeBase = feeBase;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getPayAllFee() {
		return payAllFee;
	}

	public void setPayAllFee(Double payAllFee) {
		this.payAllFee = payAllFee;
	}

	public Double getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Double payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Double getPayeeUnBackFee() {
		return payeeUnBackFee;
	}

	public void setPayeeUnBackFee(Double payeeUnBackFee) {
		this.payeeUnBackFee = payeeUnBackFee;
	}

	public Double getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Double payerFee) {
		this.payerFee = payerFee;
	}

	public Double getPayerUnBackFee() {
		return payerUnBackFee;
	}

	public void setPayerUnBackFee(Double payerUnBackFee) {
		this.payerUnBackFee = payerUnBackFee;
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public Integer getChargePeriodic() {
		return chargePeriodic;
	}

	public void setChargePeriodic(Integer chargePeriodic) {
		this.chargePeriodic = chargePeriodic;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCalculateDate() {
		return calculateDate;
	}

	public void setCalculateDate(Date calculateDate) {
		this.calculateDate = calculateDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	
}
