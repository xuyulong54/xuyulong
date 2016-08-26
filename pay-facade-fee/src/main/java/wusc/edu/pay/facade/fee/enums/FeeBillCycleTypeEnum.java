package wusc.edu.pay.facade.fee.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 费用账单周期
 */
public enum FeeBillCycleTypeEnum {
	/**
	 * 周
	 */
	WEEK("周",1),
	/**
	 * 月
	 */
	MONTH("月",2),
	/**
	 * 季度
	 */
	QUARTER("季度",3),
	/**
	 * 自定义
	 */
	CUSTOMIZE("自定义",4);
	
	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;
	
	private FeeBillCycleTypeEnum(String desc, int value) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static FeeBillCycleTypeEnum getEnum(int value) {
		FeeBillCycleTypeEnum resultEnum = null;
		FeeBillCycleTypeEnum[] enumAry = FeeBillCycleTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		FeeBillCycleTypeEnum[] ary = FeeBillCycleTypeEnum.values();
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
		FeeBillCycleTypeEnum[] ary = FeeBillCycleTypeEnum.values();
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
