package wusc.edu.pay.core.user.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.MerchantRoleOperator;


/**
 * 
 * @描述: 商户权限管理--角色与操作员关联表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-6,下午3:57:59 .
 * @版本: 1.0 .
 */
public interface MerchantRoleOperatorDao extends BaseDao<MerchantRoleOperator> {

	/***
	 * 根据角色ID删除角色信息
	 * 
	 * @param roleId
	 */
	void deleteByRoleId(Long roleId);

	/**
	 * 根据操作员ID查找该操作员关联的角色.
	 * 
	 * @param operatorId
	 *            .
	 * @return list .
	 */
	List<MerchantRoleOperator> listByOperatorId(long operatorId);

	/**
	 * 根据角色ID和操作员ID获取角色关联表
	 * 
	 * @param roleId
	 * @param operatorId
	 * @return
	 */
	public MerchantRoleOperator getByRoleIdAndOperatorId(Long roleId, Long operatorId);

	/**
	 * 根据角色ID查集合 getByRoleId: <br/>
	 * 
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByRoleId(long roleId);

	/***
	 * 保存角色操作员
	 * @param id
	 * @param roleOperatorStr
	 */
	void saveRoleOperator(Long operatorId, String roleOperatorStr);

	/***
	 * 保存角色权限
	 * @param roleIdsStr
	 * @param operatorIdsStr
	 */
	void saveRoleOperators(String roleIdsStr, String operatorIdsStr);

}
