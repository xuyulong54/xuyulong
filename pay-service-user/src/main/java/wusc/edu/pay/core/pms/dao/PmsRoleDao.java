package wusc.edu.pay.core.pms.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.pms.entity.PmsRole;


/**
 * 
 * @描述: 角色表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-22,下午5:51:47 .
 * @版本: 1.0 .
 */
public interface PmsRoleDao extends BaseDao<PmsRole> {

	/**
	 * 根据角色类型、用户编号来获取角色列表（可用于列出操作员可以关联的角色）.
	 * @param roleType 角色类型. 
	 * @param userNo 用户编号. 
	 * @return roleList.
	 */
	List<PmsRole> listByRoleTypeAndUserNo(String roleType, String userNo);

	/**
	 * 根据角色名称、用户编号获取角色记录（用于判断角色名是否已存在）.
	 * @param roleName 角色名.
	 * @param userNo 用户编号.
	 * @return PmsRole.
	 */
	PmsRole getByRoleNameAndUserNo(String roleName, String userNo);

	/**
	 * 查找是否存在与ID值不相同与角色名、用户编号相同的角色记录（用于判断修改的角色名与其他的角色名冲突）。
	 * @param roleName 角色名称.
	 * @param userNo 用户编号.
	 * @param id 角色ID .
	 * @return PmsRole.
	 */
	PmsRole getByRoleNameAndUserNoNotEqId(String roleName, String userNo, Long id);

	/**
	 * 根据权限ID找出关联了此权限的角色.
	 * 
	 * @param actionId
	 *            .
	 * @return roleList.
	 */
	List<PmsRole> listByActionId(Long actionId);

}
