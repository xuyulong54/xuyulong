package wusc.edu.pay.core.user.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.enums.OperatorTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StrUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.core.agent.biz.AgentMerchantRelationBiz;
import wusc.edu.pay.core.agent.biz.AgentRelationBiz;
import wusc.edu.pay.core.pms.biz.PmsOperatorBiz;
import wusc.edu.pay.core.user.dao.MemberInfoDao;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.core.user.dao.MerchantRoleOperatorDao;
import wusc.edu.pay.core.user.dao.UserInfoDao;
import wusc.edu.pay.core.user.dao.UserOperatorDao;
import wusc.edu.pay.core.user.dao.UserRelationDao;
import wusc.edu.pay.core.user.dao.UserTradePwdDao;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.agent.entity.AgentRelation;
import wusc.edu.pay.facade.pms.entity.PmsOperator;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.service.SettManagementFacade;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.MerchantFile;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.MerchantRoleOperator;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserRelation;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.enums.MerchantTypeEnum;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.utils.EnumChangeUtils;


@Component("userRegBiz")
public class UserRegBiz {

	@Autowired
	private UserInfoDao userInfoDao; // 用户信息接口
	@Autowired
	private MerchantOnlineDao merchantOnlineDao; // 在线商户服务
	@Autowired
	private UserOperatorDao userOperatorDao; // 用户操作员服务
	@Autowired
	private MemberInfoDao memberInfoDao; // 会员服务接口
	@Autowired
	private AccountManagementFacade accountManagementFacade; // 账户服务
	@Autowired
	private UserRelationDao userRelationDao;
	@Autowired
	private UserTradePwdDao userTradePwdDao; // 用户交易密码
	@Autowired
	private MerchantRoleOperatorDao merchantRoleOperatorDao; // 商户权限管理
	@Autowired
	private AgentMerchantRelationBiz agentMerchantRelationBiz; // 代理商商户关系biz
	@Autowired
	private MerchantFileBiz merchantFileBiz; // 商户资源文件biz
	@Autowired
	private SettManagementFacade settManagementFacade; // 结算管理接口
	@Autowired
	private UserBankAccountBiz userBankAccountBiz;
	@Autowired
	private AgentRelationBiz agentRelationBiz;
	@Autowired
	private PmsOperatorBiz pmsOperatorBiz;

	private static final Log log = LogFactory.getLog(UserRegBiz.class);

	/***
	 * 运营后台创建商户信息
	 * 
	 * @param merchantOnline
	 *            在线商户信息
	 * @param merchantPos
	 *            pos商户信息
	 * @param merchantRate
	 *            商户费率
	 * @param loginName
	 *            登录名
	 * @param loginPwd
	 *            登录密码（加密后）
	 * @param payPwd
	 *            交易密码（加密后）
	 * @param mobileNo
	 *            绑定手机号
	 * @param email
	 *            绑定邮箱
	 */
	@Transactional(rollbackFor = Exception.class)
	public void createMerchant(MerchantOnline merchantOnline, String loginName, String loginPwd, String payPwd, String mobileNo, String email) {
		log.info("====> step1.开始创建商户信息.");
		// 账户类型
		int accountType = AccountTypeEnum.MERCHANT.getValue();
		// 是否更改过密码(0:否,1:是)
		int isInitialPwd = 0;
		// 账户编号
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.getEnum(accountType));

		// 根据MCC生成商户编号
		String merchantNo;
		if (StringUtils.isBlank(merchantOnline.getMcc())) {
			merchantNo = userInfoDao.buildUserNo("0000");
		} else {
			merchantNo = userInfoDao.buildUserNo(merchantOnline.getMcc());
		}

		// 设置商户的账户编号
		merchantOnline.setAccountNo(accountNo);
		/* merchantPos.setAccountNo(accountNo); */

		long merchantId = merchantOnlineDao.insert(merchantOnline);
		/*
		 * log.info("====> step2.创建在线商户信息表成功！商户ID【" + merchantId + "】"); long
		 * merchantPosId = merchantPosDao.insert(merchantPos);
		 */
		/* log.info("====> step3.创建POS商户信息表成功！商户ID【" + merchantPosId + "】"); */
		UserInfo userInfo = new UserInfo();

