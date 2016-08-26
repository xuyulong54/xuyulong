package wusc.edu.pay.facade.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.enums.TrxModelEnum;


/**
 * 支付订单
 * 
 * @author chenjh
 * 
 */
public class PaymentOrder extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5316167469319565715L;

	/** 创建日期 **/
	private Date createDate;

	/** 最后修改时间 **/
	private Date modifyTime;

	/** 商户订单号 **/
	private String merchantOrderNo;

	/** 商户编号 **/
	private String merchantNo;

	/** 商户名称 **/
	private String merchantName;

	/** 付款人编号 **/
	private String payerUserNo;
	/** 付款人类型 **/
	private Integer payerUserType;

	/** 付款人名称 **/
	private String payerName;

	/** 下单时间 **/
	private Date orderTime;

	/** 下单日期 **/
	private Date orderDate;

	/** 下单IP(客户端IP,在网关页面获取) **/
	private String orderIp;

	/** 页面链接:从哪个页面链接过来的(可用于防诈骗) **/
	private String orderRefererUrl;

	/** 下单操作员登录名 **/
	private String orderOperatorLoginName;

	/** 下单操作员姓名 **/
	private String orderOperatorRealName;

	/** 订单金额 **/
	private BigDecimal orderAmount = BigDecimal.ZERO;

	/** 商户优惠金额 **/
	private BigDecimal merchantDiscountAmount = BigDecimal.ZERO;

	/** 平台优惠金额 **/
	private BigDecimal platDiscountAmount = BigDecimal.ZERO;

	/** 付款方手续费 **/
	private BigDecimal payerFee = BigDecimal.ZERO;

	/** 付款方支付金额 **/
	private BigDecimal payerPayAmount = BigDecimal.ZERO;

	/** 收款方手续费 **/
	private BigDecimal receiverFee = BigDecimal.ZERO;

	/** 收款方实收金额 **/
	private BigDecimal receiverPostAmount = BigDecimal.ZERO;

	/** 商品名称 **/
	private String productName;

	/** 商品描述 **/
	private String productDesc;

	/** 页面回调通知URL **/
	private String returnUrl;

	/** 后台异步通知URL **/
	private String notifyUrl;

	/** 订单撤销原因 **/
	private String cancelReason;

	/** 订单有效期(单位分钟) **/
	private Integer orderPeriod;

	/** 到期时间(下单时间+订单有效期) **/
	private Date expireTime;

	/** 订单来源(参考枚举:OrderFromTypeEnum) **/
	private Integer orderFrom;

	/** 订单特殊操作(参考枚举:OrderOperateEnum) **/
	private Integer orderFlag;

	/** 货币类型(参考枚举:CurrencyTypeEnum) **/
	private Integer cur;

	/** 状态(参考枚举:OrderStatusEnum) **/
	private Integer status;

	/** 订单加密类型(参考枚举:OrderEncryptTypeEnum) **/
	private Integer orderEncryptType;

	/** 业务类型(参考枚举:TradeBizTypeEnum) **/
	private Integer bizType;

	/** 支付方式类型(参考枚举:TradePaymentTypeEnum) **/
	private Integer paymentType;

	/** 支付产品编号 **/
	private String payProductCode;

	/** 支付产品名称 **/
	private String payProductName;

	/** 支付方式编号 **/
	private String payWayCode;

	/** 支付方式名称 **/
	private String payWayName;

	/** 收货人姓名 **/
	private String consigneeName;

	/** 收货人地址 **/
	private String consigneeAddress;

	/** 收货人邮编 **/
	private String consigneePostCode;

	/** 收货人手机 **/
	private String consigneeMobile;

	/** 收货人固话 **/
	private String consigneePhone;

	/** 收货人EMAIL **/
	private String consigneeEmail;

	/** 发票抬头 **/
	private String invoiceTitle;

	/** 买家留言 **/
	private String buyerleaveMessage;

	/** 回调附加信息(和商户进行数据交互时附加信息) */
	private String callbackPara;

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

	/** 成功支付流水号 */
	private String successTrxNo;

	/** 是否退款（100是，101否） */
	private Integer isRefund = 101;

	/** 成功退款次数 */
	private Integer successRefundTimes = 0;

	/** 成功退款总金额 */
	private BigDecimal successRefundAmount = BigDecimal.ZERO;

	/** 交易模式类型(参考枚举:TrxModelEnum) 设置一个默认值为即时到帐 **/
	private Integer trxModel = TrxModelEnum.IMMEDIATELY.getValue();

	/** 分账明细 **/
	private String splitDetail;

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

	/**
	 * 格式化支付状态，用于展现
	 * 
	 * @return
	 */
	public String getFormatStatusEnumDesc() {
		if (status != null) {
			return OrderStatusEnum.getEnum(status) != null ? OrderStatusEnum.getEnum(status).getDesc() : "";
		}
		return "";
	}

	/** 创建日期 **/
	public Date getCreateDate() {
		return createDate;
	}

	/** 创建日期 **/
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

	/** 商户编号 **/
	public String getMerchantNo() {
		return merchantNo;
	}

	/** 商户编号 **/
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo == null ? null : merchantNo.trim();
	}

	/** 商户名称 **/
	public String getMerchantName() {
		return merchantName;
	}

	/** 商户名称 **/
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	/** 付款人编号 **/
	public String getPayerUserNo() {
		return payerUserNo;
	}

	/** 付款人编号 **/
	public void setPayerUserNo(String payerUserNo) {
		this.payerUserNo = payerUserNo == null ? null : payerUserNo.trim();
	}

	/**
	 * payerAccountType
	 * 
	 * @return the payerAccountType
	 * @since 1.0
	 */

	public Integer getPayerUserType() {
		return payerUserType;
	}

	/**
	 * @param payerAccountType
	 *            the payerAccountType to set
	 */
	public void setPayerUserType(Integer payerUserType) {
		this.payerUserType = payerUserType;
	}

	/** 付款人名称 **/
	public String getPayerName() {
		return payerName;
	}

	/** 付款人名称 **/
	public void setPayerName(String payerName) {
		this.payerName = payerName == null ? null : payerName.trim();
	}

	/** 下单时间 **/
	public Date getOrderTime() {
		return orderTime;
	}

	/** 下单时间 **/
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/** 下单日期 **/
	public Date getOrderDate() {
		return orderDate;
	}

	/** 下单日期 **/
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/** 下单IP(客户端IP,在网关页面获取) **/
	public String getOrderIp() {
		return orderIp;
	}

	/** 下单IP(客户端IP,在网关页面获取) **/
	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp == null ? null : orderIp.trim();
	}

	/** 页面链接:从哪个页面链接过来的(可用于防诈骗) **/
	public String getOrderRefererUrl() {
		return orderRefererUrl;
	}

	/** 页面链接:从哪个页面链接过来的(可用于防诈骗) **/
	public void setOrderRefererUrl(String orderRefererUrl) {
		this.orderRefererUrl = orderRefererUrl == null ? null : orderRefererUrl.trim();
	}

	/** 下单操作员登录名 **/
	public String getOrderOperatorLoginName() {
		return orderOperatorLoginName;
	}

	/** 下单操作员登录名 **/
	public void setOrderOperatorLoginName(String orderOperatorLoginName) {
		this.orderOperatorLoginName = orderOperatorLoginName == null ? null : orderOperatorLoginName.trim();
	}

	/** 下单操作员姓名 **/
	public String getOrderOperatorRealName() {
		return orderOperatorRealName;
	}

	/** 下单操作员姓名 **/
	public void setOrderOperatorRealName(String orderOperatorRealName) {
		this.orderOperatorRealName = orderOperatorRealName == null ? null : orderOperatorRealName.trim();
	}

	/** 订单金额 **/
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	/** 订单金额 **/
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	/** 商户优惠金额 **/
	public BigDecimal getMerchantDiscountAmount() {
		return merchantDiscountAmount;
	}

	/** 商户优惠金额 **/
	public void setMerchantDiscountAmount(BigDecimal merchantDiscountAmount) {
		this.merchantDiscountAmount = merchantDiscountAmount;
	}

	/** 平台优惠金额 **/
	public BigDecimal getPlatDiscountAmount() {
		return platDiscountAmount;
	}

	/** 平台优惠金额 **/
	public void setPlatDiscountAmount(BigDecimal platDiscountAmount) {
		this.platDiscountAmount = platDiscountAmount;
	}

	/** 付款方手续费 **/
	public BigDecimal getPayerFee() {
		return payerFee;
	}

	/** 付款方手续费 **/
	public void setPayerFee(BigDecimal payerFee) {
		this.payerFee = payerFee;
	}

	/** 付款方支付金额 **/
	public BigDecimal getPayerPayAmount() {
		return payerPayAmount;
	}

	/** 付款方支付金额 **/
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
	public String getCancelReason() {
		return cancelReason;
	}

	/** 订单撤销原因 **/
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason == null ? null : cancelReason.trim();
	}

	/** 订单有效期(单位分钟) **/
	public Integer getOrderPeriod() {
		return orderPeriod;
	}

	/** 订单有效期(单位分钟) **/
	public void setOrderPeriod(Integer orderPeriod) {
		this.orderPeriod = orderPeriod;
	}

	/** 到期时间(下单时间+订单有效期) **/
	public Date getExpireTime() {
		return expireTime;
	}

	/** 到期时间(下单时间+订单有效期) **/
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
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

	/** 状态(参考枚举:OrderStatusEnum) **/
	public Integer getStatus() {
		return status;
	}

	/** 状态(参考枚举:OrderStatusEnum) **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	/** 订单加密类型(参考枚举:OrderEncryptTypeEnum) **/
	public Integer getOrderEncryptType() {
		return orderEncryptType;
	}

	/** 订单加密类型(参考枚举:OrderEncryptTypeEnum) **/
	public void setOrderEncryptType(Integer orderEncryptType) {
		this.orderEncryptType = orderEncryptType;
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

	/** 收货人姓名 **/
	public String getConsigneeName() {
		return consigneeName;
	}

	/** 收货人姓名 **/
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName == null ? null : consigneeName.trim();
	}

	/** 收货人地址 **/
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	/** 收货人地址 **/
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress == null ? null : consigneeAddress.trim();
	}

	/** 收货人邮编 **/
	public String getConsigneePostCode() {
		return consigneePostCode;
	}

	/** 收货人邮编 **/
	public void setConsigneePostCode(String consigneePostCode) {
		this.consigneePostCode = consigneePostCode == null ? null : consigneePostCode.trim();
	}

	/** 收货人手机 **/
	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	/** 收货人手机 **/
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile == null ? null : consigneeMobile.trim();
	}

	/** 收货人固话 **/
	public String getConsigneePhone() {
		return consigneePhone;
	}

	/** 收货人固话 **/
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone == null ? null : consigneePhone.trim();
	}

	/** 收货人EMAIL **/
	public String getConsigneeEmail() {
		return consigneeEmail;
	}

	/** 收货人EMAIL **/
	public void setConsigneeEmail(String consigneeEmail) {
		this.consigneeEmail = consigneeEmail == null ? null : consigneeEmail.trim();
	}

	/** 发票抬头 **/
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	/** 发票抬头 **/
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
	}

	/** 买家留言 **/
	public String getBuyerleaveMessage() {
		return buyerleaveMessage;
	}

	/** 买家留言 **/
	public void setBuyerleaveMessage(String buyerleaveMessage) {
		this.buyerleaveMessage = buyerleaveMessage == null ? null : buyerleaveMessage.trim();
	}

	/** 回调附加信息(和商户进行数据交互时附加信息) */
	public String getCallbackPara() {
		return callbackPara;
	}

	/** 回调附加信息(和商户进行数据交互时附加信息) */
	public void setCallbackPara(String callbackPara) {
		this.callbackPara = callbackPara;
	}

	/** 支付成功日期 **/
	public Date getPaySuccessDate() {
		return paySuccessDate;
	}

	/** 支付成功日期 **/
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

	/** 成功退款后回写的支付流水号 */
	public String getSuccessTrxNo() {
		return successTrxNo;
	}

	/** 成功退款后回写的支付流水号 */
	public void setSuccessTrxNo(String successTrxNo) {
		this.successTrxNo = successTrxNo;
	}

	/** 是否退款（100是，101否） */
	public Integer getIsRefund() {
		return isRefund;
	}

	/** 是否退款（100是，101否） */
	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

	/** 成功退款次数 */
	public Integer getSuccessRefundTimes() {
		return successRefundTimes;
	}

	/** 成功退款次数 */
	public void setSuccessRefundTimes(Integer successRefundTimes) {
		this.successRefundTimes = successRefundTimes;
	}

	/** 成功退款总金额 */
	public BigDecimal getSuccessRefundAmount() {
		return successRefundAmount;
	}

	/** 成功退款总金额 */
	public void setSuccessRefundAmount(BigDecimal successRefundAmount) {
		this.successRefundAmount = successRefundAmount;
	}

	public Integer getTrxModel() {
		return trxModel;
	}

	public void setTrxModel(Integer trxModel) {
		this.trxModel = trxModel;
	}

	public String getSplitDetail() {
		return splitDetail;
	}

	public void setSplitDetail(String splitDetail) {
		this.splitDetail = splitDetail;
	}

	public Integer getRiskDay() {
		return riskDay;
	}

	public void setRiskDay(Integer riskDay) {
		this.riskDay = riskDay;
	}

}
