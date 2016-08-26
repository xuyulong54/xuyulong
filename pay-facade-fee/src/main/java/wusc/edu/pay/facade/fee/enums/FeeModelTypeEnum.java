package wusc.edu.pay.facade.fee.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @desc 费率模型类型枚举
 * @author shenjialong
 * @date 2014-6-27,下午4:07:58
 */
public enum FeeModelTypeEnum {
	/**
	 * 私有节点
	 */
	PRIVATE_NODE("私有节点", 1),
	/**
	 * 公共节点
	 */
	PUBLIC_NODE("公共节点", 2),
	/**
	 * 模板节点
	 */
	TEMPLATE_NODE("模板节点", 3);

	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private FeeModelTypeEnum(String desc, int value) {
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

	public static FeeModelTypeEnum getEnum(int value) {
		FeeModelTypeEnum resultEnum = null;
		FeeModelTypeEnum[] enumAry = FeeModelTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		FeeModelTypeEnum[] ary = FeeModelTypeEnum.values();
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
		FeeModelTypeEnum[] ary = FeeModelTypeEnum.values();
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
		FeeModelTypeEnum[] enums = FeeModelTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (FeeModelTypeEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'").append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}

}
