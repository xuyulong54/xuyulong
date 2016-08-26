package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BankOrderStatusEnum {
	
	ORDER_NONEPROCESS("未处理",0),
	ORDER_SUSSESS("支付成功",1),
	ORDER_FAIL("支付失败",2),
	ORDER_PROCESSING("处理中",3),
	ORDER_ERR("未知异常",4);
	
	private BankOrderStatusEnum(String desc , int value){
		this.desc = desc;
		this.value = value;
	}
	
	/*枚举值*/
	private int value;
	/*描述*/
	private String desc;

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
	
	public static BankOrderStatusEnum getEnum(int value){
		BankOrderStatusEnum resultEnum=null;
		BankOrderStatusEnum[] enumAry=BankOrderStatusEnum.values();
		for(int i=0;i<enumAry.length;i++){
			if(enumAry[i].getValue()==value){
				resultEnum=enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		BankOrderStatusEnum[] ary = BankOrderStatusEnum.values();
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
		BankOrderStatusEnum[] ary = BankOrderStatusEnum.values();
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
}
