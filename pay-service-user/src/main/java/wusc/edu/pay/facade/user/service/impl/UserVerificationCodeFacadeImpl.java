package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.biz.UserVerificationCodeBiz;
import wusc.edu.pay.facade.user.entity.UserVerificationCode;
import wusc.edu.pay.facade.user.service.UserVerificationCodeFacade;


/**
 * 
 * @描述: 用户验证码Dubbo服务接口实现类.
 * @作者: huqian.
 * @创建: 2015-3-2,下午12:01:07
 * @版本: V1.0
 *
 */
@Component("userVerificationCodeFacade")
public class UserVerificationCodeFacadeImpl implements UserVerificationCodeFacade{
	
	@Autowired
	private UserVerificationCodeBiz userVerificationCodeBiz; 
	
	/**
	 * 根据登录名获取用户验证码信息.<br/>
	 * @param loginName 登录名.
	 * @return
	 */
	public List<UserVerificationCode> listUserVerificationCodeByLoginName(String loginName) {
		return userVerificationCodeBiz.listUserVerificationCodeByLoginName(loginName);
	}

	/**
	 * 创建用户验证码信息.<br/>
	 * @param verificationCode
	 * @return
	 */
	public long createUserVerificationCode(UserVerificationCode verificationCode) {
		return userVerificationCodeBiz.createUserVerificationCode(verificationCode);
	}

	/**
	 * 更新用户验证码信息.<br/>
	 * @param verificationCode
	 * @return
	 */
	public long updateUserVerificationCode(UserVerificationCode verificationCode) {
		return userVerificationCodeBiz.updateUserVerificationCode(verificationCode);
	}

	/**
	 * 根据ID获取验证码信息.<br/>
	 * @param id
	 * @return
	 */
	public UserVerificationCode getUserVerificationCodeById(long id) {
		return userVerificationCodeBiz.getUserVerificationCodeById(id);
	}

	/**
	 * 分页查询
	 * @param pageParam
	 * @param params
	 * @return
	 */
	public PageBean listUserVerificationCodeForPage(PageParam pageParam, Map<String,Object> params) {
		return userVerificationCodeBiz.listUserVerificationCodeForPage(pageParam, params);
	}
}
