package wusc.edu.pay.facade.banklink.netpay.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.facade.banklink.netpay.enums.BankCardTypeEnum;
import wusc.edu.pay.facade.banklink.netpay.enums.BankTradeStatusEnum;


/**
 * 支付完成后验证vo
 * 
 * @author Administrator
 * 
 */
public class NotifyResult implements Serializable {

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

	public String getResponseStr() {
		return responseStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

}
