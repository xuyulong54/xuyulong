package wusc.edu.pay.core.user.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.MerchantRole;


/**
 * 
 * @描述: 商户权限管理--角色表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-6,下午4:01:16 .
 * @版本: 1.0 .
 */
public interface MerchantRoleDao extends BaseDao<MerchantRole> {

	/**
	 * 根据用户编号查询商户角色
	 * 
	 * @param merchantId
	 * @return
	 */
	MerchantRole findByUserNo(String userNo);

	/**
	 * 列出所有角色，以供添加操作员时选择.
	 * @return roleList .
	 */
	List<MerchantRole> listAllRole();
	
	/**
	 * 根据用户编号查找商户角色
	 * @param userNo
	 * @return
	 */
	List<MerchantRole> listRoleByUserNo(String userNo);

}
