package wusc.edu.pay.facade.settlement.exception;

import wusc.edu.pay.common.exceptions.BizException;

/**
 * 结算异常
 * 
 * @author pc-Along
 * 
 */
public class SettBizException extends BizException {

	private static final long serialVersionUID = -7779332048157371401L;

	/** 未知异常 **/
	public static final int SETT_UNKNOWN_ERROR = 20060000;

	/** 不可结算日开始日期必须在截止日期之前 **/
	public static final int SETTLEMENT_HOLIDAY_ERROR_END = 20060001;

	/** 已设置为结算日调整记录，不可设置为不可结算日 **/
	public static final int SETTLEMENT_DAY_ADJUST_EXIST = 2006002;

	/** 结算日和结算日调整到日期不能为同一天 **/
	public static final int SETTLEMENT_DAY_ADJUST_DAY_SAME_ERROR = 2006003;

	/** 结算日调整到日期必须在当前日期之后 **/
	public static final int SETTLEMENT_DAY_ADJUST_DAY_LESS_CURRENTDAY = 2006004;

	/** 已经是节假日，不能为调整日期 **/
	public static final int SETTLEMENT_HOLIDAY_SETTING_EXIST = 2006005;

	/** 已进行了结算日调整 **/
	public static final int SETTLEMENT_DAY_ADJUST_SINGLE = 2006006;

	/** 一个账户只能有一个结算规则 **/
	public static final int SETTLEMENT_RULE_SINGLE_ACCOUNT = 2006007;

	/** 结算规则中风险预存期小于0异常 **/
	public static final int SETTLEMENT_RULE_RISK_DAYS_LESS_ZERO = 2006008;

	/** 设置结算规则结算周期类型不能为空 **/
	public static final int SETTLEMENT_RULE_CYCLE_TYPE_ILLEGAL = 2006009;

	/** 设置结算规则结算周期数据不能为空 **/
	public static final int SETTLEMENT_RULE_CYCLE_DATA_ILLEGAL = 2006010;

	/** 日汇总中，不能进行结算 **/
	public static final int SETTLEMENT_DAY_COLLECT = 2006011;

	/** 结算日已调整，不能进行结算 **/
	public static final int SETTLEMENT_DAY_ADJUST = 2006012;

	/** 节假日，不能进行结算 **/
	public static final int SETTLEMENT_DAY_HOLIDAY = 2006013;

	/** 打款请求号为空 **/
	public static final int REMITREQUESTNO_NULL = 2006014;

	/** 结算批次号为空 **/
	public static final int SETTBATCHNO_NULL = 2006015;

	/** 获取打款请求号异常 **/
	public static final int GET_REMITREQUESTNO_ERROR = 2006016;

	/** 结算账户的结算规则不存在 **/
	public static final int SETT_RULE_NOT_EXIST = 2006017;

	/** 可结算截止日期错误 **/
	public static final int SETTLE_OVER_DATE = 2006018;

	/** 结算规则为不可结算 **/
	public static final int SETT_RULE_NOTSETTLE = 2006019;

	/** 账户正在结算中 **/
	public static final int SETTLE_ACCOUNT_SETTLEING = 2006020;

	/** 结算银行信息不存在 **/
	public static final int SETT_BANK_ACCOUNT_NOT_EXIT = 2006021;

	/** 结算金额大于可用余额 **/
	public static final int SETTLE_AMOUNT_ACCOUNT_CHECKERR = 2006022;

	/** 结算记录状态不是待确认，不能进行结算处理 **/
	public static final int SETTLE_STATUS_ERROR = 2006023;

	/** 结算金额比0小不可以结算 **/
	public static final int SETTAMONT_LESS_THAN_ZERO = 2006024;

	/** 结算记录状态不是打款失败，不能重新发起结算 **/
	public static final int SETTLE_RESEND_ERROR = 2006025;

	/** 账户不存在 **/
	public static final int ACCOUNT_IS_NULL = 2006026;
	/** 账户不是激活状态 **/
	public static final int ACCOUNT_IS_NOT_ACTIVE = 2006028;
	/** 限制包金额被限制 **/
	public static final int SETT_IS_IN_LIMIT = 2006029;

	/**
	 * 账户历史记录不存在
	 */
	public static final int ACCOUNTHISTORY_IS_NULL = 2006030;

	/**
	 * 结算记录不存在
	 */
	public static final SettBizException SETT_RECORD_NOT_EXIST = new SettBizException(2006031, "结算记录不存在");
	/**
	 * 结算记录已经成功，重复回调
	 */
	public static final SettBizException SETT_RECORD_ALREADY_SUCCESS = new SettBizException(2006032, "结算记录已经成功，重复回调");

	public SettBizException() {

	}

	public SettBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public SettBizException(int code, String msg) {
		super(code, msg);
	}

}
