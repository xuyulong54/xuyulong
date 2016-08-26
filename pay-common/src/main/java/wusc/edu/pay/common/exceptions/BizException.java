package wusc.edu.pay.common.exceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 业务异常基类，所有业务异常都必须继承于此异常
 * 
 * @author healy
 * 
 *         定义异常时，需要先确定异常所属模块。例如：添加商户报错 可以定义为 [10020001] 前四位数为系统模块编号，后4位为错误代码 ,唯一 <br>
 *         商户门户异常 1002 <br>
 *         会员门户异常 1004 <br>
 *         boss门户异常 1005 <br>
 *         商户API 异常 1008 <br>
 *         支付网关异常 1009 <br>
 *         会计门户异常 1010 <br>
 *         通知应用异常 1011 <br>
 *         银行服务异常 1012 <br>
 *         银行后置异常 1013 <br>
 *         支付规则异常 1015 <br>
 *         用户服务异常 2002 <br>
 *         boss服务异常 2005 <br>
 *         结算服务异常 2006 <br>
 *         订单服务异常 2007 <br>
 *         账户服务异常 2008 <br>
 *         退款服务异常 2009 <br>
 *         会计服务异常 2010 <br>
 *         通知服务异常 2011 <br>
 *         商户接口异常2012 <br>
 *         证书异常 3001 <br>
 *         风控异常 4001 <br>
 *         计费异常 5001 <br>
 *         成本计费异常 6001 <br>
 *         限制开关异常 7001 <br>
 *         限制开关（业务）异常 7002 <br>
 *         限制开关（金额限制）异常 7003 <br>
 *         银行打款异常 8001 <br>
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -5875371379845226068L;

	/**
	 * 数据库操作,insert返回0
	 */
	public static final BizException DB_INSERT_RESULT_0 = new BizException(90040001, "数据库操作,insert返回0");

	/**
	 * 数据库操作,update返回0
	 */
	public static final BizException DB_UPDATE_RESULT_0 = new BizException(90040002, "数据库操作,update返回0");

	/**
	 * 数据库操作,selectOne返回null
	 */
	public static final BizException DB_SELECTONE_IS_NULL = new BizException(90040003, "数据库操作,selectOne返回null");

	/**
	 * 数据库操作,list返回null
	 */
	public static final BizException DB_LIST_IS_NULL = new BizException(90040004, "数据库操作,list返回null");

	/**
	 * Token 验证不通过
	 */
	public static final BizException TOKEN_IS_ILLICIT = new BizException(90040005, "Token 验证非法");
	/**
	 * 会话超时　获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
	 */
	public static final BizException SESSION_IS_OUT_TIME = new BizException(90040006, "会话超时");

	/**
	 * 获取序列出错
	 */
	public static final BizException DB_GET_SEQ_NEXT_VALUE_ERROR = new BizException(90040007, "获取序列出错");

	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public BizException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public BizException() {
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
	public BizException newInstance(String msgFormat, Object... args) {
		return new BizException(this.code, msgFormat, args);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message) {
		super(message);
	}
}
