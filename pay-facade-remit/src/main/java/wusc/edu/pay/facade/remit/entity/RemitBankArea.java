package wusc.edu.pay.facade.remit.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * @Title: 银行地区信息实体
 * @Description: 
 * @author zzh
 * @date 2014-7-24 下午3:07:36
 */
public class RemitBankArea extends BaseEntity{

	private static final long serialVersionUID = 3305852172116095424L;

	private String areaCode; //银行地区代码

    private String areaName; //银行地区名称

    private Integer level; //级别:1-省级，2-市级，3-县级，4-县级市，5-地级

    private String city; //所在城市

    private String province; //所在省

	/**
	 * @return 银行地区代码
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param 银行地区代码
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return 银行地区名称
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param 银行地区名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return 级别:1-省级，2-市级，3-县级，4-县级市，5-地级
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param 级别:1-省级，2-市级，3-县级，4-县级市，5-地级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * @return 所在城市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param 所在城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return 所在省
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param 所在省
	 */
	public void setProvince(String province) {
		this.province = province;
	}

    
}