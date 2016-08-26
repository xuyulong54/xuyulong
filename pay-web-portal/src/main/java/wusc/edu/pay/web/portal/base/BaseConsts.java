package wusc.edu.pay.web.portal.base;

/**
 * 
 * @描述: 基础常量类.
 * @作者: WuShuicheng.
 * @创建时间: 2014-1-3,下午11:08:09.
 * @版本号: V1.0 .
 * 
 */
public class BaseConsts {

	/**
	 * 登录商户的session键名:merchantOperator.
	 */
	public static final String MERCHANT_SESSION_KEY = "merchantOperator";

	/**
	 * 登录商户的application域键名:merchantOperatorApp.
	 */
	public static final String MERCHANT_APPLICATION_KEY = "merchantOperatorApp";

	/**
	 * 登录会员的session键名:loginMember.
	 */
	public static final String MEMBER_SESSION_KEY = "loginMember";

	/**
	 * 登录会员的application域键名:loginMemberApp.
	 */
	public static final String MEMBER_APPLICATION_KEY = "loginMemberApp";

	/**
	 * 会员未登录或登录超时访问地址
	 */
	public static final String MEMBER_REDIRECT_URL = "memberRedirectUrl";
	/**
	 * 商户未登录或登录超时访问地址
	 */
	public static final String MERCHANT_REDIRECT_URL = "merchantRedirectUrl";
	/**
	 * 功能权限ID串
	 */
	public static final String MERCHANT_ACTIONS = "actions";
	/**
	 * 当前登录用户
	 */
	public static final String CURRENT_USER = "currentUser";

	/**
	 * 当前用户的手机验证码
	 */
	public static final String CURRENT_USER_PHONE_CODE = "CURRENT_USER_PHONE_CODE";

	/**
	 * 当前用户的SN列表
	 */
	public static final String CURRENT_SN_LIST = "CURRENT_SN_LIST";
	/**
	 * 当前用户的SN
	 */
	public static final String CURRENT_SN = "CURRENT_SN";
	/**
	 * 是否是数字证书用户
	 */
	public static final String IS_SN_USER = "IS_SN_USER";

	/**
	 * 页面错误提示KEY
	 */
	public static final String PAGE_ERROR_MSG = "PAGE_ERROR_MSG";
	/**
	 * ACTION错误跳转地址
	 */
	public static final String ACTION_ERROR_ADD = "ACTION_ERROR_ADD";

	/**
	 * 当前操作员
	 */
	public static final String CURRENT_USER_OPERATOR = "userOperator";

	/***
	 * 存放商户注册时第二步的信息
	 */
	public static final String SAVE_SECOND_STEP_VALUE = "saveSecondStepValue";
	/***
	 * 系统标示
	 */
	public static final String SYSTEM_NAME = "GWWEBPORTAL";

	/***
	 * 用于验证邮件链接，存放于MAP中的KEY
	 */
	public static final String EMAILVERIFY_MAP_KEY = "EMAILVERIFY_MAP";
	
	/***
	 * 用户登陆出错次数 Session KEY 值 
	 */
	public static final  String LOGIN_TIMES_REACH = "LOGIN_TIMES_REACH";
}
