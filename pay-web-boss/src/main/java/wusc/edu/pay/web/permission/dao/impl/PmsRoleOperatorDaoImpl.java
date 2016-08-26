package wusc.edu.pay.web.permission.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.web.permission.dao.PmsRoleOperatorDao;
import wusc.edu.pay.web.permission.entity.PmsRoleOperator;


/**
 * 
 * @描述: 角色-操作员关联表数据访问层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-22,下午5:51:47 .
 * @版本: 1.0 .
 */
@Repository("pmsRoleOperatorDao")
public class PmsRoleOperatorDaoImpl extends BaseDaoImpl<PmsRoleOperator> implements PmsRoleOperatorDao {

	/**
	 * 根据操作员ID查找该操作员关联的角色.
	 * 
	 * @param operatorId
	 *            .
	 * @return list .
	 */
	public List<PmsRoleOperator> listByOperatorId(long operatorId) {
		return super.getSqlSession().selectList(getStatement("listByOperatorId"), operatorId);
	}

	/**
	 * 根据角色ID查找该操作员关联的操作员.
	 * 
	 * @param roleId
	 * @return
	 */
	public List<PmsRoleOperator> listByRoleId(long roleId) {
		return super.getSqlSession().selectList(getStatement("listByRoleId"), roleId);
	}

	/**
	 * 根据操作员ID删除与角色的关联记录.
	 * 
	 * @param operatorId
	 *            .
	 */
	public void deleteByOperatorId(long operatorId) {

		super.getSqlSession().delete(getStatement("deleteByOperatorId"), operatorId);
	}

	/**
	 * 根据角色ID删除操作员与角色的关联关系.
	 * 
	 * @param roleId
	 *            .
	 */
	public void deleteByRoleId(long roleId) {
		super.getSqlSession().delete(getStatement("deleteByRoleId"), roleId);
	}

	/**
	 * 根据角色ID和操作员ID删除关联数据(用于更新操作员的角色).
	 * @param roleId 角色ID.
	 * @param operatorId 操作员ID.
	 */
	@Override
	public void deleteByRoleIdAndOperatorId(long roleId, long operatorId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("operatorId", operatorId);
		super.getSqlSession().delete(getStatement("deleteByRoleIdAndOperatorId"), paramMap);
	}

}
