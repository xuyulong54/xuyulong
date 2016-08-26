package wusc.edu.pay.facade.cost.entity;

import wusc.edu.pay.common.entity.BaseEntity;


public class CalDimension extends BaseEntity {

	private static final long serialVersionUID = 3333608117458584719L;

	private String calProduct; // 计费产品名称

	private String calCostInterfaceCode; // 计费接口编码
	
	public String getCalProduct() {
		return calProduct;
	}

	/**
	 * 计费产品名称
	 * 
	 * @param calProduct
	 */
	public void setCalProduct(String calProduct) {
		this.calProduct = calProduct == null ? null : calProduct.trim();
	}

	/**
	 * 计费接口编码
	 * 
	 * @return
	 */
	public String getCalCostInterfaceCode() {
		return calCostInterfaceCode;
	}

	/**
	 * 计费接口编码
	 * 
	 * @return
	 */
	public void setCalCostInterfaceCode(String calCostInterfaceCode) {
		this.calCostInterfaceCode = calCostInterfaceCode;
	}

}