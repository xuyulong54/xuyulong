package wusc.edu.pay.facade.banklink.netpay.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.facade.banklink.netpay.enums.BankCardTypeEnum;
import wusc.edu.pay.facade.banklink.netpay.enums.BankTradeStatusEnum;


/**
 * 支付订单查询结果vo
 * 
 * @author Administrator
 * 
 */
public class PaymentQueryResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4411469809803021521L;

	/** 银行订单号 */
	private String bankOrderNo;

	/** 支付金额 */
	private BigDecimal payAmount;

	/** 支付状态（如果不能确定是否为已付或未付则为其它，已付确认需谨慎） */
	private BankTradeStatusEnum status;

	/** 银行支付状态（银行原始支付状态） */
	private String bankStatus;

	/** 银行成功时间（如果不能确定银行成功时间则放空） */
	private Date bankSuccessTime;

	/** 银行返回交易流水号 **/
	private String bankTrxNo;

	/** 银行返回用户支付卡种 **/
	private BankCardTypeEnum cardType;

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BankTradeStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BankTradeStatusEnum status) {
		this.status = status;
	}

	public String getBankStatus() {
		return bankStatus;
	}

	public void setBankStatus(String bankStatus) {
		this.bankStatus = bankStatus;
	}

	public Date getBankSuccessTime() {
		return bankSuccessTime;
	}

	public void setBankSuccessTime(Date bankSuccessTime) {
		this.bankSuccessTime = bankSuccessTime;
	}

	public String getBankTrxNo() {
		return bankTrxNo;
	}

	public void setBankTrxNo(String bankTrxNo) {
		this.bankTrxNo = bankTrxNo;
	}

	public BankCardTypeEnum getCardType() {
		return cardType;
	}

	public void setCardType(BankCardTypeEnum cardType) {
		this.cardType = cardType;
	}

}
