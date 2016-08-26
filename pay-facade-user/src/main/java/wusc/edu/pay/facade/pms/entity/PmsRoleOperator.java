package wusc.edu.pay.facade.pms.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 
 * 权限管理-角色,操作员关联表
 * 
 * @author xiehui
 * 
 */
public class PmsRoleOperator extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 角色ID **/
	private Long roleId;
	
	/** 操作员ID **/
	private Long operatorId;

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
	 * 操作员ID
	 * 
	 * @return
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * 操作员ID
	 * 
	 * @return
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public PmsRoleOperator() {

	}

}
