package wusc.edu.pay.facade.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易码，账户服务专用
 * 
 * @author Administrator
 * 
 */
public enum AccountTradeTypeEnum {

	/**
	 * 分账
	 */
	SPLIT("分账", 1001),

	/**
	 * 会员转账
	 */
	ACCOUNT_TRANSFER("会员转账", 2001),

	/**
	 * 会员充值
	 */
	ACCOUNT_DEPOSIT("会员充值", 3001),

	/**
	 * B2C网银退款
	 */
	NET_B2C_REFUND("B2C网银退款", 4001),

	/**
	 * B2B网银退款
	 */
	NET_B2B_REFUND("B2B网银退款", 4002),

	/**
	 * 充值失败退款
	 */
	DEPOSIT_FAILD_REFUND("充值失败退款", 4003),

	/**
	 * 快捷支付退款
	 */
	FAST_REFUND("快捷支付退款", 4004),

	/**
	 * 会员余额支付退款
	 */
	ACCOUNT_BALANCE_REFUND("会员余额支付退款", 4005),

	/**
	 * POS退货
	 */
	POS_REFUND("POS退货", 4008),

	/**
	 * 分账退款
	 */
	SPLIT_REFUND("分账退款", 4009),
	
	/**
	 * 现金支付退款
	 */
	CASH_REFUND("现金支付退款", 4010),

	/**
	 * POS充值
	 */
	POS_RECHARGE("POS充值", 4013),

	/**
	 * 结算
	 */
	SETTLEMENT("结算", 5001),

	/**
	 * 提现
	 */
	ATM("提现", 5002),

	/**
	 * 结算到账
	 */
	SETTLEMENT_INTO("结算到账", 5003),

	/**
	 * 提现到账
	 */
	ATM_INTO("提现到账", 5004),

	/**
	 * B2C银行卡支付
	 */
	NET_B2C_PAY("B2C银行卡支付", 6001),

	/**
	 * B2B银行卡支付
	 */
	NET_B2B_PAY("B2B银行卡支付", 6002),

	/**
	 * 快捷支付
	 */
	FAST_PAY("快捷支付", 6004),

	/**
	 * 余额支付
	 */
	ACCOUNT_BALANCE_PAY("余额支付", 6005),

	/**
	 * POS消费
	 */
	POS_PAY("POS消费", 6006),

	/**
	 * 现金支付
	 */
	CASH_PAY("现金支付", 6007),

	/**
	 * 打款
	 */
	REMIT("打款", 7001),

	/**
	 * 会计日终
	 */
	ACCOUNTING_DAILY_CUT("会计日终", 8001),

	/**
	 * 账户调账
	 */
	ACCOUNT_ADJUST("账户调账", 9001),

	/**
	 * 商户认账
	 */
	MERCHANT_RECON("商户认账", 1101),

	/**
	 * 银行长款平台认账
	 */
	BANK_MORE_PLAT_RECON("银行长款平台认账", 1102),

	/**
	 * 银行短款平台认账
	 */
	BANK_LESS_PLAT_RECON("银行短款平台认账", 1103),
	
	BANK_MORE_NOT_MATCH_BANK_RECON("银行长款金额不符银行认账",1104),
	
	CASH_PAY_RECON("现金支付入账",1105),
	
	ACCOUNT_AGENCYDEBIT("代扣",1106);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private AccountTradeTypeEnum(String desc, int value) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static AccountTradeTypeEnum getEnum(int value) {
		AccountTradeTypeEnum resultEnum = null;
		AccountTradeTypeEnum[] enumAry = AccountTradeTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		AccountTradeTypeEnum[] ary = AccountTradeTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		AccountTradeTypeEnum[] ary = AccountTradeTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", ary[i].getValue() + "");
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
