package wusc.edu.pay.facade.boss.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/***
 * 销售人员与商户信息管理表
 * 
 * @author huangbin
 * 
 */
public class MerchantSales extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5523041089055296401L;
	private String merchantNo; // 商户编号
	private Long salesId;// 销售人员ID
	private String merchantFullName;// 商户全称(只用于展示数据库没有该字段)

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Long getSalesId() {
		return salesId;
	}

	public void setSalesId(Long salesId) {
		this.salesId = salesId;
	}

	/**
	 * merchantFullName
	 * 
	 * @return the merchantFullName
	 * @since 1.0
	 */

	public String getMerchantFullName() {
		return merchantFullName;
	}

	/**
	 * @param merchantFullName
	 *            the merchantFullName to set
	 */
	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
	}

}
