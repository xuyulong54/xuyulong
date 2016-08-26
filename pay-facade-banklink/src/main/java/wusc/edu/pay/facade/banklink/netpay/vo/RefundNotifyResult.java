package wusc.edu.pay.facade.banklink.netpay.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import wusc.edu.pay.facade.banklink.netpay.enums.BankRefundTradeStatusEnum;


/**
 * 退款完成后验证vo
 * 
 * @author Administrator
 * 
 */
public class RefundNotifyResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3929565355870158716L;

	/** 支付接口（非空） */
	private String payInterface;

	/**
	 * 是否验签通过
	 */
	private boolean isVerify;

	/** 银行订单号 */
	private String bankOrderNo;

	/** 退款银行订单号 */
	private String bankRefundOrderNo;

	/** 退款状态（如果不能确定是否为已付或未付则为其它，已付确认需谨慎） */
	private BankRefundTradeStatusEnum status;

	/** 银行退款状态（银行原始支付状态） */
	private String bankStatus;

	/** 退款金额 */
	private BigDecimal refundAmount;

	/** 成功后回写字符串 **/
	private String responseStr;

	public String getPayInterface() {
		return payInterface;
	}

	public void setPayInterface(String payInterface) {
		this.payInterface = payInterface;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public String getBankStatus() {
		return bankStatus;
	}

	public void setBankStatus(String bankStatus) {
		this.bankStatus = bankStatus;
	}

	public String getBankRefundOrderNo() {
		return bankRefundOrderNo;
	}

	public void setBankRefundOrderNo(String bankRefundOrderNo) {
		this.bankRefundOrderNo = bankRefundOrderNo;
	}

	public BankRefundTradeStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BankRefundTradeStatusEnum status) {
		this.status = status;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getResponseStr() {
		return responseStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

}
