package wusc.edu.pay.facade.bank.entity;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.common.enums.BankCode;

/**
 * 
 * @描述: 银行渠道参数实体 .
 * @作者: HuQian,WuShuicheng .
 * @创建时间: 2014-4-15, 下午3:48:52
 */
public class BankChannel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 银行渠道编号：系统自动生成 */
	private String bankChannelCode;

	/** 银行渠道名称：系统自动生成 */
	private String bankChannelName;

	/** 银行名称 */
	private String bankName;

	/** 银行编号：银行简称，例如：工商银行(ICBC) */
	private BankCode bankCode;

	/** 状态:100:激活 101:冻结 */
	private Integer status;

	/** 落地行名称：具体到支行 */
	private String landingBankName;

	/** 银行协议ID */
	private Long bankAgreementId;

	/** 银行账户ID */
	private Long bankAccountId;

	/** 描述 */
	private String remark;

	/** 银行序号（非数据表映射字段，只用于展示数据） */
	private String bankSequence;
	/** 银行账户名称 （非数据表映射字段，只用于展示数据） */
	private String bankAccountName;

	/** 银行账户名称 （非数据表映射字段，只用于展示数据） */
	public String getBankAccountName() {
		return bankAccountName;
	}

	/** 银行账户名称 （非数据表映射字段，只用于展示数据） */
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	// --------along add ---------
	private Double bankRateOrFee;
	private String province;
	private String city;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getBankRateOrFee() {
		return bankRateOrFee;
	}

	public void setBankRateOrFee(Double bankRateOrFee) {
		this.bankRateOrFee = bankRateOrFee;
	}

	// -----------along add------------------

	/**
	 * 银行渠道编号：系统自动生成
	 * 
	 * @return
	 */
	public String getBankChannelCode() {
		return bankChannelCode;
	}

	/**
	 * 银行渠道编号：系统自动生成
	 * 
	 * @param bankChannelCode
	 */
	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}

	/**
	 * 银行渠道名称：系统自动生成
	 * 
	 * @return
	 */
	public String getBankChannelName() {
		return bankChannelName;
	}

	/**
	 * 银行渠道名称：系统自动生成
	 * 
	 * @param bankChannelName
	 */
	public void setBankChannelName(String bankChannelName) {
		this.bankChannelName = bankChannelName;
	}

	/**
	 * 银行名称
	 * 
	 * @return
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * 银行名称
	 * 
	 * @param bankName
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 银行编号：银行简称，例如：工商银行(ICBC)
	 * 
	 * @return
	 */
	public BankCode getBankCode() {
		return bankCode;
	}

	/**
	 * 银行编号：银行简称，例如：工商银行(ICBC)
	 * 
	 * @param bankCode
	 */
	public void setBankCode(BankCode bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 状态:100:激活 101:冻结
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态:100:激活 101:冻结
	 * 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 落地行名称：具体到支行
	 * 
	 * @return
	 */
	public String getLandingBankName() {
		return landingBankName;
	}

	/**
	 * 落地行名称：具体到支行
	 * 
	 * @param landingBankName
	 */
	public void setLandingBankName(String landingBankName) {
		this.landingBankName = landingBankName;
	}

	/**
	 * 银行协议ID
	 * 
	 * @return
	 */
	public Long getBankAgreementId() {
		return bankAgreementId;
	}

	/**
	 * 银行协议ID
	 * 
	 * @param bankAgreementId
	 */
	public void setBankAgreementId(Long bankAgreementId) {
		this.bankAgreementId = bankAgreementId;
	}

	/**
	 * 银行账户ID
	 * 
	 * @return
	 */
	public Long getBankAccountId() {
		return bankAccountId;
	}

	/**
	 * 银行账户ID
	 * 
	 * @param bankAccountId
	 */
	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
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
	 * @param desc
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 银行序号（非数据表映射字段，只用于展示数据）
	 * 
	 * @return
	 */
	public String getBankSequence() {
		return bankSequence;
	}

	/**
	 * 银行序号（非数据表映射字段，只用于展示数据）
	 * 
	 * @param bankSequence
	 */
	public void setBankSequence(String bankSequence) {
		this.bankSequence = bankSequence;
	}

}
