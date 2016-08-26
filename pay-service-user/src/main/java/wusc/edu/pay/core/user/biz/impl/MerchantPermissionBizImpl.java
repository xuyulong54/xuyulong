package wusc.edu.pay.core.user.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.user.biz.MerchantPermissionBiz;
import wusc.edu.pay.core.user.dao.MerchantActionDao;
import wusc.edu.pay.core.user.dao.MerchantRoleActionDao;
import wusc.edu.pay.core.user.dao.MerchantRoleDao;
import wusc.edu.pay.core.user.dao.MerchantRoleOperatorDao;
import wusc.edu.pay.core.user.dao.UserOperatorDao;
import wusc.edu.pay.facade.user.entity.MerchantAction;
import wusc.edu.pay.facade.user.entity.MerchantRole;
import wusc.edu.pay.facade.user.entity.UserOperator;


/**
 * 商户系统权限管理模块功能的业务层接口实现类
 * 
 * @author liliqiong
 * @date 2013-8-26
 * @version 1.0
 */
@Component("merchantPermissionBiz")
public class MerchantPermissionBizImpl implements MerchantPermissionBiz {

	@Autowired
	private MerchantActionDao merchantActionDao;

	@Autowired
	private UserOperatorDao userOperatorDao;

	@Autowired
	private MerchantRoleActionDao merchantRoleActionDao;

	@Autowired
	private MerchantRoleOperatorDao merchantRoleOperatorDao;

	@Autowired
	private MerchantRoleDao merchantRoleDao;

	public void saveMerchantAction(MerchantAction merchantAction) {
		merchantActionDao.insert(merchantAction);
	}

	public void updateMerchantAction(MerchantAction merchantAction) {
		merchantActionDao.update(merchantAction);
	}

	public void deleteMerchantActionById(Long id) {
		// 根据ID删除权限记录
		merchantActionDao.deleteById(id);
		// 根据权限ID删除 权限和角色关联表中的关联关系
		merchantRoleActionDao.deleteByActionId(id);

	}

	public void saveMerchantRoleAndMerchantRelateAction(MerchantRole merchantRole, String actionStr) {
		merchantRoleDao.insert(merchantRole);
		if (actionStr != null && actionStr.length() > 0) {
			merchantRoleActionDao.saveRoleAction(merchantRole.getId(), actionStr);
		}
	}

	public void updateMerchantRoleAndMerchantRelateAction(MerchantRole merchantRole, String actionStr) {
		merchantRoleDao.update(merchantRole);
		// 保存角色和权限关联信息
		if (merchantRole != null && actionStr.length() > 0) {
			merchantRoleActionDao.saveRoleAction(merchantRole.getId(), actionStr);
		}
	}

	public void deleteMerchantRoleById(Long id) {
		merchantRoleDao.deleteById(id);
		// 删除角色操作员关联表中的内容
		merchantRoleOperatorDao.deleteByRoleId(id);
		// 删除角色权限关联表中的内容
		merchantRoleActionDao.deleteByRoleId(id);
		//merchantRoleActionDao.deleteRoleActionByRoleId(id);

	}

	/***
	 * 保存商户的操作员
	 */
	public void saveMerchantOperator(UserOperator userOperator, String roleOperatorStr) {
		// 保存操作员信息
		userOperatorDao.insert(userOperator);
		// 保存角色信息
		if (roleOperatorStr != null && roleOperatorStr.length() > 0) {
			merchantRoleOperatorDao.saveRoleOperator(userOperator.getId(), roleOperatorStr);
		}
	}

	public void saveMerchantRoleOperator(String roleIdsStr, String operatorIdsStr) {
		if (roleIdsStr != null && roleIdsStr.trim().length() > 0 && operatorIdsStr != null && operatorIdsStr.trim().length() > 0) {
			merchantRoleOperatorDao.saveRoleOperators(roleIdsStr, operatorIdsStr);
		}
	}

	public void updateMerchantOperator(UserOperator userOperator, String roleOperatorStr) {
		userOperatorDao.update(userOperator);
		if (roleOperatorStr != null && roleOperatorStr.length() > 0) {
			merchantRoleOperatorDao.saveRoleOperator(userOperator.getId(), roleOperatorStr);
		}
	}

	public void deleteMerchantOperator(Long id) {
		userOperatorDao.deleteById(id);
	}

	public void saveMerchantRoleOperator(MerchantRole merchantRole, String operatorIdsStr) {
		if (operatorIdsStr != null && operatorIdsStr.length() > 0) {
			merchantRoleOperatorDao.saveRoleOperator(merchantRole.getId(), operatorIdsStr);
		}
	}

}
