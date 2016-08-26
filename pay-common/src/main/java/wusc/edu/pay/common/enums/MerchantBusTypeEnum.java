package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 商户业务类型枚举. <br/>
 * 适用于表：
 * @作者: WuShuicheng .
 * @创建时间: 2013-9-12,上午11:16:23 .
 * @版本: 1.0 .
 */
public enum MerchantBusTypeEnum {
	
	ONLINE_MERCHANT("在线商户", 1),
	POS_MERCHANT("POS商户", 2);
	//MOBILE_MERCHANT("移动商户", 3);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private MerchantBusTypeEnum(String desc, int value) {
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
	
	public static MerchantBusTypeEnum getEnum(int value){
		MerchantBusTypeEnum resultEnum = null;
		MerchantBusTypeEnum[] enumAry = MerchantBusTypeEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue() == value){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static String getName(int value){
		String result = null;
		MerchantBusTypeEnum[] enumAry = MerchantBusTypeEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(value == enumAry[i].getValue()){
				result = enumAry[i].name();
				break;
			}
		}
		return result;
	}
	
	public static String getDesc(int value){
		String result = null;
		MerchantBusTypeEnum[] enumAry = MerchantBusTypeEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(value == enumAry[i].getValue()){
				result = enumAry[i].getDesc();
				break;
			}
		}
		return result;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		MerchantBusTypeEnum[] ary = MerchantBusTypeEnum.values();
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
	public static List toList(){
		MerchantBusTypeEnum[] ary = MerchantBusTypeEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

}
