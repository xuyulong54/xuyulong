package wusc.edu.pay.facade.remit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: 银行地区级别枚举
 * @Description: 
 * @author zzh
 * @date 2014-7-24 下午3:21:30
 */
public enum BankAreaLevelEnum {
	/**
	 * 省级
	 */
	PROVINCE("省级", 1),
	
	/**
	 * 市级
	 */
	CITY("市级", 2),
	
	/**
	 * 县级
	 */
	COUNTY("县级", 3),
	
	/**
	 * 县级市
	 */
	COUNTY_CITY("县级市", 4),
	
	/**
	 * 地级
	 */
	PREFECTURE("地级", 5);

	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;
	private BankAreaLevelEnum(String desc, int value) {
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

	public static BankAreaLevelEnum getEnum(int value) {
		BankAreaLevelEnum resultEnum = null;
		BankAreaLevelEnum[] enumAry = BankAreaLevelEnum.values();
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
		BankAreaLevelEnum[] ary = BankAreaLevelEnum.values();
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
		BankAreaLevelEnum[] ary = BankAreaLevelEnum.values();
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
