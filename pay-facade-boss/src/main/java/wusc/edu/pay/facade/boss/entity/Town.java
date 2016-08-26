package wusc.edu.pay.facade.boss.entity; 

import wusc.edu.pay.common.entity.BaseEntity;

/**
 *类描述：地区管理--镇区信息
 *@author: huangbin
 *@date： 日期：2013-11-27 时间：下午2:56:03
 *@version 1.0
 */
public class Town extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 911906660249007130L;
	private String cityNo;//地市编号
	private String townNo;//镇区编号
	private String townName;//镇区名称
	private Integer orders;//排序号
	public String getCityNo() {
		return cityNo;
	}
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	public String getTownNo() {
		return townNo;
	}
	public void setTownNo(String townNo) {
		this.townNo = townNo;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
}
 