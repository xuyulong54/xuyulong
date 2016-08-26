package wusc.edu.pay.facade.bank.exceptions;

import wusc.edu.pay.common.exceptions.BizException;

public class BankBizException extends BizException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7779332048157371401L;

	/**
	 * 未知异常
	 */
	public static final int BANK_UNKNOWN_ERROR = 10120000;
	/**
	 * 参数错误
	 */
	public static final int BANK_SERVICE_PARAMS_ERROR = 10120001;

	/**
	 * 反射安全异常
	 */
	public static final int BANK_SECURITY_PARAMS_ERROR = 10120002;

	/**
	 * 生成银行支付请求异常
	 */
	public static final int BANK_BUILD_PAY_URL_ERROR = 10120003;
	/**
	 * 签名失败
	 * */
	public static final int BANK_SIGN_ERROR = 10120004;
	
	/**
	 * 系统配置异常
	 * */
	public static final int BANK_CONFIG_ERROR = 10120005;
	
	/**
	 * 银行请求通讯异常
	 * */
	public static final int BANK_REQUEST_ERR0R = 10120006;
	
	/**
	 * 银行返回通讯异常
	 * */
	public static final int BANK_RESPONSE_ERR0R = 10120007;
	
	/**
	 * 上传业务对账文件时异常
	 */
	public static final int BANK_UPLOADBUSFILETOSERVER_ERROR = 10120008;
	
	/**
	 * 上传清算对账文件时异常
	 */
	public static final int BANK_UPLOADACCOUNTFILETOSERVER_ERROR = 10120009;
	
	public static final int BANK_SYSTEM_ERROR = 10120010;
	
	public static final int BANK_ERROR_DATA = 10120011;
	
	public BankBizException(){}
	public BankBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public BankBizException(int code, String msg) {
		super(code, msg);
	}

}
