package wusc.edu.pay.core.user.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.UserAuditRecordStatus;


/***
 * 
 * @描述: 用户状态审核表数据访问层接口.
 * @作者: Lanzy.
 * @创建时间: 2014-6-16, 下午5:12:30 .
 * @版本: V1.0.
 * 
 */
public interface UserAuditRecordStatusDao extends BaseDao<UserAuditRecordStatus> {

	/**
	 * 根据userNo、auditStatus查询用户状态表更表
	 * @param userNo
	 * @param auditStatus
	 * @return
	 */
	public UserAuditRecordStatus getByUserNo_auditStatus(String userNo, Integer auditStatus);

	/***
	 * @Title: updateStatus
	 * @Description: POS商户重构商编，需要把审核记录表中对应的商编也要改过来。
	 * @param @param oldMerchNo	旧商编
	 * @param @param newMerchNo 新商编
	 * @param @return    设定文件
	 * @return long    返回类型
	 * @throws
	 */
	public long updateMerchNo(String oldMerchNo, String newMerchNo);

}
