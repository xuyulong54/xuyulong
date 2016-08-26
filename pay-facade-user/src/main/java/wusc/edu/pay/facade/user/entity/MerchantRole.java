package wusc.edu.pay.facade.user.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 商户权限管理-角色
 * 
 * @author xiehui
 * 
 */
public class MerchantRole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String roleName; // 角色名称
	private String remark; // 描述
	private String userNo;// 用户编号

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

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public MerchantRole() {

	}

}
