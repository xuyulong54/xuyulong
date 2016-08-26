package wusc.edu.pay.facade.payrule.exceptions;

import wusc.edu.pay.common.exceptions.BizException;

/**
 * ClassName: RuleBizException <br/>
 * Function:  支付规则 异常<br/>
 * date: 2014-7-15 上午10:20:14 <br/>
 * @author laich
 */
public class PayruleBizException extends BizException {
	private static final long serialVersionUID = -7037812471969887916L;

	/**
	 * 支付规则不存在
	 */
	public static final int RULE_RULE_IS_NULL = 10150001;
	/**
	 * 支付产品编号已存在
	 */
	public static final int RULE_PRODUCTCODE_IS_EXIT = 1015002;

	/**
	 * 该支付方式编号已存在
	 */
	public static final int RULE_PAYWAYCODE_IS_EXIT = 1015003;
	
	/**
	 * 该支付方式已被使用，不能加入该产品
	 */
	public static final int RULE_PAYWAYCODE_PAYWAY_IS_EXIT = 1015004;
	
	/**
	 * 该支付方式不存在
	 */
	public static final int RULE_PAYWAYCODE_IS_NOT_EXIT = 1015005;
	
	
	public PayruleBizException() {
	}

	public PayruleBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public PayruleBizException(int code, String msg) {
		super(code, msg);
	}
}
