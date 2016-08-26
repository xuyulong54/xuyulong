package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核算账户枚举.
 * 适用于TBL_ACCOUNTING_TEMPLATE_TITLE表.
 * @author Hill
 */
public enum AdjustAccountEnum {
	 BANK("银行",1002),
	 MEMBER("会员",2001),
	 MERCHANT("商户",2002),
	 BANKCHANNEL("渠道",4001);

	 /** 描述  */
	 private String desc;
	 /** 枚举值 */
	 private int value;
	 
	 private AdjustAccountEnum(String desc , int value){
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
	 
	public static AdjustAccountEnum getEnum(int value){
		AdjustAccountEnum resultEnum = null;
		AdjustAccountEnum[] enumAry = AdjustAccountEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue() == value){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		AdjustAccountEnum[] ary = AdjustAccountEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		AdjustAccountEnum[] ary = AdjustAccountEnum.values();
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
