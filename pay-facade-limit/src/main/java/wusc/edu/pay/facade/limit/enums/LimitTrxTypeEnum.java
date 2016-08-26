package wusc.edu.pay.facade.limit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 开关限制的业务功能枚举
 * 
 * @author Administrator
 */
public enum LimitTrxTypeEnum {

	/********** 在线交易枚举 **********/
	ACCOUNT_TRANSFER("转账", 2001),

	ACCOUNT_DEPOSIT("充值", 3001),

	REFUND("退款", 4000),

	SETTLEMENT("结算", 5001),
	
	SETTLEMENT_T_0("结算T+0", 5002),

	PAY("收款", 6000),

	ACCOUNT_ATM("出款", 5004),

	// POS余额查询
	POS_BALANCE_QUERY("POS余额查询", 4014),

	POS_RECHARGE("POS充值", 4013), // pos充值

	POS_REFUND("POS退货", 4008), // pos退货

	POS_PAY("POS消费", 6006), // POS 消费类 4 种

	POS_REVOKE("消费撤销", 4006),

	POS_PAY_RUSH("消费冲正", 6014),

	POS_REVOKE_RUSH("消费撤销冲正", 6015),

	POS_PREAUTH("POS预授权", 6010), // POS 预授权 8 种

	POS_PREAUTH_RUSH("预授权冲正", 6012),

	POS_PREAUTH_REVOKE("预授权撤销", 6011),

	POS_PREAUTH_REVOKE_RUSH("预授权撤销冲正", 6013),

	POS_PREAUTHED("预授权完成", 6007),

	POS_PREAUTHED_RUSH("预授权完成冲正", 6016),

	POS_PREAUTHED_REVOKE("预授权完成撤销", 4009),

	POS_PREAUTHED_REVOKE_RUSH("预授权完成撤销冲正", 6017);

	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private LimitTrxTypeEnum(String desc, int value) {
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

	public static LimitTrxTypeEnum getEnum(int value) {
		LimitTrxTypeEnum resultEnum = null;
		LimitTrxTypeEnum[] enumAry = LimitTrxTypeEnum.values();
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
		LimitTrxTypeEnum[] ary = LimitTrxTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].name()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	public static Map<String, Map<String, Object>> toMap() {
		LimitTrxTypeEnum[] ary = LimitTrxTypeEnum.values();
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

	/**
	 * 在线开关限制
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForOnlineSwitchLimit() {
		LimitTrxTypeEnum[] ary = LimitTrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = { 2001, 3001, 4000, 5001, 6000, 5004 };
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					map.put("name", ary[i].toString());
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 * POS开关限制
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForPosSwitchLimit() {
		LimitTrxTypeEnum[] ary = LimitTrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = { 4014, 4013, 4008, 6006, 6010 };
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					map.put("name", ary[i].toString());
					list.add(map);
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 开关限制
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListSwitchLimit() {
		LimitTrxTypeEnum[] ary = LimitTrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = { 2001, 3001, 4000, 5001, 6000, 5004,6006,4008,6010 };
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					map.put("name", ary[i].toString());
					list.add(map);
				}
			}
		}
		return list;
	}
	

	/**
	 * POS预授权 包含的业务
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForPosPreauth() {
		LimitTrxTypeEnum[] ary = LimitTrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = { 6010, 6012, 6011, 6013, 6007, 6016, 4009, 6017 };
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					map.put("name", ary[i].toString());
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 * POS消费 包含的业务
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForPosPay() {
		LimitTrxTypeEnum[] ary = LimitTrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = { 6006, 4006, 6014, 6015 };
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					map.put("name", ary[i].toString());
					list.add(map);
				}
			}
		}
		return list;
	}
	/**
	 * 
	 * boss系统--金额限制包--添加金额限制包
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForAmountLimit() {
		LimitTrxTypeEnum[] ary = LimitTrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = { 2001, 3001, 4000, 5001, 5002, 6000, 5004, 6006, 6010, 4008 };
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					map.put("name", ary[i].toString());
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 结算 包含的业务
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForSettlement() {
		LimitTrxTypeEnum[] ary = LimitTrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = { 5001, 5002 };
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					map.put("name", ary[i].toString());
					list.add(map);
				}
			}
		}
		return list;
	}
}
