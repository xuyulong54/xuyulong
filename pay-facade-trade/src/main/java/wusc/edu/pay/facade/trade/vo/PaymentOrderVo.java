package wusc.edu.pay.facade.trade.vo;

import java.io.Serializable;
import java.util.Date;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.trade.exception.TradeBizException;


/**
 * 
 * @author Administrator
 * 
 */
public class PaymentOrderVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8337822502481512229L;

	/** 商户订单号 **/
	private String merchantOrderNo;

	/** 商户编号 **/
	private String merchantNo;

	/** 商户名称 **/
	private String merchantName;

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
	private String orderAmount;

	/** 商品名称 **/
	private String productName;

	/** 商品描述 **/
	private String productDesc;

	/** 页面回调通知URL **/
	private String returnUrl;

	/** 后台异步通知URL **/
	private String notifyUrl;

	/** 订单来源(参考枚举:OrderFromTypeEnum) **/
	private Integer orderFrom;

	/** 货币类型(参考枚举:CurrencyTypeEnum) **/
	private Integer cur;

	/** 订单加密类型(参考枚举:OrderEncryptTypeEnum) **/
	private Integer orderEncryptType;

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

	/**
	 * 回调附加信息
	 */
	private String callbackPara;

	/** 支付方式编号 **/
	private String payWayCode;

	/** 订单有效期(单位分钟) **/
	private Integer orderPeriod;

	/** 交易模式类型 **/
	private Integer trxModel;

	/** 分账明细 **/
	private String splitDetail;

	/** 付款人登录名 **/
	private String payerLoginName;

	/**
	 * 风险预存期天数
	 */
	private Integer riskDay;

	/**
	 * 参数检查
	 */
	public void validateParam() {

		if (!ValidateUtils.isDoubleAnd2decimals(orderAmount)) {
			throw TradeBizException.PARAM_ORDER_AMOUNT_ERROR;
		}

		if (AmountUtil.bigger(0, Double.parseDouble(orderAmount))) {
			throw TradeBizException.PARAM_ORDER_AMOUNT_ERROR;
		}

		if (StringUtil.isEmpty(productName)) {
			throw TradeBizException.PARAM_PRODUCT_NAME_ERROR;
		}

		if (!ValidateUtils.isURL(returnUrl.trim())) {
			throw TradeBizException.PARAM_RETURN_URL_ERROR;
		}

		if (!ValidateUtils.isURL(notifyUrl.trim())) {
			throw TradeBizException.PARAM_NOTIFY_URL_ERROR;
		}

		if (StringUtil.isEmpty(merchantOrderNo)) {
			throw TradeBizException.PARAM_ORDER_NO_ERROR;
		}
		// 验证订单过期时间是否正确
		if (orderPeriod != null && !ValidateUtils.isNumeric(orderPeriod.toString())) {
			throw TradeBizException.EXPIRED_PARAM_IS_ERROR;
		}

		if (merchantOrderNo.length() > 30) {
			throw TradeBizException.PARAM_ORDER_NO_ERROR;
		}

	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderIp() {
		return orderIp;
	}

	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp;
	}

	public String getOrderRefererUrl() {
		return orderRefererUrl;
	}

	public void setOrderRefererUrl(String orderRefererUrl) {
		this.orderRefererUrl = orderRefererUrl;
	}

	public String getOrderOperatorLoginName() {
		return orderOperatorLoginName;
	}

	public void setOrderOperatorLoginName(String orderOperatorLoginName) {
		this.orderOperatorLoginName = orderOperatorLoginName;
	}

	public String getOrderOperatorRealName() {
		return orderOperatorRealName;
	}

	public void setOrderOperatorRealName(String orderOperatorRealName) {
		this.orderOperatorRealName = orderOperatorRealName;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Integer getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}

	public Integer getCur() {
		return cur;
	}

	public void setCur(Integer cur) {
		this.cur = cur;
	}

	public Integer getOrderEncryptType() {
		return orderEncryptType;
	}

	public void setOrderEncryptType(Integer orderEncryptType) {
		this.orderEncryptType = orderEncryptType;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneePostCode() {
		return consigneePostCode;
	}

	public void setConsigneePostCode(String consigneePostCode) {
		this.consigneePostCode = consigneePostCode;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getConsigneeEmail() {
		return consigneeEmail;
	}

	public void setConsigneeEmail(String consigneeEmail) {
		this.consigneeEmail = consigneeEmail;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getBuyerleaveMessage() {
		return buyerleaveMessage;
	}

	public void setBuyerleaveMessage(String buyerleaveMessage) {
		this.buyerleaveMessage = buyerleaveMessage;
	}

	public String getCallbackPara() {
		return callbackPara;
	}

	public void setCallbackPara(String callbackPara) {
		this.callbackPara = callbackPara;
	}

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

	public Integer getOrderPeriod() {
		return orderPeriod;
	}

	public void setOrderPeriod(Integer orderPeriod) {
		this.orderPeriod = orderPeriod;
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

	public String getPayerLoginName() {
		return payerLoginName;
	}

	public void setPayerLoginName(String payerLoginName) {
		this.payerLoginName = payerLoginName;
	}

	public Integer getRiskDay() {
		return riskDay;
	}

	public void setRiskDay(Integer riskDay) {
		this.riskDay = riskDay;
	}

}
