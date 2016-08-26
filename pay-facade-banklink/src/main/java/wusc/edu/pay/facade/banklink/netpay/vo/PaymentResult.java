package wusc.edu.pay.facade.banklink.netpay.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import wusc.edu.pay.facade.banklink.netpay.enums.BankTradeStatusEnum;



/**
 * 
 * 
 * @author Administrator
 * 
 */
public class PaymentResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3214910405189675362L;

	/** 银行订单号 */
	private String bankOrderNo;

	/** 银行交易流水号 */
	private String bankTrxNo;

	/**
	 * 支付金额
	 */
	private BigDecimal orderAmount;

	/** 状态 */
	private BankTradeStatusEnum bankTradeStatusEnum;

	/**
	 * 是否异步通知交易结果
	 */
	private boolean isAsyncNotify;

	/** 错误信息 */
	private String errorMsg;

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public String getBankTrxNo() {
		return bankTrxNo;
	}

	public void setBankTrxNo(String bankTrxNo) {
		this.bankTrxNo = bankTrxNo;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BankTradeStatusEnum getBankTradeStatusEnum() {
		return bankTradeStatusEnum;
	}

	public void setBankTradeStatusEnum(BankTradeStatusEnum bankTradeStatusEnum) {
		this.bankTradeStatusEnum = bankTradeStatusEnum;
	}

	public boolean isAsyncNotify() {
		return isAsyncNotify;
	}

	public void setAsyncNotify(boolean isAsyncNotify) {
		this.isAsyncNotify = isAsyncNotify;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
