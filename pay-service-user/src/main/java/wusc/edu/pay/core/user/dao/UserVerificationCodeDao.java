package wusc.edu.pay.core.user.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.UserVerificationCode;


/**
 * 
 * @描述: 用户验证码数据访问层接口.
 * @作者: huqian .
 * @创建时间: 2015-3-2,下午3:50:29 .
 * @版本: 1.0 .
 */
public interface UserVerificationCodeDao extends BaseDao<UserVerificationCode> {

	/**
	 * 根据登录名获取用户验证码信息.<br/>
	 * @param loginName 登录名.
	 * @return
	 */
	public List<UserVerificationCode> listUserVerificationCodeByLoginName(String loginName);

}
