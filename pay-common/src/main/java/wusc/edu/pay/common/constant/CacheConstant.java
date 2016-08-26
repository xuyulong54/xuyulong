/**
 * wusc.edu.pay.common.constant.CacheConstant.java
 */
package wusc.edu.pay.common.constant;

/**
 * <ul>
 * <li>Title:缓存常量类</li>
 * <li>Description: 定义缓存KEY值</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-4-22
 */
public class CacheConstant {
	/**
	 * 银行费率 BANK_RATE_银行渠道编号
	 */
	public static final String BANK_RATE = "BANK_RATE_";
	/**
	 * 银行渠道 BANK_CHANNEL_银行渠道编号
	 */
	public static final String BANK_CHANNEL = "BANK_CHANNEL_";
	/**
	 * 商户信息 MERCHANT_INFO_商户编号
	 */
	public static final String MERCHANT_INFO = "MERCHANT_INFO_";
	/**
	 * 商户费率 MERCHANT_RATE_商户ID_支付渠道编号_交易类型
	 */
	public static final String MERCHANT_RATE = "MERCHANT_RATE_";
	/**
	 * 商户阶梯费率 MERCHANT_STEP_RATE_商户ID_交易类型
	 */
	public static final String MERCHANT_STEP_RATE = "MERCHANT_STEP_RATE_";
	/**
	 * 商户业务设置 MERCHANT_BUSISETTING_商户ID
	 */
	public static final String MERCHANT_BUSISETTING = "MERCHANT_BUSISETTING_";
	/**
	 * 商户交易限制 MERCHANT_TRADE_LIMIT_商户ID_交易类型
	 */
	public static final String MERCHANT_TRADE_LIMIT = "MERCHANT_TRADE_LIMIT_";
	/**
	 * 代理商 AGENT_RATE_代理商ID
	 */
	public static final String AGENT_RATE = "AGENT_RATE_";
	/**
	 * 银行分流通道BANK_BRANCH_支付渠道编号
	 */
	public static final String BANK_BRANCH = "BANK_BRANCH_";
	/**
	 * 银行完成支付成功 COMPLETE_PAYMENT_银行订单号
	 */
	public static final String COMPLETE_PAYMENT = "COMPLETE_PAYMENT_";
	
	/**
	 * 会计科目与账户关联关系ACCOUNTING_ACCOUNT_TITLE_RELATION_科目编号
	 */
	public static final String ACCOUNTING_ACCOUNT_TITLE_RELATION = "ACCOUNTING_ACCOUNT_TITLE_RELATION_";
	
	/**
	 * 快捷支付Session FAST_PAY_SESSION_sessionid
	 */
	public static final String FAST_PAY_SESSION = "FAST_PAY_SESSION_";

}
