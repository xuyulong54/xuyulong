package wusc.edu.pay.facade.remit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打款加急枚举
 * @author： Peter
 * @ClassName: RemitUrgentEnum.java
 * @Date： 2014-7-24 下午3:44:26 
 * @version：  V1.0
 */
public enum RemitUrgentEnum {
	URGENT(1,"加急"),UN_URGENT(2,"非加急");
	private int value;
	private String desc;
	
	private RemitUrgentEnum(int value , String desc){
		this.value = value;
		this.desc = desc;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		RemitUrgentEnum[] ary = RemitUrgentEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
