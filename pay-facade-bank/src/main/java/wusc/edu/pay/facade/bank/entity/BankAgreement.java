package wusc.edu.pay.facade.bank.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 银行协议参数实体.
 * @作者: WuShuicheng .
 * @创建时间: 2013-9-24,下午2:59:26 .
 * @版本: 1.0 .
 */
public class BankAgreement extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 银行编码，即银行简称，例如：工商银行（ICBC） */
	private String bankCode;

	/** 商户编号，支付平台在银行处的编号，由银行提供 */
	private String merchantNo;

	/** 合同编号，支付平台与银行签订的接口协议编号，由具体合同确定 */
	private String agreementNo;

	/** 银行序号，由支付平台生成的用来区分银行接口的序号 */
	private String bankSequence;

	/** 接口名称，具体根据银行接口名称而定 */
	private String interfaceName;

	/** 大小额：银行区分系统使用大小额的金额限制 */
	private Double amountSystem;

	/** B2B/B2C/快捷支付/批量大款/代收代付,1：B2B、2：B2C、3：快捷支付、4：批量大款、5：代收代付 */
	private Integer linkType;

	/** 通讯方式，HTTP、HTTPS、SFTP */
	private String communicationMode;

	/** 通讯地址，支付平台请求银行的地址 */
	private String communicationAddress;

	/** 合同OA号，协议在支付平台OA系统中的标号 */
	private String contractOANo;

	/** 卡种，借记卡100、信用卡101 */
	private String cardType;

	/** 上线时间，协议正式生效时间 */
	private Date onlineTime;

	/** 下线时间，协议到期时间 */
	private Date offlineTime;

	/** 商户类型，接口支持的允许支付平台接入的商户类型 */
	private String merchantType;

	/** 省 */
	private String province;
	/** 市 */
	private String city;
	/** 区（县） */
	private String area;

	/** 银行联系人：名称、类型、电话、邮箱 */
	private String linkMan;

	/** 描述 */
	private String remark;

	/**
	 * 银行编码，即银行简称，例如：工商银行（ICBC）
	 * 
	 * @return
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * 银行编码，即银行简称，例如：工商银行（ICBC）
	 * 
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 商户编号，支付平台在银行处的编号，由银行提供
	 * 
	 * @return
	 */
	public String getMerchantNo() {
		return merchantNo;
	}

	/**
	 * 商户编号，支付平台在银行处的编号，由银行提供
	 * 
	 * @param merchantNo
	 */
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	/**
	 * 合同编号，支付平台与银行签订的接口协议编号，由具体合同确定
	 * 
	 * @return
	 */
	public String getAgreementNo() {
		return agreementNo;
	}

	/**
	 * 合同编号，支付平台与银行签订的接口协议编号，由具体合同确定
	 * 
	 * @param agreementNo
	 */
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	/**
	 * 银行序号，由支付平台生成的用来区分银行接口的序号
	 * 
	 * @return
	 */
	public String getBankSequence() {
		return bankSequence;
	}

	/**
	 * 银行序号，由支付平台生成的用来区分银行接口的序号
	 * 
	 * @param bankSequence
	 */
	public void setBankSequence(String bankSequence) {
		this.bankSequence = bankSequence;
	}

	/**
	 * 接口名称，具体根据银行接口名称而定
	 * 
	 * @return
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	/**
	 * 接口名称，具体根据银行接口名称而定
	 * 
	 * @param interfaceName
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**
	 * 大小额：银行区分系统使用大小额的金额限制
	 * 
	 * @return
	 */
	public Double getAmountSystem() {
		return amountSystem;
	}

	/**
	 * 大小额：银行区分系统使用大小额的金额限制
	 * 
	 * @param amountSystem
	 */
	public void setAmountSystem(Double amountSystem) {
		this.amountSystem = amountSystem;
	}

	/**
	 * 1：B2B、2：B2C、3：快捷支付、4：批量大款、5：代收代付
	 * 
	 * @return
	 */
	public Integer getLinkType() {
		return linkType;
	}

	/**
	 * 1：B2B、2：B2C、3：快捷支付、4：批量大款、5：代收代付
	 * 
	 * @param linkType
	 */
	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	/**
	 * 通讯方式，HTTP、HTTPS、SFTP
	 * 
	 * @return
	 */
	public String getCommunicationMode() {
		return communicationMode;
	}

	/**
	 * 通讯方式，HTTP、HTTPS、SFTP
	 * 
	 * @param communicationMode
	 */
	public void setCommunicationMode(String communicationMode) {
		this.communicationMode = communicationMode;
	}

	/**
	 * 通讯地址，支付平台请求银行的地址
	 * 
	 * @return
	 */
	public String getCommunicationAddress() {
		return communicationAddress;
	}

	/**
	 * 通讯地址，支付平台请求银行的地址
	 * 
	 * @param communicationAddress
	 */
	public void setCommunicationAddress(String communicationAddress) {
		this.communicationAddress = communicationAddress;
	}

	/**
	 * 合同OA号，协议在支付平台OA系统中的标号
	 * 
	 * @return
	 */
	public String getContractOANo() {
		return contractOANo;
	}

	/**
	 * 合同OA号，协议在支付平台OA系统中的标号
	 * 
	 * @param contractOANo
	 */
	public void setContractOANo(String contractOANo) {
		this.contractOANo = contractOANo;
	}

	/**
	 * 卡种，借记卡100、信用卡101
	 * 
	 * @return
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * 卡种，借记卡100、信用卡101
	 * 
	 * @param cardType
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * 上线时间，协议正式生效时间
	 * 
	 * @return
	 */
	public Date getOnlineTime() {
		return onlineTime;
	}

	/**
	 * 上线时间，协议正式生效时间
	 * 
	 * @param onlineTime
	 */
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	/**
	 * 下线时间，协议到期时间
	 * 
	 * @return
	 */
	public Date getOfflineTime() {
		return offlineTime;
	}

	/**
	 * 下线时间，协议到期时间
	 * 
	 * @param offlineTime
	 */
	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	/**
	 * 商户类型，接口支持的允许支付平台接入的商户类型
	 * 
	 * @return
	 */
	public String getMerchantType() {
		return merchantType;
	}

	/**
	 * 商户类型，接口支持的允许支付平台接入的商户类型
	 * 
	 * @param merchantType
	 */
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	/**
	 * 省
	 * 
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 省
	 * 
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 市
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 市
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 区（县）
	 * 
	 * @return
	 */
	public String getArea() {
		return area;
	}

	/**
	 * 区（县）
	 * 
	 * @param area
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * 银行联系人：名称、类型、电话、邮箱
	 * 
	 * @return
	 */
	public String getLinkMan() {
		return linkMan;
	}

	/**
	 * 银行联系人：名称、类型、电话、邮箱
	 * 
	 * @param linkMan
	 */
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
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
		this.remark = remark;
	}

}
