package wusc.edu.pay.facade.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户操作类型枚举
 * 
 * @author Administrator
 * 
 */
public enum AccountOperationTypeEnum {

	/** 冻结止付 */
	FREEZE_DEBIT("冻结止付", 1),

	/** 解冻止付 */
	UNFREEZE_DEBIT("解冻止付", 2),

	/** 冻结止收 */
	FREEZE_CREDIT("冻结止收", 3),

	/** 解冻止收 */
	UNFREEZE_CREDIT("解冻止收", 4),

	/** 冻结账户 */
	FREEZE_ACCOUNT("冻结账户", 5),

	/** 解冻账户 */
	UNFREEZE_ACCOUNT("解冻账户", 6),

	/** 创建付款账户 */
	CREATE_ACCOUNT("创建付款账户", 7),

	/** 冻结资金 */
	FREEZE_FUND("冻结资金", 8),

	/** 解冻资金 */
	UNFREEZE_FUND("解冻资金", 9),

	/** 注销账户 */
	CANCEL_ACCOUNT("注销账户", 10);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private AccountOperationTypeEnum(String desc, int value) {
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

	public static AccountOperationTypeEnum getEnum(int value) {
		AccountOperationTypeEnum resultEnum = null;
		AccountOperationTypeEnum[] enumAry = AccountOperationTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		AccountOperationTypeEnum[] ary = AccountOperationTypeEnum.values();
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
		AccountOperationTypeEnum[] ary = AccountOperationTypeEnum.values();
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
