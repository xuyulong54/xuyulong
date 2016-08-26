package wusc.edu.pay.core.mobile;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.enums.OperatorTypeEnum;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.agent.biz.AgentMerchantRelationBiz;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.core.user.dao.MerchantRoleOperatorDao;
import wusc.edu.pay.core.user.dao.UserInfoDao;
import wusc.edu.pay.core.user.dao.UserOperatorDao;
import wusc.edu.pay.core.user.dao.UserTradePwdDao;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.MerchantRoleOperator;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/***
 * @ClassName: MobileBiz 
 * @Description: 手机端接口业务类
 * @author Huang Bin 
 * @date 2015-3-27 上午9:52:23
 */
@Component("mobileBiz")
public class MobileBiz {
	
	private static final Log log = LogFactory.getLog(MobileBiz.class);
	
	@Autowired
	private UserInfoDao userInfoDao; // 用户信息接口
	@Autowired
	private MerchantOnlineDao merchantOnlineDao; // 在线商户服务
	@Autowired
	private UserOperatorDao userOperatorDao; // 用户操作员服务
	@Autowired
	private UserTradePwdDao userTradePwdDao; // 用户交易密码
	@Autowired
	private AccountManagementFacade accountManagementFacade; // 账户服务
	@Autowired
	private AgentMerchantRelationBiz agentMerchantRelationBiz;
	@Autowired
	private MerchantRoleOperatorDao merchantRoleOperatorDao;
	
	/***
	 * @Title: addAgentMerchantInfo 
	 * @Description: 手机端的商户注册
	 * @param @param userInfo
	 * @param @param merchant
	 * @param @param relation
	 * @param @param p2_LoginPwd    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Transactional(rollbackFor = Exception.class)
	public String addMobileMerchantInfo(UserInfo userInfo, MerchantOnline merchant, 
			AgentMerchantRelation agentMerchantRelation, String loginPwd) {
		
		// 账户类型
		int accountType = AccountTypeEnum.POS_OUT_SETT.getValue();

		log.info("===================> 进入用户接口创建POS商户信息 <===================");
		log.info("===================> 开始生成账户编号。");
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.getEnum(accountType));
		log.info("===================> 调用账户服务生成账户编号【" + accountNo + "】。");

		log.info("===================> 开始生成用户编号。");
		String userNo = userInfoDao.buildUserNo(merchant.getMcc());
		if(StringUtil.isEmpty(userNo)){
			throw new UserBizException(UserBizException.USER_NO_IS_NULL, "用户编号为空");
		}
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

		UserOperator operator = userOperatorDao.getOperator_userNo_type(userNo, OperatorTypeEnum.ADMINISTRATOR.getValue());
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

		// 创建账户信息
		log.info("===================> 开始创建账户信息数据。");
		accountManagementFacade.createAccount(userNo, accountNo, accountType);
		log.info("===================> 调用账户服务创建账户信息成功，账户编号【" + accountNo + "】。");
		log.info("===================> 创建POS商户信息成功 <===================");
		
		return userNo;
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
	
}
