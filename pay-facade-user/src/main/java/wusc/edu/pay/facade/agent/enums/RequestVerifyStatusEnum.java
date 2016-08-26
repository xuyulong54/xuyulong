package wusc.edu.pay.facade.agent.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * 代理商系统-请求审核枚举
 * @author huangbin
 *
 */
public enum RequestVerifyStatusEnum {
	
	AUDIT_ING("审核中", 101), 
	SUCCESS("审核通过", 100),
	NO_PASS("审核不通过", 102);
	

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private RequestVerifyStatusEnum(String desc, int value) {
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

	public static RequestVerifyStatusEnum getEnum(int value) {
		RequestVerifyStatusEnum resultEnum = null;
		RequestVerifyStatusEnum[] enumAry = RequestVerifyStatusEnum.values();
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
		RequestVerifyStatusEnum[] ary = RequestVerifyStatusEnum.values();
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
		RequestVerifyStatusEnum[] ary = RequestVerifyStatusEnum.values();
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
