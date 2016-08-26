package wusc.edu.pay.facade.user.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户费率的枚举类
 * @desc  
 * @author shenjialong
 * @date  Jun 19, 2014,2:34:09 PM
 */
public enum MerchantRateTypeEnum {
	PERTIMES("按笔", 1), 
	PRESENT("百分比", 2), 
	PACKYEAR("包年", 3), 
	SEALTOP("封顶", 4);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private MerchantRateTypeEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static MerchantRateTypeEnum getEnum(int value) {
		MerchantRateTypeEnum resultEnum = null;
		MerchantRateTypeEnum[] enumAry = MerchantRateTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		MerchantRateTypeEnum[] ary = MerchantRateTypeEnum.values();
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
		MerchantRateTypeEnum[] ary = MerchantRateTypeEnum.values();
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
