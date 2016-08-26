package wusc.edu.pay.facade.settlement.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: 结算控制状态
 * 
 */
public enum RemitFeeSetEnum {
	/**
	 * 不使用费率计算
	 */
	NOT("不使用费率计算",1),
	/**
	 * 使用临时结算的费率规则
	 */
	TEMP("使用临时结算的费率规则",2),
	/**
	 * 使用结算规则的费率规则
	 */
	RULE("使用结算规则的费率规则",3);
	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private RemitFeeSetEnum(String desc, int value) {
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

	public static RemitFeeSetEnum getEnum(int value) {
		RemitFeeSetEnum resultEnum = null;
		RemitFeeSetEnum[] enumAry = RemitFeeSetEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		RemitFeeSetEnum[] ary = RemitFeeSetEnum.values();
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
		RemitFeeSetEnum[] ary = RemitFeeSetEnum.values();
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
