package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.UserAuditRecordRealNameDao;
import wusc.edu.pay.facade.user.entity.UserAuditRecordRealName;


/***
 * 
 * @描述: 用户审核表数据访问层接口实现类.
 * @作者: Lanzy.
 * @创建时间: 2014-6-16, 下午5:23:46 .
 * @版本: V1.0.
 * 
 */
@Repository("userAuditRecordRealNameDao")
public class UserAuditRecordRealNameDaoImpl extends BaseDaoImpl<UserAuditRecordRealName> implements UserAuditRecordRealNameDao {

	/**
	 * 根据userNo和auditStatus查询UserAuditRecordStatus数据
	 */
	public UserAuditRecordRealName getByUserNo_auditStatus(String userNo, Integer auditStatus) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("auditStatus", auditStatus);
		return super.getBy(paramMap);
	}

}
