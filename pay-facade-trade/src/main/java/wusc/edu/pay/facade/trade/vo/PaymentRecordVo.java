package wusc.edu.pay.facade.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author Administrator
 * 
 */
public class PaymentRecordVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3975009761530906542L;

	/** 商户订单号 **/
	private String merchantOrderNo;

	/** 支付流水号 **/
	private String trxNo;

	/** 银行订单号 **/
	private String bankOrderNo;

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
	private BigDecimal payerAmountFee = BigDecimal.ZERO;

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

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getTrxNo() {
		return trxNo;
	}

	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
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

	public Integer getReceiverAccountType() {
		return receiverAccountType;
	}

	public void setReceiverAccountType(Integer receiverAccountType) {
		this.receiverAccountType = receiverAccountType;
	}

	public String getPayerUserNo() {
		return payerUserNo;
	}

	public void setPayerUserNo(String payerUserNo) {
		this.payerUserNo = payerUserNo;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public Integer getPayerAccountType() {
		return payerAccountType;
	}

	public void setPayerAccountType(Integer payerAccountType) {
		this.payerAccountType = payerAccountType;
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

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getPayerAmountFee() {
		return payerAmountFee;
	}

	public void setPayerAmountFee(BigDecimal payerAmountFee) {
		this.payerAmountFee = payerAmountFee;
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

	public Integer getStatus() {
		return status;
	}

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

	public Integer getTrxModel() {
		return trxModel;
	}

	public void setTrxModel(Integer trxModel) {
		this.trxModel = trxModel;
	}

	public String getPayProductCode() {
		return payProductCode;
	}

	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode;
	}

	public String getPayProductName() {
		return payProductName;
	}

	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName;
	}

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

	public String getPayWayName() {
		return payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public String getPayInterfaceCode() {
		return payInterfaceCode;
	}

	public void setPayInterfaceCode(String payInterfaceCode) {
		this.payInterfaceCode = payInterfaceCode;
	}

	public String getPayInterfaceName() {
		return payInterfaceName;
	}

	public void setPayInterfaceName(String payInterfaceName) {
		this.payInterfaceName = payInterfaceName;
	}

}
