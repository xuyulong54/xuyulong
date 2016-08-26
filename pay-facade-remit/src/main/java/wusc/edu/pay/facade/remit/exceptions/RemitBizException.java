package wusc.edu.pay.facade.remit.exceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.exceptions.BizException;


/**
 * 
 * @Title: 打款服务异常类
 * @Description: 8001
 * @author zzh
 * @date 2014-7-22 下午2:42:18
 */
public class RemitBizException extends BizException {

	private static final long serialVersionUID = -6465740318269785531L;

	private static final Log log = LogFactory.getLog(RemitBizException.class);

	/**
	 * 打款未知异常
	 */
	public static final int REMIT_UNKNOWN_ERROR = 80010000;

	/**
	 * 商户结算请求参数为空
	 */
	public static final int REMIT_REQUESTPARAM_ISNULL = 80010001;

	/**
	 * 打款请求参数有误
	 */
	public static final int REMIT_REQUESTPARAM_ERR = 80010002;

	/**
	 * 无法查到需要重新制单的数据
	 */
	public static final int REMIT_NO_DATA_ERR = 80010003;

	/**
	 * 平台账户资金打款检查不过
	 */
	public static final RemitBizException REMIT_AMOUNT_NOT_ENOUGH = new RemitBizException(80010004, "平台账户资金打款检查不过");

	/**
	 * 打款前资金冻结异常则 终止 打款
	 */
	public static final RemitBizException REMIT_AMOUNT_BEFORE_ERROR = new RemitBizException(80010005, "打款前资金冻结异常则 终止 打款");

	/**
	 * 银行打款出异常
	 */
	public static final RemitBizException REMIT_BANK_ERROR = new RemitBizException(80010006, "银行打款出异常");

	/**
	 * 出款银行通道不存在
	 */
	public static final RemitBizException REMIT_BANK_CHANNEL_NOT_EXIST = new RemitBizException(80010007, "出款银行通道不存在");

	/**
	 * 出款银行开户信息不存在
	 */
	public static final RemitBizException REMIT_BANK_ACCOUNT_NOT_EXIST = new RemitBizException(80010008, "出款银行开户信息不存在");

	/**
	 * 出款请求记录不存在
	 */
	public static final RemitBizException REMIT_REQUEST_RECORD_NOT_EXIST = new RemitBizException(80010009, "出款请求记录不存在");

	/**
	 * 出款处理记录不存在
	 */
	public static final RemitBizException REMIT_PROCESS_RECORD_NOT_EXIST = new RemitBizException(80010010, "出款处理记录不存在");

	/**
	 * 出款批次不存在
	 */
	public static final RemitBizException REMIT_BATCH_RECORD_NOT_EXIST = new RemitBizException(80010011, "出款批次不存在");

	/**
	 * 出款批次已处理
	 */
	public static final RemitBizException REMIT_BATCH_IS_COMPLETED = new RemitBizException(80010012, "出款批次已处理");

	public RemitBizException() {
	}

	public RemitBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public RemitBizException(int code, String msg) {
		super(code, msg);
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public RemitBizException newInstance(String msgFormat, Object... args) {
		return new RemitBizException(this.code, msgFormat, args);
	}

	public RemitBizException print() {
		log.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return new RemitBizException(this.code, this.msg);
	}
}
