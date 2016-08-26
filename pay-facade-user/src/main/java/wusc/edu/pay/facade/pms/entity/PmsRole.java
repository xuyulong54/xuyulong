package wusc.edu.pay.facade.pms.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 
 * @描述: 权限--角色实体.
 * @作者: WuShuicheng.
 * @创建: 2014-11-27,上午11:59:51
 * @版本: V1.0
 *
 */
public class PmsRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 角色类型（1:超级管理员角色,2:普通管理员角色,3:默认操作员角色,4:普通操作员角色，参考枚举PmsRoleTypeEnum） **/
	private String roleType;
	
	/** 角色名称 **/
	private String roleName;
	
	/** 描述 **/
	private String remark;
	
	/** 用户编号（可以是商户编号、代理商编号） **/
	private String userNo;
	

	public PmsRole() {}

	/** 角色类型（1:超级管理员角色,2:普通管理员角色,3:默认操作员角色,4:普通操作员角色，参考枚举PmsRoleTypeEnum） **/
	public String getRoleType() {
		return roleType;
	}

	/** 角色类型（1:超级管理员角色,2:普通管理员角色,3:默认操作员角色,4:普通操作员角色，参考枚举PmsRoleTypeEnum） **/
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/** 角色名称 **/
	public String getRoleName() {
		return roleName;
	}

	/** 角色名称 **/
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/** 描述 **/
	public String getRemark() {
		return remark;
	}

	/** 描述 **/
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 用户编号（可以是商户编号、代理商编号） **/
	public String getUserNo() {
		return userNo;
	}

	/** 用户编号（可以是商户编号、代理商编号） **/
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	
}
