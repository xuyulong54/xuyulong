package wusc.edu.pay.facade.report.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 
 * @描述: 会员报表数据表（统计会员非金钱的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-19, 上午1:03:08 .
 * @版本: V1.0.
 * 
 */
public class MemberReport extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 360076175698146905L;
	private Integer memberBusType;// 会员业务类型
	private String province;// 所属省份
	private String city;// 所属地市
	private Integer createNum;// 新增数量
	private Integer cancelNum;// 终止数量
	private Integer stockNum;// 存量数量
	private Integer checkingNum;// 审批数量
	private Date acountDate;// 统计日期

	public Integer getMemberBusType() {
		return memberBusType;
	}

	public void setMemberBusType(Integer memberBusType) {
		this.memberBusType = memberBusType;
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

	public Integer getCreateNum() {
		return createNum;
	}

	public void setCreateNum(Integer createNum) {
		this.createNum = createNum;
	}

	public Integer getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(Integer cancelNum) {
		this.cancelNum = cancelNum;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public Integer getCheckingNum() {
		return checkingNum;
	}

	public void setCheckingNum(Integer checkingNum) {
		this.checkingNum = checkingNum;
	}

	public Date getAcountDate() {
		return acountDate;
	}

	public void setAcountDate(Date acountDate) {
		this.acountDate = acountDate;
	}

}
