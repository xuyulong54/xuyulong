package wusc.edu.pay.facade.user.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 用户操作日志类型枚举
 * 1-增加，2-修改，3-删除，4-查询
 * @author Administrator
 * 
 */
public enum UserOperatorLogTypeEnum {

	OPER_ADD("增加", 1), 
	OPER_MOTIFY("修改", 2), 
	OPER_DELETE("删除", 3), 
	OPER_SELECT("查询", 4);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private UserOperatorLogTypeEnum(String desc, int value) {
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

	public static UserOperatorLogTypeEnum getEnum(int value) {
		UserOperatorLogTypeEnum resultEnum = null;
		UserOperatorLogTypeEnum[] enumAry = UserOperatorLogTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		UserOperatorLogTypeEnum[] ary = UserOperatorLogTypeEnum.values();
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

	public static String getName(int value) {
		String result = null;
		UserOperatorLogTypeEnum[] enumAry = UserOperatorLogTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (value == enumAry[i].getValue()) {
				result = enumAry[i].name();
				break;
			}
		}
		return result;
	}

	public static String getDesc(int value) {
		String result = null;
		UserOperatorLogTypeEnum[] enumAry = UserOperatorLogTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (value == enumAry[i].getValue()) {
				result = enumAry[i].getDesc();
				break;
			}
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		UserOperatorLogTypeEnum[] ary = UserOperatorLogTypeEnum.values();
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
