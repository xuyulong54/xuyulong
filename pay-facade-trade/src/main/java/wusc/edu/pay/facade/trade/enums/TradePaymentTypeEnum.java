package wusc.edu.pay.facade.trade.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付方式类型
 * 
 * @author Administrator
 * 
 */
public enum TradePaymentTypeEnum {

	/**
	 * 余额支付
	 */
	ACCOUNT_BALANCE_PAY("余额支付", 1),

	/**
	 * 网银B2C支付
	 */
	NET_B2C_PAY("网银B2C支付", 2),

	/**
	 * 网银B2B支付
	 */
	NET_B2B_PAY("网银B2B支付", 3),

	/**
	 * 快捷支付
	 */
	FAST_PAY("快捷支付", 4),

	/**
	 * POS线下支付
	 */
	POS_PAY("POS线下支付", 5),

	/**
	 * 现金支付
	 */
	CASH_PAY("现金支付", 6),

	/**
	 * 
	 * 代扣支付
	 */
	AGENCY_DIBIT("代扣支付", 7),

	/**
	 * 
	 * POS线下支付_不结算
	 */
	POS_PAY_NOT_SETT("POS线下支付_不结算", 8);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	/** 构造函数 */
	private TradePaymentTypeEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
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

	public static TradePaymentTypeEnum getEnum(int value) {
		TradePaymentTypeEnum resultEnum = null;
		TradePaymentTypeEnum[] enumAry = TradePaymentTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		TradePaymentTypeEnum[] ary = TradePaymentTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
