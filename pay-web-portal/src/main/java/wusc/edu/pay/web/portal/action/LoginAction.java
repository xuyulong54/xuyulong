/**
 * wusc.edu.pay.web.portal.action.LoginAction.java
 */
package wusc.edu.pay.web.portal.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.enums.ArticleTypeEnum;
import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.facade.boss.service.ArticleFacade;
import wusc.edu.pay.facade.boss.service.ScoreFacade;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.enums.UserOperatorTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * <ul>
 * <li>Title:登录控制器</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-5-29
 */
public class LoginAction extends BaseAction {
	private static Log log = LogFactory.getLog(LoginAction.class);
	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private ArticleFacade articleFacade;
	@Autowired
	private ScoreFacade scoreFacade;
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5215571553482591944L;

	public String loginUI() {
		putData("userType", "merchant");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", ArticleTypeEnum.NOTICE.getValue());// 通知、公告
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue());
		paramMap.put("articleType", 1);
		getSessionMap().put("recordListNotice", articleFacade.listPage(getPageParam(), paramMap).getRecordList());

		return "loginUI";
	}

	public String loginout() {
		getSessionMap().clear();
		return "loginout";
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	public String login() {
		
		String returnStr = "loginUI";
		String userType = StringTools.stringToTrim(getString("userType"));// 登录类型
		String loginName = StringTools.stringToTrim(getString("loginName"));// 登录名
		String password = super.getPwd("loginPwd"); // 登录密码
		String randomCode = StringTools.stringToTrim(getString("randomCode"));// 验证码
		
		Map<String, Object> paramMap = super.getParamMap_Utf8();
		if(paramMap != null){
			paramMap.remove("loginPwd");
		}
		log.info("请求数据：" + paramMap);
		
		if(!"false".equals(randomCode)){
			// 1、验证码是否有误
			if (ValidateUtil.isValidateCode(this.getHttpRequest(), randomCode)) {
				putData("loginMsg", "验证码错误");
				putData("userType", userType);
				putData("loginName", loginName);
				return returnStr;
			}
		}

		// 2、防止重复登录
	/*	int userCount = getOnLineUserCount(loginName, userType.equals("merchant") ? BaseConsts.MERCHANT_APPLICATION_KEY : BaseConsts.MEMBER_APPLICATION_KEY);
		if (userCount >= (userType.equals("merchant") ? PublicConfig.MERCHANT_BIGGEST_LOGINS : PublicConfig.MEMBER_BIGGEST_LOGINS)) {
			putData("loginMsg", "系统繁忙，请稍后再试");
			putData("userType", userType);
			putData("loginName", loginName);
			return returnStr;
		}
	 */
		// 3、申明用户操作对象
		UserOperator userOperator = null;

		// 4.用户登录
		if ("merchant".equals(userType)) {
			try {
				// 1.验证用户登录
				UserInfo userInfo = userManagementFacade.merchantLogin(loginName, DigestUtils.sha1Hex(password), PublicConfig.PWD_ERROR_LIMIT_TIMES,
						PublicConfig.PWD_ERROR_LIMIT_TIME);

				// 2.增加登录积分（2分）
				scoreFacade.loginAddScore(userInfo.getAccountNo(), 2L, "登录积分");

				// 3.把相关信息放入Session
				userOperator = getUserOperator(loginName);

				// 获取商户权限
				getSessionMap().put(BaseConsts.MERCHANT_ACTIONS, getMerchantActions(userOperator.getId()));

				// 用户信息
				setCurrentUserInfo(userInfo);

				// 操作员信息
				setCurrentUserOperator(userOperator);

				// 4.跳转页面
				Object redirectUrl = getSessionMap().get(BaseConsts.MERCHANT_REDIRECT_URL);
				if (StringUtil.isNotNull(redirectUrl)) {
					return BaseConsts.MERCHANT_REDIRECT_URL;
				}

				// 判断是否修改过登录密码,如果未修改，则去修改密码页面
				if (userOperator.getIsChangedPwd() == null || userOperator.getIsChangedPwd().intValue() == 0) {
					if (userOperator.getType()==UserOperatorTypeEnum.USER.getValue()) {//操作员第一次登陆
						returnStr ="firstEditLoginPwdUI";
					}
					else {//管理员第一次登陆
						returnStr = "firstEditLoginTradePwdUI";
					}
				}else {
					returnStr = "merchantIndex";
				}
			} catch (UserBizException e) {
				//UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户名或密码错误"  则统计是否出现验证吗
				if(PublicConfig.PWD_TIMES_USE_KAPTCHA>0){
					super.setLoginTimesReach();
					
				}
				putData("loginMsg", e.getMsg());
				putData("userType", userType);
				putData("loginName", loginName);
				return returnStr;
			}
		} else if ("member".equals(userType)) {
			try {
				// 1.验证用户登录
				UserInfo userInfo = userManagementFacade.memberLogin(loginName, DigestUtils.sha1Hex(password), PublicConfig.PWD_ERROR_LIMIT_TIMES,
						PublicConfig.PWD_ERROR_LIMIT_TIME);

				// 2.增加登录积分（2分）
				scoreFacade.loginAddScore(userInfo.getAccountNo(), 2L, "登录积分");

				// 3.把相关信息放入Session
				userOperator = getUserOperator(loginName);

				// 用户信息
				setCurrentUserInfo(userInfo);

				// 操作员信息
				setCurrentUserOperator(userOperator);

				// 4.跳转页面
				Object redirectUrl = getSessionMap().get(BaseConsts.MEMBER_REDIRECT_URL);
				if (StringUtil.isNotNull(redirectUrl)) {
					return BaseConsts.MEMBER_REDIRECT_URL;
				}
				returnStr = "memberIndex";
			} catch (UserBizException e) {
				//UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户名或密码错误"  则统计是否出现验证吗
				if(PublicConfig.PWD_TIMES_USE_KAPTCHA>0){
					super.setLoginTimesReach();
					
				}
				putData("loginMsg", e.getMsg());
				putData("userType", userType);
				putData("loginName", loginName);
				return returnStr;
			}
		}
		log.info("===> 登录成功，准备记录操作日志！");
		// 记录操作日志
		this.addUserLog(OpeStatusEnum.SUCCESS, "登录系统成功。");
		log.info(String.format("===> 记录操作日志完毕，跳转到页面{%s}", returnStr));
		if(!StringUtil.isEmpty(returnStr)) return returnStr;
		return SUCCESS;
	}

//	private int getOnLineUserCount(String loginName, String applicationKey) {
//		Map<String, String> applicationMap = (Map<String, String>) this.getApplicationMap().get(applicationKey);
//		if (applicationMap == null) {
//			applicationMap = new HashMap<String, String>();
//		}
//		String sessionId = this.getHttpRequest().getSession().getId(); // 当前会话ID
//		String checkSessionId = applicationMap.get(loginName); // 判断此账户是否已登录
//		if (applicationMap.containsKey(loginName) && !sessionId.equals(checkSessionId)) {
//			// 登录名存在，但sessionid不同，证明账户已登录，按需求将已登录的sessionid失效，以使其强制退出
//			// 根据sessionI强制清除session
//			GzzYzzSessionContext mySessionMap = GzzYzzSessionContext.getInstance();
//			if (mySessionMap.getSession(checkSessionId) != null) {
//				mySessionMap.getSession(checkSessionId).invalidate(); // 使较早登录的用户会话失效
//				mySessionMap.delSessionById(checkSessionId); // 集合中移除session
//			}
//			// 更新applicationOperatorMap中登录名对应的sessionid
//			applicationMap.put(loginName, sessionId);
//		}
//		// 判断application域中是否有此会员信息，用于防止重复登录 End
//		if (!applicationMap.containsKey(loginName)) {
//			// 如果没登录，把登录名放进application域
//			applicationMap.put(loginName, sessionId);
//			this.getApplicationMap().put(applicationKey, applicationMap);
//		}
//		return applicationMap.size();
//	}

	/**
	 * 设置用户的CA证书
	 */
	public void setCASession() {
		this.getSessionMap().put(BaseConsts.CURRENT_SN, getString("SN"));
	}

}
