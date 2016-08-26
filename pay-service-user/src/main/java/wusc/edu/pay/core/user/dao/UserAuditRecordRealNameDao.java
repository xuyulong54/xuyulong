package wusc.edu.pay.core.user.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.UserAuditRecordRealName;


/***
 * 
 * @描述: 用户审核表数据访问层接口.
 * @作者: Lanzy.
 * @创建时间: 2014-6-16, 下午5:16:56 .
 * @版本: V1.0.
 *
 */
public interface UserAuditRecordRealNameDao extends BaseDao<UserAuditRecordRealName>{
	
	/**
	 * 根据userNo、auditStatus查询用户状态表更表
	 * @param userNo
	 * @param auditStatus
	 * @return
	 */
	public UserAuditRecordRealName getByUserNo_auditStatus(String userNo, Integer auditStatus);

}
