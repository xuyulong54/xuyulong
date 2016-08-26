package wusc.edu.pay.core.user.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.UserAuditRecordClose;


/***
 * 
 * @描述: 用户注销审核表数据访问层接口.
 * @作者: Lanzy.
 * @创建时间: 2014-6-16, 下午5:15:40.
 * @版本: V1.0.
 * 
 */
public interface UserAuditRecordCloseDao extends BaseDao<UserAuditRecordClose> {
	
	/**
	 * 根据userNo、auditStatus查询用户状态表更表
	 * 
	 * @param userNo
	 * @param auditStatus
	 * @return
	 */
	public UserAuditRecordClose getByUserNo_auditStatus(String userNo, Integer auditStatus);
}
