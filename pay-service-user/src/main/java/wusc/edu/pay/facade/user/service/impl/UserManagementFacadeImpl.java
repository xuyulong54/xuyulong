/**
 * wusc.edu.pay.facade.user.service.UserManagementFacadeImpl.java
 */
package wusc.edu.pay.facade.user.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.user.biz.UserBiz;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.agent.entity.AgentRelation;
import wusc.edu.pay.facade.pms.entity.PmsOperator;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.user.entity.MerchantFile;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserManagementFacade;

import com.alibaba.dubbo.rpc.RpcException;

/** 
 * <ul>
 * <li>Title:用户操作对外接口实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-5-29
 */
@Component("userManagementFacade")
public class UserManagementFacadeImpl implements UserManagementFacade {

	@Autowired
	private UserBiz userBiz;

	/**
	 * 会员信息注册 .
	 * 
	 * @param loginName
	 *            登录名 .
	 * @param loginPwd
	 *            登录密码(传递密文).
	 * @param payPwd
	 *            支付密码(传递密文).
	 * @param question
	 *            安全保护问题 .
	 * @param answer
	 *            安全保护问题答案 .
	 * @param greeting
	 *            预留信息 .
	 * @param realName
	 *            真实姓名 .
	 * @param cardNo
	 *            身份证号 .
	 * @param fax
	 *            传真号.
	 * @param qq
	 *            QQ号.
	 * @param telNo
	 *            联系电话.
	 * @param address
	 *            地址 .
	 */
	public void registerMember(String loginName, String loginPwd, String payPwd, String question, String answer, String greeting,
			String realName, String cardNo, String fax, String qq, String telNo, String address) {
		userBiz.registerMember(loginName, loginPwd, payPwd, question, answer, greeting, realName, cardNo, fax, qq, telNo, address);
	}

	/**
	 * 商户登录.
	 * 
	 * @param loginName
	 *            登录名.
	 * @param loginPwd
	 *            登录密码(密文)
	 * @param pwdErrMaxTimes
	 *            密码错误次数限制.
	 * @param validMinute
	 *            密码多次输错后的账户锁定时间.
	 * @return userInfo 用户信息.
	 */
	public UserInfo merchantLogin(String loginName, String loginPwd, Integer pwdErrMaxTimes, Integer validMinute) {
		return userBiz.merchantLogin(loginName, loginPwd, pwdErrMaxTimes, validMinute);
	}

	/**
	 * 会员登录.
	 * 
	 * @param loginName
	 *            登录名.
	 * @param loginPwd
	 *            登录密码(密文)
	 * @param pwdErrMaxTimes
	 *            密码错误次数限制.
	 * @param validMinute
	 *            密码多次输错后的账户锁定时间.
	 * @return userInfo 用户信息.
	 */
	public UserInfo memberLogin(String loginName, String loginPwd, Integer pwdErrMaxTimes, Integer validMinute) {
		return userBiz.memberLogin(loginName, loginPwd, pwdErrMaxTimes, validMinute);
	}

	/**
	 * 修改用户信息 .
	 * 
	 * @param userInfo
	 *            用户信息 .
	 */
	public long updateUserInfo(UserInfo userInfo) {
		return userBiz.updateUserInfo(userInfo);
	}

	/**
	 * 修改登录密码
	 * 
	 * @param loginName
	 *            操作员登录名
	 * @param oldPwd
	 *            原密码（加密后）
	 * @param newPwd
	 *            新密码（加密后）
	 * @param isChangedPwd
	 *            是否更改过密码(0:否,1:是)
	 */
	public void changeOperatorLoginPwd(String loginName, String oldPwd, String newPwd, Integer isChangedPwd) throws UserBizException,
			RpcException {
		userBiz.changeOperatorLoginPwd(loginName, oldPwd, newPwd, isChangedPwd);
	}


