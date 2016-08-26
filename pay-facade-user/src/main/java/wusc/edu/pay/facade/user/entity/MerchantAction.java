package wusc.edu.pay.facade.user.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 商户权限管理-权限功能点实体.
 * 
 * @author xiehui
 * 
 */
public class MerchantAction extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String actionName;// 权限名称
	private String action;// 权限
	private String remark;// 描述
	private Integer merchantBusType; // 标示此权限是那个类型商户所有的

	public Integer getMerchantBusType() {
		return merchantBusType;
	}

	public void setMerchantBusType(Integer merchantBusType) {
		this.merchantBusType = merchantBusType;
	}

	/**
	 * 权限名称
	 * 
	 * @return
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * 权限名称
	 * 
	 * @param actionName
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * 权限
	 * 
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 权限
	 * 
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public MerchantAction() {

	}

}
