package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.UserRelationDao;
import wusc.edu.pay.facade.user.entity.UserRelation;


/***
 * 
 * 
 * @author chenjianhua
 * 
 */
@Repository("userRelationDao")
public class UserRelationDaoImpl extends BaseDaoImpl<UserRelation> implements UserRelationDao {

	/**
	 * 
	 * @param parentUserNo
	 * @param childUserNo
	 * @return
	 */
	public UserRelation getBy_parentUserNo_childUserNo(String parentUserNo, String childUserNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentUserNo", parentUserNo);
		paramMap.put("childUserNo", childUserNo);
		return super.getBy(paramMap);
	}
	
}
