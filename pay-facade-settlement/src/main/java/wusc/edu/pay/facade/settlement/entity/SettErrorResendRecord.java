package wusc.edu.pay.facade.settlement.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 结算失败重发记录
 * 
 * @author pc-Along
 * 
 */
public class SettErrorResendRecord extends BaseEntity {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -948605083405391261L;

	/** 结算批次号 **/
	private String batchNo;

	/** 打款请求号 **/
	private String remitNo;

	/** 结算账户币种(参考CurrencyTypeEnum) **/
	private Integer currencyType;

	/** 银行编码 **/
	private String bankCode;

	/** 开户名 **/
	private String bankAccountName;

	/** 开户账户 **/
	private String bankAccountNo;

	/** 银行卡类型(参考枚举:BankAccountTypeEnum) **/
	private Integer bankAccountType;

	/** 开户行所在国家 **/
	private String country;

	/** 开户行所在省份 **/
	private String province;

	/** 开户行所在城市 **/
	private String city;

	/** 开户行地址 **/
	private String bankAccountAddress;

	/** 描述 **/
	private String remark;

	/** 操作员登录名 **/
	private String operatorLoginname;

	/** 操作员姓名 **/
	private String operatorRealname;

	/**
	 * 结算批次号
	 * 
	 * @return
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * 结算批次号
	 * 
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	/**
	 * 打款请求号
	 * 
	 * @return
	 */
	public String getRemitNo() {
		return remitNo;
	}

	/**
	 * 打款请求号
	 * 
	 * @param remitNo
	 */
	public void setRemitNo(String remitNo) {
		this.remitNo = remitNo == null ? null : remitNo.trim();
	}

	/**
	 * 结算账户币种(参考CurrencyTypeEnum)
	 * 
	 * @return
	 */
	public Integer getCurrencyType() {
		return currencyType;
	}

	/**
	 * 结算账户币种(参考CurrencyTypeEnum)
	 * 
	 * @param currencyType
	 */
	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * 银行编码
	 * 
	 * @return
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * 银行编码
	 * 
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode == null ? null : bankCode.trim();
	}

	/**
	 * 开户名
	 * 
	 * @return
	 */
	public String getBankAccountName() {
		return bankAccountName;
	}

	/**
	 * 开户名
	 * 
	 * @param bankAccountName
	 */
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName == null ? null : bankAccountName.trim();
	}

	/**
	 * 开户账户
	 * 
	 * @return
	 */
	public String getBankAccountNo() {
		return bankAccountNo;
	}

	/**
	 * 开户账户
	 * 
	 * @param bankAccountNo
	 */
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo == null ? null : bankAccountNo.trim();
	}

	/**
	 * 银行卡类型(参考枚举:BankAccountTypeEnum)
	 * 
	 * @return
	 */
	public Integer getBankAccountType() {
		return bankAccountType;
	}

	/**
	 * 银行卡类型(参考枚举:BankAccountTypeEnum)
	 * 
	 * @param bankAccountType
	 */
	public void setBankAccountType(Integer bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	/**
	 * 开户行所在国家
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 开户行所在国家
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	/**
	 * 开户行所在省份
	 * 
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 开户行所在省份
	 * 
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	/**
	 * 开户行所在城市
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 开户行所在城市
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	/**
	 * 开户行地址
	 * 
	 * @return
	 */
	public String getBankAccountAddress() {
		return bankAccountAddress;
	}

	/**
	 * 开户行地址
	 * 
	 * @param bankAccountAddress
	 */
	public void setBankAccountAddress(String bankAccountAddress) {
		this.bankAccountAddress = bankAccountAddress == null ? null : bankAccountAddress.trim();
	}

	/**
	 * 描述
	 * 
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 描述
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * 操作员登录名
	 * 
	 * @return
	 */
	public String getOperatorLoginname() {
		return operatorLoginname;
	}

	/**
	 * 操作员登录名
	 * 
	 * @param operatorLoginname
	 */
	public void setOperatorLoginname(String operatorLoginname) {
		this.operatorLoginname = operatorLoginname == null ? null : operatorLoginname.trim();
	}

	/**
	 * 操作员姓名
	 * 
	 * @return
	 */
	public String getOperatorRealname() {
		return operatorRealname;
	}

	/**
	 * 操作员姓名
	 * 
	 * @param operatorRealname
	 */
	public void setOperatorRealname(String operatorRealname) {
		this.operatorRealname = operatorRealname == null ? null : operatorRealname.trim();
	}
}