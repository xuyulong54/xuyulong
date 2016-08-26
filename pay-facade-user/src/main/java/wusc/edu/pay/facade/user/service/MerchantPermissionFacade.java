package wusc.edu.pay.facade.user.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.MerchantAction;
import wusc.edu.pay.facade.user.entity.MerchantRole;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 商户权限管理对外接口
 * 
 * @author liliqiong
 * @date 2013-9-29
 * @version 1.0
 */
public interface MerchantPermissionFacade {

	/**
	 * 根据权限ID删除权限并解除权限与角色的关联关系.
	 * 
	 * @param id
	 */
	void deleteMerchantActionById(Long id) throws UserBizException;

	/**
	 * 保存角色并关联权限.
	 * 
	 * @param merchantRole
	 *            角色信息.
	 * @param actionStr
	 *            要关联的权限点集字符串.
	 */
	void saveMerchantRoleAndMerchantRelateAction(MerchantRole merchantRole, String actionStr) throws UserBizException;

	/**
	 * 修改角色信息，并更新其关联的权限点.
	 * 
	 * @param bossRole
	 *            角色信息.
	 * @param bossRole
	 *            要关联的权限点集字符串.
	 */
	void updateMerchantRoleAndMerchantRelateAction(MerchantRole merchantRole, String actionStr) throws UserBizException;

	/**
	 * 根据角色ID删除角色，并删除与操作员、权限的关联关系.
	 * 
	 * @param id
	 *            .
	 */
	void deleteMerchantRoleById(Long id) throws UserBizException;

	/**
	 * 保存操作员信息及其关联的角色.
	 * 
	 * @param bossOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */
	void saveMerchantOperator(UserOperator userOperator, String roleOperatorStr) throws UserBizException;

	/**
	 * 批量给操作员分配角色
	 * 
	 * @param merchantRole
	 * @param operatorIdsStr
	 */
	void saveMerchantRoleOperator(String roleIdsStr, String operatorIdsStr) throws UserBizException;

	/**
	 * 修改操作员信息及其关联的角色.
	 * 
	 * @param bossOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */
	void updateMerchantOperator(UserOperator userOperator, String roleOperatorStr) throws UserBizException;

	/**
	 * 根据操作员ID删除操作员，并解除与角色的关联.
	 * 
	 * @param id
	 */
	void deleteMerchantOperator(Long id) throws UserBizException;

	long create(MerchantAction entity) throws UserBizException;

	long update(MerchantAction entity) throws UserBizException;

	MerchantAction getById(long id) throws UserBizException;

	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException;
}
