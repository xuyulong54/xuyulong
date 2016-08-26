package wusc.edu.pay.facade.payrule.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @描述: 支付类型枚举.
 * @作者: WuShuicheng.
 * @创建: 2014-6-30,上午11:17:18
 * @版本: V1.0
 * 
 */
public enum PayTypeEnum {
	BANK_CARD("银行卡", 1), NO_CARD("非银行卡", 2);
	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private PayTypeEnum(String desc, int value) {
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

	public static PayTypeEnum getEnum(int value) {
		PayTypeEnum resultEnum = null;
		PayTypeEnum[] enumAry = PayTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		PayTypeEnum[] ary = PayTypeEnum.values();
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
