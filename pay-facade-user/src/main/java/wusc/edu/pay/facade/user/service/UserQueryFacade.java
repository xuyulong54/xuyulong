/**
 * wusc.edu.pay.facade.user.service.UserQueryFacade.java
 */
package wusc.edu.pay.facade.user.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserRelation;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * <ul>
 * <li>Title:用户查询接口</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-5-29
 */
public interface UserQueryFacade {

	/**
	 * 根据登录名查询用户信息.
	 * 
	 * @param loginName
	 *            登录名.
	 * @return userInfo 用户信息.
	 */
	public UserInfo getUserInfoByLoginName(String loginName) throws UserBizException;

	/**
	 * 根据账户编号查询用户信息
	 * 
	 * @param accountNo
	 *            账户编号.
	 * @return userInfo 用户信息.
	 */
	public UserInfo getUserInfoByAccountNo(String accountNo) throws UserBizException;

	/***
	 * 根据用户编号查询用户信息 .
	 * 
	 * @param userNo
	 *            用户编号.
	 * @return userInfo 用户信息.
	 */
	public UserInfo getUserInfoByUserNo(String userNo) throws UserBizException;

	/**
	 * 根据绑定手机号码查询用户信息.
	 * 
	 * @param bindMobileNo
	 *            用户绑定手机号码(唯一).
	 * @return userInfo 用户信息.
	 */
	public UserInfo getUserInfoByBindMobileNo(String bindMobileNo) throws UserBizException;

	/***
	 * 根据绑定邮箱号查询用户信息.
	 * 
	 * @param bindEmail
	 *            用户绑定邮箱(唯一).
	 * @return userInfo 用户信息.
	 */
	public UserInfo getUserInfoByBindEmail(String bindEmail) throws UserBizException;

	/***
	 * 根据条件查询用户列表 .
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param paramMap
	 *            查询参数.
	 * @return pageBean.
	 * @throws UserBizException
	 */
	PageBean listUserInfoListPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException;

	/**
	 * 查询用户父子关系
	 * 
	 * @param parentUserNo
	 * @param childUserNo
	 * @return
	 */
	UserRelation getUserRelationBy_parentUserNo_childUserNo(String parentUserNo, String childUserNo) throws UserBizException;

	/***
	 * 检验法人身份证号是否
	 * @param cardNo
	 * @return
	 * @throws UserBizException
	 */
	public UserInfo getUserInfoByCardNo(String cardNo) throws UserBizException;
	
	/**
	 * 根据userNo模糊查找 
	 * @param userNoKey .
	 * @return List .
	 */
	public List<UserInfo> likeBy(String userNoKey) throws UserBizException;
	
}
