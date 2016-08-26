package wusc.edu.pay.facade.agent.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * 商户安全级别枚举
 * @author Administrator
 *
 */
public enum MerchantSafeLevelEnum {
	
	ONE_LEVEL("一级", 1), 
	TWO_LEVEL("二级", 2), 
	THREE_LEVEL("三级", 3);
	

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private MerchantSafeLevelEnum(String desc, int value) {
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

	public static MerchantSafeLevelEnum getEnum(int value) {
		MerchantSafeLevelEnum resultEnum = null;
		MerchantSafeLevelEnum[] enumAry = MerchantSafeLevelEnum.values();
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
		MerchantSafeLevelEnum[] ary = MerchantSafeLevelEnum.values();
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
		MerchantSafeLevelEnum[] ary = MerchantSafeLevelEnum.values();
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
