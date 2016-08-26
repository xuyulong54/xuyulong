package wusc.edu.pay.web.permission.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.web.permission.entity.PmsRole;


/**
 * 
 * @描述: 角色表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-22,下午5:51:47 .
 * @版本: 1.0 .
 */
public interface PmsRoleDao extends BaseDao<PmsRole> {

	/**
	 * 列出所有角色，以供添加操作员时选择.
	 * 
	 * @return roleList .
	 */
	List<PmsRole> listAll();

	/**
	 * 根据角色名称获取角色记录（用于判断角色名是否已存在）.
	 * 
	 * @param roleName
	 *            角色名.
	 * @return PmsRole.
	 */
	PmsRole getByRoleName(String roleName);

	/**
	 * 查找是否存在与ID值不相同与角色名相同的角色记录（用于判断修改的角色名与其他的角色名冲突）。
	 * 
	 * @param id
	 *            角色ID .
	 * @param roleName
	 *            角色名.
	 * @return PmsRole.
	 */
	PmsRole findByRoleNameNotEqId(Long id, String roleName);

	/**
	 * 根据权限ID找出关联了此权限的角色.
	 * 
	 * @param actionId
	 *            .
	 * @return roleList.
	 */
	List<PmsRole> listByActionId(Long actionId);

}
