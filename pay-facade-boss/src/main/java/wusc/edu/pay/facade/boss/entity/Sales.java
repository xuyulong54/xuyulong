package wusc.edu.pay.facade.boss.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 类描述：销售人员表
 * 
 * @author: huangbin
 * @date： 日期：2013-11-25 时间：下午5:14:17
 * @version 1.0
 */
public class Sales extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3346779282672986501L;
	private String salesNo;// 销售人员编号
	private String salesName; // 销售人员名称
	private String mobile;// 手机号码
	private Integer status;// 状态：100:激活 101:冻结
	private String description;// 描述

	public String getSalesNo() {
		return salesNo;
	}

	public void setSalesNo(String salesNo) {
		this.salesNo = salesNo;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
