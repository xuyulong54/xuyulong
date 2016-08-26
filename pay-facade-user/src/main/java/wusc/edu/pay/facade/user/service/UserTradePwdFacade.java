package wusc.edu.pay.facade.user.service;

import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 用户交易密码接口
 * @desc  
 * @author shenjialong
 * @date  2014-6-11,下午6:06:39
 */
public interface UserTradePwdFacade {
	
	/**
	 * 根据登录名获取用户交易密码.
	 * @param loginName 登录名.
	 * @return
	 * @throws UserBizException
	 */
	public UserTradePwd getUserTradePwdByLoginName(String loginName) throws UserBizException;
	
	/**
	 * 修改交易密码
	 * @param loginName 登录名.
	 * @param oldPwd 旧密码(密文).
	 * @param newPwd 新密码(密文).
	 * @throws UserBizException
	 */
	public void changePwd(String loginName, String oldPwd, String newPwd) throws UserBizException;

//	/**
//	 * 修改交易密码
//	 * @param loginName
//	 * @param pwd
//	 * @throws UserBizException
//	 */
//	public void changePwd(String loginName, String pwd) throws UserBizException;
	
	/**
	 * 验证用户交易密码
	 * @param loginName
	 * @param pwd
	 * @param lockMinute
	 * @param errMaxTimes
	 * @throws UserBizException
	 */
	public void validTradePwd(String loginName, String pwd, int lockMinute, int errMaxTimes) throws UserBizException;

//	/**
//	 * 修改用户交易密码表
//	 * @param pwdEntity
//	 * @throws UserBizException
//	 */
//	public void updatePwd(UserTradePwd pwdEntity) throws UserBizException;
	
}
