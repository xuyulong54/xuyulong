package wusc.edu.pay.facade.remit.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * @Title: 银行信息实体
 * @Description: 
 * @author zzh
 * @date 2014-7-22 上午11:30:36
 */
public class RemitBankInfo extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String bankChannelNo; //银行行号

    private String bankName; //银行名称

    private String province; //省份

    private String city; //城市

    private Integer bankType; //类别：1-清算行，2-开户行
    
    private String clearBankChannelNo; //清算银行行号

    private Integer isInProvince; //是否省内：100-是，101-否


	/**
	 * @return 银行名称
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param 银行名称
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return 省份
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param 省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return 城市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param 城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return 类别：1-清算行，2-开户行
	 */
	public Integer getBankType() {
		return bankType;
	}

	/**
	 * @param 类别：1-清算行，2-开户行
	 */
	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}

	/**
	 * 银行行号
	 * @return
	 */
	public String getBankChannelNo() {
		return bankChannelNo;
	}
	/**
	 * 银行行号
	 * @return
	 */
	public void setBankChannelNo(String bankChannelNo) {
		this.bankChannelNo = bankChannelNo;
	}
	/**
	 * 清算银行行号
	 * @return
	 */
	public String getClearBankChannelNo() {
		return clearBankChannelNo;
	}
	/**
	 * 清算银行行号
	 * @return
	 */
	public void setClearBankChannelNo(String clearBankChannelNo) {
		this.clearBankChannelNo = clearBankChannelNo;
	}

	/**
	 * @return 是否省内：100-是，101-否
	 */
	public Integer getIsInProvince() {
		return isInProvince;
	}

	/**
	 * @param 是否省内：100-是，101-否
	 */
	public void setIsInProvince(Integer isInProvince) {
		this.isInProvince = isInProvince;
	}
	
	

  
}