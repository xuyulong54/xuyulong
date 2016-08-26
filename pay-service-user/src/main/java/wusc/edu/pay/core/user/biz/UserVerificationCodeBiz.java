package wusc.edu.pay.core.user.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.UserVerificationCodeDao;
import wusc.edu.pay.facade.user.entity.UserVerificationCode;


/**
 * 
 * @描述: 用户验证码业务逻辑实现 .
 * @作者: huqian.
 * @创建: 2015-3-2,上午11:16:35
 * @版本: V1.0
 *
 */
@Component("userVerificationCodeBiz")
public class UserVerificationCodeBiz {
	
	@Autowired
	private UserVerificationCodeDao userVerificationCodeDao;
	
	/**
	 * 根据登录名获取用户操作员信息.<br/>
	 * @param loginName 登录名.
	 * @return
	 */
	public List<UserVerificationCode> listUserVerificationCodeByLoginName(String loginName) {
		return userVerificationCodeDao.listUserVerificationCodeByLoginName(loginName);
	}
	

	/**
	 * 创建用户验证码信息.<br/>
	 * @param verificationCode
	 * @return
	 */
	public long createUserVerificationCode(UserVerificationCode verificationCode) {
		return userVerificationCodeDao.insert(verificationCode);
	}
	

	/**
	 * 更新用户验证码信息
	 * @param verificationCode
	 * @return
	 */
	public long updateUserVerificationCode(UserVerificationCode verificationCode) {
		return userVerificationCodeDao.update(verificationCode);
	}
	

	/**
	 * 根据ID获取用户验证码信息.<br/>
	 * @param id
	 * @return
	 */
	public UserVerificationCode getUserVerificationCodeById(long id) {
		return userVerificationCodeDao.getById(id);
	}
	
	
	/**
	 * 分页查询
	 * @param pageParam
	 * @param params
	 * @return
	 */
	public PageBean listUserVerificationCodeForPage(PageParam pageParam, Map<String,Object> params) {
		return userVerificationCodeDao.listPage(pageParam, params);
	}

}
