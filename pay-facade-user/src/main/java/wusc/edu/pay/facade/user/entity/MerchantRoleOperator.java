package wusc.edu.pay.facade.user.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 商户权限管理-角色,操作员关联表
 * 
 * @author xh
 * 
 */
public class MerchantRoleOperator extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long roleId;// 角色ID
	private Long operatorId;// 操作员ID

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

	public MerchantRoleOperator() {

	}

}
