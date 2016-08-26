package wusc.edu.pay.facade.pms.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 权限-权限表实体
 * 
 * @author xiehui
 * 
 */
public class PmsAction extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 权限名称 **/
	private String actionName;
	
	/** 权限标识 **/
	private String action;
	
	/** 权限描述 **/
	private String remark;
	
	/** 菜单ID **/
	private PmsMenu menu;

	/**
	 * 权限名称
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * 权限名称
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * 权限标识
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 权限标识
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/** 权限描述 **/
	public String getRemark() {
		return remark;
	}

	/** 权限描述 **/
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 菜单ID **/
	public PmsMenu getMenu() {
		return menu;
	}

	/** 菜单ID **/
	public void setMenu(PmsMenu menu) {
		this.menu = menu;
	}

	public PmsAction() {

	}

}
