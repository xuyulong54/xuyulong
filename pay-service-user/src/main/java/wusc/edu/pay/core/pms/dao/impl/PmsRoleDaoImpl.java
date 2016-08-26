package wusc.edu.pay.core.pms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.pms.dao.PmsRoleDao;
import wusc.edu.pay.facade.pms.entity.PmsRole;


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
	 * 根据角色类型、用户编号来获取角色列表（可用于列出操作员可以关联的角色）.
	 * @param roleType 角色类型. 
	 * @param userNo 用户编号. 
	 * @return roleList.
	 */
	public List<PmsRole> listByRoleTypeAndUserNo(String roleType, String userNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleType", roleType);
		param.put("userNo", userNo);
		return super.getSqlSession().selectList(getStatement("listByRoleTypeAndUserNo"), param);
	}

	/**
	 * 根据角色名称、用户编号获取角色记录（用于判断角色名是否已存在）.
	 * @param roleName 角色名.
	 * @param userNo 用户编号.
	 * @return PmsRole.
	 */
	@Override
	public PmsRole getByRoleNameAndUserNo(String roleName, String userNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleName", roleName);
		if (StringUtils.isBlank(userNo)){
			userNo = "";
		}
		param.put("userNo", userNo);
		return super.getSqlSession().selectOne(getStatement("getByRoleNameAndUserNo"), param);
	}

	
	@Override
	public PmsRole getByRoleNameAndUserNoNotEqId(String roleName, String userNo, Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleName", roleName);
		param.put("userNo", userNo);
		param.put("id", id);
		return super.getSqlSession().selectOne(getStatement("getByRoleNameAndUserNoNotEqId"), param);
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
