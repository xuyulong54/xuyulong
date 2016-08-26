package wusc.edu.pay.facade.remit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: 省内省外枚举类：100-省内，101-省外
 * @Description: 
 * @author zzh
 * @date 2014-8-7 上午10:16:14
 */
public enum IsInProvinceEnum {
	/**
	 * 省内
	 */
	IN_PROVINCE("省内", 100),
	
	/**
	 * 省外
	 */
	OUT_PROVINCE("省外", 101);
	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;
	private IsInProvinceEnum(String desc, int value) {
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

	public static IsInProvinceEnum getEnum(int value) {
		IsInProvinceEnum resultEnum = null;
		IsInProvinceEnum[] enumAry = IsInProvinceEnum.values();
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
		IsInProvinceEnum[] ary = IsInProvinceEnum.values();
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
		IsInProvinceEnum[] ary = IsInProvinceEnum.values();
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