	/***
	 * 创建在线支付的商户.
	 * 
	 * @param merchant
	 *            在线支付的商户信息.
	 * @param userInfo
	 *            用户信息.
	 * @param payPwd
	 *            支付密码(传递密文).
	 * @param loginPwd
	 *            登陆密码 (传递密文).
	 * @return 商户ID.
	 */
	public long createOnlineMerchant(MerchantOnline merchant, UserInfo userInfo, String payPwd, String loginPwd) {
		return userBiz.createOnlineMerchant(merchant, userInfo, payPwd, loginPwd);
	}

	/***
	 * 更新商户和用户信息
	 * 
	 * @param merchant
	 *            商户信息表
	 * @param userInfo
	 *            用户信息表
	 */
	public long updateMerchantAndUser(MerchantOnline merchant, UserInfo userInfo) {
		return userBiz.updateMerchantAndUser(merchant, userInfo);
	}


	/**
	 * 根据用户编号更新绑定邮箱.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 * @param bindEmail
	 *            要更新的绑定邮箱.<br/>
	 */
	public long bindEmail(String userNo, String bindEmail) {
		return userBiz.bindEmail(userNo, bindEmail);
	}

	/**
	 * 根据用户编号解除邮箱绑定.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 */
	public long unBindEmail(String userNo) {
		return userBiz.unBindEmail(userNo);
	}

	/**
	 * 根据用户编号更新绑定手机号.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 * @param bindMobileNo
	 *            要更新的绑定手机号.<br/>
	 */
	public long bindMobileNo(String userNo, String bindMobileNo) {
		return userBiz.bindMobileNo(userNo, bindMobileNo);
	}

	/**
	 * 根据用户编号解除手机号绑定.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 */
	public long unBindMobileNo(String userNo) {
		return userBiz.unBindMobileNo(userNo);
	}

	/***
	 * 冻结用户不能登录
	 * 
	 * @param userNo
	 *            商户编号
	 * @return
	 */
	public boolean freezeUserOperator(String userNo, String ip) {
		return userBiz.freezeuserOperator(userNo, ip);
	}

	/***
	 * 在线支付的商户注册.
	 * 
	 * @param loginName
	 *            登录名
	 * @param loginPwd
	 *            登陆密码
	 * @param tradePwd
	 *            交易密码
	 * @param question
	 *            安全保护问题
	 * @param answer
	 *            安全保护问题答案
	 * @param greeting
	 *            预留信息
	 * @param bindMobileNo
	 *            绑定手机号
	 * @param userType
	 *            用户类型(参考:UserTypeEnum)
	 * @param merchantType
	 *            商户类型(参考:MerchantTypeEnum,个人-10,个体工商户-11, 企业-12)
	 * @param fullName
	 *            商户全称
	 * @param shortName
	 *            商户简称
	 * @param licenseNo
	 *            营业执照
	 * @param url
	 *            网站地址
	 * @param icp
	 *            备案号
	 * @param busiContactName
	 *            联系人
	 * @param busiContactMobileNo
	 *            联系人手机号
	 * @param scope
	 *            经营范围
	 * @param province
	 *            省
	 * @param city
	 *            市
	 * @param area
	 *            区
	 * @param address
	 *            详细地址
	 * @param legalPerson
	 *            法人姓名
	 * @param cardNo
	 *            法人身份证号
	 */
	public void registerMerchant(String loginName, String loginPwd, String tradePwd, int question, String answer, String greeting,
			String bindMobileNo, Integer userType, Integer merchantType, String fullName, String shortName, String licenseNo, String url,
			String icp, String busiContactName, String busiContactMobileNo, String scope, String province, String city, String area,
			String address, String legalPerson, String cardNo) throws UserBizException, RpcException {

		userBiz.registerMerchant(loginName, loginPwd, tradePwd, question, answer, greeting, bindMobileNo, userType, merchantType, fullName,
				shortName, licenseNo, url, icp, busiContactName, busiContactMobileNo, scope, province, city, area, address, legalPerson,
				cardNo);
	}



