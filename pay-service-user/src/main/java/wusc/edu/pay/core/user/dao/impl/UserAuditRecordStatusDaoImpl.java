package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.UserAuditRecordStatusDao;
import wusc.edu.pay.facade.user.entity.UserAuditRecordStatus;


/***
 * 
 * @描述: 用户状态审核表数据访问层接口实现类.
 * @作者: Lanzy.
 * @创建时间: 2014-6-16, 下午5:28:05 .
 * @版本: V1.0.
 * 
 */
@Repository("userAuditRecordStatusDao")
public class UserAuditRecordStatusDaoImpl extends BaseDaoImpl<UserAuditRecordStatus> implements
		UserAuditRecordStatusDao {

	/**
	 * 根据userNo和auditStatus查询UserAuditRecordStatus数据
	 */
	public UserAuditRecordStatus getByUserNo_auditStatus(String userNo, Integer auditStatus) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("auditStatus", auditStatus);
		return super.getBy(paramMap);
	}

	/***
	 * @Title: updateStatus
	 * @Description: POS商户重构商编，需要把审核记录表中对应的商编也要改过来。
	 * @param @param oldMerchNo	旧商编
	 * @param @param newMerchNo 新商编
	 * @param @return    设定文件
	 * @return long    返回类型
	 * @throws
	 */
	public long updateMerchNo(String oldMerchNo, String newMerchNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("oldMerchNo", oldMerchNo);
		paramMap.put("newMerchNo", newMerchNo);
		return super.getSqlSession().update(getStatement("updateMerchNo"), paramMap);
	}
	

}
