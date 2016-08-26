package wusc.edu.pay.facade.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户资金变动方向
 * 
 * @author huqian
 * @date 2014-01-07
 * @version 1.0
 */
public enum AccountFundDirectionEnum {

	/**
	 * 加款
	 */
	ADD("加款", 123),

	/**
	 * 减款
	 */
	SUB("减款", 321),

	/**
	 * 冻结
	 */
	FROZEN("冻结", 321),

	/**
	 * 解冻
	 */
	UNFROZEN("解冻", 123);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private AccountFundDirectionEnum(String desc, int value) {
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

	public static AccountFundDirectionEnum getEnum(int value) {
		AccountFundDirectionEnum resultEnum = null;
		AccountFundDirectionEnum[] enumAry = AccountFundDirectionEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		AccountFundDirectionEnum[] ary = AccountFundDirectionEnum.values();
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
		AccountFundDirectionEnum[] ary = AccountFundDirectionEnum.values();
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
