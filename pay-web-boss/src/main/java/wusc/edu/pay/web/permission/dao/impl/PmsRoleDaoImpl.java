package wusc.edu.pay.web.permission.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.web.permission.dao.PmsRoleDao;
import wusc.edu.pay.web.permission.entity.PmsRole;


/**
 * 
 * @描述: 角色表数据访问层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-22,下午5:51:47 .
 * @版本: 1.0 .
 */
@Repository("pmsRoleDao")
public class PmsRoleDaoImpl extends BaseDaoImpl<PmsRole> implements PmsRoleDao {

	/**
	 * 列出所有角色，以供添加操作员时选择.
	 * 
	 * @return roleList .
	 */
	public List<PmsRole> listAll() {
		return super.getSqlSession().selectList(getStatement("listAll"));
	}

	/**
	 * 根据角色名称获取角色记录（用于判断角色名是否已存在）.
	 * 
	 * @param roleName
	 *            角色名.
	 * @return PmsRole.
	 */
	@Override
	public PmsRole getByRoleName(String roleName) {
		return super.getSqlSession().selectOne(getStatement("getByRoleName"), roleName);
	}

	/**
	 * 查找是否存在与ID值不相同与角色名相同的角色记录（用于判断修改的角色名与其他的角色名冲突）。
	 * 
	 * @param id
	 *            角色ID .
	 * @param roleName
	 *            角色名.
	 * @return PmsRole.
	 */
	@Override
	public PmsRole findByRoleNameNotEqId(Long id, String roleName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("roleName", roleName);
		return super.getSqlSession().selectOne(getStatement("findByRoleNameNotEqId"), param);
	}

	/**
	 * 根据权限ID找出关联了此权限的角色.
	 * 
	 * @param actionId
	 *            .
	 * @return roleList.
	 */
	@Override
	public List<PmsRole> listByActionId(Long actionId) {
		return super.getSqlSession().selectList(getStatement("listByActionId"), actionId);
	}
}
