package wusc.edu.pay.core.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.MerchantRoleDao;
import wusc.edu.pay.facade.user.entity.MerchantRole;


/**
 * 
 * @描述: 商户权限管理--角色表数据访问层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-6,下午4:01:49 .
 * @版本: 1.0 .
 */
@Repository("merchantRoleDao")
public class MerchantRoleDaoImpl extends BaseDaoImpl<MerchantRole> implements
		MerchantRoleDao {

	/**
	 * 根据商户ID查找
	 * 
	 * @param merchantId
	 * @return
	 */
	public final MerchantRole findByUserNo(String userNo) {
		return this.getSqlSession().selectOne(getStatement("findByUserNo"),
				userNo);
	}
	
	/**
	 * 列出所有角色，以供添加操作员时选择.
	 * @return roleList .
	 */
	public List<MerchantRole> listAllRole(){
		return super.getSqlSession().selectList(getStatement("listAll"));
	}

	public List<MerchantRole> listRoleByUserNo(String userNo) {
		return this.getSqlSession().selectList(getStatement("findRoleByUserNo"),
				userNo);
	}
}
