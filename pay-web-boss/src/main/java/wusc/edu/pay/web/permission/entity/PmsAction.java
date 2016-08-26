package wusc.edu.pay.web.permission.entity;

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

	private String actionName; // 权限名称
	private String action; // 权限标识
	private String remark; // 权限描述
	private PmsMenu menu; // 菜单ID

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
	 * @return
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * 权限标识
	 * 
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 权限标识
	 * 
	 * @return
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

	public PmsMenu getMenu() {
		return menu;
	}

	public void setMenu(PmsMenu menu) {
		this.menu = menu;
	}

	public PmsAction() {

	}

}
