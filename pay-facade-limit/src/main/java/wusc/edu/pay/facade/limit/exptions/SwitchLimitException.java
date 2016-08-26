/**
 * wusc.edu.pay.facade.limit.exptions.SwitchLimitException.java
 */
package wusc.edu.pay.facade.limit.exptions;

import wusc.edu.pay.common.exceptions.BizException;

/**
 * 
 * <ul>
 * <li>Title:限制开关业务异常</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
public class SwitchLimitException extends BizException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5176246979962761017L;

	/**
	 * 商户未开通此业务功能
	 */
	public static final int MERCHANT_NOT_OPEN_THIS_BIZ_FUNCTION = 70020001;

	/**
	 * 商户未开通任何业务功能
	 */
	public static final int MERCHANT_OPENED_BIZ_FUNCTION_IS_NULL = 70020002;

	/**
	 * 商户未开通任何支付产品
	 */
	public static final int MERCHANT_OPENED_PAY_PRODUCT_IS_NULL = 70020003;

	/**
	 * 商户未开通支付产品
	 */
	public static final int MERCHANT_NOT_OPEN_THIS_PAY_PRODUCT = 70020004;

	/**
	 * 商户未开通支付方式
	 */
	public static final int MERCHANT_NOT_OPEN_THIS_PAY_WAY = 70020005;

	/**
	 * 商户未开通任何支付
	 */
	public static final int MERCHANT_OPENED_PAY_WAY_IS_NULL = 70020006;

	/**
	 * 商户未设置交易限制
	 */
	public static final int TRADE_LIMIT_NOT_EXIST = 70020007;

	/**
	 * 商户未设置开关限制
	 */
	public static final int MERCHANT_SWITCH_LIMIT_NOT_EXIST = 70020008;
	
	/**
	 * 商户未开通支付接口
	 */
	public static final int MERCHANT_NOT_OPEN_THIS_PAY_INTERFACE = 70020009;

	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public SwitchLimitException(int code, String msgFormat, Object... args) {
		super(code,msgFormat);
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public SwitchLimitException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public SwitchLimitException newInstance(String msgFormat, Object... args) {
		return new SwitchLimitException(this.code, msgFormat, args);
	}
}