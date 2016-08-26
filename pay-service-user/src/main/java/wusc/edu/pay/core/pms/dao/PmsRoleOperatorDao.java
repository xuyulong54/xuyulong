package wusc.edu.pay.core.pms.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.pms.entity.PmsRoleOperator;


/**
 * 
 * @描述: 角色-操作员关联表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-22,下午5:51:47 .
 * @版本: 1.0 .
 */
public interface PmsRoleOperatorDao extends BaseDao<PmsRoleOperator> {

	/**
	 * 根据操作员ID查找该操作员关联的角色.
	 * 
	 * @param operatorId
	 *            .
	 * @return list .
	 */
	List<PmsRoleOperator> listByOperatorId(long operatorId);

	/**
	 * 根据角色ID查找该操作员关联的操作员.
	 * 
	 * @param roleId
	 * @return
	 */
	List<PmsRoleOperator> listByRoleId(long roleId);

	/**
	 * 根据操作员ID删除与角色的关联记录.
	 * 
	 * @param operatorId
	 *            .
	 */
	void deleteByOperatorId(long operatorId);

	/**
	 * 根据角色ID删除操作员与角色的关联关系.
	 * 
	 * @param roleId
	 *            .
	 */
	void deleteByRoleId(long roleId);

	/**
	 * 根据角色ID和操作员ID删除关联数据(用于更新操作员的角色).
	 * @param roleId 角色ID.
	 * @param operatorId 操作员ID.
	 */
	void deleteByRoleIdAndOperatorId(long roleId, long operatorId);

}
