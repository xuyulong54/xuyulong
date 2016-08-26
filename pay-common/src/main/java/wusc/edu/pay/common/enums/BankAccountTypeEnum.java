package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行账户类型
 * 
 * @desc
 * @author shenjialong
 * @date 2014-1-13,下午2:19:59
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public enum BankAccountTypeEnum {

	PRIVATE_DEBIT_CARD("对私借记卡", 1), 
	PRIVATE_CREDIT_CARD("对私信用卡", 2), 
	PRIVATE_BANK_BOOK("对私存折", 3), 
	PUBLIC_ACCOUNTS("对公账户", 4);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private BankAccountTypeEnum(String desc, int value) {
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

	public static BankAccountTypeEnum getEnum(int value) {
		BankAccountTypeEnum resultEnum = null;
		BankAccountTypeEnum[] enumAry = BankAccountTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		BankAccountTypeEnum[] ary = BankAccountTypeEnum.values();
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
	 * 获取对私结算账户枚举列表
	 * 
	 * @return
	 */
	public static List toPrivateList() {
		BankAccountTypeEnum[] ary = BankAccountTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			int tempIntValue = ary[i].getValue();
			if (tempIntValue == 4 || tempIntValue == 2) { // 对私列表，隐藏对公账户信息
				continue;
			}
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	/***
	 * POS商户添加银行结算账户时，显示的列表
	 * @return
	 */
	public static List toPosList() {
		BankAccountTypeEnum[] ary = BankAccountTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			int tempIntValue = ary[i].getValue();
			if (tempIntValue != 2) { // 隐藏信用卡
				map.put("value", String.valueOf(ary[i].getValue()));
				map.put("desc", ary[i].getDesc());
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 获取对公商户账户枚举列表
	 * 
	 * @return
	 */
	public static List toPublicList() {
		BankAccountTypeEnum[] ary = BankAccountTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			int tempIntValue = ary[i].getValue();
			if (tempIntValue == 2) { // 对私列表，隐藏对公账户信息
				continue;
			}
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	
	public static List toList() {
		BankAccountTypeEnum[] ary = BankAccountTypeEnum.values();
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
	 * @Title: 结算打款的账户类型 
	 * @Description: 
	 * @param @return    
	 * @return List  
	 * @throws
	 */
	public static List toRemitList(){
		BankAccountTypeEnum[] ary = BankAccountTypeEnum.values();
		List list = new ArrayList();
		int value[] = {1,4} ; 
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
