package wusc.edu.pay.core.pms.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.pms.entity.PmsRoleMenu;


/**
 * @author System
 * 
 * @since 2013-11-12
 */
public interface PmsRoleMenuDao extends BaseDao<PmsRoleMenu> {

	/**
	 * 根据角色ID删除菜单与角色的关联记录.
	 * 
	 * @param roleId
	 */
	void deleteByRoleId(Long roleId);

	/**
	 * 根据角色ID统计关联到此角色的菜单数.
	 * 
	 * @param roleId
	 *            角色ID.
	 * @return count.
	 */
	List<PmsRoleMenu> listByRoleId(Long roleId);
}