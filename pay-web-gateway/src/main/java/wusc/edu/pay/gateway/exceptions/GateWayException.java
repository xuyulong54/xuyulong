package wusc.edu.pay.gateway.exceptions;

import wusc.edu.pay.common.exceptions.BizException;

/**
 * ClassName: GateWayException <br/>
 * Function: 网关异常类 <br/>
 * date: 2013-11-19 下午9:25:36 <br/>
 * 
 * @author laich
 */

public class GateWayException extends BizException {

	private static final long serialVersionUID = -114884746405439913L;

	/**
	 * 银行渠道不存在
	 */
	public static final int GATEWAY_BANKCHANNAL_NOT_EXIST = 10090001;

	/**
	 * 调用银行接口失败
	 */
	public static final int GATEWAY_INVOKE_BANKCHANNAL_IS_FAILED = 10090002;

	/**
	 * 业务设置为空
	 */
	public static final int GATEWAY_BUSINESS_SETTING_NOT_EXIST = 10090003;

	/**
	 * 商户阶梯费率为空
	 */
	public static final int GATEWAY_MERCHANT_STEPRATE_NOT_EXIST = 10090004;

	/**
	 * 商户费率为空
	 */
	public static final int GATEWAY_MERCHANT_RATE_NOT_EXIST = 10090005;

	/**
	 * 代理商费率为空
	 */
	public static final int GATEWAY_AGENT_RATE_NOT_EXIST = 10090006;

	/**
	 * 代理商费率比商户费率大
	 */
	public static final int GATEWAY_AGENTRATE_GREAT_MERCHANTRATE = 10090007;

	/**
	 * 订单已经支付成功,不能重复支付
	 */
	public static final int GATEWAY_ORDER_ALREADY_PAY_SUCCESS = 10090008;

	/**
	 * 商户订单不存在
	 */
	public static final int GATEWAY_MERCHANT_ORDER_NOT_EXIST = 10090009;

	/**
	 * 提交订单数据不符合要求
	 */
	public static final int GATEWAY_ORDER_DATE_IS_ERROR = 10090010;

	/**
	 * 银行渠道编号为空
	 */
	public static final int GATEWAY_BANK_CHANNAL_CODE_NOT_EXIST = 10090011;

	/**
	 * 商户不存在
	 */
	public static final int GATEWAY_MERCHANT_NOT_EXIST = 10090012;

	/**
	 * 代理商不存在
	 */
	public static final int GATEWAY_AGENT_NOT_EXIST = 10090013;
	/**
	 * 收款渠道为空
	 */
	public static final int GATEWAY_FRPCODELIMIT_NOT_EXIST = 10090014;

	/**
	 * 商户可支付银行为空
	 */
	public static final int GATEWAY_MERCHANT_CAN_USE_BANK_NOT_EXIST = 10090015;

	/**
	 * 登录名不存在
	 */
	public static final int GATEWAY_LOGINNAME_NOT_EXIST = 10090016;

	/**
	 * 账户不存在
	 */
	public static final int GATEWAY_ACCOUNT_NOT_EXIST = 10090017;

	/**
	 * 商户未激活.
	 */
	public static final int GATEWAY_MERCHANT_STATUS_IS_INACTIVE = 10090018;

	/**
	 * 验证签名失败
	 */
	public static final int GATEWAY_VALIDATE_HMAC_IS_FAILED = 10090019;

	/**
	 * 订单金额超过单笔最大金额
	 */
	public static final int GATEWAY_AMOUNT_OUT_SINGLE_MAXLIMIT = 10090020;

	/**
	 * 订单金额低于单笔最小金额
	 */
	public static final int GATEWAY_AMOUNT_LESS_SINGLE_MINLIMIT = 10090021;

	/**
	 * 订单金额超出日交易限额
	 */
	public static final int GATEWAY_AMOUNT_OUT_DAY_MAXLIMIT = 10090022;

	/**
	 * 订单金额超出月交易限额
	 */
	public static final int GATEWAY_AMOUNT_OUT_MONTH_MAXLIMIT = 10090023;

	/**
	 * 订单金额小于0
	 */
	public static final int GATEWAY_ORDERAMOUNT_LESS_ZERO = 10090024;

	/**
	 * 商户编号为空
	 */
	public static final int GATEWAY_MERCHANTNO_NOT_EXIST = 10090025;

	/**
	 * 银行费率为空
	 */
	public static final int GATEWAY_BANK_RATE_NOT_EXIST = 10090026;

	/**
	 * 余额支付，会员登录信息被篡改
	 */
	public static final int GATEWAY_USER_INFO_VALIDATE_FAILED = 10090027;
	/**
	 * 登录用户不存在，登录之后传递的信息被篡改
	 */
	public static final int GATEWAY_USER_IS_NOT_EXIST = 10090028;
	/**
	 * 商户没有收款功能，消费者不能去该商户购买商品
	 */
	public static final int GATEWAY_MERCHANT_NOT_COLLECTION_FUNCTION = 10090029;
	/**
	 * 订单已经过期
	 */
	public static final int GATEWAY_ORDER_OUT_DATE = 10090030;

	/**
	 * 充值，登录信息被篡改
	 */
	public static final int GATEWAY_RECHARGE_USER_INFO_VALIDATE_FAILED = 10090031;

	/**
	 * 订单金额格式错误
	 */
	public static final int GATEWAY_ORDERAMOUNT_FORMATTING_IS_ERROR = 10090032;

	/**
	 * 商户名称为空
	 */
	public static final int GATEWAY_PRODUCTNAME_IS_EMPTY = 10090033;

	/**
	 * 请求参数格式错误
	 */
	public static final int GATEWAY_REQUEST_PARAM_FORMATTING_IS_ERROR = 10090034;

	/**
	 * 业务功能未开通
	 */
	public static final int GATEWAY_BIZ_SWITCH_IS_CLOSED = 10090035;

	/**
	 * 商户未设置交易限额
	 */
	public static final int GATEWAY_MERCHANT_TRADELIMIT_IS_NULL = 10090036;
	/**
	 * 调用风控，返回拒绝交易
	 */
	public static final int GATEWAY_RCS_REFUND_TRADE = 10090037;

	/**
	 * 用户不存在
	 */
	public static final int GATEWAY_USER_NOT_EXIST = 10090038;
	/**
	 * 订单号为空
	 */
	public static final int GATEWAY_ORDERNO_IS_NULL = 10090039;

	/**
	 * 商户无效
	 */
	public static final int GATEWAY_MERCHANT_IS_INVALID = 10090040;
	
	/**
	 * 银行渠道无效
	 */
	public static final int GATEWAY_BANKCHANNAL_IS_INVALID = 10090041;
	

	/**
	 * 银行支付接口主备都已受限
	 */
	public static final int GATEWAY_INTERFACE_IS_LIMIT = 10090042;
	
	/**
	 * 银行支付接口主已受限，备接口不 存在
	 */
	public static final int GATEWAY_SPARE_INTERFACE_IS_NOT_EXIT = 10090043;
	/**
	 * 直连接口渠道编号不正确
	 */
	public static final int DIRECT_CONNECT_PAYWAYCODE_IS_ERROR = 10090044;
	

	public GateWayException() {
	}

	public GateWayException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public GateWayException(int code, String msg) {
		super(code, msg);
	}

	public GateWayException(int code) {
		super(code, "message");
	}

}
