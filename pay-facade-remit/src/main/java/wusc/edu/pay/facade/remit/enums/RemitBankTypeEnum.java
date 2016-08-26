package wusc.edu.pay.facade.remit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: 银行行别类型枚举
 * @Description: 
 * @author zzh
 * @date 2014-7-24 下午3:25:05
 */
public enum RemitBankTypeEnum {
	/**
	 * 中央银行
	 */
	CENTRAL_BANK("中央银行", 1),
	
	/**
	 * 国有独资商业银行
	 */
	FUNDED_COMMERCIAL_BANK("国有独资商业银行", 2),
	
	/**
	 * 政策性银行
	 */
	POLICY_BANK("政策性银行", 3),
	
	/**
	 * 其他商业银行
	 */
	OTHER_COMMERCIAL_BANK("其他商业银行", 4),
	
	/**
	 * 非银行金融机构
	 */
	NONBANKING_FINANCIAL("非银行金融机构", 5),

	/**
	 * 外资在华银行或代表处
	 */
	FOREIGN_BANK("外资在华银行或代表处", 6),
	
	/**
	 * 特区参与者
	 */
	PARTICIPANTS("特区参与者", 7);
	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;
	private RemitBankTypeEnum(String desc, int value) {
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

	public static RemitBankTypeEnum getEnum(int value) {
		RemitBankTypeEnum resultEnum = null;
		RemitBankTypeEnum[] enumAry = RemitBankTypeEnum.values();
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
		RemitBankTypeEnum[] ary = RemitBankTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		RemitBankTypeEnum[] ary = RemitBankTypeEnum.values();
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
