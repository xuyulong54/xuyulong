package wusc.edu.pay.facade.limit.exceptions;

import wusc.edu.pay.common.exceptions.BizException;

/**
 * 限制开关biz异常类
 * 
 * @author zh
 * 
 */
public class LimitBizException extends BizException {

	private static final long serialVersionUID = 1L;

	/**
	 * 参数为空
	 */
	public static final int  PARAM_IS_NULL = 70010001;

	public LimitBizException() {
	}

	public LimitBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public LimitBizException(int code, String msg) {
		super(code, msg);
	}
}
