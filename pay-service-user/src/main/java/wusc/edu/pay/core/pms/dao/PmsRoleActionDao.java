package wusc.edu.pay.core.pms.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.pms.entity.PmsRoleAction;


/**
 * 
 * @描述: 角色-权限点关联表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-22,下午5:51:47 .
 * @版本: 1.0 .
 */
public interface PmsRoleActionDao extends BaseDao<PmsRoleAction> {


	/**
	 * 根据角色ID找到角色关联的权限点.
	 * 
	 * @param roleId
	 *            .
	 * @return roleActionList .
	 */
	List<PmsRoleAction> listByRoleId(long roleId);
	
	/**
	 * 根据角色ID字符串获取相应角色-权限关联信息.
	 * 
	 * @param roleIds
	 * @return
	 */
	List<PmsRoleAction> listByRoleIds(String roleIds);

	/**
	 * 根据权限ID查找权限所关联的角色.
	 * 
	 * @param actionId
	 *            .
	 * @return roleActionList.
	 */
	List<PmsRoleAction> listByActionId(long actionId);

	/**
	 * 根据权限ID删除权限与角色的关联记录.
	 * 
	 * @param actionId
	 *            .
	 */
	void deleteByActionId(long actionId);

	/**
	 * 根据角色ID删除角色与权限的关联记录.
	 * 
	 * @param roleId
	 *            .
	 */
	void deleteByRoleId(long roleId);

}
