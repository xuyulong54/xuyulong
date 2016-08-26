package wusc.edu.pay.core.user.biz;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.user.dao.UserTradePwdDao;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 用户交易密码Biz
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-11,下午6:45:15
 */
@Component("userTradePwdBiz")
public class UserTradePwdBiz {

	@Autowired
	private UserTradePwdDao userTradePwdDao;

	/**
	 * 根据登录名查询用户交易密码对象
	 * 
	 * @param loginName
	 * @return
	 * @throws UserBizException
	 */
	public UserTradePwd getUserTradePwdByLoginName(String loginName) {
		return userTradePwdDao.getByLoginName(loginName);
	}

	/**
	 * 修改交易密码
	 * 
	 * @param loginName
	 * @param oldPwd
	 *            加密后，非明文
	 * @param newPwd
	 * @throws UserBizException
	 */
	public void changePwd(String loginName, String oldPwd, String newPwd) {
		UserTradePwd userTradePwd = userTradePwdDao.getByLoginName(loginName);
		if (userTradePwd == null) {
			throw new UserBizException(UserBizException.USER_TRADEPWD_NOT_EXIT, "交易密码不存在.");
		}

		if (!userTradePwd.getTradePwd().equals(oldPwd)) {
			throw new UserBizException(UserBizException.USER_TRADEPWD_ERROR, "交易密码错误.");
		}

		userTradePwd.setIsInitialPwd(0);
		userTradePwd.setPwdErrorTimes(0);
		userTradePwd.setTradePwd(newPwd);
		userTradePwdDao.update(userTradePwd);
	}

//	/**
//	 * 修改交易密码
//	 * 
//	 * @param loginName
//	 * @param pwd
//	 * @throws UserBizException
//	 */
//	public void changePwd(String loginName, String pwd) {
//		UserTradePwd userTradePwd = userTradePwdDao.getByLoginName(loginName);
//		if (userTradePwd == null) {
//			throw new UserBizException(UserBizException.USER_TRADEPWD_NOT_EXIT, "交易密码不存在!");
//		}
//
//		userTradePwd.setIsInitialPwd(0);
//		userTradePwd.setTradePwd(pwd);
//		userTradePwd.setPwdErrorTimes(0);
//		userTradePwdDao.update(userTradePwd);
//	}

	/**
	 * 验用户交易密码
	 * 
	 * @param loginName
	 * @param pwd
	 * @param lockMinute
	 * @param errMaxTimes
	 * @throws UserBizException
	 */
	public void validTradePwd(String loginName, String pwd, int lockMinute, int errMaxTimes) {
		UserTradePwd userTradePwd = userTradePwdDao.getByLoginName(loginName);

		if (userTradePwd == null) {
			throw new UserBizException(UserBizException.USER_TRADEPWD_NOT_EXIT, "账户密码不存在!");
		}

		if (userTradePwd.getPwdErrorLastTime() == null) {
			userTradePwd.setPwdErrorLastTime(new Date());
		}

		if (new Date().before(DateUtils.addMinute(userTradePwd.getPwdErrorLastTime(), lockMinute))) {
			if (userTradePwd.getPwdErrorTimes() >= errMaxTimes) {
				throw new UserBizException(UserBizException.USER_TRADEPWD_ERROR_TIMES_OUTLIMIT, "支付密码输入错误次数超限!，锁定时间为%s分钟！",lockMinute);
			}
		} else {
			userTradePwd.setPwdErrorTimes(0);
		}

		if (userTradePwd.getTradePwd().equals(pwd)) {
			userTradePwd.setPwdErrorTimes(0);
			userTradePwdDao.update(userTradePwd);
		} else {
			// 只记录第一次错误时间
			if (userTradePwd.getPwdErrorTimes() == 0) {
				userTradePwd.setPwdErrorLastTime(new Date());
			}
			userTradePwd.setPwdErrorTimes(userTradePwd.getPwdErrorTimes() + 1);
			userTradePwdDao.update(userTradePwd);

			if (errMaxTimes - userTradePwd.getPwdErrorTimes() == 0) {
				throw new UserBizException(UserBizException.USER_TRADEPWD_ERROR, "支付密码错误，已被锁定，锁定时间为%s分钟！",lockMinute);
			} else {
				throw new UserBizException(UserBizException.USER_TRADEPWD_ERROR, "支付密码错误，还有次%s机会！", errMaxTimes - userTradePwd.getPwdErrorTimes());
			}
		}
	}

//	/**
//	 * 修改用户交易密码表
//	 * 
//	 * @param pwdEntity
//	 * @throws UserBizException
//	 */
//	public void updatePwd(UserTradePwd pwdEntity) {
//		userTradePwdDao.update(pwdEntity);
//	}

}
