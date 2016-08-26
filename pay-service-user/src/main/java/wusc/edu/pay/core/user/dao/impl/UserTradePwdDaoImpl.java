package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.UserTradePwdDao;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.exceptions.UserBizException;

/**
 * 用户交易密码表数据访问层接口实现.
 * @desc  
 * @author shenjialong
 * @date  2014-6-11,下午6:23:53
 */
@Repository("userTradePwdDao")
public class UserTradePwdDaoImpl extends BaseDaoImpl<UserTradePwd> implements UserTradePwdDao {
	
	/**
	 * 根据登录名查询用户交易密码对象
	 * @param loginName
	 * @return
	 * @throws UserBizException
	 */
	public UserTradePwd getByLoginName(String loginName) {
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("loginName", loginName);
		return this.getBy(map);
	}
}
