package wusc.edu.pay.core.user.biz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.MerchantActionDao;
import wusc.edu.pay.core.user.dao.MerchantRoleActionDao;
import wusc.edu.pay.core.user.dao.MerchantRoleDao;
import wusc.edu.pay.core.user.dao.MerchantRoleOperatorDao;
import wusc.edu.pay.core.user.dao.UserOperatorDao;
import wusc.edu.pay.facade.user.entity.MerchantAction;
import wusc.edu.pay.facade.user.entity.MerchantRole;
import wusc.edu.pay.facade.user.entity.MerchantRoleAction;
import wusc.edu.pay.facade.user.entity.MerchantRoleOperator;
import wusc.edu.pay.facade.user.entity.UserOperator;


/**
 * 类描述：商户-操作员 权限Biz类
 * 
 * @author: huangbin
 * @date： 日期：2013-11-6 时间：上午11:10:05
 * @todo: TODO
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Component("merchantActionBiz")
public class MerchantActionBiz {
	@Autowired
	private MerchantActionDao merchantActionDao; // 商户操作接口
	@Autowired
	private UserOperatorDao userOperatorDao; // 商户权限管理-操作员接口
	@Autowired
	private MerchantRoleDao merchantRoleDao; // 商户角色
	@Autowired
	private MerchantRoleActionDao merchantRoleActionDao; // 商户角色操作
	@Autowired
	private MerchantRoleOperatorDao merchantRoleOperatorDao; // 商户权限管理-角色,操作员关联表

	/***
	 * 保存商户权限管理-权限功能点
	 * 
	 * @return
	 */
	public long createMerchantAction(MerchantAction merchantAction) {
		return merchantActionDao.insert(merchantAction);
	}

	/***
	 * 保存商户权限管理-权限功能点
	 * 
	 * @return
	 */
	public long updateMerchantAction(MerchantAction merchantAction) {
		return merchantActionDao.update(merchantAction);
	}

	/****
	 * 根据ID商户权限管理-权限功能点
	 * 
	 * @param merchantActionId
	 * @return
	 */
	public MerchantAction getMerchantActionById(long merchantActionId) {
		return merchantActionDao.getById(merchantActionId);
	}

	/***
	 * 查询商户权限管理-权限功能点分页列表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPageForMerchantAction(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantActionDao.listPage(pageParam, paramMap);
	}

	/***
	 * 查询商户权限管理-权限功能点 列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<MerchantAction> listMerchantActionByIdStr(String ids) {
		return merchantActionDao.listByIds(ids);
	}

	/**
	 * 根据ID删除商户权限.
	 */
	public void deleteMerchantActionById(Long id) {
		merchantActionDao.deleteById(id);
	}

	/******************* split line ***************/
	/****************** merchantOperator **********/
	/***
	 * 根据ID删除商户操作员
	 * 
	 * @param id
	 * @return
	 */
	public long deleteMerchantOperatorById(long id) {
		return userOperatorDao.deleteById(id);
	}

	/******************* split line ***************/
	/****************** merchantRole **********/
	/***
	 * 根据ID查询商户角色
	 * 
	 * @param id
	 * @return
	 */
	public MerchantRole getMerchantRoleById(long id) {
		return merchantRoleDao.getById(id);
	}

	/**
	 * 根据商户ID查找
	 * 
	 * @param merchantId
	 * @return
	 */
	public MerchantRole getMerchantRoleByUserNo(String userNo) {
		return merchantRoleDao.findByUserNo(userNo);
	}

	/***
	 * 查询商户角色列表-分页
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPageForMerchantRole(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantRoleDao.listPage(pageParam, paramMap);
	}

	/***
	 * 查询所有的商户角色
	 * 
	 * @return
	 */
	public List<MerchantRole> listAllRoleForMerchantRole() {
		return merchantRoleDao.listAllRole();
	}

	/***
	 * 根据商户id查询商户角色列表
	 * 
	 * @param merchantId
	 * @return
	 */
	public List<MerchantRole> listMerchantRoleByUserNo(String userNo) {
		return merchantRoleDao.listRoleByUserNo(userNo);
	}

	/**
	 * 根据ID删除角色
	 */
	public void deleteMerchantRoleById(long id) {
		merchantRoleDao.deleteById(id);
	}

	/***
	 * 保存操作员角色
	 * 
	 * @param entity
	 * @return
	 */
	public long createMerchantRole(MerchantRole entity) {
		return merchantRoleDao.insert(entity);
	}

	/***
	 * 更新操作员角色
	 * 
	 * @param entity
	 * @return
	 */
	public long updateMerchantRole(MerchantRole entity) {
		return merchantRoleDao.update(entity);
	}

	/****
	 * 根据条件查询
	 * 
	 * @param paramMap
	 * @return
	 */
	public List listModelByCondition(Map<String, Object> paramMap) {
		return merchantRoleDao.listBy(paramMap);
	}

	/******************* split line ***************/
	/****************** merchantRoleAction **********/
	/***
	 * 创建
	 * 
	 * @param entity
	 * @return
	 */
	public long createMerchantRoleAction(MerchantRoleAction entity) {
		return merchantRoleActionDao.insert(entity);
	}

	/***
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public long updateMerchantRoleAction(MerchantRoleAction entity) {
		return merchantRoleActionDao.update(entity);
	}

	/***
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	public MerchantRoleAction getMerchantRoleActionById(long id) {
		return merchantRoleActionDao.getById(id);
	}

	/***
	 * 根据权限ID删除 权限和角色关联表中的关联关系
	 * 
	 * @param actionId
	 */
	public void deleteMerchantRoleActionById(Long actionId) {
		merchantRoleActionDao.deleteByActionId(actionId);
	}

	/***
	 * 保存角色权限
	 * 
	 * @param roleId
	 * @param actionStr
	 */
	public void saveMerchantRoleAction(long roleId, String actionStr) {
		// 删除原有权限
		merchantRoleActionDao.deleteByRoleId(roleId);
		// 创建新的关联
		if (!"".equals(actionStr) && actionStr != null) {
			String[] actionIds = actionStr.split(",");
			for (int i = 0; i < actionIds.length; i++) {
				long actionId = Long.parseLong(actionIds[i]);
				MerchantRoleAction merchantRoleActionnew = new MerchantRoleAction();
				merchantRoleActionnew.setActionId(actionId);
				merchantRoleActionnew.setRoleId(roleId);
				merchantRoleActionDao.insert(merchantRoleActionnew);
			}
		}
	}

	/***
	 * 根据角色id删除商户角色
	 * 
	 * @param roleId
	 */
	public void deleteMerchantRoleActionByRoleId(Long roleId) {
		merchantRoleActionDao.deleteByRoleId(roleId);

	}

	/***
	 * 根据角色ID查询商户角色权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<MerchantRoleAction> listMerchantRoleActionByRoleId(long roleId) {
		return merchantRoleActionDao.listMerchantRoleActionByRoleId(roleId);
	}

	/***
	 * 查询权限列表
	 * 
	 * @param id
	 * @return
	 */
	public String getMerchantActionStrByRoleId(Long id) {
		// 得到角色－权限表中roleiId在ids中的所有关联对象
		List<MerchantRoleAction> listMerchantRoleActions = listMerchantRoleActionByRoleId(id);
		// 构建StringBuffer
		StringBuffer roleActionBuffer = new StringBuffer("");
		// 拼接字符串
		for (MerchantRoleAction merchantRoleAction : listMerchantRoleActions) {
			roleActionBuffer.append(merchantRoleAction.getActionId());
			roleActionBuffer.append(",");
		}
		String actionIdStr = roleActionBuffer.toString();
		// 截取字符串
		if (actionIdStr.length() > 0) {
			actionIdStr = actionIdStr.substring(0, actionIdStr.length() - 1);
		}
		return actionIdStr;
	}

	/***
	 * 查询权限列表
	 * 
	 * @param id
	 * @return
	 */
	public String getMerchantRoleActionStrByRoleIds(String roleIds) {
		// 得到角色－权限表中roleiId在ids中的所有关联对象
		List<MerchantRoleAction> listMerchantRoleAction = listMerchantRoleByRoleStr(roleIds);
		// 构建StringBuffer
		StringBuffer roleActionBuffer = new StringBuffer("");

		// 拼接字符串
		for (MerchantRoleAction merchantRoleAction : listMerchantRoleAction) {
			roleActionBuffer.append(merchantRoleAction.getActionId());
			roleActionBuffer.append(",");
		}
		String actionIdStr = roleActionBuffer.toString();
		// 截取字符串
		if (actionIdStr.length() > 0) {
			actionIdStr = actionIdStr.substring(0, actionIdStr.length() - 1);
		}
		return actionIdStr;
	}

	public List<MerchantRoleAction> listMerchantRoleByRoleStr(String roleIds) {
		return merchantRoleActionDao.listByRoleIds(roleIds);
	}

	/******************* split line ***************/
	/****************** merchantRoleOperator **********/

	public void deleteRoleOperatorByRoleId(Long roleId) {
		merchantRoleOperatorDao.deleteByRoleId(roleId);

	}

	public void saveRoleOperator(long operatorId, String roleIdStr) {
		// 删除原来的角色与操作员关联
		List<MerchantRoleOperator> listMerchantRoleOperators = merchantRoleOperatorDao.listByOperatorId(operatorId);
		Map<Long, MerchantRoleOperator> delMap = new HashMap<Long, MerchantRoleOperator>();
		for (MerchantRoleOperator merchantRoleOperator : listMerchantRoleOperators) {
			delMap.put(merchantRoleOperator.getRoleId(), merchantRoleOperator);
		}
		// 创建新的关联
		String[] roleIds = roleIdStr.split(",");
		for (int i = 0; i < roleIds.length; i++) {
			long roleId = Long.parseLong(roleIds[i]);
			if (delMap.get(roleId) == null) {
				MerchantRoleOperator merchantRoleOperator = new MerchantRoleOperator();
				merchantRoleOperator.setOperatorId(operatorId);
				merchantRoleOperator.setRoleId(roleId);
				merchantRoleOperatorDao.insert(merchantRoleOperator);
			} else {
				delMap.remove(roleId);
			}
		}
		Iterator<Long> iterator = delMap.keySet().iterator();
		while (iterator.hasNext()) {
			long key = iterator.next();
			merchantRoleOperatorDao.deleteByRoleId(key);
		}
	}

	public void saveRoleOperators(String roleIdsStr, String operatorIdsStr) {
		String[] operatorIds = operatorIdsStr.split(",");
		String[] roleIds = roleIdsStr.split(",");
		for (int i = 0; i < operatorIds.length; i++) {
			long operatorId = Long.parseLong(operatorIds[i]);
			for (int j = 0; j < roleIds.length; j++) {
				long roleId = Long.parseLong(roleIds[j]);
				MerchantRoleOperator merchantRoleOperator = getByRoleIdAndOperatorId(roleId, operatorId);
				if (merchantRoleOperator == null) {
					MerchantRoleOperator merchantRoleOperator1 = new MerchantRoleOperator();
					merchantRoleOperator1.setOperatorId(operatorId);
					merchantRoleOperator1.setRoleId(roleId);
					merchantRoleOperatorDao.insert(merchantRoleOperator1);
				}
			}
		}
	}

	public List<MerchantRoleOperator> listMerchantRoleOperatorByOperatorId(long id) {
		return merchantRoleOperatorDao.listByOperatorId(id);
	}

	public String getRoleStrByOperatorId(Long id) {
		List<MerchantRoleOperator> listMerchantRoleOperator = listMerchantRoleOperatorByOperatorId(id);
		StringBuffer buffer = new StringBuffer("");
		for (MerchantRoleOperator merchantRoleOperator : listMerchantRoleOperator) {
			buffer.append(merchantRoleOperator.getRoleId());
			buffer.append(",");
		}
		String owenedRoleIds = buffer.toString();
		if (owenedRoleIds.length() > 0) {
			owenedRoleIds = owenedRoleIds.substring(0, owenedRoleIds.length() - 1);
		}
		return owenedRoleIds;
	}

	public MerchantRoleOperator getByRoleIdAndOperatorId(Long roleId, Long operatorId) {
		return merchantRoleOperatorDao.getByRoleIdAndOperatorId(roleId, operatorId);
	}

	/**
	 * 根据角色ID查集合 getByRoleId: <br/>
	 * 
	 * @param roleId
	 * @return
	 */
	public List listMerchantRoleOperatorByRoleId(long roleId) {
		return merchantRoleOperatorDao.listByRoleId(roleId);
	}

	/***
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	public long createMerchantRoleOperator(MerchantRoleOperator entity) {
		return merchantRoleOperatorDao.insert(entity);
	}

	public long updateMerchantRoleOperator(MerchantRoleOperator entity) {
		return merchantRoleOperatorDao.update(entity);
	}

	public MerchantRoleOperator getMerchantRoleOperatorById(long id) {
		return merchantRoleOperatorDao.getById(id);
	}

	public PageBean listPageForMerchantRoleOperator(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantRoleOperatorDao.listPage(pageParam, paramMap);
	}

	public void deleteMerchantRoleOperatorByRoleId(long id) {
		merchantRoleOperatorDao.deleteByRoleId(id);
	}

	/**
	 * 根据商户类型查询功能权限
	 * 
	 * @param merType
	 * @return
	 */
	public List<MerchantAction> listMerActionByMerType(String merType) {
		return merchantActionDao.listMerActionByMerType(merType);
	}

	/**
	 * 新增用户操作员
	 * 
	 * @param userOperator
	 */
	public void createUserOperator(UserOperator userOperator) {
		userOperatorDao.insert(userOperator);
	}

	/**
	 * 修改用户操作员
	 * 
	 * @param userOperator
	 */
	public void updateUserOperator(UserOperator userOperator) {
		userOperatorDao.update(userOperator);
	}

}