	/**
	 * 子商户注册 by chenjianhua
	 * 
	 * @param merchantNo
	 * @param childLoginName
	 * @param childLoginPwd
	 * @param childRealName
	 * @param childMobileNo
	 * @param childEmail
	 */
	public void registerChildMerchant(String merchantNo, String childLoginName, String childLoginPwd, String childRealName,
			String childMobileNo, String childEmail) {
		userBiz.registerChildMerchant(merchantNo, childLoginName, childLoginPwd, childRealName, childMobileNo, childEmail);
	}

	
	/***
	 * 门户注册商户
	 * @param loginName
	 * @param merchantType
	 * @param fullName
	 * @param shortName
	 * @param licenseNo
	 * @param licenseValid
	 * @param url
	 * @param mcc
	 * @param orgCode
	 * @param icp
	 * @param legalPerson
	 * @param cardNo
	 * @param cardValid
	 * @param busiContactName
	 * @param busiContactMobileNo
	 * @param scope
	 * @param province
	 * @param city
	 * @param address
	 * @param sha1Hex
	 * @param sha1Hex2
	 * @param question
	 * @param answer
	 * @param greeting
	 * @param bindingPhone
	 */
	public void registerPortalMerchant(String loginName, Integer merchantType, String fullName, String shortName, String licenseNo, Date licenseValid ,
			String url, String mcc, String orgCode, String icp, String legalPerson, String cardNo, String cardValid , String busiContactName,
			String busiContactMobileNo, String scope, String province, String city, String address, String loginPwd, String tradePwd,
			int question, String answer, String greeting, String bindingPhone) {
		
		userBiz.registerPortalMerchant(loginName, merchantType, fullName, shortName, licenseNo, licenseValid, url, mcc, orgCode, icp, legalPerson,
				cardNo, cardValid , busiContactName, busiContactMobileNo, scope, province, city, address, loginPwd, tradePwd, question, answer,
				greeting, bindingPhone);

	}

	/***
	 * 代理商系统注册商户信息
	 * @param userInfo
	 * @param merchant
	 * @param agentMerchantRelation
	 * @param loginPwd
	 * @param merchantFile 资质文件
	 * @throws UserBizException
	 */
	public void addAgentMerchantInfo(UserInfo userInfo, MerchantOnline merchant, AgentMerchantRelation agentMerchantRelation, 
			String loginPwd, MerchantFile merchantFile, SettRule settRule, UserBankAccount bankaccount) throws UserBizException {
		userBiz.addAgentMerchantInfo(userInfo, merchant, agentMerchantRelation, loginPwd, merchantFile, settRule, bankaccount);
	}

	/***
	 * 更新代理商系统的商户信息，以及关联的结算账户，结算方式，用户信息，代理商商户关系表，商户资质信息
	 * @param userInfo
	 * @param merchant
	 * @param agentMerchantRelation
	 * @param merchantFile 			商户资质文件记录
	 */
	public void updateAgentMerchantInfo(UserInfo userInfo, MerchantOnline merchant, SettRule settRule ,
			UserBankAccount userBankAccount, AgentMerchantRelation agentMerchantRelation, MerchantFile merchantFile) {
		userBiz.updateAgentMerchantInfo(userInfo, merchant, settRule, userBankAccount, agentMerchantRelation, merchantFile);
	}

	@Override
	public void addAgentInfo(UserInfo userInfo, MerchantOnline merchant, AgentRelation agentRelation, SettRule settRule, UserBankAccount userBankAccount,PmsOperator pmsOperator) {
		userBiz.addAgentInfo( userInfo,  merchant,  agentRelation,  settRule,  userBankAccount,pmsOperator);
		
	}

	@Override
	public void updateAgentInfo(UserInfo userInfo, MerchantOnline merchant, SettRule settRule, UserBankAccount userBankAccount, AgentRelation agentRelation) {
		userBiz.updateAgentInfo( userInfo,  merchant,  settRule,  userBankAccount,  agentRelation);		
	}
	
}
