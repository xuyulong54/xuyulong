package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 
 * @描述:文件管理--文件性质（ 1、首次签约，2、续约，3、变更资料，4、其它）
 * @作者: Lanzy.
 * @创建时间: 2014-4-14, 下午4:03:15 .
 * @版本: V1.0.
 *
 */
public enum ContractFilePropertiesEnum {
	
	FIRST("首次签约", 1),
	CHANGE("变更文件",2),
	CONTRACT("续约",3),
	OTHER("其它",4);
	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;
	
	private ContractFilePropertiesEnum(String desc, int value) {
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
	
	public static ContractFilePropertiesEnum getEnum(int value) {
		ContractFilePropertiesEnum resultEnum = null;
		ContractFilePropertiesEnum[] enumAry = ContractFilePropertiesEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		ContractFilePropertiesEnum[] ary = ContractFilePropertiesEnum.values();
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
		ContractFilePropertiesEnum[] ary = ContractFilePropertiesEnum.values();
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
