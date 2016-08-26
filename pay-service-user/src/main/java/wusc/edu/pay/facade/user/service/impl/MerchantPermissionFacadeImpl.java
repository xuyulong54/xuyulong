package wusc.edu.pay.facade.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.biz.MerchantActionBiz;
import wusc.edu.pay.facade.user.entity.MerchantAction;
import wusc.edu.pay.facade.user.entity.MerchantRole;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.service.MerchantPermissionFacade;


/***
 * 
 * 类描述：商户权限
 * 
 * @author: blank
 * @date： 日期：2013-11-6 时间：上午11:18:00
 * @version 1.0
 */
@Component("merchantPermissionFacade")
public class MerchantPermissionFacadeImpl implements MerchantPermissionFacade {
	@Autowired
	private MerchantActionBiz merchantActionBiz; // 商户角色Biz

	public long create(MerchantAction merchantAction) {
		return merchantActionBiz.createMerchantAction(merchantAction);
	}

	public long update(MerchantAction merchantAction) {
		return merchantActionBiz.updateMerchantAction(merchantAction);
	}

	public MerchantAction getById(long id) {
		return merchantActionBiz.getMerchantActionById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantActionBiz.listPageForMerchantAction(pageParam, paramMap);
	}

	public void deleteMerchantActionById(Long id) {
		// 根据ID删除权限记录
		merchantActionBiz.deleteMerchantActionById(id);
		// 根据权限ID删除 权限和角色关联表中的关联关系
		merchantActionBiz.deleteMerchantRoleActionById(id);
	}

	public void saveMerchantRoleAndMerchantRelateAction(MerchantRole merchantRole, String actionStr) {
		merchantActionBiz.createMerchantRole(merchantRole);
		if (actionStr != null && actionStr.length() > 0) {
			merchantActionBiz.saveMerchantRoleAction(merchantRole.getId(), actionStr);
		}
	}

	public void updateMerchantRoleAndMerchantRelateAction(MerchantRole merchantRole, String actionStr) {
		merchantActionBiz.updateMerchantRole(merchantRole);
		// 保存角色和权限关联信息
		if (merchantRole != null) {
			merchantActionBiz.saveMerchantRoleAction(merchantRole.getId(), actionStr);
		}
	}

	public void deleteMerchantRoleById(Long id) {
		merchantActionBiz.deleteMerchantRoleById(id);
		// 删除角色操作员关联表中的内容
		// merchantRoleOperatorService.deleteRoleOperatorByRoleId(id);
		merchantActionBiz.deleteRoleOperatorByRoleId(id);
		// 删除角色权限关联表中的内容
		// merchantRoleActionService.deleteRoleActionByRoleId(id);
		merchantActionBiz.deleteMerchantRoleActionByRoleId(id);
	}

	/***
	 * 保存商户操作员信息
	 */
	public void saveMerchantOperator(UserOperator userOperator, String roleOperatorStr) {
		merchantActionBiz.createUserOperator(userOperator);
		// 保存角色信息
		if (roleOperatorStr != null && roleOperatorStr.length() > 0) {
			merchantActionBiz.saveRoleOperators(roleOperatorStr, userOperator.getId().toString());
		}
	}

	public void saveMerchantRoleOperator(String roleIdsStr, String operatorIdsStr) {
		if (roleIdsStr != null && roleIdsStr.trim().length() > 0 && operatorIdsStr != null && operatorIdsStr.trim().length() > 0) {
			merchantActionBiz.saveRoleOperators(roleIdsStr, operatorIdsStr);
		}
	}
	
	/**
	 * 修改商户操作员
	 */
	public void updateMerchantOperator(UserOperator userOperator, String roleOperatorStr) {
		merchantActionBiz.updateUserOperator(userOperator);
		if (roleOperatorStr != null && roleOperatorStr.length() > 0) {
			merchantActionBiz.saveRoleOperator(userOperator.getId(), roleOperatorStr);
		}
	}

	public void deleteMerchantOperator(Long id) {
		merchantActionBiz.deleteMerchantOperatorById(id);
	}

}
