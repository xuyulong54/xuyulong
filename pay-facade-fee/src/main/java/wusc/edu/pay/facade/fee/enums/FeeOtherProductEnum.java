package wusc.edu.pay.facade.fee.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Desc: 计费维度支付产品以外的产品
 * @author lichao
 * Date: 2014-7-17
 *
 */
public enum FeeOtherProductEnum {
	/**
	 * 账户内转账
	 */
	ACCOUNT_ACCOUNT_TRANSFER("账户内转账",1),
	/**
	 * 账户银行卡转账
	 */
	ACCOUNT_CARD_TRANSFER("账户-银行卡转账",2),
	/**
	 * 银行卡-银行卡转账
	 */
	CARD_CARD_TRANSFER("银行卡-银行卡转账",3),
	/**
	 * 提现
	 */
	ATM("提现",4),
	/**
	 * 结算
	 */
	SETTLEMENT("结算",5);
	
	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;
	
	private FeeOtherProductEnum(String desc, int value) {
		this.setValue(value);
		this.setDesc(desc);
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
	
	public static FeeOtherProductEnum getEnum(int value) {
		FeeOtherProductEnum resultEnum = null;
		FeeOtherProductEnum[] enumAry = FeeOtherProductEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		FeeOtherProductEnum[] ary = FeeOtherProductEnum.values();
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
		FeeOtherProductEnum[] ary = FeeOtherProductEnum.values();
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
	 * value  to name
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListName() {
		FeeOtherProductEnum[] ary = FeeOtherProductEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", ary[i].name());
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 转账 name
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListTransferName() {
		int[] ary = {FeeOtherProductEnum.ACCOUNT_ACCOUNT_TRANSFER.getValue(),FeeOtherProductEnum.ACCOUNT_CARD_TRANSFER.getValue(),FeeOtherProductEnum.CARD_CARD_TRANSFER.getValue()};
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", FeeOtherProductEnum.getEnum(ary[i]).name());
			map.put("desc", FeeOtherProductEnum.getEnum(ary[i]).getDesc());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 提现 name
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListATMName() {
		int[] ary = {FeeOtherProductEnum.ATM.getValue()};
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", FeeOtherProductEnum.getEnum(ary[i]).name());
			map.put("desc", FeeOtherProductEnum.getEnum(ary[i]).getDesc());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 结算 name
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListSettlementName() {
		int[] ary = {FeeOtherProductEnum.SETTLEMENT.getValue()};
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", FeeOtherProductEnum.getEnum(ary[i]).name());
			map.put("desc", FeeOtherProductEnum.getEnum(ary[i]).getDesc());
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
		FeeOtherProductEnum[] enums = FeeOtherProductEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (FeeOtherProductEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'").append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}

}
