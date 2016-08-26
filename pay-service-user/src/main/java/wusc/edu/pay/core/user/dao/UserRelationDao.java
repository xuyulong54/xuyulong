package wusc.edu.pay.core.user.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.UserRelation;


/***
 * 
 * 
 * @author chenjianhua
 * 
 */
public interface UserRelationDao extends BaseDao<UserRelation> {

	/**
	 * 
	 * @param parentUserNo
	 * @param childUserNo
	 * @return
	 */
	UserRelation getBy_parentUserNo_childUserNo(String parentUserNo, String childUserNo);

}
