package wusc.edu.pay.webservice.merchant.exception;

import wusc.edu.pay.common.exceptions.BizException;

public class WebTradeBizException extends BizException {

	private static final long serialVersionUID = -114884746405439913L;

	/**
	 * 系统异常
	 */
	public static final WebTradeBizException SYSTEM_ERR = new WebTradeBizException(10080000, "系统异常");
	/**
	 * 用户信息不存在
	 */
	public static final WebTradeBizException MERCHANTAPI_USER_NOT_EXIT = new WebTradeBizException(10080001, "用户信息不存在");
	/**
	 * 验证签名失败
	 */
	public static final WebTradeBizException MERCHANTAPI_VERIFYHMAC_LOSE = new WebTradeBizException(10080002, "验证签名失败");
	/**
	 * 订单号不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_ORDER_IS_NULL = new WebTradeBizException(10080003, "订单号不正确");
	/**
	 * 金额不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_AMOUNT_IS_NULL = new WebTradeBizException(10080004, "金额不正确");
	/**
	 * 币种不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_CUR_IS_NULL = new WebTradeBizException(10080005, "币种不正确");
	/**
	 * 产品名称不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_PRODUCTNAME_IS_NULL = new WebTradeBizException(10080006, "产品名称不正确");
	/**
	 * 后台通知地址不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_NOTIFYURL_IS_NULL = new WebTradeBizException(10080007, "后台通知地址不正确");
	
	/**
	 * 登录名不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_LOGINNAME_IS_NULL = new WebTradeBizException(10080008, "登录名不正确");
	/**
	 * 登录密码不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_LOGINPWD_IS_NULL = new WebTradeBizException(10080009, "登录密码不正确");
	/**
	 * 真实名称不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_REALNAME_IS_NULL = new WebTradeBizException(10080010, "真实名称不正确");
	/**
	 * 电话号码不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_MOBILENO_IS_NULL = new WebTradeBizException(10080011, "电话号码不正确");
	/**
	 * 邮箱地址不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_EMAIL_IS_NULL = new WebTradeBizException(10080012, "邮箱地址不正确");

	/**
	 * 商户不存在
	 */
	public static final WebTradeBizException MERCHANT_IS_NOT_EXIST = new WebTradeBizException(10080013, "商户不存在");

	/**
	 * 参数格式错误
	 */
	public static final WebTradeBizException PARAM_FORMAT_ERROR = new WebTradeBizException(10080014, "参数格式错误");

	/**
	 * 父商户未激活
	 */
	public static final WebTradeBizException PARENT_MERCHANT_IS_NOT_EXIST = new WebTradeBizException(10080017, "商户未激活");

	/**
	 * 广发接口统一异常
	 */
	public static final WebTradeBizException GF_PARAMS_EXCEPTION = new WebTradeBizException(10080018, "传递参数有误：");

	/**
	 * 广发接口异常--没有鉴权
	 */
	public static final WebTradeBizException USER_NOT_SIGN_AGREEMENT = new WebTradeBizException(10080019, "该银行卡没有签协议");

	/**
	 * 广发接口异常--会员银行卡信息不存在
	 */
	public static final WebTradeBizException USER_BANKACCOUNT_NOT_EXIT = new WebTradeBizException(10080020, "会员银行卡信息不存在");

	/**
	 * 广发接口异常--会员银行卡信息不存在
	 */
	public static final WebTradeBizException BANK_ACCOUNT_PARAM_IS_ERROR = new WebTradeBizException(10080021, "会员银行卡信息错误");

	/**
	 * 下载对账文件时间段参数错误
	 */
	public static final WebTradeBizException LOADRECONDATA_START_END_IS_ERROR = new WebTradeBizException(10080022, "下载对账文件时间段参数错误");
	
	/**
	 * 分账明细不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_SPLITDETAIL_IS_NULL = new WebTradeBizException(100800023, "分账明细不正确");
	
	/**
	 * 日期格式不正确
	 */
	public static final WebTradeBizException DATE_FORMAT_ERROR = new WebTradeBizException(100800024, "日期格式不正确");
	
	/**
	 * 支付渠道编码不正确
	 */
	public static final WebTradeBizException FRP_CODE_IS_NULL = new WebTradeBizException(100800025, "支付渠道编码不正确");
	
	/**
	 * 支付方式不正确
	 */
	public static final WebTradeBizException PAY_IS_ERROR = new WebTradeBizException(100800026, "支付方式不正确");
	/**
	 * 验证码不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_VERIFICATIONCODE_IS_NULL = new WebTradeBizException(100800027, "验证码不正确");
	/**
	 * 原密码不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_OLDPWD_IS_NULL = new WebTradeBizException(100800028, "原密码不正确");
	/**
	 * 新密码不能和原密码一致
	 */
	public static final WebTradeBizException NEW_OLD_PWD_IS_ERROR = new WebTradeBizException(100800029, "新密码不能和原密码一致");
	/**
	 * 密码应为8-20位数字,字母和特殊符号组合
	 */
	public static final WebTradeBizException PWD_IS_ERROR = new WebTradeBizException(100800030, "密码应为8-20位数字,字母和特殊符号组合");
	/**
	 * 登录密码不能与支付密码一致
	 */
	public static final WebTradeBizException LOGINPWD_PAYPWD_IS_ERROR = new WebTradeBizException(100800031, "登录密码不能与支付密码一致");
	
	/**
	 * 验证码过期
	 */
	public static final WebTradeBizException MERCHANTAPI_VERIFICATIONCODE_IS_EXPIRED = new WebTradeBizException(100800032, "验证码过期");
	
	/**
	 * 银行卡号不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_BANKCARDNO_IS_NULL = new WebTradeBizException(10080033, "银行卡号不正确");
	
	/**
	 * 通道方平台订单日期不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_ACCDATE_IS_NULL = new WebTradeBizException(10080034, "通道方平台订单日期不正确");
	
	/**
	 * 通道方平台订单号不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_ACCNO_IS_NULL = new WebTradeBizException(10080035, "通道方平台订单号不正确");
	
	/**
	 * 通道方平台交易标识不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_ACCSESSIONID_IS_NULL = new WebTradeBizException(10080036, "通道方平台交易标识不正确");
	
	/**
	 * 短信验证码不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_SMSCODE_IS_NULL = new WebTradeBizException(10080037, "短信验证码不正确");
	
	/**
	 * 信用卡有效期不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_CARDEXPIRE_IS_NULL = new WebTradeBizException(10080038, "信用卡有效期不正确");
	
	/**
	 * 信用卡CVN2码不正确
	 */
	public static final WebTradeBizException MERCHANTAPI_CARDCVN2_IS_NULL = new WebTradeBizException(10080039, "信用卡CVN2码不正确");
	
	
	
	
	
	
	
	
	public WebTradeBizException() {
	}

	public WebTradeBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	String msg = "";

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public WebTradeBizException(int code, String msg) {
		super(code, msg);
		this.msg = msg;
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public WebTradeBizException newInstance(String msgFormat, Object... args) {
		return new WebTradeBizException(this.code, msgFormat, args);
	}
}
