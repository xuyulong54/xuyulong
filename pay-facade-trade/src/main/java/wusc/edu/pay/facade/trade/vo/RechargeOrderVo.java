package wusc.edu.pay.facade.trade.vo;

import java.io.Serializable;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.trade.exception.TradeBizException;


/**
 * 
 * @描述: 充值订单VO.
 * @作者: WuShuicheng.
 * @创建: 2014-7-16,下午1:35:26
 * @版本: V1.0
 * 
 */
public class RechargeOrderVo implements Serializable {

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

	/** 付款人编号 **/
	private String payerUserNo;

	/** 付款人类型 **/
	private Integer payerAccountType;

	/** 付款人名称 **/
	private String payerName;

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

	/** 订单来源(参考枚举:OrderFromTypeEnum) **/
	private Integer orderFrom;

	/** 货币类型(参考枚举:CurrencyTypeEnum) **/
	private Integer cur;

	/** 订单加密类型(参考枚举:OrderEncryptTypeEnum) **/
	private Integer orderEncryptType;

	/** 支付方式编号 **/
	private String payWayCode;

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

		if (StringUtil.isEmpty(merchantOrderNo)) {
			throw TradeBizException.PARAM_ORDER_NO_ERROR;
		}

		if (merchantOrderNo.length() > 30) {
			throw TradeBizException.PARAM_ORDER_NO_ERROR;
		}

	}

	public Integer getPayerAccountType() {
		return payerAccountType;
	}

	public void setPayerAccountType(Integer payerAccountType) {
		this.payerAccountType = payerAccountType;
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

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

}
