package wusc.edu.pay.facade.remit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Title: 业务类型枚举
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午2:10:37
 */
public enum TradeTypeEnum {
	/**
	 * 会员提现
	 */
	MEMBER_CASH("会员提现", 1),

	/**
	 * 商户结算
	 */
	MERCHANT_AUTO_SETTLEMENT("商户结算", 2),

	/**
	 * 手工制单
	 */
	MERCHANT_MANUAL_ORDER("手工制单", 3),

	/**
	 * 商户加急结算
	 */
	MERCHANT_AUTO_SETTLEMENT_URGENT("商户加急结算", 4),
	/**
	 * 代理商结算
	 */
	AGENT_AUTO_SETTLEMENT("代理商结算", 5);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private TradeTypeEnum(String desc, int value) {
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

	public static TradeTypeEnum getEnum(int value) {
		TradeTypeEnum resultEnum = null;
		TradeTypeEnum[] enumAry = TradeTypeEnum.values();
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
		TradeTypeEnum[] ary = TradeTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	public static Map<String, Map<String, Object>> toMap() {
		TradeTypeEnum[] ary = TradeTypeEnum.values();
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
}
