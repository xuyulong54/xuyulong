package wusc.edu.pay.core.user.biz;

import wusc.edu.pay.facade.user.entity.MerchantAction;
import wusc.edu.pay.facade.user.entity.MerchantRole;
import wusc.edu.pay.facade.user.entity.UserOperator;

/**
 * 商户系统权限管理模块功能的业务层接口.
 * 
 * @author liliqiong
 * @date 2013-8-26
 * @version 1.0
 */
public interface MerchantPermissionBiz {

	/**
	 * 保存权限
	 * 
	 * @param merchantAction
	 */
	void saveMerchantAction(MerchantAction merchantAction);

	/**
	 * 修改权限
	 * 
	 * @param merchantAction
	 */
	void updateMerchantAction(MerchantAction merchantAction);

	/**
	 * 根据权限ID删除权限并解除权限与角色的关联关系.
	 * 
	 * @param id
	 */
	void deleteMerchantActionById(Long id);

	/**
	 * 保存角色并关联权限.
	 * 
	 * @param merchantRole
	 *            角色信息.
	 * @param actionStr
	 *            要关联的权限点集字符串.
	 */
	void saveMerchantRoleAndMerchantRelateAction(MerchantRole merchantRole,
			String actionStr);

	/**
	 * 修改角色信息，并更新其关联的权限点.
	 * 
	 * @param bossRole
	 *            角色信息.
	 * @param bossRole
	 *            要关联的权限点集字符串.
	 */
	void updateMerchantRoleAndMerchantRelateAction(MerchantRole merchantRole,
			String actionStr);

	/**
	 * 根据角色ID删除角色，并删除与操作员、权限的关联关系.
	 * 
	 * @param id
	 *            .
	 */
	void deleteMerchantRoleById(Long id);

	/**
	 * 保存操作员信息及其关联的角色.
	 * 
	 * @param bossOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */
	void saveMerchantOperator(UserOperator userOperator,
			String roleOperatorStr);

	/**
	 * 批量给操作员分配角色
	 * 
	 * @param merchantRole
	 * @param operatorIdsStr
	 */
	void saveMerchantRoleOperator(String roleIdsStr, String operatorIdsStr);

	/**
	 * 修改操作员信息及其关联的角色.
	 * 
	 * @param bossOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */
	void updateMerchantOperator(UserOperator userOperator,
			String roleOperatorStr);

	/**
	 * 根据操作员ID删除操作员，并解除与角色的关联.
	 * 
	 * @param id
	 */
	void deleteMerchantOperator(Long id);

}
