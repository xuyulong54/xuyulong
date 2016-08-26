package wusc.edu.pay.facade.settlement.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算失败，退回手续费类型
 * 
 * @author huqian
 * @date 2014-01-07
 * @version 1.0
 */
public enum SettReturnFeeTypeEnum {

	/**
	 * 包含手续费
	 */
	INClUDE_FEE("包含手续费", 1),

	/**
	 * 不含手续费
	 */
	EXCLUDE_FEE("不含手续费", 2);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private SettReturnFeeTypeEnum(String desc, int value) {
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

	public static SettReturnFeeTypeEnum getEnum(int value) {
		SettReturnFeeTypeEnum resultEnum = null;
		SettReturnFeeTypeEnum[] enumAry = SettReturnFeeTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		SettReturnFeeTypeEnum[] ary = SettReturnFeeTypeEnum.values();
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
		SettReturnFeeTypeEnum[] ary = SettReturnFeeTypeEnum.values();
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
