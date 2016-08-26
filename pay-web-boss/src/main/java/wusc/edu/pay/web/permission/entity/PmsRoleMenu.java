/**
 * 
 */
package wusc.edu.pay.web.permission.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * <ul>
 * <li>Title:角色菜单关联表</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2013-11-12
 */
public class PmsRoleMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long roleId; // 角色ID
	private Long menuId; // 菜单ID

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}
