package wusc.edu.pay.facade.fee.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 阶梯周期类型
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-27,下午3:57:08
 */
public enum LadderCycleTypeEnum {
	/**
	 * 周
	 */
	WEEK("周", 1),
	/**
	 * 月
	 */
	MONTH("月", 2),
	/**
	 * 季度
	 */
	QUARTER("季度", 3),
	/**
	 * 年
	 */
	YEAR("年", 4),
	/**
	 * 自定义
	 */
	CUSTOMIZE("自定义", 5);

	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private LadderCycleTypeEnum(String desc, int value) {
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

	public static LadderCycleTypeEnum getEnum(int value) {
		LadderCycleTypeEnum resultEnum = null;
		LadderCycleTypeEnum[] enumAry = LadderCycleTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		LadderCycleTypeEnum[] ary = LadderCycleTypeEnum.values();
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
		LadderCycleTypeEnum[] ary = LadderCycleTypeEnum.values();
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
	 * 自定义
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toCustomList() {
		int[] valueArr = {LadderCycleTypeEnum.WEEK.getValue(),LadderCycleTypeEnum.MONTH.getValue()};
		List list = new ArrayList();
		for (int i = 0; i < valueArr.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(valueArr[i]));
			map.put("desc", LadderCycleTypeEnum.getEnum(valueArr[i]).getDesc());
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
		LadderCycleTypeEnum[] enums = LadderCycleTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (LadderCycleTypeEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'").append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
}
