package wusc.edu.pay.facade.settlement.DTO;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.common.utils.string.StringUtil;

/**
 * 
 * @title 结算账户信息vo
 * @company 中益智正(gzzyzz)
 * @author jialong.shen
 * @since 2014-8-18
 */
public class UserBankAccountVo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String bankAccountAddress;// 开户行地址
	private String bankAccountName;// 开户名称(持卡人)
	private String bankAccountNo;// 开户账号
	private Integer bankAccountType;// 银行卡类型
	private String bankChannelNo;// 银行行号
	private String bankCode;// 银行编码
	private String province;// 开户行所在省份
	private String city;// 城市
	private String bankName;// 银行名称

	public String getBankChannelNo() {
		return bankChannelNo;
	}

	public void setBankChannelNo(String bankChannelNo) {
		this.bankChannelNo = bankChannelNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	/**
	 * 中间使用*代替了的银行卡账号
	 * 
	 * @return
	 */
	public String gettempBankAccountNo() {
		if (StringUtil.isBlank(bankAccountNo)) {
			return null;
		} else {
			return StringUtil.replaceStringByChar(4, 4, '*', bankAccountNo);
		}
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public Integer getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(Integer bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

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

	public String getBankAccountAddress() {
		return bankAccountAddress;
	}

	public void setBankAccountAddress(String bankAccountAddress) {
		this.bankAccountAddress = bankAccountAddress;
	}

}
