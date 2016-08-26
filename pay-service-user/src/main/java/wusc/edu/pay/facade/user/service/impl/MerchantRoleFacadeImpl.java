package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.biz.MerchantActionBiz;
import wusc.edu.pay.facade.user.entity.MerchantRole;
import wusc.edu.pay.facade.user.service.MerchantRoleFacade;


/**
 * 商户权限管理-角色
 * 
 * @author jialong
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component("merchantRoleFacade")
public class MerchantRoleFacadeImpl implements MerchantRoleFacade {
	@Autowired
	private MerchantActionBiz merchantActionBiz;

	/***
	 * 创建商户权限管理-角色
	 */
	public long create(MerchantRole merchantRole) {
		return merchantActionBiz.createMerchantRole(merchantRole);
	}

	/***
	 * 更新商户权限管理-角色
	 */
	public long update(MerchantRole merchantRole) {
		return merchantActionBiz.updateMerchantRole(merchantRole);
	}

	/***
	 * 根据ID查询
	 */
	public MerchantRole getById(long id) {
		return merchantActionBiz.getMerchantRoleById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantActionBiz.listPageForMerchantRole(pageParam, paramMap);
	}

	public List listRoleByUserNo(String userNo) {
		return merchantActionBiz.listMerchantRoleByUserNo(userNo);
	}

	public MerchantRole getByUserNo(String userNo) {
		return merchantActionBiz.getMerchantRoleByUserNo(userNo);
	}

	public List listAllRole() {
		return merchantActionBiz.listAllRoleForMerchantRole();
	}

	
	public void deleteById(long id) {
		// 删除角色
		//merchantRoleService.deleteById(id);
		merchantActionBiz.deleteMerchantRoleById(id);
		// 删除角色与权限的关系
		//merchantRoleActionService.deleteRoleActionByRoleId(id);
		merchantActionBiz.deleteMerchantRoleActionByRoleId(id);
		
		// 删除 merchantRoleOpera
		merchantActionBiz.deleteMerchantRoleOperatorByRoleId(id);
		
	}

	/****
	 * 根据条件查询
	 * @param paramMap
	 * @return
	 */
	public List listModelByCondition(Map<String, Object> paramMap) {
		
		return merchantActionBiz.listModelByCondition(paramMap);
	}
}
