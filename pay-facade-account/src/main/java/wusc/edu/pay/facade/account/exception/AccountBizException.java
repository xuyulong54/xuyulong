package wusc.edu.pay.facade.account.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.exceptions.BizException;


/**
 * 账户服务业务异常类,异常代码8位数字组成,前4位固定2008打头,后4位自定义
 * 
 * @author healy
 * 
 */
public class AccountBizException extends BizException {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(AccountBizException.class);

	public static final AccountBizException ACCOUNT_NOT_EXIT = new AccountBizException(20080001, "账户不存在");

	public static final AccountBizException ACCOUNTHISTORY_NOT_EXIT = new AccountBizException(20080002, "账户历史记录不存在");

	public static final AccountBizException ACCOUNTBANK_NOT_EXIT = new AccountBizException(20080003, "结算银行账户不存在");

	public static final AccountBizException ACCOUNTOUTADDUP_NOT_EXIT = new AccountBizException(20080004, "账户出款统计记录不存在");

	public static final AccountBizException ACCOUNTOUTLIMIT_NOT_EXIT = new AccountBizException(20080005, "账户出款限制记录不存在");

	public static final AccountBizException ACCOUNTPWD_NOT_EXIT = new AccountBizException(20080006, "账户密码不存在");

	public static final AccountBizException ACCOUNTSECURITY_NOT_EXIT = new AccountBizException(20080007, " 账户安全不存在");

	public static final AccountBizException ACCOUNT_AVAILABLEBALANCE_IS_NOT_ENOUGH = new AccountBizException(20080100, "可用余额不足");

	public static final AccountBizException ACCOUNT_STATUS_IS_INACTIVE = new AccountBizException(20080101, "账户状态不可用");

	public static final AccountBizException ACCOUNT_MERCHANT_CANNOT_TRANSFER_TO_MEMBER = new AccountBizException(20080102, "商户不能向会员转账");

	public static final AccountBizException ACCOUNT_AGENT_CANNOT_TRANSFER_TO_MEMBER = new AccountBizException(20080103, "代理商不能向会员转账");

	public static final AccountBizException ACCOUNT_TRADEPWD_ERROR = new AccountBizException(20080104, "交易密码错误");

	public static final AccountBizException ACCOUNT_CANNOT_TRANSFER_TO_MYSELF = new AccountBizException(20080105, "不能给自己转账");

	public static final AccountBizException ACCOUNT_CANNOT_TRADE_TO_SELF = new AccountBizException(20080106, "不能给自己交易");

	public static final AccountBizException ACCOUNT_AMOUNT_ERROR = new AccountBizException(20080107, "金额错误");

	public static final AccountBizException ACCOUNT_CREATE_FAILED = new AccountBizException(20080108, "创建账户失败");

	public static final AccountBizException ACCOUNT_OUTADDUP_FAILED = new AccountBizException(20080109, "账户出款统计失败");

	public static final AccountBizException ACCOUNT_SETTLE_AMOUNT_LARGER = new AccountBizException(20080110, "结算金额大于可结算金额");

	public static final AccountBizException ACCOUNT_AMOUNT_LESS_THAN_SETTLE_AMOUNT = new AccountBizException(20080111,
			"结算账户余额 小于不可用余额，不能结算");

	public static final AccountBizException ACCOUNT_POS_TRXTYPE_INVALID = new AccountBizException(20080112, "POS交易类型无效");

	public static final AccountBizException ACCOUNT_CUSTOMER_WITHDRAWAMOUNT_BIGGER_BALANCE = new AccountBizException(20080113,
			"会员提现金额大于可用金额");

	public static final AccountBizException REMITTANCERECORD_NOT_EXIT = new AccountBizException(20080114, " 结算打款不存在");

	public static final AccountBizException PEPEAT_REMITTANCERECORD_ERROR = new AccountBizException(20080115, "重复结算打款");

	public static final AccountBizException ACCOUNTADJUSTLOG_NOT_EXIT = new AccountBizException(20080116, "调账记录不存在");

	public static final AccountBizException ACCOUNT_SETTMENTINFO_NOT_EXIT = new AccountBizException(20080117, "自动结算中商户的结算信息不存在");

	public static final AccountBizException MERCHANT_IS_NOT_EXIT = new AccountBizException(20080118, "自动结算中商户不存在");

	public static final AccountBizException ACCOUNT_TRADEPWD_ERROR_TIMES_OUTLIMIT = new AccountBizException(20080119, "交易密码错误次数超限");

	public static final AccountBizException ACCOUNT_BUILDACCOUNTNO_ERROR = new AccountBizException(20080120, "生成账户编号错误");

	public static final AccountBizException ACCOUNT_ACCOUNTOUTADDUP_IS_NULL = new AccountBizException(20080121, "账户账出款统计记录不存在 ");

	public static final AccountBizException ACCOUNTHISTORY_IS_EXIST = new AccountBizException(20080122, "账户历史记录已存在");

	public static final AccountBizException ACCOUNT_UN_FROZEN_AMOUNT_OUTLIMIT = new AccountBizException(20080123, "解冻金额超限");

	public static final AccountBizException BATCH_CREDIT_FOR_SAME_ACCOUNT_ERROR = new AccountBizException(20080124, "同一账户批量加款错误");

	public AccountBizException() {
	}

	public AccountBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public AccountBizException(int code, String msg) {
		super(code, msg);
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public AccountBizException newInstance(String msgFormat, Object... args) {
		return new AccountBizException(this.code, msgFormat, args);
	}

	public AccountBizException print() {
		log.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return new AccountBizException(this.code, this.msg);
	}
}
