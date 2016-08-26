package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.MerchantRoleOperatorDao;
import wusc.edu.pay.facade.user.entity.MerchantRoleOperator;


/**
 * 
 * @描述: 商户权限管理--角色与操作员关联表数据访问层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-6,下午3:59:48 .
 * @版本: 1.0 .
 */
@Repository("merchantRoleOperatorDao")
public class MerchantRoleOperatorDaoImpl extends BaseDaoImpl<MerchantRoleOperator> implements MerchantRoleOperatorDao {

	public void deleteByRoleId(Long roleId) {
		super.getSqlSession().delete(getStatement("deleteByRoleId"), roleId);

	}

	public List<MerchantRoleOperator> listByOperatorId(long operatorId) {
		return super.getSqlSession().selectList(getStatement("selectListByOperatorId"), operatorId);
	}

	public MerchantRoleOperator getByRoleIdAndOperatorId(Long roleId, Long operatorId) {
		MerchantRoleOperator merchantRoleOperator = new MerchantRoleOperator();
		merchantRoleOperator.setOperatorId(operatorId);
		merchantRoleOperator.setRoleId(roleId);
		return super.getSqlSession().selectOne(getStatement("getByRoleIdAndOperatorId"), merchantRoleOperator);
	}

	/**
	 * 根据角色ID查集合 getByRoleId: <br/>
	 * 
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByRoleId(long roleId) {
		return this.getSqlSession().selectList(getStatement("getByRoleId"), roleId);
	}

	public void saveRoleOperator(Long operatorId, String roleIdStr) {
		// 删除原来的角色与操作员关联
		List<MerchantRoleOperator> listMerchantRoleOperators = this.listByOperatorId(operatorId);
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
				this.insert(merchantRoleOperator);
			} else {
				delMap.remove(roleId);
			}
		}
		Iterator<Long> iterator = delMap.keySet().iterator();
		while (iterator.hasNext()) {
			long key = iterator.next();
			this.deleteByRoleId(key);
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
					this.insert(merchantRoleOperator1);
				}
			}
		}
	}
}
