package wusc.edu.pay.facade.user.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 商户签名类型枚举类
 * @author： Peter
 * @ClassName: MerchantSignType.java
 * @Date： 2015-3-4 下午2:33:25 
 * @version：  V1.0
 */
public enum MerchantSignTypeEnum {
	
	MD5("MD5" , 1),RSA("RSA" , 2);
	
	private MerchantSignTypeEnum( String desc , int value ){
		this.value = value;
		this.desc = desc;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		MerchantSignTypeEnum[] ary = MerchantSignTypeEnum.values();
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
	
	public static MerchantSignTypeEnum getEnum(int value) {
		MerchantSignTypeEnum resultEnum = null;
		MerchantSignTypeEnum[] enumAry = MerchantSignTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	/**值*/
	private int value;
	
	/**描述*/
	private String desc;
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
	
	
}
