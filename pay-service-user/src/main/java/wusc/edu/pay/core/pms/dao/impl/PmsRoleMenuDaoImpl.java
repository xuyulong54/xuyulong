package wusc.edu.pay.core.pms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.pms.dao.PmsRoleMenuDao;
import wusc.edu.pay.facade.pms.entity.PmsRoleMenu;


/**
 * @author System
 * 
 * @since 2013-11-12
 */
@Repository("pmsRoleMenuDao")
public class PmsRoleMenuDaoImpl extends BaseDaoImpl<PmsRoleMenu> implements PmsRoleMenuDao {

	@Override
	public void deleteByRoleId(Long roleId) {
		super.getSqlSession().delete(getStatement("deleteByRoleId"), roleId);
	}

	/**
	 * 根据角色ID统计关联到此角色的菜单数.
	 * 
	 * @param roleId
	 *            角色ID.
	 * @return count.
	 */
	@Override
	public List<PmsRoleMenu> listByRoleId(Long roleId) {
		return super.getSqlSession().selectList(getStatement("listByRoleId"), roleId);
	}
}