package wusc.edu.pay.facade.fee.vo;

import java.io.Serializable;
import java.util.List;

public class FeeDimensionBindVO implements Serializable {

	private static final long serialVersionUID = -5457155558754127970L;

	/**
	 * ID
	 */
	private Long id;

	/** 支付产品编号 **/
	private String payProductCode;

	/** 支付产品名称 **/
	private String payProductName;
	/**
	 * 是否选中
	 */
	private Boolean selected;

	/**
	 * 计费方式List
	 */
	private List<PayWayVO> listPayWay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPayProductCode() {
		return payProductCode;
	}

	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode;
	}

	public String getPayProductName() {
		return payProductName;
	}

	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName;
	}

	public List<PayWayVO> getListPayWay() {
		return listPayWay;
	}

	public void setListPayWay(List<PayWayVO> listPayWay) {
		this.listPayWay = listPayWay;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
