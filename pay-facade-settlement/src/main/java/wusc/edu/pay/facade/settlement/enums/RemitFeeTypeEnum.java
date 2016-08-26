package wusc.edu.pay.facade.settlement.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: 结算规则手续费类型
 * 
 */
public enum RemitFeeTypeEnum {
	/**
	 * 固定手续费
	 */
	STEADY("固定手续费",1),
	/**
	 * 百分比
	 */
	PERCENT("百分比",2),
	
	/**
	 * 无手续费
	 */
	NONE("无手续费",3);
	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private RemitFeeTypeEnum(String desc, int value) {
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

	public static RemitFeeTypeEnum getEnum(int value) {
		RemitFeeTypeEnum resultEnum = null;
		RemitFeeTypeEnum[] enumAry = RemitFeeTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		RemitFeeTypeEnum[] ary = RemitFeeTypeEnum.values();
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
		RemitFeeTypeEnum[] ary = RemitFeeTypeEnum.values();
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
