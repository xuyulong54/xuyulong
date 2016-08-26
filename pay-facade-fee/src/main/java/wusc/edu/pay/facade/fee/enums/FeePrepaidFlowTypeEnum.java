package wusc.edu.pay.facade.fee.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 费率包量类型
 * @desc  
 * @author shenjialong
 * @date  2014-7-1,下午2:37:02
 */
public enum FeePrepaidFlowTypeEnum {
	/**
	 * 包量
	 */
	AMOUNT("包量",1),
	/**
	 * 包笔
	 */
	QUANTITY("包笔",2);
	
	
	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private FeePrepaidFlowTypeEnum(String desc, int value) {
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

	public static FeePrepaidFlowTypeEnum getEnum(int value) {
		FeePrepaidFlowTypeEnum resultEnum = null;
		FeePrepaidFlowTypeEnum[] enumAry = FeePrepaidFlowTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		FeePrepaidFlowTypeEnum[] ary = FeePrepaidFlowTypeEnum.values();
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
		FeePrepaidFlowTypeEnum[] ary = FeePrepaidFlowTypeEnum.values();
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
		FeePrepaidFlowTypeEnum[] enums = FeePrepaidFlowTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (FeePrepaidFlowTypeEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'").append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}

}
