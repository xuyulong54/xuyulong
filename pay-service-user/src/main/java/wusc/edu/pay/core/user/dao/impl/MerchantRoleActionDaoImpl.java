package wusc.edu.pay.core.user.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.MerchantRoleActionDao;
import wusc.edu.pay.facade.user.entity.MerchantRoleAction;


/**
 * 
 * @描述: 商户权限管理--角色与权限关联表数据访问层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-6,下午3:54:02 .
 * @版本: 1.0 .
 */
@Repository("merchantRoleActionDao")
public class MerchantRoleActionDaoImpl extends BaseDaoImpl<MerchantRoleAction> implements MerchantRoleActionDao {

	public void deleteByActionId(long actionId) {
		super.getSqlSession().delete(getStatement("deleteByActionId"), actionId);
	}

	public List<MerchantRoleAction> listMerchantRoleActionByRoleId(long roleId) {
		return super.getSqlSession().selectList(getStatement("selectListByRoleId"), roleId);
	}

	public void deleteByRoleId(Long roleId) {
		super.getSqlSession().delete(getStatement("deleteByRoleId"), roleId);

	}

	public List<MerchantRoleAction> listByRoleIds(String roleIdsStr) {
		List<String> roldIds = Arrays.asList(roleIdsStr.split(","));
		return super.getSqlSession().selectList(getStatement("findByRoleIds"), roldIds);
	}

	/***
	 * 保存角色的操作权限
	 */
	public void saveRoleAction(Long roleId, String actionStr) {
		// 先删除原有的关联关系
		MerchantRoleAction merchantRoleAction = new MerchantRoleAction();
		merchantRoleAction.setRoleId(roleId);
		List<MerchantRoleAction> listMerchantRoleActions = this.listMerchantRoleActionByRoleId(roleId);
		Map<Long, MerchantRoleAction> delMap = new HashMap<Long, MerchantRoleAction>();

		for (MerchantRoleAction merchantRoleActiondel : listMerchantRoleActions) {
			delMap.put(merchantRoleActiondel.getActionId(), merchantRoleActiondel);
		}
		// 创建新的关联
		String[] actionIds = actionStr.split(",");
		for (int i = 0; i < actionIds.length; i++) {
			long actionId = Long.parseLong(actionIds[i]);
			if (delMap.get(actionId) == null) {
				MerchantRoleAction merchantRoleActionnew = new MerchantRoleAction();
				merchantRoleActionnew.setActionId(actionId);
				merchantRoleActionnew.setRoleId(roleId);
				this.insert(merchantRoleActionnew);
			} else {
				delMap.remove(actionId);
			}
		}
		Iterator<Long> iterator = delMap.keySet().iterator();
		while (iterator.hasNext()) {
			long key = iterator.next();
			this.deleteByActionId(key);
		}
	}
}
