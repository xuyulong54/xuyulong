package wusc.edu.pay.web.portal.exceptions;

import wusc.edu.pay.common.exceptions.BizException;

/**
 * ClassName: PortalException <br/>
 * Function: 门户异常 <br/>
 * date: 2013-11-15 下午2:45:46 <br/>
 * 
 * @author laich
 */
public class PortalMerchantException extends BizException {

	private static final long serialVersionUID = 1L;

	/**
	 * 商户信息不存在
	 */
	public static final int MERCHANT_IS_NULL = 10020001;

	/**
	 * 业务类型不存在
	 */
	public static final int CMD_IS_NULL = 10020002;

	/**
	 * 接收返回的签名验证失败
	 */
	public static final int RECEIVE_SIGN_FAILURE = 10020003;

	/**
	 * 账户信息不存在
	 */
	public static final int ACCOUNT_IS_NULL = 10020004;

	/**
	 * 退款HTTP响应超时
	 */
	public static final int HTTP_REQUEST_ERROR = 10020005;

	/**
	 * 订单不存在
	 */
	public static final int ORDER_IS_NOT_EXIT = 10020006;

	/**
	 * 已全部退款完成
	 */
	public static final int REFUND_IS_COMPLETE = 10020007;

	/**
	 * 交易状态不成功
	 */
	public static final int TRXTYPE_NOT_SUCCESS = 10020008;

	/**
	 * 结算周期错误
	 */
	public static final int SETTINTERVAL_IS_ERROR = 10020009;

	/**
	 * 提现银行错误
	 */
	public static final int WITHDRAW_BANK_IS_ERROR = 10020010;

	/**
	 * 账户状态冻结
	 */
	public static final int ACCOUNT_STATUS_INACTIVE = 10020011;

	/**
	 * 参数错误
	 */
	public static final int PARAM_IS_ERROR = 10020012;

	/**
	 * 实名认证状态错误
	 */
	public static final int REALNAME_CHECK_STATUS_ERROR = 10020013;

	/**
	 * 业务未设置
	 */
	public static final int BUSISETTING_IS_NULL = 10020014;

	/**
	 * 上一笔退款未处理完成
	 */
	public static final int ORDER_PRE_REFUND_NOT_DEAL_COMPLETED = 10020015;

	/**
	 * 交易记录不存在
	 */
	public static final int ORDER_PAYMENTRECORD_NOT_EXIT = 10020016;
	
	
	public static final int SING_DATE_IS_NULL = 10020016;

	/**
	 * 请求退款接口错误
	 */
	public static final int REQUEST_TO_REFUNDAPI_ERROR = 10020017;

	/**
	 * 权限错误（越权操作）
	 */
	public static final int PMS_IS_ERROR = 10020018;
	
	/**
	 * 结算风控验证不通过
	 */
	public static final int SETTLEMENT_RCS_IS_NOPASS = 10020019;

	public PortalMerchantException() {
	}

	public PortalMerchantException(String message) {
		super(-1, message);
		printStackTrace();
	}

	public PortalMerchantException(Integer code, String message) {
		super(code, message);
		printStackTrace();
	}

}
