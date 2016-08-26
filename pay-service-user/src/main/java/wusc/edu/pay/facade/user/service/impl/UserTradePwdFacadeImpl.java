package wusc.edu.pay.facade.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.user.biz.UserTradePwdBiz;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserTradePwdFacade;


/**
 * 
 * @描述: 商户业务服务（业务设置、业务限制、银行分流）接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-23,上午11:11:05 .
 * @版本: 1.0 .
 */
@Component("userTradePwdFacade")
public class UserTradePwdFacadeImpl implements UserTradePwdFacade {

	@Autowired
	private UserTradePwdBiz  userTradePwdBiz;
	/**
	 * 根据登录名查询用户交易密码对象
	 * @param loginName
	 * @return
	 * @throws UserBizException
	 */
	public UserTradePwd getUserTradePwdByLoginName(String loginName) throws UserBizException {
		return userTradePwdBiz.getUserTradePwdByLoginName(loginName);
	}

	/**
	 * 修改交易密码
	 * @param loginName
	 * @param oldPwd 加密后，非明文
	 * @param newPwd
	 * @throws UserBizException
	 */
	public void changePwd(String loginName, String oldPwd, String newPwd) throws UserBizException {
		userTradePwdBiz.changePwd(loginName, oldPwd, newPwd);
	}

//	/**
//	 * 修改交易密码
//	 * @param loginName
//	 * @param pwd
//	 * @throws UserBizException
//	 */
//	public void changePwd(String loginName, String pwd) throws UserBizException {
//		userTradePwdBiz.changePwd (loginName, pwd);		
//	}

	/**
	 * 验用户交易密码
	 * @param loginName
	 * @param pwd
	 * @param lockMinute
	 * @param errMaxTimes
	 * @throws UserBizException
	 */
	public void validTradePwd(String loginName, String pwd, int lockMinute, int errMaxTimes) throws UserBizException {
		userTradePwdBiz.validTradePwd( loginName, pwd, lockMinute, errMaxTimes);
	}

//	/**
//	 * 修改用户交易密码表
//	 * @param pwdEntity
//	 * @throws UserBizException
//	 */
//	public void updatePwd(UserTradePwd pwdEntity) throws UserBizException {
//		userTradePwdBiz.updatePwd(pwdEntity);		
//	}

}
