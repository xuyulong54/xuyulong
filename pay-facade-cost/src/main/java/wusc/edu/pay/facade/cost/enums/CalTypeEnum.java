package wusc.edu.pay.facade.cost.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CalTypeEnum {
	/**
	 * 单笔
	 */
	SIMPLE("单笔", 1),
	
	/**
	 * 区间
	 */
	INTERVAL("区间", 2),
	/**
	 * 单阶梯
	 */
	LADDER_SINGLE("单阶梯", 3),
	/**
	 * 多阶梯
	 */
	LADDER_MULTIPLE("多阶梯", 4);
	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;
	private CalTypeEnum(String desc, int value) {
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

	public static CalTypeEnum getEnum(int value) {
		CalTypeEnum resultEnum = null;
		CalTypeEnum[] enumAry = CalTypeEnum.values();
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
		CalTypeEnum[] ary = CalTypeEnum.values();
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
		CalTypeEnum[] ary = CalTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key,map);
		}
		return enumMap;
	}
}
