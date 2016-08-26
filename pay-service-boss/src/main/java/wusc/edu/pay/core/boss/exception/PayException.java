package wusc.edu.pay.core.boss.exception;

public class PayException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 成功.<br/>
	 */
	public static String SUCCESS = "1000";
	/**
	 * 业务类型有误.<br/>
	 */
	public static String CMD_E = "1001";
	/**
	 * 商户不存在.<br/>
	 * MERCHANT_IS_NULL
	 */
	public static String M_IS_N = "1002";

	
	//在线支付
	/**
	 * 商户提交的数据 验证不通过.<br/>
	 * B2C_MERCHANTDATE_IS_ERROR
	 */
	public static String B2C_M_I_E = "3001";
	/**
	 * 已经支付成功,不能重复支付.<br/>
	 * B2C_ORDER_HAS_SUCCESS
	 */
	public static String B2C_O_H_S = "3002";
	/**
	 * 订单的金额大于单笔最大金额.<br/>
	 * B2C_AMOUNT_IS_OUTMAXLIMIT
	 */
	public static String B2C_A_I_O = "3003";
	/**
	 * 订单的金额小于当笔最小金额.<br/>
	 * B2C_AMOUNT_IS_LESSMINLIMIT
	 */
	public static String B2C_A_I_L = "3004";
	/**
	 * 订单的金额小于当笔最小金额.<br/>
	 * B2C_MERCHANTDATE_IS_ILLEGAL
	 */
	public static String B2C_M_I_I = "3005";
	/**
	 * 超出了日交易额的限制.<br/>
	 * B2C_DAYLIMIT_IS_OUTLIMIT
	 */
	public static String B2C_D_I_O = "3006";
	/**
	 * 超出了月交易额的限制.<br/>
	 * B2C_MONTHLIMIT_IS_OUTLIMIT
	 */
	public static String B2C_M_I_O = "3007";
	/**
	 * 订单金额小于等于0.<br/>
	 * B2C_ORDERAMOUNT_IS_LESSZERO
	 */
	public static String B2C_O_I_L = "3008";
	

	
	//订单查询
	/**
	 * 支付订单不存在.<br/>
	 * ORDER_PAYMENTRECORD_IS_NULL
	 */
	public static String ORD_P_I_N = "4001";
	
	
	
	//退款 
	/**
	 * 支付订单不存在.<br/>
	 * REFUND_PAYMENTRECORD_IS_NULL
	 */
	public static String REF_P_I_N = "2001";
	/**
	 * 存在未处理完的退款订单.<br/>
	 * REFUNDORDER_STATUS_IS_CREATED
	 */
	public static String REF_S_I_C = "2002";
	/**
	 * 重复退款.<br/>
	 * REFUNDORDER_HAS_BEEN_COMPLETED
	 */
	public static String REF_O_H_B_C = "2003";
	/**
	 * 退款金额超限.<br/>
	 * REFUNDAMOUNT_IS_LIMITED
	 */
	public static String REF_I_L = "2004";
	/**
	 * 银行订单不存在.<br/>
	 * REFUNDBANKORDER_IS_NULL
	 */
	public static String REF_I_N = "2005";
	/**
	 * 交易订单金额不一致.<br/>
	 * MERCHANT_AMOUNT_IS_NOT_EQUAL_TO
	 */
	public static String REF_M_A_N_E = "2006";
	/**
	 * 银行订单金额不一致.<br/>
	 * BANK_AMOUNT_IS_NOT_EQUAL_TO
	 */
	public static String REF_B_A_N_E = "2007";
	/**
	 * 银行存在未处理完的退款订单.<br/>
	 * REFUNDBANKORDER_HAS_BEEN_COMPLETED
	 */
	public static String REF_B_H_B_C = "2008";
	/**
	 * 退款交易记录不存在.<br/>
	 * TRADEREFUNDRECORD_IS_NULL
	 */
	public static String REF_T_I_N = "2009";
	/**
	 * 退款交易记录存在未处理完的退款订单.<br/>
	 * TRADEREFUNDRECORD_HAS_BEEN_COMPLETED
	 */
	public static String REF_T_H_B_C = "2010";
	/**
	 * 商户b2c退款订单记录不存在.<br/>
	 * MERCHANTB2CREFUNDORDER_IS_NULL
	 */
	public static String REF_M_I_N = "2011";
	/**
	 * 商户b2c退款订单存在未处理完的退款订单.<br/>
	 * MERCHANTB2CREFUNDORDER_HAS_BEEN_COMPLETED .
	 */
	public static String REF_M_H_B_C = "2012";
	/**
	 * 银行业务设置不存在.<br/>
	 * BANKBUSISETTING_IS_NULL
	 */
	public static String REF_B_I_N = "2013";
	/**
	 * 银行不支持部分退款.<br/>
	 * ISPARTREFUND_NOT_SUPPORT
	 */
	public static String REF_B_I_N_S = "2014";
	
	
	public String errMsg;

	public PayException() {
		super();
	}

	public PayException(String errMsg) {
		super();
		this.errMsg = errMsg;
	}
}
