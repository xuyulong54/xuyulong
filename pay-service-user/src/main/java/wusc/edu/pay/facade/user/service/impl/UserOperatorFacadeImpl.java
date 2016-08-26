package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.biz.UserOperatorBiz;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;


/**
 * 
 * @描述: 用户操作员Dubbo服务接口实现类.
 * @作者: WuShuicheng.
 * @创建: 2014-5-28,下午12:01:07
 * @版本: V1.0
 *
 */
@Component("userOperatorFacade")
public class UserOperatorFacadeImpl implements UserOperatorFacade{
	
	@Autowired
	private UserOperatorBiz userOperatorBiz; 
	
	/**
	 * 根据登录名获取用户操作员信息.<br/>
	 * @param loginName 登录名.
	 * @return
	 */
	public UserOperator getUserOperatorByLoginName(String loginName) {
		return userOperatorBiz.getUserOperatorByLoginName(loginName);
	}

	/**
	 * 创建用户操作员信息.<br/>
	 * @param operator <br/>
	 * @return ID.<br/>
	 * @throws BizException
	 */
	public long createUserOperator(UserOperator operator) {
		return userOperatorBiz.createMerchantOperator(operator);
	}

	/**
	 * 更新用户操作员信息.<br/>
	 * @param operator.<br/>
	 * @return
	 * @throws BizException
	 */
	public long updateUserOperator(UserOperator operator) {
		return userOperatorBiz.updateUserOperator(operator);
	}

	/**
	 * 根据ID获取操作员信息.<br/>
	 * @param id 操作员ID.<br/>
	 * @return operator.<br/>
	 * @throws BizException
	 */
	public UserOperator getUserOperatorById(long id) {
		return userOperatorBiz.getUserOperatorById(id);
	}
	
	/**
	 * 根据商户ID列出该商户的所有操作员.
	 * @param merchantId 商户ID.
	 * @return MerchantOperatorList .
	 */
	public List<UserOperator> listUserOperatorByUserNo(String userNo) {
		return userOperatorBiz.listUserOperatorByUserNo(userNo);
	}

	/**
	 * 分页查询
	 * @param pageParam
	 * @param params
	 * @return
	 */
	public PageBean listUserOperatorForPage(PageParam pageParam, Map<String,Object> params) {
		return userOperatorBiz.listUserOperatorForPage(pageParam, params);
	}

	/***
	 * 根据用户编号,修改该用户下所有操作员的状态.<br/>
	 * @param userNo 用户编号.<br/>
	 * @param status 要更新的状态.<br/>
	 */
	public void updateUserOperatorStatusByUserNo(String userNo, int status) {
		userOperatorBiz.updateUserOperatorStatusByUserNo(userNo, status);
	}

	/***
	 * 重置操作员密码
	 * @param loginName 登录名
	 * @param newPwd 新密码-加密后的数据
	 */
	public void resetUserOperatorPassword(String loginName, String newPwd) {
		userOperatorBiz.resetUserOperatorPassword(loginName, newPwd);
	}

}
