/**
 * wusc.edu.pay.facade.user.service.impl.UserQueryFacadeImpl.java
 */
package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.UserInfoDao;
import wusc.edu.pay.core.user.dao.UserRelationDao;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserRelation;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserQueryFacade;


/**
 * <ul>
 * <li>Title:用户查询接口实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-5-29
 */
@Component("userQueryFacade")
public class UserQueryFacadeImpl implements UserQueryFacade {

	@Autowired
	private UserInfoDao userInfoDao; // 用户信息接口
	
	@Autowired
	private UserRelationDao userRelationDao;

	/***
	 * 根据登陆名查询用户信息
	 * 
	 * @param loginName
	 * @return
	 */
	public UserInfo getUserInfoByLoginName(String loginName) {
		return userInfoDao.getUserInfoByLoginName(loginName);
	}

	/***
	 * 根据账户编号查询用户信息
	 * 
	 * @param accountNo
	 * @return
	 */
	public UserInfo getUserInfoByAccountNo(String accountNo) {
		return userInfoDao.getUserInfoByAccountNo(accountNo);
	}

	/***
	 * 根据用户编号查询用户信息
	 * 
	 * @param userNo
	 * @return
	 */
	public UserInfo getUserInfoByUserNo(String userNo) {
		return userInfoDao.getUserInfoByUserNo(userNo);
	}

	/***
	 * 根据绑定手机号码查询用户信息
	 * 
	 * @param bindMobileNo
	 * @return
	 */
	public UserInfo getUserInfoByBindMobileNo(String bindMobileNo) {
		return userInfoDao.getUserInfoByBindMobileNo(bindMobileNo);
	}

	/***
	 * 根据绑定邮箱号查询用户信息
	 * 
	 * @param bindEmail
	 * @return
	 */
	public UserInfo getUserInfoByBindEmail(String bindEmail) {
		return userInfoDao.getUserInfoByBindEmail(bindEmail);
	}

	/***
	 * 根据条件查询用户列表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	public PageBean listUserInfoListPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException {
		return userInfoDao.listPage(pageParam, paramMap);
	}

	/**
	 * 查询用户父子关系
	 * 
	 * @param parentUserNo
	 * @param childUserNo
	 * @return
	 */
	public UserRelation getUserRelationBy_parentUserNo_childUserNo(String parentUserNo, String childUserNo) {
		return userRelationDao.getBy_parentUserNo_childUserNo(parentUserNo, childUserNo);
	}

	/***
	 * 检验法人身份证号是否存在
	 * @param cardNo
	 * @return
	 * @throws UserBizException
	 */
	public UserInfo getUserInfoByCardNo(String cardNo) throws UserBizException {
		return userInfoDao.getUserInfoByCardNo(cardNo);
	}
	
	
	
	/**
	 * 根据userNo模糊查找 
	 * @param userNoKey .
	 * @return List .
	 */
	public List<UserInfo> likeBy(String userNoKey) {
		return userInfoDao.likeBy(userNoKey);
	}
}
