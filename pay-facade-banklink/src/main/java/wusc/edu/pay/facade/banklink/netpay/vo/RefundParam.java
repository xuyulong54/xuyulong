package wusc.edu.pay.facade.banklink.netpay.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 退款请求vo
 * 
 * @author Administrator
 * 
 */
public class RefundParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3929565355870158716L;

	/** 支付接口（非空） */
	private String payInterface;

	/** 银行退款订单号 */
	private String bankRefundOrderNo;

	/** 原支付接口 */
	private String orgPayInterface;

	/** 原银行订单号 */
	private String orgBankOrderNo;

	/** 退款金额 */
	private BigDecimal refundAmount;

	/** 原银行交易时间 */
	private Date orgBankTradeTime;

	/** 原银行交易流水号 */
	private String orgBankTrxNo;
	
	/**支付银行编码（用来区分原支付订单是否为跨行支付订单）*/
	private String bankCode;
	
	/** 支付接口（非空） */
	public String getPayInterface() {
		return payInterface;
	}

	/** 支付接口（非空） */
	public void setPayInterface(String payInterface) {
		this.payInterface = payInterface;
	}

	/** 银行退款订单号 */
	public String getBankRefundOrderNo() {
		return bankRefundOrderNo;
	}

	/** 银行退款订单号 */
	public void setBankRefundOrderNo(String bankRefundOrderNo) {
		this.bankRefundOrderNo = bankRefundOrderNo;
	}

	/** 原支付接口 */
	public String getOrgPayInterface() {
		return orgPayInterface;
	}

	/** 原支付接口 */
	public void setOrgPayInterface(String orgPayInterface) {
		this.orgPayInterface = orgPayInterface;
	}

	/** 原银行订单号 */
	public String getOrgBankOrderNo() {
		return orgBankOrderNo;
	}

	/** 原银行订单号 */
	public void setOrgBankOrderNo(String orgBankOrderNo) {
		this.orgBankOrderNo = orgBankOrderNo;
	}

	/** 退款金额 */
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	/** 退款金额 */
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	/** 原银行交易时间 */
	public Date getOrgBankTradeTime() {
		return orgBankTradeTime;
	}

	/** 原银行交易时间 */
	public void setOrgBankTradeTime(Date orgBankTradeTime) {
		this.orgBankTradeTime = orgBankTradeTime;
	}

	/** 原银行交易流水号 */
	public String getOrgBankTrxNo() {
		return orgBankTrxNo;
	}

	/** 原银行交易流水号 */
	public void setOrgBankTrxNo(String orgBankTrxNo) {
		this.orgBankTrxNo = orgBankTrxNo;
	}

	/**支付银行编码（用来区分原支付订单是否为跨行支付订单）*/
	public String getBankCode() {
		return bankCode;
	}

	/**支付银行编码（用来区分原支付订单是否为跨行支付订单）*/
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
