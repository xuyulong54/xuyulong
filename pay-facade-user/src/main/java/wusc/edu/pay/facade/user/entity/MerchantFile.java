package wusc.edu.pay.facade.user.entity;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 商户资质文件
 * @author huangbin
 *
 */
public class MerchantFile extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2174743552183942314L;
	
	private String merchantNo;			// 商户编号
	private String tradeLicence;		// 营业执照照片
	private String openAccountLicence;	// 开户许可证照
	private String cardPositive; 		// 身份证正面照
	private String cardNegative;		// 身份证反面照
	private String handleCard;			// 手持身份证照
	private String bankCard;			// 银行卡照
	private String bankCardNegative;	// 银行卡反面照
	private String cashier;				// 收银台照
	private String tradeAddress;		// 经营场所照
	private String doorHead;			// 门头照
	private String other;				// 其他
	
	
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getTradeLicence() {
		return tradeLicence;
	}
	public void setTradeLicence(String tradeLicence) {
		this.tradeLicence = tradeLicence;
	}
	public String getOpenAccountLicence() {
		return openAccountLicence;
	}
	public void setOpenAccountLicence(String openAccountLicence) {
		this.openAccountLicence = openAccountLicence;
	}
	public String getCardPositive() {
		return cardPositive;
	}
	public void setCardPositive(String cardPositive) {
		this.cardPositive = cardPositive;
	}
	public String getCardNegative() {
		return cardNegative;
	}
	public void setCardNegative(String cardNegative) {
		this.cardNegative = cardNegative;
	}
	public String getHandleCard() {
		return handleCard;
	}
	public void setHandleCard(String handleCard) {
		this.handleCard = handleCard;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	public String getTradeAddress() {
		return tradeAddress;
	}
	public void setTradeAddress(String tradeAddress) {
		this.tradeAddress = tradeAddress;
	}
	public String getDoorHead() {
		return doorHead;
	}
	public void setDoorHead(String doorHead) {
		this.doorHead = doorHead;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getBankCardNegative() {
		return bankCardNegative;
	}
	public void setBankCardNegative(String bankCardNegative) {
		this.bankCardNegative = bankCardNegative;
	}
	
}
