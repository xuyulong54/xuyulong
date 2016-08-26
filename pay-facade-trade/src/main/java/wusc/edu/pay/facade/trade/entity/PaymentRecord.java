package wusc.edu.pay.facade.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;


/**
 * 
 * @描述: 支付记录实体.
 * @作者: HuQian,WuShuicheng.
 * @创建: 2014-5-8,上午10:17:39
 * @版本: V1.0
 * 
 */
public class PaymentRecord extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9055994737874932755L;

	/** 创建日期 **/
	private Date createDate;

	/** 最后修改时间 **/
	private Date modifyTime;

	/** 商户订单号 **/
	private String merchantOrderNo;

	/** 支付流水号 **/
	private String trxNo;

	/** 银行订单号 **/
	private String bankOrderNo;

	/** 银行流水号 **/
	private String bankTrxNo;

	/** 商户编号 **/
	private String merchantNo;

	/** 商户名称 **/
	private String merchantName;

	/** 收款方账户类型(参考账户类型枚举:AccountTypeEnum) **/
	private Integer receiverAccountType;

	/** 付款人编号 **/
	private String payerUserNo;

	/** 付款人名称 **/
	private String payerName;

	/** 付款方账户类型(参考账户类型枚举:AccountTypeEnum) **/
	private Integer payerAccountType;

	/** 下单IP(客户端IP,从网关中获取) **/
	private String orderIp;

	/** 从哪个页面链接过来的(可用于防诈骗) **/
	private String orderRefererUrl;

	/** 订单金额 **/
	private BigDecimal orderAmount = BigDecimal.ZERO;

	/** 付款方手续费 **/
	private BigDecimal payerFee = BigDecimal.ZERO;

	/** 付款方支付金额 **/
	private BigDecimal payerPayAmount = BigDecimal.ZERO;

	/** 收款方手续费 **/
	private BigDecimal receiverFee = BigDecimal.ZERO;

	/** 收款方实收金额 **/
	private BigDecimal receiverPostAmount = BigDecimal.ZERO;

	/** 平台收入 **/
	private BigDecimal platIncome = BigDecimal.ZERO;

	// /** 平台成本 **/
	// private BigDecimal platCost = BigDecimal.ZERO;
	//
	// /** 平台利润 **/
	// private BigDecimal platProfit = BigDecimal.ZERO;

	/** 商品名称 **/
	private String productName;

	/** 商品描述 **/
	private String productDesc;

	/** 页面回调通知URL **/
	private String returnUrl;

	/** 后台异步通知URL **/
	private String notifyUrl;

	/** 订单撤销原因 **/
	private String cancelDesc;

	/** 订单来源(参考枚举:OrderFromTypeEnum) **/
	private Integer orderFrom;

	/** 订单特殊操作(参考枚举:OrderOperateEnum) **/
	private Integer orderFlag;

	/** 货币类型(参考枚举:CurrencyTypeEnum) **/
	private Integer cur;

	/** 状态(参考枚举:PaymentRecordStatusEnum) **/
	private Integer status;

	/** 业务类型(参考枚举:TradeBizTypeEnum) **/
	private Integer bizType;

	/** 支付方式类型(参考枚举:TradePaymentTypeEnum) **/
	private Integer paymentType;

	/** 交易模式(参考枚举:TrxModelEnum) **/
	private Integer trxModel;

	/** 支付产品编号 **/
	private String payProductCode;

	/** 支付产品名称 **/
	private String payProductName;

	/** 支付方式编号 **/
	private String payWayCode;

	/** 支付方式名称 **/
	private String payWayName;

	/** 支付接口编号 **/
	private String payInterfaceCode;

	/** 支付接口名称 **/
	private String payInterfaceName;

	/** 支付接口Mcc **/
	private String payInterfaceMcc;

	/** 支付规则ID **/
	private Long payRuleId;

	/** 费率维度ID **/
	private Long feeDimensionId;

	/** 成本维度ID **/
	private Long costDimensionId;

	/** 支付成功日期 **/
	private Date paySuccessDate;

	/** 支付成功时间 **/
	private Date paySuccessTime;

	/** 完成日期 **/
	private Date completeDate;

	/** 完成时间 **/
	private Date completeTime;

	/** 撤消日期 **/
	private Date cancelDate;

	/** 撤消时间 **/
	private Date cancelTime;

	/** 是否退款(100:是,101:否,默认值为:101) **/
	private Integer isRefund = 101;

	/** 退款次数(默认值为:0) **/
	private Integer refundTimes = 0;

	/** 成功退款总金额 **/
	private BigDecimal successRefundAmount = BigDecimal.ZERO;

	/**
	 * 风险预存期天数
	 */
	private Integer riskDay;

	/**
	 * 格式化业务类型,用于展现
	 * 
	 * @return
	 */
	public String getFormatBizTypeEnumDesc() {
		if (bizType != null) {
			return TradeBizTypeEnum.getEnum(bizType) != null ? TradeBizTypeEnum.getEnum(bizType).getDesc() : "";
		}
		return "";
	}

	/**
	 * 格式化支付方式类型,用于展现
	 * 
	 * @return
	 */
	public String getFormatPaymentTypeEnumDesc() {
		if (paymentType != null) {
			return TradePaymentTypeEnum.getEnum(paymentType) != null ? TradePaymentTypeEnum.getEnum(paymentType).getDesc() : "";
		}
		return "";
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 最后修改时间 **/
	public Date getModifyTime() {
		return modifyTime;
	}

	/** 最后修改时间 **/
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/** 商户订单号 **/
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	/** 商户订单号 **/
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
	}

	/** 支付流水号 **/
	public String getTrxNo() {
		return trxNo;
	}

	/** 支付流水号 **/
	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo == null ? null : trxNo.trim();
	}

	/** 银行订单号 **/
	public String getBankOrderNo() {
		return bankOrderNo;
	}

	/** 银行订单号 **/
	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo == null ? null : bankOrderNo.trim();
	}

	/** 银行流水号 **/
	public String getBankTrxNo() {
		return bankTrxNo;
	}

	/** 银行流水号 **/
	public void setBankTrxNo(String bankTrxNo) {
		this.bankTrxNo = bankTrxNo == null ? null : bankTrxNo.trim();
	}

	/** 商户编号 **/
	public String getMerchantNo() {
		return merchantNo;
	}

	/** 商户编号 **/
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo == null ? null : merchantNo.trim();
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	public Integer getReceiverAccountType() {
		return receiverAccountType;
	}

	public void setReceiverAccountType(Integer receiverAccountType) {
		this.receiverAccountType = receiverAccountType;
	}

	/** 付款人编号 **/
	public String getPayerUserNo() {
		return payerUserNo;
	}

	/** 付款人编号 **/
	public void setPayerUserNo(String payerUserNo) {
		this.payerUserNo = payerUserNo == null ? null : payerUserNo.trim();
	}

	/** 付款人名称 **/
	public String getPayerName() {
		return payerName;
	}

	/** 付款人名称 **/
	public void setPayerName(String payerName) {
		this.payerName = payerName == null ? null : payerName.trim();
	}

	/** 付款方账户类型(参考账户类型枚举:AccountTypeEnum) **/
	public Integer getPayerAccountType() {
		return payerAccountType;
	}

	/** 付款方账户类型(参考账户类型枚举:AccountTypeEnum) **/
	public void setPayerAccountType(Integer payerAccountType) {
		this.payerAccountType = payerAccountType;
	}

	/** 下单IP(客户端IP,从网关中获取) **/
	public String getOrderIp() {
		return orderIp;
	}

	/** 下单IP(客户端IP,从网关中获取) **/
	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp == null ? null : orderIp.trim();
	}

	/** 从哪个页面链接过来的(可用于防诈骗) **/
	public String getOrderRefererUrl() {
		return orderRefererUrl;
	}

	/** 从哪个页面链接过来的(可用于防诈骗) **/
	public void setOrderRefererUrl(String orderRefererUrl) {
		this.orderRefererUrl = orderRefererUrl == null ? null : orderRefererUrl.trim();
	}

	/** 订单金额 **/
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	/** 订单金额 **/
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * 付款方手续费
	 */
	public BigDecimal getPayerFee() {
		return payerFee;
	}

	/**
	 * 付款方手续费
	 */
	public void setPayerFee(BigDecimal payerFee) {
		this.payerFee = payerFee;
	}

	/**
	 * 付款方支付金额
	 */
	public BigDecimal getPayerPayAmount() {
		return payerPayAmount;
	}

	/**
	 * 付款方支付金额
	 */
	public void setPayerPayAmount(BigDecimal payerPayAmount) {
		this.payerPayAmount = payerPayAmount;
	}

	/** 收款方手续费 **/
	public BigDecimal getReceiverFee() {
		return receiverFee;
	}

	/** 收款方手续费 **/
	public void setReceiverFee(BigDecimal receiverFee) {
		this.receiverFee = receiverFee;
	}

	/** 收款方实收金额 **/
	public BigDecimal getReceiverPostAmount() {
		return receiverPostAmount;
	}

	/** 收款方实收金额 **/
	public void setReceiverPostAmount(BigDecimal receiverPostAmount) {
		this.receiverPostAmount = receiverPostAmount;
	}

	/** 平台收入 **/
	public BigDecimal getPlatIncome() {
		return platIncome;
	}

	/** 平台收入 **/
	public void setPlatIncome(BigDecimal platIncome) {
		this.platIncome = platIncome;
	}

	/** 商品名称 **/
	public String getProductName() {
		return productName;
	}

	/** 商品名称 **/
	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	/** 商品描述 **/
	public String getProductDesc() {
		return productDesc;
	}

	/** 商品描述 **/
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc == null ? null : productDesc.trim();
	}

	/** 页面回调通知URL **/
	public String getReturnUrl() {
		return returnUrl;
	}

	/** 页面回调通知URL **/
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl == null ? null : returnUrl.trim();
	}

	/** 后台异步通知URL **/
	public String getNotifyUrl() {
		return notifyUrl;
	}

	/** 后台异步通知URL **/
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
	}

	/** 订单撤销原因 **/
	public String getCancelDesc() {
		return cancelDesc;
	}

	/** 订单撤销原因 **/
	public void setCancelDesc(String cancelDesc) {
		this.cancelDesc = cancelDesc == null ? null : cancelDesc.trim();
	}

	/** 订单来源(参考枚举:OrderFromTypeEnum) **/
	public Integer getOrderFrom() {
		return orderFrom;
	}

	/** 订单来源(参考枚举:OrderFromTypeEnum) **/
	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}

	/** 订单特殊操作(参考枚举:OrderOperateEnum) **/
	public Integer getOrderFlag() {
		return orderFlag;
	}

	/** 订单特殊操作(参考枚举:OrderOperateEnum) **/
	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}

	/** 货币类型(参考枚举:CurrencyTypeEnum) **/
	public Integer getCur() {
		return cur;
	}

	/** 货币类型(参考枚举:CurrencyTypeEnum) **/
	public void setCur(Integer cur) {
		this.cur = cur;
	}

	/** 状态(参考枚举:PaymentRecordStatusEnum) **/
	public Integer getStatus() {
		return status;
	}

	/** 状态(参考枚举:PaymentRecordStatusEnum) **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	/** 交易模式(参考枚举:TrxModelEnum) **/
	public Integer getTrxModel() {
		return trxModel;
	}

	/** 交易模式(参考枚举:TrxModelEnum) **/
	public void setTrxModel(Integer trxModel) {
		this.trxModel = trxModel;
	}

	/** 支付产品编号 **/
	public String getPayProductCode() {
		return payProductCode;
	}

	/** 支付产品编号 **/
	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode == null ? null : payProductCode.trim();
	}

	/** 支付产品名称 **/
	public String getPayProductName() {
		return payProductName;
	}

	/** 支付产品名称 **/
	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName == null ? null : payProductName.trim();
	}

	/** 支付方式编号 **/
	public String getPayWayCode() {
		return payWayCode;
	}

	/** 支付方式编号 **/
	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode == null ? null : payWayCode.trim();
	}

	/** 支付方式名称 **/
	public String getPayWayName() {
		return payWayName;
	}

	/** 支付方式名称 **/
	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName == null ? null : payWayName.trim();
	}

	/** 支付接口编号 **/
	public String getPayInterfaceCode() {
		return payInterfaceCode;
	}

	/** 支付接口编号 **/
	public void setPayInterfaceCode(String payInterfaceCode) {
		this.payInterfaceCode = payInterfaceCode == null ? null : payInterfaceCode.trim();
	}

	/** 支付接口名称 **/
	public String getPayInterfaceName() {
		return payInterfaceName;
	}

	/** 支付接口名称 **/
	public void setPayInterfaceName(String payInterfaceName) {
		this.payInterfaceName = payInterfaceName == null ? null : payInterfaceName.trim();
	}

	/** 支付规则ID **/
	public Long getPayRuleId() {
		return payRuleId;
	}

	/** 支付规则ID **/
	public void setPayRuleId(Long payRuleId) {
		this.payRuleId = payRuleId;
	}

	/** 费率维度ID **/
	public Long getFeeDimensionId() {
		return feeDimensionId;
	}

	/** 费率维度ID **/
	public void setFeeDimensionId(Long feeDimensionId) {
		this.feeDimensionId = feeDimensionId;
	}

	/** 成本维度ID **/
	public Long getCostDimensionId() {
		return costDimensionId;
	}

	/** 成本维度ID **/
	public void setCostDimensionId(Long costDimensionId) {
		this.costDimensionId = costDimensionId;
	}

	public Date getPaySuccessDate() {
		return paySuccessDate;
	}

	public void setPaySuccessDate(Date paySuccessDate) {
		this.paySuccessDate = paySuccessDate;
	}

	/** 支付成功时间 **/
	public Date getPaySuccessTime() {
		return paySuccessTime;
	}

	/** 支付成功时间 **/
	public void setPaySuccessTime(Date paySuccessTime) {
		this.paySuccessTime = paySuccessTime;
	}

	/** 完成日期 **/
	public Date getCompleteDate() {
		return completeDate;
	}

	/** 完成日期 **/
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	/** 完成时间 **/
	public Date getCompleteTime() {
		return completeTime;
	}

	/** 完成时间 **/
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	/** 撤消日期 **/
	public Date getCancelDate() {
		return cancelDate;
	}

	/** 撤消日期 **/
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	/** 撤消时间 **/
	public Date getCancelTime() {
		return cancelTime;
	}

	/** 撤消时间 **/
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	/** 是否退款(100:是,101:否,默认值为:101) **/
	public Integer getIsRefund() {
		return isRefund;
	}

	/** 是否退款(100:是,101:否,默认值为:101) **/
	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

	/** 退款次数(默认值为:0) **/
	public Integer getRefundTimes() {
		return refundTimes;
	}

	/** 退款次数(默认值为:0) **/
	public void setRefundTimes(Integer refundTimes) {
		this.refundTimes = refundTimes;
	}

	public BigDecimal getSuccessRefundAmount() {
		return successRefundAmount;
	}

	public void setSuccessRefundAmount(BigDecimal successRefundAmount) {
		this.successRefundAmount = successRefundAmount;
	}

	public String getPayInterfaceMcc() {
		return payInterfaceMcc;
	}

	public void setPayInterfaceMcc(String payInterfaceMcc) {
		this.payInterfaceMcc = payInterfaceMcc;
	}

	public Integer getRiskDay() {
		return riskDay;
	}

	public void setRiskDay(Integer riskDay) {
		this.riskDay = riskDay;
	}

}
