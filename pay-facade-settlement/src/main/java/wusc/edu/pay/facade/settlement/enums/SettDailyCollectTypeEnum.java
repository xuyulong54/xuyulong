package wusc.edu.pay.facade.settlement.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算日汇总类型
 * 
 * @author huqian
 * @date 2014-01-07
 * @version 1.0
 */
public enum SettDailyCollectTypeEnum {

	/**
	 * 存入汇总
	 */
	ADD("存入汇总", 1),

	/**
	 * 减少汇总
	 */
	SUB("减少汇总", 2),

	/**
	 * 存入/减少汇总
	 */
	ALL("存入/减少汇总", 3),

	/**
	 * 临时汇总
	 */
	TEMP("临时汇总", 4),
	/**
	 * 未结算遗留汇总(例如：小数点后两位部分)
	 */
	LEAVE("遗留汇总", 5);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private SettDailyCollectTypeEnum(String desc, int value) {
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

	public static SettDailyCollectTypeEnum getEnum(int value) {
		SettDailyCollectTypeEnum resultEnum = null;
		SettDailyCollectTypeEnum[] enumAry = SettDailyCollectTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		SettDailyCollectTypeEnum[] ary = SettDailyCollectTypeEnum.values();
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
		SettDailyCollectTypeEnum[] ary = SettDailyCollectTypeEnum.values();
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
