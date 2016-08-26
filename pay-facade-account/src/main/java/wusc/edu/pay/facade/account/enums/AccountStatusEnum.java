package wusc.edu.pay.facade.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 账户状态. 适用于账户表.
 * 
 * @作者: chenjh .
 * @创建时间: 2013-8-29,下午1:49:34 .
 * @版本: 1.0 .
 */
public enum AccountStatusEnum {

	/**
	 * 激活
	 */
	ACTIVE("激活", 100), 
	/**
	 * 冻结
	 */
	INACTIVE("冻结", 101),
	/**
	 * 冻结止收
	 */
	INACTIVE_FREEZE_CREDIT("冻结止收", 102),
	/**
	 * 冻结止付
	 */
	INACTIVE_FREEZE_DEBIT("冻结止付", 103),
	/**
	 * 注销
	 */
	CANCELLED("注销", 104);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private AccountStatusEnum(String desc, int value) {
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

	public static AccountStatusEnum getEnum(int value) {
		AccountStatusEnum resultEnum = null;
		AccountStatusEnum[] enumAry = AccountStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		AccountStatusEnum[] ary = AccountStatusEnum.values();
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
		AccountStatusEnum[] ary = AccountStatusEnum.values();
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
	 * 只要冻结，冻结止收，冻结止付3种状态
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toAccountStatusList(){
		AccountStatusEnum[] ary = AccountStatusEnum.values();
		List list = new ArrayList();
		int value[] = {101,102,103} ; 
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for(int v:value){
				if(val==v){
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					list.add(map);
				}
			}
		}
		return list;
	}
}
