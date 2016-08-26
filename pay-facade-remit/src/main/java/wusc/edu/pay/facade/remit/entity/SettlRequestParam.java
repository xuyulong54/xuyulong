package wusc.edu.pay.facade.remit.entity;

import java.io.Serializable;

/**
 * 商户打款请求参数实体
 * 
 * @author： Peter
 * @ClassName: MerchantSettlRequestParam.java
 * @Date： 2014-7-25 上午10:33:37
 * @version： V1.0
 */
public class SettlRequestParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 打款请求号：保证系统唯一 */
	private String requestNo;
	/** 是否加急:调用枚举类PublicStatusEnum */
	private Integer isUrgent;
	/** 收款人银行户名 */
	private String bankAccountName;
	/** 收款人银行账号 */
	private String bankAccountNo;
	/** 收款银行名称 */
	private String bankName;
	/** 收款银行行号 */
	private String bankChannelNo;
	/** 结算金额 */
	private Double amount;
	/** 用户编号 */
	private String userNo;
	/** 账户编号 */
	private String accountNo;
	/** 收款账号省份 */
	private String province;
	/** 收款账号城市 */
	private String city;
	/** 银行账户类型:调用枚举类 BankAccountTypeEnum */
	private Integer bankAccountType;
	/** 业务类型:调用枚举类 TradeTypeEnum */
	private Integer tradeType;
	/** 业务来源:调用枚举类 TradeSourcesEnum */
	private Integer tradeSource;

	/** 业务类型:调用枚举类TradeTypeEnum */
	public Integer getTradeType() {
		return tradeType;
	}

	/** 业务类型:调用枚举类TradeTypeEnum */
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	/** 业务来源:调用枚举类TradeSourcesEnum */
	public Integer getTradeSource() {
		return tradeSource;
	}

	/** 业务来源:调用枚举类TradeSourcesEnum */
	public void setTradeSource(Integer tradeSource) {
		this.tradeSource = tradeSource;
	}

	/** 用户编号 */
	public String getUserNo() {
		return userNo;
	}

	/** 用户编号 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/** 打款请求号：保证系统唯一 */
	public String getRequestNo() {
		return requestNo;
	}

	/** 打款请求号：保证系统唯一 */
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	/** 收款人银行户名 */
	public String getBankAccountName() {
		return bankAccountName;
	}

	/** 收款人银行户名 */
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	/** 收款银行名称 */
	public String getBankName() {
		return bankName;
	}

	/** 收款银行名称 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/** 收款银行行号 */
	public String getBankChannelNo() {
		return bankChannelNo;
	}

	/** 收款银行行号 */
	public void setBankChannelNo(String bankChannelNo) {
		this.bankChannelNo = bankChannelNo;
	}

	/** 提现金额 */
	public Double getAmount() {
		return amount;
	}

	/** 提现金额 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/** 是否加急:调用枚举类PublicStatusEnum */
	public Integer getIsUrgent() {
		return isUrgent;
	}

	/** 是否加急:调用枚举类PublicStatusEnum */
	public void setIsUrgent(Integer isUrgent) {
		this.isUrgent = isUrgent;
	}

	/** 银行账户类型:调用枚举类BankAccountTypeEnum */
	public void setBankAccountType(Integer bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	/** 银行账户类型:调用枚举类BankAccountTypeEnum */
	public Integer getBankAccountType() {
		return bankAccountType;
	}

	/** 账户编号 */
	public String getAccountNo() {
		return accountNo;
	}

	/** 账户编号 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/** 收款账号省份 */
	public String getProvince() {
		return province;
	}

	/** 收款账号省份 */
	public void setProvince(String province) {
		this.province = province;
	}

	/** 收款账号城市 */
	public String getCity() {
		return city;
	}

	/** 收款账号城市 */
	public void setCity(String city) {
		this.city = city;
	}

}
