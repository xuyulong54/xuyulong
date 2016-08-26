package wusc.edu.pay.facade.user.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 用户操作员Dubbo服务接口.<br/>
 * @author WuShuicheng.
 * @date 2013-9-29
 * @version 1.0
 */
public interface UserOperatorFacade {

	/**
	 * 根据登录名获取用户操作员信息.<br/>
	 * @param loginName 登录名.
	 * @return
	 */
	UserOperator getUserOperatorByLoginName(String loginName) throws UserBizException;

	/**
	 * 创建用户操作员信息.<br/>
	 * @param operator <br/>
	 * @return ID.<br/>
	 * @throws UserBizException
	 */
	long createUserOperator(UserOperator operator) throws UserBizException;

	/**
	 * 更新用户操作员信息.<br/>
	 * @param operator.<br/>
	 * @return
	 * @throws UserBizException
	 */
	long updateUserOperator(UserOperator operator) throws UserBizException;

	/**
	 * 根据ID获取操作员信息.<br/>
	 * @param id 操作员ID.<br/>
	 * @return operator.<br/>
	 * @throws UserBizException
	 */
	UserOperator getUserOperatorById(long id) throws UserBizException;

	/**
	 * 根据用户编号列出该用户的所有操作员.<br/>
	 * @param userNo 用户编号.
	 * @return UserOperatorList 操作员列表.
	 */
	List<UserOperator> listUserOperatorByUserNo(String userNo) throws UserBizException;

	/**
	 * 用户分页查询
	 * @param pageParam
	 * @param params
	 * @return
	 * @throws UserBizException
	 */
	PageBean listUserOperatorForPage(PageParam pageParam, Map<String,Object> params) throws UserBizException;

	
	/***
	 * 根据用户编号,修改该用户下所有操作员的状态.<br/>
	 * @param userNo 用户编号.<br/>
	 * @param status 要更新的状态.<br/>
	 */
	void updateUserOperatorStatusByUserNo(String userNo, int status) throws UserBizException;

	/***
	 * 重置操作员密码
	 * @param loginName 登录名
	 * @param newPwd 新密码-加密后的数据
	 */
	void resetUserOperatorPassword(String loginName, String newPwd) throws UserBizException;
	

}
