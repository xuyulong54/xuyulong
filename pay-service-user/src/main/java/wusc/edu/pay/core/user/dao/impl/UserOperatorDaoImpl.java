package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.UserOperatorDao;
import wusc.edu.pay.facade.user.entity.UserOperator;


/**
 * 
 * @描述: 用户操作员表数据访问层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-6,下午3:50:58 .
 * @版本: 1.0 .
 */
@Repository("userOperatorDao")
public class UserOperatorDaoImpl extends BaseDaoImpl<UserOperator> implements UserOperatorDao {

	public UserOperator getByLoginName(String loginName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", loginName);
		return super.getBy(map);
	}

	/**
	 * 根据用户编号列出该用户的所有操作员.<br/>
	 * @param userNo 用户编号.
	 * @return UserOperatorList 操作员列表.
	 */
	public List<UserOperator> listByUserNo(String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		return super.listBy(map);
	}

	/***
	 * 根据用户编号,修改该用户下所有操作员的状态.<br/>
	 * @param userNo 用户编号.<br/>
	 * @param status 要更新的状态.<br/>
	 */
	public void updateUserOperatorStatusByUserNo(String userNo, int status) {
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("status", status);
		super.getSessionTemplate().update("updateUserOperatorStatusByUserNo", map);
	}

	/***
	 * 重置操作员密码
	 * @param loginName 登录名
	 * @param newPwd 新密码-加密后的数据
	 */
	public void resetUserOperatorPassword(String loginName, String newPwd) {
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put("loginName", loginName);
		map.put("newPwd", newPwd);
		super.getSessionTemplate().update("resetUserOperatorPassword", map);
	}

	/***
	 * @Description: 根据商户编号和类型查询操作员表
	 * @return UserOperator  
	 * @throws
	 * @author Huang Bin
	 * @date 2015-4-7
	 */
	public UserOperator getOperator_userNo_type(String userNo, int type) {
		// 查询商户的管理员操作员
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("type", type); // 管理员
		return super.getBy(map);
	}
	
}
