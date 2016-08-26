package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API接口，对应编号
 * 
 * @author Administrator
 * 
 */
public enum CmdCodeEnum {

	/**
	 * 充值
	 */
	RECHARGE("充值", 1001),

	/**
	 * 退款
	 */
	REFUND("退款", 1002),

	/**
	 * 在线B2C收款
	 */
	ONLINE_B2C_ACQUIRING("在线B2C收款", 1003),

	/**
	 * 在线B2B收款
	 */
	ONLINE_B2B_ACQUIRING("在线B2B收款", 1004),

	/**
	 * 支付订单查询
	 */
	QUERY_PAYMENT_ORDER("支付订单查询", 1004),

	/**
	 * 退款订单查询
	 */
	QUERY_REFUND("退款订单查询", 1005),
	/**
	 * 移动收单
	 */
	MOBILE_ACQUIRING("移动收单", 1006),
	/**
	 * POS收单
	 */
	POS_ACQUIRING("POS收单", 1007),
	/**
	 * 外卡收单
	 */
	ICC_ACQUIRING("外卡收单", 1008),
	/**
	 * 代扣
	 */
	ACCOUNT_AGENCYDEBIT("代扣", 1009);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	/** 构造函数 */
	private CmdCodeEnum(String desc, int value) {
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

	public static CmdCodeEnum getEnum(int value) {
		CmdCodeEnum resultEnum = null;
		CmdCodeEnum[] enumAry = CmdCodeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		CmdCodeEnum[] ary = CmdCodeEnum.values();
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
		CmdCodeEnum[] ary = CmdCodeEnum.values();
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