		String fullName = merchantOnline.getFullName(); // 商户全称
		if (merchantId > 0) {
			log.info("====> step4.创建在线商户表成功，商户ID=" + merchantId + ", 商户编号=" + merchantNo + ", 账户编号=" + accountNo);
			userInfo.setAccountNo(accountNo); // 账户编号
			userInfo.setUserNo(merchantNo); // 商户编号
			userInfo.setIsEmailAuth(PublicStatusEnum.ACTIVE.getValue()); // 是否绑定邮箱
			userInfo.setIsMobileAuth(PublicStatusEnum.ACTIVE.getValue()); // 是否绑定手机
			userInfo.setIsRealNameAuth(PublicStatusEnum.INACTIVE.getValue()); // 实名认证未通过
			userInfo.setStatus(UserStatusEnum.INACTIVE.getValue());

			// 创建用户信息表
			userInfoDao.insert(userInfo);
			log.info("====> step5.创建在线用户信息成功!用户编号【" + userInfo.getUserNo() + "】");

			// 创建操作员
			createUserOperator(merchantNo, loginName, loginPwd, userInfo.getBindMobileNo(), isInitialPwd, fullName);
			log.info("====> step6.创建在线操作员成功 .");
			// 创建账户密码表
			createUserTradePwd(loginName, merchantNo, payPwd, isInitialPwd);

			// 创建账户信息
			accountManagementFacade.createAccount(merchantNo, accountNo, accountType);
			log.info("====> step7.创建账户信息成功 . ");

		}
	}

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
	@Transactional(rollbackFor = Exception.class)
	public void registerMember(String loginName, String loginPwd, String payPwd, String question, String answer, String greeting, String realName, String cardNo, String fax, String qq, String telNo, String address) {

		Integer isMobileAuth = PublicStatusEnum.INACTIVE.getValue(); // 是否绑定手机
		Integer isEmailAuth = PublicStatusEnum.INACTIVE.getValue(); // 是否绑定邮箱

		int userType = UserTypeEnum.CUSTOMER.getValue();
		String bindMobileNo = "";

		// 验证登录名是否存在
		UserInfo userInfo = userInfoDao.getUserInfoByLoginName(loginName);
		if (userInfo != null) {
			throw new UserBizException(UserBizException.MERCHANT_USERINFO_IS_EXIST, "用户已存在");
		}

		// 生成账户编号和用户编号
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.CUSTOMER);

		String userNo = userInfoDao.buildUserNo("0000");

		// 判断是邮箱登陆
		if (EmailValidator.getInstance().isValid(loginName)) {
			isEmailAuth = PublicStatusEnum.ACTIVE.getValue();
		}

		// 创建用户表信息
		createUserInfo(loginName, accountNo, userNo, question, answer, bindMobileNo, greeting, userType, cardNo, UserStatusEnum.ACTIVE.getValue(), isMobileAuth, isEmailAuth, null, null, null, realName);

		// 创建会员表信息
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setMemberNo(userNo); // 会员编号
		memberInfo.setRealName(realName); // 真实姓名
		memberInfo.setStatus(UserStatusEnum.ACTIVE.getValue()); // 状态
		memberInfo.setFax(fax); // 传真
		memberInfo.setQq(qq); // qq
		memberInfo.setTelNo(telNo); // 联系手机
		memberInfo.setAddress(address); // 联系地址
		memberInfo.setAccountNo(accountNo);// 账户编号
		memberInfo.setCardNo(cardNo);
		memberInfoDao.insert(memberInfo);

		// 创建操作员
		createUserOperator(userNo, loginName, loginPwd, bindMobileNo, 0, memberInfo.getRealName());

		// 创建账户密码表
		createUserTradePwd(loginName, userNo, payPwd, 0);

		// 创建账户信息
		accountManagementFacade.createAccount(userNo, accountNo, EnumChangeUtils.getAccountType(userType));
	}

	/***
	 * 创建操作员
	 * 
	 * @param userNo
	 *            商户编号
	 * @param loginName
	 *            登陆名
	 * @param loginPwd
	 *            登陆密码
	 * @param bindMobileNo
	 *            绑定手机号
	 */
	private void createUserOperator(String userNo, String loginName, String loginPwd, String bindMobileNo, int isInitialPwd, String realName) {

		UserOperator userOperator = new UserOperator();
		userOperator.setUserNo(userNo);
		userOperator.setLoginName(loginName);
		userOperator.setLoginPwd(loginPwd);
		userOperator.setIsChangedPwd(isInitialPwd);
		userOperator.setLastAlertPwdTime(new Date());
		userOperator.setLastLoginTime(new Date());
		userOperator.setMobileNo(bindMobileNo);
		userOperator.setPwdErrorLastTime(new Date());
		userOperator.setPwdErrorTimes(0);
		if (realName == null) {
			realName = "";
		}
		userOperator.setRealName(realName);
		userOperator.setStatus(UserStatusEnum.ACTIVE.getValue());// 默认为激活状态
		userOperator.setType(OperatorTypeEnum.ADMINISTRATOR.getValue());// 默认为管理员
		userOperatorDao.insert(userOperator);

		// 添加操作员角色
	}

	/***
	 * POS运营后台创建pos代理商
	 * 
	 * @param merchantPos
	 *            POS商户信息
	 * @return
	 * @throws UserBizException
	 * @throws RpcException
	 */
	/*
	 * @Transactional(rollbackFor = Exception.class) public long
	 * createPosMerchant(MerchantPos merchantPos, String loginName, String
	 * loginPwd, String payPwd, String mobileNo, String email) {
	 * 
	 * // 生成账户编号 if (StringUtil.isEmpty(merchantPos.getMcc())) return 0;
	 * 
	 * // 生成账户编号 String accountNo =
	 * accountManagementFacade.buildAccountNo(AccountTypeEnum.POS); // 商户编号
	 * String merchantPosNo = userInfoDao.buildUserNo(merchantPos.getMcc());
	 * 
	 * // 生成用户信息表 UserInfo userInfo = new UserInfo();
	 * userInfo.setUserNo(merchantPosNo); userInfo.setAccountNo(accountNo);
	 * userInfo.setLoginName(loginName);
	 * userInfo.setUserType(AccountTypeEnum.POS.getValue());
	 * userInfo.setBindMobileNo(mobileNo); // 商户的联系人手机
	 * userInfo.setBindEmail(email);
	 * userInfo.setIsEmailAuth(PublicStatusEnum.ACTIVE.getValue()); // 是否绑定邮箱
	 * userInfo.setIsMobileAuth(PublicStatusEnum.ACTIVE.getValue()); // 是否绑定手机
	 * userInfo.setProvince(merchantPos.getProvince());
	 * userInfo.setCity(merchantPos.getCity());
	 * userInfo.setArea(merchantPos.getArea());
	 * userInfo.setStatus(UserStatusEnum.INACTIVE.getValue()); // 未激活
	 * userInfo.setRealName(merchantPos.getFullName()); // 真实姓名
	 * userInfo.setBindEmail(loginName); // 绑定邮箱
	 * userInfo.setIsRealNameAuth(PublicStatusEnum.ACTIVE.getValue());
	 * userInfoDao.insert(userInfo);
	 * 
	 * // 创建POS商户 merchantPos.setMerchantNo(merchantPosNo);
	 * merchantPos.setAccountNo(accountNo); long merchantPosId =
	 * merchantPosDao.insert(merchantPos);
	 * 
	 * int isInitialPwd = 0; // 是否更改过密码(0:否,1:是)
	 * 
	 * // 创建操作员信息表 createUserOperator(merchantPosNo, loginName, loginPwd,
	 * mobileNo, isInitialPwd, merchantPos.getFullName());
	 * 
	 * // 创建账户密码表 createUserTradePwd(loginName, merchantPosNo, payPwd,
	 * isInitialPwd);
	 * 
	 * // 创建账户信息表 accountManagementFacade.createAccount(merchantPosNo,
	 * accountNo, AccountTypeEnum.POS.getValue());
	 * 
	 * return merchantPosId; }
	 */

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
	@Transactional(rollbackFor = Exception.class)
	public long createOnlineMerchant(MerchantOnline merchant, UserInfo userInfo, String payPwd, String loginPwd) {
		if (merchant == null || userInfo == null)
			return 0;
		log.info("====> 创建在线商户.");
		String loginName = userInfo.getLoginName();

		// 商户编号
		String merchantNo;
		userInfo.setUserType(UserTypeEnum.MERCHANT.getValue()); // 商户类型
		if (StringUtils.isBlank(merchant.getMcc())) {
			merchantNo = userInfoDao.buildUserNo("0000");
		} else {
			merchantNo = userInfoDao.buildUserNo(merchant.getMcc());
		}

		// 账户类型
		int accountType = AccountTypeEnum.MERCHANT.getValue();
		
		
		// 商户类型
		// int merchantType = merchant.getMerchantType();
		// 是否更改过密码(0:否,1:是)
		int isInitialPwd = 0;
		// 账户编号
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.getEnum(accountType));

		merchant.setMerchantNo(merchantNo);
		merchant.setAccountNo(accountNo); // 设置商户的账户编号
		// 创建在线商户信息表
		long merchantId = merchantOnlineDao.insert(merchant);
		log.info("====> 创建在线商户表成功，商户ID=" + merchantId + ", 商户编号=" + merchantNo + ", 账户编号=" + accountNo);
		userInfo.setAccountNo(accountNo); // 账户编号
		userInfo.setUserNo(merchantNo); // 商户编号
		userInfo.setIsEmailAuth(PublicStatusEnum.ACTIVE.getValue()); // 是否绑定邮箱
		userInfo.setIsMobileAuth(PublicStatusEnum.ACTIVE.getValue()); // 是否绑定手机
		userInfo.setIsRealNameAuth(PublicStatusEnum.INACTIVE.getValue()); // 实名认证未通过
		userInfo.setStatus(UserStatusEnum.INACTIVE.getValue());

		// 创建用户信息表
		userInfoDao.insert(userInfo);
		log.info("====> 创建在线用户信息成功.");
		// 创建操作员
		createUserOperator(merchantNo, loginName, loginPwd, userInfo.getBindMobileNo(), isInitialPwd, merchant.getFullName());
		log.info("====> 创建在线操作员成功 .");
		Integer isInitialTradepwd = 1;// 是否为初始交易密码,1是 0否
		// 创建账户密码表
		createUserTradePwd(loginName, merchantNo, payPwd, isInitialTradepwd);

		// 创建账户信息
		accountManagementFacade.createAccount(merchantNo, accountNo, accountType);
		log.info("====> 创建账户信息成功 . ");

		// step.7插入门户 用户和角色的关联表
		// 查询商户的管理员操作员
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", merchantNo);
		map.put("type", OperatorTypeEnum.ADMINISTRATOR.getValue()); // 管理员
		UserOperator operator = userOperatorDao.getBy(map);
		if (operator != null) {
			// 商户审核通过，往操作员与角色关联表插记录
			MerchantRoleOperator merchantRoleOperator = merchantRoleOperatorDao.getByRoleIdAndOperatorId(0L, operator.getId());
			if (merchantRoleOperator == null) {
				merchantRoleOperator = new MerchantRoleOperator();
				merchantRoleOperator.setOperatorId(operator.getId());
				merchantRoleOperator.setRoleId(0L); // 0-管理员角色，1-普通用户
				merchantRoleOperatorDao.insert(merchantRoleOperator);
			}
		}

		return merchantId;
	}

	/***
	 * POS运营后台创建pos商户信息
	 * 
	 * @param merchantPos
	 *            POS商户信息
	 * @return
	 * @throws UserBizException
	 * @throws RpcException
	 */
	/*
	 * @Transactional(rollbackFor = Exception.class) public long
	 * createPosMerchant(MerchantPos merchantPos) { if
	 * (StringUtil.isEmpty(merchantPos.getMcc())) return 0;
	 * 
	 * // 生成账户编号 String accountNo =
	 * accountManagementFacade.buildAccountNo(AccountTypeEnum.POS); //
	 * 生成支付密码和登陆密码 String payPwd = UUIDUitl.generateString(8); String loginPwd =
	 * UUIDUitl.generateString(8); // 商户编号 String merchantPosNo =
	 * userInfoDao.buildUserNo(merchantPos.getMcc());
	 * 
	 * // 生成用户信息表 UserInfo userInfo = new UserInfo();
	 * userInfo.setUserNo(merchantPosNo); userInfo.setAccountNo(accountNo);
	 * userInfo.setLoginName(merchantPosNo);
	 * userInfo.setUserType(AccountTypeEnum.POS.getValue());
	 * userInfo.setBindMobileNo(merchantPos.getBusiContactMobileNo()); //
	 * 商户的联系人手机 userInfo.setIsEmailAuth(PublicStatusEnum.INACTIVE.getValue());
	 * // 是否绑定邮箱 userInfo.setIsMobileAuth(PublicStatusEnum.INACTIVE.getValue());
	 * // 是否绑定手机 userInfo.setProvince(merchantPos.getProvince());
	 * userInfo.setCity(merchantPos.getCity());
	 * userInfo.setArea(merchantPos.getArea());
	 * userInfo.setStatus(UserStatusEnum.INACTIVE.getValue());
	 * userInfo.setRealName(merchantPos.getFullName());
	 * 
	 * userInfoDao.insert(userInfo);
	 * 
	 * // 创建POS商户 merchantPos.setMerchantNo(merchantPosNo);
	 * merchantPos.setAccountNo(accountNo); long merchantPosId =
	 * merchantPosDao.insert(merchantPos);
	 * 
	 * int isInitialPwd = 0; // 是否更改过密码(0:否,1:是) // 创建操作员信息表
	 * createUserOperator(merchantPosNo, merchantPosNo, loginPwd,
	 * userInfo.getBindMobileNo(), isInitialPwd, merchantPos.getFullName());
	 * 
	 * // 创建账户密码表 createUserTradePwd(merchantPosNo, merchantPosNo, payPwd,
	 * isInitialPwd);
	 * 
	 * accountManagementFacade.createAccount(merchantPosNo, accountNo,
	 * AccountTypeEnum.POS.getValue());
	 * 
	 * return merchantPosId; }
	 */

	/***
	 * 创建用户交易密码
	 * 
	 * @param loginName
	 *            登录名
	 * @param userNo
	 *            用户编号
	 * @param payPwd
	 *            交易密码
	 * @param isInitialPwd
	 *            是否初始化密码, 如果==1，则是，需提示修改
	 */
	private void createUserTradePwd(String loginName, String userNo, String payPwd, int isInitialPwd) {
		UserTradePwd trade = new UserTradePwd();
		trade.setLoginName(loginName); // 登录名
		trade.setUserNo(userNo); // 用户编号
		trade.setTradePwd(payPwd); // 交易密码
		trade.setIsInitialPwd(isInitialPwd); // 是否需要修改密码
		trade.setPwdErrorTimes(0);
		userTradePwdDao.insert(trade);
	}

	/***
	 * 创建用户表
	 * 
	 * @param loginName
	 *            登录名
	 * @param accountNo
	 *            账户编号
	 * @param userNo
	 *            商户/会员编号
	 * @param question
	 *            安全保护问题
	 * @param answer
	 *            安全保护问题答案
	 * @param bindMobileNo
	 *            绑定手机号
	 * @param greeting
	 *            预留信息
	 * @param userType
	 *            用户类型
	 * @param stauts
	 *            状态, 用的UserStatusEnum枚举
	 */
	private void createUserInfo(String loginName, String accountNo, String userNo, String question, String answer, String bindMobileNo, String greeting, int userType, String cardNo, int status, Integer isMobileAuth, Integer isEmailAuth, String province, String city, String area, String fullName) {

		UserInfo userInfo = new UserInfo();
		userInfo.setLoginName(loginName);
		userInfo.setAccountNo(accountNo);
		userInfo.setUserNo(userNo);
		userInfo.setQuestion(String.valueOf(question));
		userInfo.setAnswer(answer);
		userInfo.setProvince(province);
		userInfo.setCity(city);
		userInfo.setArea(area);
		userInfo.setRealName(fullName); // 全称
		userInfo.setStatus(status);
		if (EmailValidator.getInstance().isValid(loginName)) {
			// 如果登录名是邮箱，则设置用户的绑定邮箱为登录名
			userInfo.setBindEmail(loginName);
			isEmailAuth = PublicStatusEnum.ACTIVE.getValue();
		}
		if (ValidateUtils.isMobile(loginName)) {// 会员注册的情况
			userInfo.setBindMobileNo(loginName);
			isMobileAuth = PublicStatusEnum.ACTIVE.getValue();
		} else if (!StringUtil.isEmpty(bindMobileNo)) {
			userInfo.setBindMobileNo(bindMobileNo);
			isMobileAuth = PublicStatusEnum.ACTIVE.getValue();
		} else {
			userInfo.setBindMobileNo("");
		}

		userInfo.setGreeting(greeting);
		userInfo.setUserType(userType);
		userInfo.setCardNo(cardNo);
		userInfo.setStatus(status);
		userInfo.setIsEmailAuth(isEmailAuth); // 是否绑定邮箱
		userInfo.setIsMobileAuth(isMobileAuth); // 是否绑定手机
		userInfo.setIsRealNameAuth(PublicStatusEnum.INACTIVE.getValue()); // 实名认证未认证

		userInfoDao.insert(userInfo);
		log.info("step.1 创建用户表完毕. ");
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
	@Transactional(rollbackFor = Exception.class)
	public String registerMerchant(String loginName, String loginPwd, String tradePwd, int question, String answer, String greeting, String bindMobileNo, Integer userType, Integer merchantType, String fullName, String shortName, String licenseNo, String url, String icp, String busiContactName, String busiContactMobileNo, String scope, String province, String city, String area, String address, String legalPerson, String cardNo) {

		// 判断是否是联合注册，如果联合注册，则需要把商户状态设置成激活。
		int merchantStatus = PublicStatusEnum.INACTIVE.getValue();
		int userInfoStatus = UserStatusEnum.INACTIVE.getValue();
		if (merchantType == MerchantTypeEnum.CHILDMERCHANT.getValue()) {
			// 表示联合注册
			merchantStatus = PublicStatusEnum.ACTIVE.getValue();
			userInfoStatus = UserStatusEnum.ACTIVE.getValue();
		}

		int isInitialPwd = 0; // 是否更改过密码(0:否,1:是)

		// 验证用户是否存在
		UserInfo userInfo = userInfoDao.getUserInfoByLoginName(loginName);
		if (userInfo != null) {
			throw new UserBizException(UserBizException.MERCHANT_USERINFO_IS_EXIST, "用户已存在");
		}
		
		int accountType = AccountTypeEnum.MERCHANT.getValue();
		if(userType == UserTypeEnum.CUSTOMER.getValue()){
			accountType = AccountTypeEnum.CUSTOMER.getValue();
		}else if(userType == UserTypeEnum.POSAGENT.getValue()){
			accountType = AccountTypeEnum.POS.getValue();
		}

		// 生成账户编号(调用账户服务)和商户编号
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.getEnum(accountType));
		String userNo = userInfoDao.buildUserNo("0000");

		// step.1 创建用户
		createUserInfo(loginName, accountNo, userNo, String.valueOf(question), answer, bindMobileNo, greeting, userType, cardNo, userInfoStatus, PublicStatusEnum.ACTIVE.getValue(), PublicStatusEnum.ACTIVE.getValue(), province, city, area, fullName);

		// step.2 创建在线商户
		MerchantOnline merchantOnline = new MerchantOnline();
		merchantOnline.setMerchantNo(userNo); // 商户编号
		merchantOnline.setAccountNo(accountNo); // 账户编号
		merchantOnline.setMerchantType(merchantType); // 商户类型
		merchantOnline.setMerchantKey(StrUtil.get32UUID()); // 商户秘钥
		merchantOnline.setFullName(fullName);
		merchantOnline.setShortName(fullName); // 同步使用商户名称
		merchantOnline.setLicenseNo(licenseNo);
		merchantOnline.setUrl(url);
		merchantOnline.setIcp(icp);
		merchantOnline.setBusiContactName(fullName); // 同步使用商户名称
		merchantOnline.setBusiContactMobileNo(busiContactMobileNo);
		merchantOnline.setScope(scope);
		merchantOnline.setStatus(merchantStatus);
		merchantOnline.setAddress(address);
		merchantOnline.setLegalPerson(legalPerson);
		merchantOnline.setCardNo(cardNo);
		merchantOnlineDao.insert(merchantOnline);

		// step.4 创建操作员
		createUserOperator(userNo, loginName, loginPwd, bindMobileNo, isInitialPwd, merchantOnline.getFullName());

		// step.5 创建账户密码
		createUserTradePwd(loginName, userNo, tradePwd, isInitialPwd);

		// step.6 创建账户信息
		accountManagementFacade.createAccount(userNo, accountNo, accountType);

		// step.7插入门户 用户和角色的关联表
		// 查询商户的管理员操作员
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("type", OperatorTypeEnum.ADMINISTRATOR.getValue()); // 管理员
		UserOperator operator = userOperatorDao.getBy(map);
		if (operator != null) {
			// 商户审核通过，往操作员与角色关联表插记录
			MerchantRoleOperator merchantRoleOperator = merchantRoleOperatorDao.getByRoleIdAndOperatorId(0L, operator.getId());
			if (merchantRoleOperator == null) {
				merchantRoleOperator = new MerchantRoleOperator();
				merchantRoleOperator.setOperatorId(operator.getId());
				merchantRoleOperator.setRoleId(0L); // 0-管理员角色，1-普通用户
				merchantRoleOperatorDao.insert(merchantRoleOperator);
			}
		}

		return userNo;
	}

	/***
	 * 注册POS商户
	 * 
	 * @param loginName
	 * @param merchantType
	 * @param signName
	 * @param shortName
	 * @param licenseNo
	 * @param url
	 * @param icp
	 * @param busiContactName
	 * @param busiContactMobileNo
	 * @param scope
	 * @param province
	 * @param city
	 * @param area
	 * @param orgCode
	 * @param address
	 * @param legalPerson
	 * @param cardNo
	 * @param mcc
	 */
	/*
	 * public void registerPosMerchant(int userType, String loginPwd, String
	 * tradePwd, int question, String answer, String greeting, String
	 * bindMobileNo, String loginName, Integer merchantType, String fullName,
	 * String shortName, String licenseNo, String busiContactName, String
	 * busiContactMobileNo, String scope, String province, String city, String
	 * area, String orgCode, String address, String legalPerson, String cardNo,
	 * String mcc) {
	 * 
	 * int isInitialPwd = 0; // 是否更改过密码(0:否,1:是)
	 * 
	 * // 验证用户是否存在 UserInfo userInfo =
	 * userInfoDao.getUserInfoByLoginName(loginName); if (userInfo != null) {
	 * throw new UserBizException(UserBizException.MERCHANT_USERINFO_IS_EXIST,
	 * "用户已存在"); }
	 * 
	 * // 生成POS商户编号(888+4位mcc码+8位随机数) String posMerchantNo =
	 * userInfoDao.buildUserNo(mcc);
	 * 
	 * // 账户编号 String accountNo =
	 * accountManagementFacade.buildAccountNo(AccountTypeEnum
	 * .getEnum(userType));
	 * 
	 * // step.1 创建用户表 createUserInfo(loginName, accountNo, posMerchantNo,
	 * String.valueOf(question), answer, bindMobileNo, greeting, userType,
	 * cardNo, UserStatusEnum.INACTIVE.getValue(),
	 * PublicStatusEnum.ACTIVE.getValue(), PublicStatusEnum.ACTIVE.getValue(),
	 * province, city, area, fullName);
	 * 
	 * // step.3 创建pos商户 MerchantPos merchantPos = new MerchantPos();
	 * merchantPos.setMcc(mcc); merchantPos.setMerchantNo(posMerchantNo);
	 * merchantPos.setAccountNo(accountNo); // 账户编号
	 * merchantPos.setMerchantLevel(MerchantLevelEnum.MERCHANT.getValue());//
	 * 等级为商户 merchantPos.setMerchantType(merchantType);
	 * merchantPos.setFullName(fullName); merchantPos.setShortName(shortName);
	 * merchantPos.setLicenseNo(licenseNo);
	 * merchantPos.setBusiContactName(busiContactName);
	 * merchantPos.setBusiContactMobileNo(busiContactMobileNo);
	 * merchantPos.setScope(scope); merchantPos.setAddress(address);
	 * merchantPos.setOrgCode(orgCode); merchantPos.setProvince(province);
	 * merchantPos.setCity(city); merchantPos.setArea(area);
	 * merchantPos.setStatus(MerchantStatusEnum.CREATED.getValue()); // 已创建
	 * merchantPos.setCardNo(cardNo); merchantPos.setLegalPerson(legalPerson);
	 * merchantPosDao.insert(merchantPos);
	 * 
	 * // step.4 创建操作员 createUserOperator(posMerchantNo, loginName, loginPwd,
	 * bindMobileNo, isInitialPwd, fullName);
	 * 
	 * // step.5 创建账户密码表 createUserTradePwd(loginName, posMerchantNo, tradePwd,
	 * isInitialPwd);
	 * 
	 * // step.6 创建账户信息 accountManagementFacade.createAccount(posMerchantNo,
	 * accountNo, userType);
	 * 
	 * }
	 */

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
	@Transactional(rollbackFor = Exception.class)
	public void registerChildMerchant(String merchantNo, String childLoginName, String childLoginPwd, String childRealName, String childMobileNo, String childEmail) {

		String childUserNo = this.registerMerchant(childLoginName, childLoginPwd, childLoginPwd, 0, "", "", childMobileNo, wusc.edu.pay.facade.user.enums.UserTypeEnum.MERCHANT.getValue(), MerchantTypeEnum.CHILDMERCHANT.getValue(), childRealName, childRealName, "", "", "", childRealName, childMobileNo, "", "", "", "", "", "", "");

		// 建立父子关系
		UserRelation userRelation = new UserRelation();
		userRelation.setParentUserNo(merchantNo);
		userRelation.setChildUserNo(childUserNo);

		userRelationDao.insert(userRelation);
	}

	/**
	 * 门户注册
	 * 
	 * @param loginName
	 *            登录名
	 * @param merchantType
	 *            签约属性
	 * @param fullName
	 *            商户全称
	 * @param shortName
	 *            商户简称
	 * @param licenseNo
	 *            营业执照号
	 * @param url
	 *            商城网址
	 * @param mcc
	 *            MCC码
	 * @param orgCode
	 *            组织机构代码
	 * @param icp
	 *            IP
	 * @param legalPerson
	 *            法人姓名
	 * @param cardNo
	 *            身份证号
	 * @param busiContactName
	 *            联系人姓名
	 * @param busiContactMobileNo
	 *            联系人电话
	 * @param scope
	 *            经营范围
	 * @param province
	 *            省
	 * @param city
	 *            市
	 * @param address
	 *            具体地址
	 * @param loginPwd
	 *            登录密码
	 * @param tradePwd
	 *            交易密码
	 * @param question
	 *            安全问题
	 * @param answer
	 *            安全问题回答
	 * @param greeting
	 *            预留信息
	 * @param bindingPhone
	 *            绑定手机
	 */
	public void registerPortalMerchant(String loginName, Integer merchantType, String fullName, String shortName, String licenseNo, Date licenseValid, String url, String mcc, String orgCode, String icp, String legalPerson, String cardNo, String cardValid, String busiContactName, String busiContactMobileNo, String scope, String province, String city, String address, String loginPwd, String tradePwd, int question, String answer, String greeting, String bindingPhone) {

		//int userType;

		// 验证用户是否存在
		UserInfo userInfo = userInfoDao.getUserInfoByLoginName(loginName);
		if (userInfo != null) {
			throw new UserBizException(UserBizException.MERCHANT_USERINFO_IS_EXIST, "用户已存在");
		}

		// 生成POS商户编号(888+4位mcc码+8位随机数)
		String posMerchantNo;
		int accountType = AccountTypeEnum.MERCHANT.getValue();
		if (StringUtils.isBlank(mcc)) {
			posMerchantNo = userInfoDao.buildUserNo("0000");
		} else {
			posMerchantNo = userInfoDao.buildUserNo(mcc);
		}
		// 账户编号
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.MERCHANT);

		// step.1 创建用户表
		createUserInfo(loginName, accountNo, posMerchantNo, String.valueOf(question), answer, bindingPhone, greeting, UserTypeEnum.MERCHANT.getValue(), cardNo, UserStatusEnum.INACTIVE.getValue(), PublicStatusEnum.ACTIVE.getValue(), PublicStatusEnum.ACTIVE.getValue(), province, city, "", fullName);

		// step.2创建在线商户
		MerchantOnline merchantOnline = new MerchantOnline();

		merchantOnline.setMerchantNo(posMerchantNo);// 商户编号
		merchantOnline.setAccountNo(accountNo); // 账户编号
		merchantOnline.setMcc(mcc);// MCC编号
		merchantOnline.setOrgCode(orgCode);// 组织机构代码
		merchantOnline.setMerchantType(merchantType);// merchantType 签约属性
		merchantOnline.setFullName(fullName);// fullName 商户全称
		merchantOnline.setShortName(shortName);// shortName 商户简称
		merchantOnline.setLicenseNo(licenseNo);// 营业执照号
		if (StringUtil.isNotNull(licenseValid)) {
			merchantOnline.setLicenseNoValid(licenseValid); // 营业执照有效期
		}
		merchantOnline.setUrl(url);// url商城网址
		merchantOnline.setOrgCode(orgCode);// 组织机构代码
		merchantOnline.setIcp(icp);// icp IP
		merchantOnline.setLegalPerson(legalPerson);// legalPerson 法人姓名
		merchantOnline.setCardNo(cardNo);// cardNo 身份证号
		if (StringUtil.isNotNull(cardValid)) { // 身份证有效期
			merchantOnline.setCertificateExpiry(cardValid);
		}
		merchantOnline.setBusiContactName(busiContactName);// busiContactName联系人姓名
		merchantOnline.setBusiContactMobileNo(busiContactMobileNo);// busiContactMobileNo联系人电话
		merchantOnline.setScope(scope);// scope 经营范围
		merchantOnline.setAddress(address);// 地址
		merchantOnline.setStatus(MerchantStatusEnum.CREATED.getValue()); // 已创建
		merchantOnline.setMerchantKey(StrUtil.get32UUID()); // 商户秘钥
		merchantOnlineDao.insert(merchantOnline);

		// step.4 创建操作员
		createUserOperator(posMerchantNo, loginName, loginPwd, bindingPhone, 1, fullName);

		// step.5 创建账户密码表
		createUserTradePwd(loginName, posMerchantNo, tradePwd, 1);

		// step.6 创建账户信息
		accountManagementFacade.createAccount(posMerchantNo, accountNo, accountType);

		// step.7插入门户 用户和角色的关联表
		// 查询商户的管理员操作员
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", posMerchantNo);
		map.put("type", OperatorTypeEnum.ADMINISTRATOR.getValue()); // 管理员
		UserOperator operator = userOperatorDao.getBy(map);
		if (operator != null) {
			// 商户审核通过，往操作员与角色关联表插记录
			MerchantRoleOperator merchantRoleOperator = merchantRoleOperatorDao.getByRoleIdAndOperatorId(0L, operator.getId());
			if (merchantRoleOperator == null) {
				merchantRoleOperator = new MerchantRoleOperator();
				merchantRoleOperator.setOperatorId(operator.getId());
				merchantRoleOperator.setRoleId(0L); // 0-管理员角色，1-普通用户
				merchantRoleOperatorDao.insert(merchantRoleOperator);
			}
		}
	}

	/***
	 * 代理商系统注册商户信息
	 * 
	 * @param userInfo
	 * @param merchant
	 * @param agentMerchantRelation
	 * @param loginPwd
	 * @param merchantFile 商户资质文件
	 * @throws UserBizException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void addAgentMerchantInfo(UserInfo userInfo, MerchantOnline merchant, AgentMerchantRelation agentMerchantRelation, 
			String loginPwd, MerchantFile merchantFile, SettRule settRule, UserBankAccount bankaccount) {
		
		// 账户类型
		int accountType = AccountTypeEnum.POS_OUT_SETT.getValue();

		log.info("===================> 进入用户接口创建POS商户信息 <===================");
		log.info("===================> 开始生成账户编号。");
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.getEnum(accountType));
		log.info("===================> 调用账户服务生成账户编号【" + accountNo + "】。");

		log.info("===================> 开始生成用户编号。");
		String userNo = userInfoDao.buildUserNo(merchant.getMcc());
		log.info("===================> 调用用户服务生成用户编号【" + userNo + "】。");

		userInfo.setAccountNo(accountNo);
		userInfo.setUserNo(userNo);
		// 保存用户信息
		log.info("===================> 开始创建用户表数据。");
		long userId = userInfoDao.insert(userInfo);
		log.info("===================> 调用用户服务创建用户信息成功，用户ID【" + userId + "】。");

		merchant.setAccountNo(accountNo);
		merchant.setMerchantNo(userNo);
		// 保存商户信息
		log.info("===================> 开始创建商户信息表数据。");
		long merchantId = merchantOnlineDao.insert(merchant);
		log.info("===================> 调用商户服务创建商户信息成功，商户ID【" + merchantId + "】。");
		
		// 创建代理商和商户的关联表
		agentMerchantRelation.setMerchantNo(userNo);
		log.info("===================> 开始创建代理商和商户关联表数据。");
		long relationId = agentMerchantRelationBiz.create(agentMerchantRelation);
		log.info("===================> 调用用户服务创建代理商和商户关联信息成功，关联ID【" + relationId + "】。");

		// 创建操作员表 
		createUserOperator(userNo, userInfo.getLoginName(), loginPwd, userInfo.getBindMobileNo(), 1, merchant.getFullName());
		
		// 查询商户的管理员操作员
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("type", OperatorTypeEnum.ADMINISTRATOR.getValue()); // 管理员
		UserOperator operator = userOperatorDao.getBy(map);
		if (operator != null) {
			// 商户审核通过，往操作员与角色关联表插记录
			MerchantRoleOperator merchantRoleOperator = merchantRoleOperatorDao.getByRoleIdAndOperatorId(0L, operator.getId());
			if (merchantRoleOperator == null) {
				merchantRoleOperator = new MerchantRoleOperator();
				merchantRoleOperator.setOperatorId(operator.getId());
				merchantRoleOperator.setRoleId(0L); // 0-管理员角色，1-普通用户
				merchantRoleOperatorDao.insert(merchantRoleOperator);
			}
		}
		
		// 创建账户密码表
		createUserTradePwd(userInfo.getLoginName(), userNo, loginPwd, 1);

		// 创建商户文件表
		merchantFile.setMerchantNo(userNo);
		log.info("===================> 开始创建商户文件信息数据。");
		long fileId = merchantFileBiz.create(merchantFile);
		log.info("===================> 调用用户服务创建商户文件信息成功，数据ID【" + fileId + "】。");

		// 创建企业结算账户信息
		bankaccount.setUserNo(userNo);
		log.info("===================> 开始创建企业结算账户信息数据。");
		long userbankId = userBankAccountBiz.creatUserBankAccount(bankaccount);
		log.info("===================> 调用用户账户服务创建企业结算账户信息成功，企业结算账户ID【" + userbankId + "】。");
		
		// 创建结算信息
		settRule.setAccountNo(accountNo);
		settRule.setUserNo(userNo);
		log.info("===================> 开始创建结算信息数据。");
		long settId = settManagementFacade.createSettlementRule(settRule);
		log.info("===================> 调用结算服务创建结算信息成功，结算ID【" + settId + "】。");

		// 创建账户信息
		log.info("===================> 开始创建账户信息数据。");
		accountManagementFacade.createAccount(userNo, accountNo, accountType);
		log.info("===================> 调用账户服务创建账户信息成功，账户编号【" + accountNo + "】。");
		log.info("===================> 创建POS商户信息成功 <===================");
	}

	@Transactional(rollbackFor = Exception.class)
	public void addAgentInfo(UserInfo userInfo, MerchantOnline merchant, AgentRelation agentRelation, SettRule settRule, UserBankAccount userBankAccount, PmsOperator pmsOperator) {

		//int userType = userInfo.getUserType();
		int accountType = AccountTypeEnum.POSAGENT.getValue();

		log.info("===================> 进入用户接口创建商户信息。");
		log.info("===================> 开始生成账户编号。");
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.getEnum(accountType));
		log.info("===================> 调用账户服务生成账户编号【" + accountNo + "】。");

		log.info("===================> 开始生成用户编号。");
		String userNo = userInfoDao.buildUserNo(merchant.getMcc());
		log.info("===================> 调用用户服务生成用户编号【" + userNo + "】。");

		userInfo.setAccountNo(accountNo);
		userInfo.setUserNo(userNo);
		// 保存用户信息
		log.info("===================> 开始创建用户表数据。");
		long userId = userInfoDao.insert(userInfo);
		log.info("===================> 调用用户服务创建用户信息成功，用户ID【" + userId + "】。");

		merchant.setAccountNo(accountNo);
		merchant.setMerchantNo(userNo);
		// 保存商户信息
		log.info("===================> 开始创建商户信息表数据。");
		long merchantId = merchantOnlineDao.insert(merchant);
		log.info("===================> 调用商户服务创建商户信息成功，商户ID【" + merchantId + "】。");

		// 创建代理商和代理商的关联表
		agentRelation.setAgentNo(userNo);
		log.info("===================> 开始创建代理商和代理商关联表数据。");
		long agentRelationId = agentRelationBiz.create(agentRelation);
		log.info("===================> 调用用户服务创建代理商和代理商关联信息成功，关联ID【" + agentRelationId + "】。");

		// 创建企业结算账户信息
		userBankAccount.setUserNo(userNo);
		log.info("===================> 开始创建企业结算账户信息数据。");
		long userbankId = userBankAccountBiz.creatUserBankAccount(userBankAccount);
		log.info("===================> 调用用户账户服务创建企业结算账户信息成功，企业结算账户ID【" + userbankId + "】。");

		// 创建创建操作员
		pmsOperator.setUserNo(userNo);
		log.info("===================> 开始创建账户信息数据。");
		pmsOperatorBiz.saveOperator(pmsOperator, "2,"); // 默认角色值为2（默认操作员角色，系统初始化时决定）
		log.info("===================> 调用用户服务创建操作员信息成功");
		
		// 创建结算信息
		settRule.setAccountNo(accountNo);
		settRule.setUserNo(userNo);
		log.info("===================> 开始创建结算信息数据。");
		long settId = settManagementFacade.createSettlementRule(settRule);
		log.info("===================> 调用结算服务创建结算信息成功，结算ID【" + settId + "】。");
				
		// 创建账户信息
		log.info("===================> 开始创建账户信息数据。");
		accountManagementFacade.createAccount(userNo, accountNo, accountType);
		log.info("===================> 调用账户服务创建账户信息成功，账户编号【" + accountNo + "】。");
		
	}
}
