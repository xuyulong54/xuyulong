package wusc.edu.pay.common.enums; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *类描述：操作日志操作
 *@author: huangbin
 *@date： 日期：2013-12-30 时间：上午11:43:32
 *@version 1.0
 */
public enum ManageLogOperStatusEnum {
	
	OPER_ADD("新增", 1),
	OPER_UPDATE("修改", 2),
	OPER_DELETE("删除", 3);
	
	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private ManageLogOperStatusEnum(String desc, int value) {
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

	public static ManageLogOperStatusEnum getEnum(int value) {
		ManageLogOperStatusEnum resultEnum = null;
		ManageLogOperStatusEnum[] enumAry = ManageLogOperStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		ManageLogOperStatusEnum[] ary = ManageLogOperStatusEnum.values();
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
		ManageLogOperStatusEnum[] ary = ManageLogOperStatusEnum.values();
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
	 * 取枚举的json字符串
	 * 
	 * @return
	 */
	public static String getJsonStr() {
		ManageLogOperStatusEnum[] enums = ManageLogOperStatusEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (ManageLogOperStatusEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum.toString()).append("',desc:'").append(senum.getDesc()).append("',value:'").append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
	
}
 