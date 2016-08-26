package wusc.edu.pay.facade.trade.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.exceptions.BizException;


/**
 * 交易服务业务异常类,异常代码8位数字组成,前4位固定2007打头,后4位自定义
 * 
 * @author Administrator
 * 
 */
public class TradeBizException extends BizException {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(TradeBizException.class);

	/**
	 * 支付订单不存在
	 */
	public final static int ORDER_PAYMENT_ORDER_NOT_EXIST = 20070001;

	/**
	 * 银行订单不存在
	 */
	public final static int ORDER_BANK_ORDER_NOT_EXIST = 20070002;

	/**
	 * 交易统计记录不存在
	 */
	public final static int ORDER_TRADEADDUP_RECORD_NOT_EXIST = 20070007;

	/**
	 * 转账记录不存在
	 */
	public final static int ORDER_TRANSFER_NOT_EXIST = 20070008;

	/**
	 * 支付记录已处理
	 */
	public final static int ORDER_PAYMENTRECORD_IS_COMPLETED = 20070009;

	public final static TradeBizException REMOTE_AMOUNT_NOT_EQ_LOCAL_AMOUNT = new TradeBizException(20070010, "远程返回金额与订单金额不符.");

	/**
	 * 生成商户通知url错误
	 */
	public final static int ORDER_BUILD_NOTIFYURL_ERROR = 20070011;

	/**
	 * 交易统计失败
	 */
	public final static int ORDER_TRADEADDUP_FAILED = 20070012;

	/**
	 * 生成银行订单号失败
	 */
	public final static int ORDER_BUILD_BANKORDERNO_FAILED = 20070013;

	/**
	 * 生成交易流水号失败
	 */
	public final static int ORDER_BUILD_TRXNO_FAILED = 20070014;

	/**
	 * 充值订单不存在
	 */
	public final static int ORDER_RECHARGE_ORDER_NOT_EXIST = 20070015;

	/**
	 * 商户余额不足
	 */
	public final static int ORDER_MERCHANT_BALANCE_NOT_ENOUGH = 20070016;

	/**
	 * 商户B2C订单已存在
	 */
	public final static int ORDER_B2C_ORDER_IS_EXISTED = 20070020;

	/**
	 * 订单已处理完成
	 */
	public final static TradeBizException PAYMENT_ORDER_IS_COMPLETED = new TradeBizException(20070021, "订单已处理完成");

	/**
	 * 订单已取消
	 */
	public final static TradeBizException PAYMENT_ORDER_IS_CANCELED = new TradeBizException(20070022, "订单已取消");

	/**
	 * 快捷支付订单不存在
	 */
	public final static int ORDER_FASTORDER_NOT_EXIST = 20070023;

	/**
	 * 商户B2B订单不存在
	 */
	public final static int ORDER_B2B_ORDER_NOT_EXIST = 20070024;

	public final static TradeBizException TRANSFER_USERINFO_NOT_EXIST = new TradeBizException(20070025, "转账,用户不存在.");

	/**
	 * 订单金额超过单笔最大金额
	 */
	public static final int ORDER_AMOUNT_OUT_SINGLE_MAXLIMIT = 20070028;

	/**
	 * 订单金额低于单笔最小金额
	 */
	public static final int ORDER_AMOUNT_LESS_SINGLE_MINLIMIT = 20070029;

	/**
	 * 订单金额超出日交易限额
	 */
	public static final int ORDER_AMOUNT_OUT_DAY_MAXLIMIT = 20070030;

	/**
	 * 订单金额超出月交易限额
	 */
	public static final int ORDER_AMOUNT_OUT_MONTH_MAXLIMIT = 20070031;

	public final static TradeBizException PAYMENT_ORDER_NOT_EXIST = new TradeBizException(20070032, "支付订单不存在");

	public final static TradeBizException PAYMENT_ORDER_IS_EXIST = new TradeBizException(20070033, "支付订单已存在");

	public final static TradeBizException PAYMENT_RECORD_NOT_EXIST = new TradeBizException(20070034, "支付记录不存在");

	public final static TradeBizException PAYMENT_RECORD_IS_EXIST = new TradeBizException(20070035, "支付记录已存在");

	public final static TradeBizException PAYMENT_ORDER_IS_EXCEPTION = new TradeBizException(20070036, "订单信息异常");

	/**
	 * 重复支付,已退款
	 */
	public final static TradeBizException REPEAT_PAYMENT_IS_REFUND = new TradeBizException(20070037, "重复支付,已退款");

	public final static TradeBizException SUCCESS_PAYMENT_RECORD_NOT_EXIST = new TradeBizException(20070038, "支付成功记录不存在");

	public static final TradeBizException ACCOUNT_CANNOT_TRANSFER_TO_MYSELF = new TradeBizException(20070039, "不能给自己转账");

	/**
	 * 订单撤消,已退款
	 */
	public final static TradeBizException PAYMENT_ORDER_CANCELED_IS_REFUND = new TradeBizException(20070040, "订单撤消,已退款");

	/**
	 * 订单过期,已退款
	 */
	public final static TradeBizException PAYMENT_ORDER_EXPIRED_IS_REFUND = new TradeBizException(20070041, "订单过期,已退款");

	/**
	 * 订单已过期
	 */
	public final static TradeBizException PAYMENT_ORDER_IS_EXPIRED = new TradeBizException(20070042, "订单已过期");

