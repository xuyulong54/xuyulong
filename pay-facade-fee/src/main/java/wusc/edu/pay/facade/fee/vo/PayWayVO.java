package wusc.edu.pay.facade.fee.vo;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


public class PayWayVO extends BaseEntity {

	private static final long serialVersionUID = -8396364442179163953L;
	/** 创建时间 **/
	private Date createTime;

	/** 修改时间 **/
	private Date modifyTime;

	/** 支付方式编号 **/
	private String payWayCode;

	/** 支付方式名称 **/
	private String payWayName;

	/** 支付产品编号 **/
	private String payProductCode;

	/** 默认支付接口 **/
	private String defaultPayInterface;

	/** 状态(100:正常状态,101非正常状态) PayProductStatusEnum **/
	private Integer status;

	/** 排序(倒序排序,默认值1000) **/
	private Integer sorts;
	/**
	 * 是否选中
	 */
	private Boolean selected;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

	public String getPayWayName() {
		return payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public String getPayProductCode() {
		return payProductCode;
	}

	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode;
	}

	public String getDefaultPayInterface() {
		return defaultPayInterface;
	}

	public void setDefaultPayInterface(String defaultPayInterface) {
		this.defaultPayInterface = defaultPayInterface;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSorts() {
		return sorts;
	}

	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
