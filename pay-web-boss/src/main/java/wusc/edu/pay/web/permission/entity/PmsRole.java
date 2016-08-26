package wusc.edu.pay.web.permission.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 权限管理-角色
 * 
 * @author xiehui
 * 
 */
public class PmsRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleType; // 角色类型（1:超级管理员角色，0:普通操作员角色）
	private String roleName; // 角色名称
	private String remark; // 描述

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * 角色名称
	 * 
	 * @return
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 角色名称
	 * 
	 * @return
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PmsRole() {

	}
}
