package wusc.edu.pay.facade.trade.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 订单状态.
 * 
 * @作者: WuShuicheng .
 * @创建时间: 2013-9-12,上午10:47:50 .
 * @版本: 1.0 .
 */
public enum OrderStatusEnum {

	/**
	 * 交易成功
	 */
	SUCCESS("交易成功", 100),
	/**
	 * 交易失败
	 */
	FAILED("交易失败", 101),
	/**
	 * 订单已创建
	 */
	CREATED("订单已创建", 102),
	/**
	 * 订单已取消
	 */
	CANCELED("订单已取消", 103);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	/** 构造函数 */
	private OrderStatusEnum(String desc, int value) {
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

	public static OrderStatusEnum getEnum(int value) {
		OrderStatusEnum resultEnum = null;
		OrderStatusEnum[] enumAry = OrderStatusEnum.values();
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
		OrderStatusEnum[] ary = OrderStatusEnum.values();
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
		OrderStatusEnum[] ary = OrderStatusEnum.values();
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
