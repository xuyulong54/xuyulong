package wusc.edu.pay.facade.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户操作,发起方
 * 
 * @author Administrator
 * 
 */
public enum AccountInitiatorEnum {

	/**
	 * 风控系统
	 */
	RCMS_SYS("风控系统", 1),
	/**
	 * 运营BOSS
	 */
	BOSS_SYS("运营BOSS", 2),
	/**
	 * 用户注册
	 */
	USER_REG("用户注册", 3);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private AccountInitiatorEnum(String desc, int value) {
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

	public static AccountInitiatorEnum getEnum(int value) {
		AccountInitiatorEnum resultEnum = null;
		AccountInitiatorEnum[] enumAry = AccountInitiatorEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		AccountInitiatorEnum[] ary = AccountInitiatorEnum.values();
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
		AccountInitiatorEnum[] ary = AccountInitiatorEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", ary[i].toString());
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
