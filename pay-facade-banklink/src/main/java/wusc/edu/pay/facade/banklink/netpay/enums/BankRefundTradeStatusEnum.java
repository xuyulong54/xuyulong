package wusc.edu.pay.facade.banklink.netpay.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 银行退款状态
 * @作者: WuShuicheng .
 * @创建时间: 2013-9-12,上午11:16:23 .
 * @版本: 1.0 .
 */
public enum BankRefundTradeStatusEnum {

	/**
	 * 退款成功
	 */
	SUCCESS("退款成功", 100),
	/**
	 * 退款失败
	 */
	FAILED("退款失败", 101),
	/**
	 * 未退
	 */
	CREATED("未退", 102),
	/**
	 * 受理成功
	 */
	ACCEPT_SUCCESS("受理成功", 103);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private BankRefundTradeStatusEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 根据枚举值获取枚举属性.
	 * 
	 * @param value
	 *            枚举值.
	 * @return enum 枚举属性.
	 */
	public static BankRefundTradeStatusEnum getEnum(int value) {
		BankRefundTradeStatusEnum resultEnum = null;
		BankRefundTradeStatusEnum[] enumAry = BankRefundTradeStatusEnum.values();
		for (int num = 0; num < enumAry.length; num++) {
			if (enumAry[num].getValue() == value) {
				resultEnum = enumAry[num];
				break;
			}
		}
		return resultEnum;
	}

	/**
	 * 将枚举类转换为map.
	 * 
	 * @return Map<key, Map<attr, value>>
	 */
	public static Map<String, Map<String, Object>> toMap() {
		BankRefundTradeStatusEnum[] ary = BankRefundTradeStatusEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", ary[num].getValue());
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	/**
	 * 将枚举类转换为list.
	 * 
	 * @return List<Map<String, Object>> list.
	 */
	public static List<Map<String, Object>> toList() {
		BankRefundTradeStatusEnum[] ary = BankRefundTradeStatusEnum.values();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < ary.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", ary[i].getValue());
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

}
