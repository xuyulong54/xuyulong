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
public class AmountOverLimitException extends BizException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5176246979962761017L;

	/**
	 * 交易金额小于单笔最小金额
	 */
	public static final int SINGLE_LESS_THAN_MIN = 70030001;

	/**
	 * 交易金额大于单笔最大金额
	 */
	public static final int SINGLE_LARGER_THAN_MAX = 70030002;
	
	/**
	 * 日累计超限
	 */
	public static final int CUMULATE_DAILY_LARGER_THAN_MAX = 70030003;

	/**
	 * 月累计超限
	 */
	public static final int CUMULATE_PER_MONTH_LARGER_THAN_MAX = 70030004;



	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public AmountOverLimitException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public AmountOverLimitException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

}