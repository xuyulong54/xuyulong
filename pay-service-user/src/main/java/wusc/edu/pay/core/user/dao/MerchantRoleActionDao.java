package wusc.edu.pay.core.user.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.MerchantRoleAction;


/**
 * 
 * @描述: 商户权限管理--角色与权限关联表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-6,下午3:53:36 .
 * @版本: 1.0 .
 */
public interface MerchantRoleActionDao extends BaseDao<MerchantRoleAction> {

	/**
	 * 根据权限ID删除
	 * 
	 * @param actionId
	 */
	void deleteByActionId(long actionId);

	/**
	 * 根据角色ID找到角色关联的权限点.
	 * 
	 * @param roleId
	 *            .
	 * @return roleActionList .
	 */
	List<MerchantRoleAction> listMerchantRoleActionByRoleId(long roleId);

	void deleteByRoleId(Long roleId);

	/**
	 * 根据角色ID字符串获取相应角色-权限关联信息.
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<MerchantRoleAction> listByRoleIds(String roleIdsStr);

	/***
	 * 保存角色的操作权限
	 * 
	 * @param id
	 * @param actionStr
	 */
	public void saveRoleAction(Long roleId, String actionStr);

}
