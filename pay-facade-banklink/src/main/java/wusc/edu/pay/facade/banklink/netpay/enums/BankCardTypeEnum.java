package wusc.edu.pay.facade.banklink.netpay.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 卡类型
 * @作者: WuShuicheng .
 * @创建时间: 2013-9-12,上午11:16:23 .
 * @版本: 1.0 .
 */
public enum BankCardTypeEnum {

	/**
	 * 借记卡
	 */
	DEBIT("借记卡", 1),
	/**
	 * 贷记卡
	 */
	CREDIT("贷记卡", 2),
	/**
	 * 准贷记卡
	 */
	QUASI_CREDIT("准贷记卡", 3),
	/**
	 * 预付卡
	 */
	PREPAID("预付卡", 4),
	/**
	 * 个人/会员
	 */
	OTHER_UPOP("他行银联卡", 5);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private BankCardTypeEnum(String desc, int value) {
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
	 * 根据枚举名称获取枚举值.
	 * 
	 * @param name
	 * @return
	 */
	public static int getCode(String name){
		int code=0;
		BankCardTypeEnum[] enumAry=BankCardTypeEnum.values();
		for(int i=0;i<enumAry.length;i++){
			if(name.equals(enumAry[i].getDesc())){
				code=enumAry[i].getValue();
				break;
			}
		}
		return code;
	}
	
	
	/**
	 * 根据枚举值获取枚举属性.
	 * 
	 * @param value
	 *            枚举值.
	 * @return enum 枚举属性.
	 */
	public static BankCardTypeEnum getEnum(int value) {
		BankCardTypeEnum resultEnum = null;
		BankCardTypeEnum[] enumAry = BankCardTypeEnum.values();
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
		BankCardTypeEnum[] ary = BankCardTypeEnum.values();
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
		BankCardTypeEnum[] ary = BankCardTypeEnum.values();
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
