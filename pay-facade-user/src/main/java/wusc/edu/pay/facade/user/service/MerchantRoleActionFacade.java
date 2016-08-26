package wusc.edu.pay.facade.user.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.MerchantRoleAction;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 角色权限关联对外接口
 * 
 * @author liliqiong
 * @date 2013-9-29
 * @version 1.0
 */
public interface MerchantRoleActionFacade {

	/**
	 * 根据权限ID删除记录
	 * 
	 * @param id
	 */
	public void deleteMerchantRoleActionByActionId(Long actionId) throws UserBizException;

	/**
	 * 保存对应角色的相应权限的关联关系
	 * 
	 * @param roleId
	 * @param actionStr
	 */
	public void saveMerchantRoleAction(long roleId, String actionStr) throws UserBizException;

	/**
	 * 根据角色ID删除对应的角色与权限关系记录
	 * 
	 * @param roleId
	 */
	public void deleteMerchantRoleActionByRoleId(Long roleId) throws UserBizException;

	/**
	 * 根据角色ID获取相应角色-权限关联信息.
	 * 
	 * @param roleId
	 * @return
	 */
	public List<MerchantRoleAction> listMerchantRoleActionByRoleId(long roleId) throws UserBizException;

	/**
	 * 根据角色ID得到所有权限ID字符串
	 * 
	 * @param ids
	 * @return
	 */
	public String getMRActionStrByRoleId(Long id) throws UserBizException;

	/**
	 * 根据角色字符串得到所有权限ID字符串
	 * 
	 * @param roleIds
	 * @return
	 */
	public String getMRActionStrByRoleIds(String roleIds) throws UserBizException;

	long createMerchantRoleAction(MerchantRoleAction entity) throws UserBizException;

	long updateMerchantRoleAction(MerchantRoleAction entity) throws UserBizException;

	MerchantRoleAction getMerchantRoleActionById(long id) throws UserBizException;

	PageBean listPageForMerchantRoleAction(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException;
}
