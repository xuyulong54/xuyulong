package wusc.edu.pay.facade.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 账户类型 . 适用于账户表 .
 * @作者: WuShuicheng .
 * @创建时间: 2013-9-12,上午11:16:23 .
 * @版本: 1.0 .
 */
public enum AccountTypeEnum {

	/**
	 * 在线商户
	 */
	MERCHANT("在线商户", 1),
	/**
	 * 代理商
	 */
	AGENT("代理商", 2),
	/**
	 * 个人/会员
	 */
	CUSTOMER("个人/会员", 3),
	/**
	 * 内部账户
	 */
	PRIVATE("内部账户", 4),
	/**
	 * POS商户
	 */
	POS("POS商户", 5),
	/**
	 * POS代理商
	 */
	POSAGENT("POS代理商", 6),
	/**
	 * POS商户_外部结算
	 */
	POS_OUT_SETT("POS商户_外部结算", 7);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private AccountTypeEnum(String desc, int value) {
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

	/**
	 * 根据枚举值获取枚举属性.
	 * 
	 * @param value
	 *            枚举值.
	 * @return enum 枚举属性.
	 */
	public static AccountTypeEnum getEnum(int value) {
		AccountTypeEnum resultEnum = null;
		AccountTypeEnum[] enumAry = AccountTypeEnum.values();
		for (int num = 0; num < enumAry.length; num++) {
			if (enumAry[num].getValue() == value) {
				resultEnum = enumAry[num];
				break;
			}
		}
		return resultEnum;
	}

	/**
	 * 将枚举类转换为map.
	 * 
	 * @return Map<key, Map<attr, value>>
	 */
	public static Map<String, Map<String, Object>> toMap() {
		AccountTypeEnum[] ary = AccountTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", ary[num].getValue());
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	/**
	 * 将枚举类转换为list.
	 * 
	 * @return List<Map<String, Object>> list.
	 */
	public static List<Map<String, Object>> toList() {
		AccountTypeEnum[] ary = AccountTypeEnum.values();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < ary.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", ary[i].getValue());
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
