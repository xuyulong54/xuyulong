package wusc.edu.pay.facade.settlement.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算发起方式
 * 
 * @author huqian
 * @date 2014-01-07
 * @version 1.0
 */
public enum SettModeTypeEnum {

	/**
	 * 每日汇总
	 */
	DAILY_COLLECT("每日汇总", 1),

	/**
	 * 手工结算
	 */
	SELFHELP_SETTLE("手工结算", 2),

	/**
	 * 自动（定期）结算
	 */
	REGULAR_SETTLE("自动结算", 3),

	/**
	 * 紧急结算
	 */
	URGENT_SETTLE("紧急结算", 4),

	/**
	 * 临时结算
	 */
	TEMP_SETTLE("临时结算", 5),
	
	/**
	 * 会员提现
	 */
	MEMBER_WITHDRAW("提现", 6);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private SettModeTypeEnum(String desc, int value) {
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

	public static SettModeTypeEnum getEnum(int value) {
		SettModeTypeEnum resultEnum = null;
		SettModeTypeEnum[] enumAry = SettModeTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		SettModeTypeEnum[] ary = SettModeTypeEnum.values();
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
		SettModeTypeEnum[] ary = SettModeTypeEnum.values();
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
	 * 结算枚举
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toSettList() {
		SettModeTypeEnum[] ary = SettModeTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			if(ary[i].getValue() != SettModeTypeEnum.MEMBER_WITHDRAW.getValue()){
				Map<String, String> map = new HashMap<String, String>();
				map.put("value", String.valueOf(ary[i].getValue()));
				map.put("desc", ary[i].getDesc());
				list.add(map);
			}
		}
		return list;
	}
}
