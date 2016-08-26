package wusc.edu.pay.core.user.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.exceptions.UserBizException;

/**
 * 用户交易密码表数据访问层接口.
 * @desc  
 * @author shenjialong
 * @date  2014-6-11,下午6:23:53
 */
public interface UserTradePwdDao extends BaseDao<UserTradePwd>{

	/**
	 * 根据登录名查询用户交易密码对象
	 * @param loginName
	 * @return
	 * @throws UserBizException
	 */
	UserTradePwd getByLoginName(String loginName);

}
