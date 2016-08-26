package wusc.edu.pay.facade.boss.entity; 

import wusc.edu.pay.common.entity.BaseEntity;

/**
 *类描述：市信息表
 *@author: huangbin
 *@date： 日期：2013-11-27 时间：下午2:54:17
 *@version 1.0
 */
public class City extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2862035842792254680L;
	private String provinceNo;//省编号
	private String cityNo;//市编号
	private String cityName;//市名称
	private Integer orders;//排序号
	public String getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	public String getCityNo() {
		return cityNo;
	}
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
}
 