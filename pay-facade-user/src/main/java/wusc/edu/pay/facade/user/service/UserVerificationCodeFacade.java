package wusc.edu.pay.facade.user.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.UserVerificationCode;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 用户验证码Dubbo服务接口.<br/>
 * @author huqian.
 * @date 2015-3-2
 * @version 1.0
 */
public interface UserVerificationCodeFacade {

	/**
	 * 根据登录名获取用户验证码信息.<br/>
	 * @param loginName 登录名.
	 * @return
	 */
	List<UserVerificationCode> listUserVerificationCodeByLoginName(String loginName) throws UserBizException;

	/**
	 * 创建用户验证码信息.<br/>
	 * @param verificationCode
	 * @return
	 * @throws UserBizException
	 */
	long createUserVerificationCode(UserVerificationCode verificationCode) throws UserBizException;

	/**
	 * 更新用户验证码信息.<br/>
	 * @param verificationCode
	 * @return
	 * @throws UserBizException
	 */
	long updateUserVerificationCode(UserVerificationCode verificationCode) throws UserBizException;

	/**
	 * 根据ID获取用户验证码信息.<br/>
	 * @param id
	 * @return
	 * @throws UserBizException
	 */
	UserVerificationCode getUserVerificationCodeById(long id) throws UserBizException;

	/**
	 * 用户分页查询
	 * @param pageParam
	 * @param params
	 * @return
	 * @throws UserBizException
	 */
	PageBean listUserVerificationCodeForPage(PageParam pageParam, Map<String,Object> params) throws UserBizException;
	

}
