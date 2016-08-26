package wusc.edu.pay.facade.pms.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 权限管理-角色权限关联表
 * 
 * @author xiehui
 */

public class PmsRoleAction extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 角色ID **/
	private Long roleId;
	
	/** 权限ID **/
	private Long actionId;

	/**
	 * 角色ID
	 * 
	 * @return
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * 角色ID
	 * 
	 * @return
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 权限ID
	 * 
	 * @return
	 */
	public Long getActionId() {
		return actionId;
	}

	/**
	 * 权限ID
	 * 
	 * @return
	 */
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public PmsRoleAction() {
		super();
	}

}