	/**
	 * 订单金额格式错误
	 */
	public final static TradeBizException PARAM_ORDER_AMOUNT_ERROR = new TradeBizException(20070043, "订单金额格式错误");

	/**
	 * 商品名称格式错误
	 */
	public final static TradeBizException PARAM_PRODUCT_NAME_ERROR = new TradeBizException(20070044, "商品名称格式错误");

	/**
	 * 返回url格式错误
	 */
	public final static TradeBizException PARAM_RETURN_URL_ERROR = new TradeBizException(20070045, "返回url格式错误");

	/**
	 * 通知url格式错误
	 */
	public final static TradeBizException PARAM_NOTIFY_URL_ERROR = new TradeBizException(20070046, "通知url格式错误");

	/**
	 * 商户订单号格式错误
	 */
	public final static TradeBizException PARAM_ORDER_NO_ERROR = new TradeBizException(20070047, "商户订单号格式错误");

	/**
	 * 付款人不存在
	 */
	public final static TradeBizException PAYER_USER_INFO_NOT_EXIST = new TradeBizException(20070048, "付款人不存在");

	/**
	 * 分账明细解析错误
	 */
	public final static TradeBizException SPLIT_DETAIL_FORMAT_ERROR = new TradeBizException(20070049, "分账明细解析错误");

	/**
	 * 商户不存在
	 */
	public final static TradeBizException MERCHANT_USER_INFO_NOT_EXIST = new TradeBizException(20070050, "商户不存在");

	/**
	 * 可分账金额不足
	 */
	public final static TradeBizException AVAILABLE_SPLIT_AMOUNT_NOT_ENOUGH = new TradeBizException(20070051, "可分账金额不足");

	/**
	 * 用户未建立关联关系
	 */
	public final static TradeBizException NOT_USER_RELATION = new TradeBizException(20070052, "用户未建立关联关系");

	/**
	 * 分账记录不存在
	 */
	public final static TradeBizException SPLIT_RECORD_NOT_EXIST = new TradeBizException(20070053, "分账记录不存在");

	/**
	 * 未找到支付成功订单
	 */
	public final static TradeBizException SUCCESS_PAYMENT_ORDER_NOT_EXIST = new TradeBizException(20070054, "未找到支付成功订单");

	/**
	 * 子商户可分账金额不足
	 */
	public final static TradeBizException SUB_AVAILABLE_SPLIT_AMOUNT_NOT_ENOUGH = new TradeBizException(20070055, "子商户可分账金额不足");

	/**
	 * 分账订单不存在
	 */
	public final static TradeBizException SPLIT_PAYMENT_ORDER_NOT_EXIST = new TradeBizException(20070056, "分账订单不存在");

	/**
	 * 分账记录存在,无法全部退款
	 */
	public final static TradeBizException SPLIT_RECORD_IS_EXIST_NOT_ALL_REFUND = new TradeBizException(20070057, "分账记录存在,无法全部退款");

	/**
	 * 退款金额与订单金额不符
	 */
	public final static TradeBizException REFUND_AMOUNT_NOT_EQ_ORDER_AMOUNT = new TradeBizException(20070058, "退款金额与订单金额不符");

	/**
	 * 订单已过期
	 */
	public final static TradeBizException EXPIRED_PARAM_IS_ERROR = new TradeBizException(20070059, "订单过期时间参数错误");

	/**
	 * 代理商分润订单不存在
	 */
	public final static TradeBizException AGENT_FEE_ORDER_NOT_EXIST = new TradeBizException(20070060, "代理商分润订单不存在");

	/**
	 * 商户-代理商对应关系不存在
	 */
	public final static TradeBizException AGENT_MERCHANT_RELATION_NOT_EXIST = new TradeBizException(20070061, "商户-代理商对应关系不存在");

	/**
	 * 商户结算规则不存在
	 */
	public final static TradeBizException MERCHANT_SETT_RULE_NOT_EXIST = new TradeBizException(20070062, "商户结算规则不存在");

	/**
	 * 支付记录已经发生退款
	 */
	public final static TradeBizException PAYMENT_RECORD_IS_REFUND = new TradeBizException(20070063, "支付记录已经发生退款");

	/**
	 * 结算账户不存在
	 */
	public final static TradeBizException BANK_ACCOUNT_IS_NOT_EXIST = new TradeBizException(20070064, "结算账户不存在");

	/**
	 * 结算风险预存期非T+0
	 */
	public final static TradeBizException RISK_DAY_IS_NOT_T0 = new TradeBizException(20070065, "结算风险预存期非T+0");

	/**
	 * 分账记录已存在
	 */
	public final static TradeBizException SPLIT_RECORD_IS_EXIST = new TradeBizException(20070066, "分账记录已存在");

	/**
	 * 账户历史不存在
	 */
	public final static TradeBizException ACCOUNT_HISTORY_NOT_EXIST = new TradeBizException(20070067, "账户历史不存在");

	/**
	 * 账户历史不能结算
	 */
	public final static TradeBizException ACCOUNT_HISTORY_DO_NOT_SETT = new TradeBizException(20070068, "账户历史不能结算");

	public TradeBizException() {
	}

	public TradeBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public TradeBizException(int code, String msg) {
		super(code, msg);
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public TradeBizException newInstance(String msgFormat, Object... args) {
		return new TradeBizException(this.code, msgFormat, args);
	}

	public TradeBizException print() {
		log.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return new TradeBizException(this.code, this.msg);
	}

}
