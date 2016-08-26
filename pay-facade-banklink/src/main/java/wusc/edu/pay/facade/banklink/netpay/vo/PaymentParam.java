package wusc.edu.pay.facade.banklink.netpay.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.facade.banklink.netpay.exceptions.BankLinkBizException;


/**
 * 支付请求vo
 * 
 * @author Administrator
 * 
 */
public class PaymentParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3692177355278901753L;

	/** 支付接口（非空） */
	private String payInterface;

	/** 银行订单号 */
	private String bankOrderNo;

	/** 平台支付流水号 */
	private String paymentTrxNo;

	/** 支付金额 */
	private BigDecimal payAmount;

	/** 币种 */
	private Integer currency;

	/** 商品名称 */
	private String productName;

	/** 下单日期 */
	private Date orderDate;

	/** 客户IP */
	private String payerIp;

	/** 客户银行账户 */
	private String payerBankAccountNo;

	/** 商户编号 */
	private String merchantNo;

	/** 商户名称 */
	private String merchantName;

	/** 银行bankCode */
	private String bankCode;
	
	/** 付款人手机号 */
	private String payerMobileNo;

	/**
	 * 校验参数数据
	 */
	public void checkValue() {
		/** 支付接口 */
		if (StringUtil.isBlank(this.payInterface)) {
			throw BankLinkBizException.PAYINTERFACE_IS_NULL;
		}
		/** 银行订单号 */
		if (StringUtil.isBlank(this.bankOrderNo)) {
			throw BankLinkBizException.BANK_ORDERNO_IS_NULL;
		}
		/** 平台支付流水号 */
		if (StringUtil.isBlank(this.paymentTrxNo)) {
			throw BankLinkBizException.PAYMENTTRXNO_IS_NULL;
		}
		/** 商品名称 */
		if (StringUtil.isBlank(this.productName)) {
			throw BankLinkBizException.PRODUCTNAME_IS_NULL;
		}
		/** 客户IP */
		if (StringUtil.isBlank(this.payerIp)) {
			throw BankLinkBizException.PAYERIP_IS_NULL;
		}
		/** 客户银行账户 */
		/*
		 * if(StringUtils.isBlank(this.payerBankAccountNo)){ throw
		 * BankLinkBizException.PAYER_BANKACCOUNTNO_IS_NULL; }
		 */
		/** 商户编号 */
		/*
		 * if(StringUtils.isBlank(this.merchantNo)){ throw
		 * BankLinkBizException.MERCHANTNO_IS_NULL; }
		 */
		/** 下单日期 */
		if (this.orderDate == null) {
			throw BankLinkBizException.ORDERDATE_IS_NULL;
		}
		/** 币种 */
		/*
		 * if(this.currency == null ){ throw
		 * BankLinkBizException.CURRENCY_IS_NULL; }
		 */
		/** 金额 */
		if (this.payAmount == null) {
			throw BankLinkBizException.PAYAMOUNT_IS_NULL;
		}
	}

	public String getPayInterface() {
		return payInterface;
	}

	public void setPayInterface(String payInterface) {
		this.payInterface = payInterface;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public String getPaymentTrxNo() {
		return paymentTrxNo;
	}

	public void setPaymentTrxNo(String paymentTrxNo) {
		this.paymentTrxNo = paymentTrxNo;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getPayerIp() {
		return payerIp;
	}

	public void setPayerIp(String payerIp) {
		this.payerIp = payerIp;
	}

	public String getPayerBankAccountNo() {
		return payerBankAccountNo;
	}

	public void setPayerBankAccountNo(String payerBankAccountNo) {
		this.payerBankAccountNo = payerBankAccountNo;
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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPayerMobileNo() {
		return payerMobileNo;
	}

	public void setPayerMobileNo(String payerMobileNo) {
		this.payerMobileNo = payerMobileNo;
	}

}
