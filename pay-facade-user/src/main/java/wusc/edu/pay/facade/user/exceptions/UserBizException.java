package wusc.edu.pay.facade.user.exceptions;

import wusc.edu.pay.common.exceptions.BizException;

/**
 * 
 * 类描述： 用户接口异常类
 * 
 * @author Liliqiong
 * @version 2014-5-27
 */
public class UserBizException extends BizException {
	
	private static final long serialVersionUID = -3175990411418914329L;
	
	/***
	 * 登录：用户名不存在
	 */
	public static final int LOGIN_LOGNAME_NOT_EXIST = 20020001;
	/***
	 * 登录：密码错误
	 */
	public static final int LOGIN_PWD_ERROR = 20020002;

	/***
	 * 登录：密码错误次数超限
	 */
	public static final int LOGIN_PWDERRORTIMES_OVERRUN = 20020003;

	/***
	 * 登录：操作员状态为冻结
	 */
	public static final int LOGIN_OPERATORSTATUS_INACTIVE = 20020004;
	
	/***
	 * 登录：操作员被销户
	 */
	public static final int LOGIN_OPERATORSTATUS_CANCELLATION = 20020005;

	/***
	 * 用户已存在
	 */
	public static final int USERINFO_IS_EXIST = 20020006;

	/***
	 * 用户不存在
	 */
	public static final int USERINFO_NOT_IS_EXIST = 20020007;

	/**
	 * 商户销户：存在交易
	 */
	public static final int CELL_ORDE_IS_EXIST = 20020008;

	/**
	 * 商户销户：有余额
	 */
	public static final int CELL_BALANCE_MORETHAN_ONE = 20020009;

	/***
	 * 你没有修改超级管理员的权限
	 */
	public static final int USERINFO_NOT_EDIT_AUTHORITY = 20020010;

	/***
	 * 找不到关联的userInfo
	 */
	public static final int NOT_FOUND_USERINFO = 20020012;

	/***
	 * 商户银行渠道分流不存在
	 */
	public static final int MERCHANT_BANK_BRANCH_IS_NULL = 20020013;

	/***
	 * 商户业务设置信息不存在
	 */
	public static final int MERCHANT_BUSI_SETTING_IS_NULL = 20020014;

	/***
	 * 商户退款限制信息不存在
	 */
	public static final int MERCHANT_REFUND_LIMIT_IS_NULL = 20020015;

	/***
	 * 商户交易限制信息不存在
	 */
	public static final int MERCHANT_TRADE_LIMIT_IS_NULL = 20020016;

	/***
	 * 商户信息不存在
	 */
	public static final int MERCHANT_IS_NULL = 20020017;

	/***
	 * 商户操作日志记录信息不存在
	 */
	public static final int MERCHANT_OPERATE_LOG_IS_NULL = 20020018;

	/***
	 * 代理商信息不存在
	 */
	public static final int AGENT_IS_NULL = 20020019;

	/***
	 * 商户请求记录信息不存在
	 */
	public static final int MERCHANT_REQUEST_LOG_IS_NULL = 20020020;

	/***
	 * 代理商费率不存在
	 */
	public static final int AGENT_RATE_IS_NULL = 20020021;

	/***
	 * 代理商费率调整记录不存在
	 */
	public static final int AGENT_RATE_ADJUST_LOG_IS_NULL = 20020022;

	/***
	 * 商户费率记录不存在
	 */
	public static final int MERCHANT_RATE_IS_NULL = 20020023;

	/***
	 * 商户费率调整记录不存在
	 */
	public static final int MERCHANT_RATE_ADJUST_LOG_IS_NULL = 20020024;

	/***
	 * 商户阶梯费率为空
	 */
	public static final int MERCHANT_STEP_RATE_IS_NULL = 20020025;

	/***
	 * 商户阶梯费率日志不存在
	 */
	public static final int MERCHANT_STEP_RATE_ADJUST_LOG_IS_NULL = 20020026;

	/***
	 * 该用户已存在
	 */
	public static final int MERCHANT_USERINFO_IS_EXIST = 20020027;

	/***
	 * 该用户不存在
	 */
	public static final int MERCHANT_USERINFO_NOT_EXIST = 20020028;

	/**
	 * 登录密码错误
	 */
	public static final int LOGPWD_ERROR = 20020029;

	/**
	 * 交易密码不存在
	 */
	public static final int USER_TRADEPWD_NOT_EXIT = 20020030;

	/**
	 * 用户交易密码错误
	 */
	public static final int USER_TRADEPWD_ERROR = 20020031;

	/**
	 * 交易密码输入错误次数超限
	 */
	public static final int USER_TRADEPWD_ERROR_TIMES_OUTLIMIT = 20020032;

	/**
	 * 实名验证的审核记录不存在
	 */
	public static final int USER_AUDIT_RECORD_REALNAME_IS_NULL = 20020033;

	/**
	 * 用户状态的审核记录不存在
	 */
	public static final int USER_AUDIT_RECORD_STATUS_IS_NULL = 20020034;

	/**
	 * 用户销户的审核记录不存在
	 */
	public static final int USER_AUDIT_RECORD_CLOSE_IS_NULL = 20020035;

	/**
	 * 上次审核未处理完成
	 */
	public static final int USER_LAST_USERAUDIT_IS_NOT_FINISHED = 20020036;

	/**
	 * 用户已经实名验证
	 */
	public static final int USER_REALNAMEED_IS_CHECKED = 20020037;

	/**
	 * 用户未开通收款功能
	 */
	public static final int MERCHANT_BUSI_SETTING_PAY_IS_NOT_EXIST = 20020038;
	
	/***
	 * 会员信息为空
	 */
	public static final int MEMBER_INFO_IS_NULL = 20020039;
	
	/***
	 * 结算账户信息不存在
	 */
	public static final int USER_BANK_ACCOUNT_IS_NULL = 20020040;
	
	/***
	 * 商户资质文件信息不存在.
	 */
	public static final int MERCHANT_FILE_IS_NULL = 20020041;
	
	/***
	 * 代理商商户关系信息不存在.
	 */
	public static final int AGENT_MERCHANT_RELATION_IS_NULL = 20020042;
	
	/***
	 * 用户操作员信息不存在
	 */
	public static final int USER_OPERATOR_IS_NULL = 20020043;
	
	/***
	 * 商户密钥为空
	 */
	public static final int MERCHANT_KEY_IS_NULL = 20020044;
	/***
	 * 生成平台公私钥错误
	 */
	public static final int GENERATE_PLATE_KEY_FAIL = 20020045;
	
	/**
	 * 商户公钥为空
	 */
	public static final int MERCHANT_PUBLICKEY_IS_NULL = 20020046;
	
	/**
	 * 商户签名类型有误
	 */
	public static final int MERCHANT_SIGNTYPE_ERR = 20020047;

	/***
	 * 用户编号为空
	 */
	public static final int USER_NO_IS_NULL = 20020048;

	public UserBizException() {
	}

	public UserBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public UserBizException(int code, String msg) {
		super(code, msg);
	}
}
