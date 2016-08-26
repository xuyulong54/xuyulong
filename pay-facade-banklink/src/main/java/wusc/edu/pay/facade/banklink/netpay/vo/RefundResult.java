package wusc.edu.pay.facade.banklink.netpay.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.facade.banklink.netpay.enums.BankRefundTradeStatusEnum;


/**
 * 退款请求返回vo
 * 
 * @author Administrator
 * 
 */
public class RefundResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8762628896299909360L;

	/** 银行退款订单 */
	private String bankRefundOrderNo;

	/** 原支付接口 */
	private String orgPayInterface;

	/** 原银行订单 */
	private String orgBankOrderNo;

	/** 退款金额 */
	private BigDecimal refundAmount;

	/** 退款状态 */
	private BankRefundTradeStatusEnum refundStatus;

	/** 退款账户 */
	private String refundAccountNo;

	/** 退款交易批次号 */
	private String refundBatchNo;

	/** 退款的银行交易流水号（凭证号） */
	private String refundBankTrxNo;

	/** 退款处理时间 */
	private Date refundProcessTime;

	/** 错误信息 */
	private String errorMsg;

	
	
	
	/** 银行退款订单 */
	public String getBankRefundOrderNo() {
		return bankRefundOrderNo;
	}

	/** 银行退款订单 */
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

	/** 原银行订单 */
	public String getOrgBankOrderNo() {
		return orgBankOrderNo;
	}

	/** 原银行订单 */
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

	/** 退款状态 */
	public BankRefundTradeStatusEnum getRefundStatus() {
		return refundStatus;
	}

	/** 退款状态 */
	public void setRefundStatus(BankRefundTradeStatusEnum refundStatus) {
		this.refundStatus = refundStatus;
	}

	/** 退款账户 */
	public String getRefundAccountNo() {
		return refundAccountNo;
	}

	/** 退款账户 */
	public void setRefundAccountNo(String refundAccountNo) {
		this.refundAccountNo = refundAccountNo;
	}

	/** 退款交易批次号 */
	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	/** 退款交易批次号 */
	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}

	/** 退款的银行交易流水号（凭证号） */
	public String getRefundBankTrxNo() {
		return refundBankTrxNo;
	}

	/** 退款的银行交易流水号（凭证号） */
	public void setRefundBankTrxNo(String refundBankTrxNo) {
		this.refundBankTrxNo = refundBankTrxNo;
	}

	/** 退款处理时间 */
	public Date getRefundProcessTime() {
		return refundProcessTime;
	}

	/** 退款处理时间 */
	public void setRefundProcessTime(Date refundProcessTime) {
		this.refundProcessTime = refundProcessTime;
	}

	/** 错误信息 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/** 错误信息 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
