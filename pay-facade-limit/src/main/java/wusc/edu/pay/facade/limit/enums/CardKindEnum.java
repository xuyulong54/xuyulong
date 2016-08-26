package wusc.edu.pay.facade.limit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 卡种.
 * 
 * @作者: xiehui .
 * @创建时间: 2013-11-19,下午1:49:34 .
 * @版本: 1.0 .
 */
public enum CardKindEnum {
	//卡种:1借记卡,2贷记卡,3准贷记卡,4预付费卡
	JIE("借记卡", 1),DAI("贷记卡", 2), ZHUN("准贷记卡", 3), YU("预付费卡", 4);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private CardKindEnum(String desc, int value) {
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

	public static CardKindEnum getEnum(int value) {
		CardKindEnum resultEnum = null;
		CardKindEnum[] enumAry = CardKindEnum.values();
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
		CardKindEnum[] ary = CardKindEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 枚举转map.<br/>
	 * @return
	 */
	public static Map<String, Map<String, Object>> toMap() {
		CardKindEnum[] ary = CardKindEnum.values();
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
