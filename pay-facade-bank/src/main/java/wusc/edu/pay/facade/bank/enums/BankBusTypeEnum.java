package wusc.edu.pay.facade.bank.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 银行业务类型枚举
 * @author Administrator
 *
 */
public enum BankBusTypeEnum {
	
	NET_B2B("B2B银行卡支付", 1),
	NET_B2C("B2C银行卡支付", 2),
	FAST("快捷支付", 3);
	///ACCOUNT_BALANCE_PAY("余额支付", 4);
	
	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private BankBusTypeEnum(String desc, int value) {
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

	public static BankBusTypeEnum getEnum(int value) {
		BankBusTypeEnum resultEnum = null;
		BankBusTypeEnum[] enumAry = BankBusTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		BankBusTypeEnum[] ary = BankBusTypeEnum.values();
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
	 * 支付方式类型
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForPayWay() {
		BankBusTypeEnum[] ary = BankBusTypeEnum.values();
		List list = new ArrayList();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value",BankBusTypeEnum.getEnum(ary[num].getValue()).name());
			map.put("desc", ary[num].getDesc());
			list.add(map);
		}
		return list;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		BankBusTypeEnum[] ary = BankBusTypeEnum.values();
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
	 * 取枚举的json字符串
	 * 
	 * @return
	 */
	public static String getJsonStr() {
		BankBusTypeEnum[] enums = BankBusTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (BankBusTypeEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'")
					.append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
	
}
