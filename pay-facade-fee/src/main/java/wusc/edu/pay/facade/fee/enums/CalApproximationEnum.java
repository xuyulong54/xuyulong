package wusc.edu.pay.facade.fee.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * @ClassName: CalApproximationEnum 
 * @Description: 计费 近似值枚举
 * @author Huang Bin 
 * @date 2015-3-27 上午11:07:18
 */
public enum CalApproximationEnum {
	
	/**
	 * 无
	 */
	NONE("无", 103),
	
	/**
	 * 舍尾法
	 */
	LAST_ROUND("舍尾法", 102),
	
	/***
	 * 进一法
	 */
	INTO_LAW("进一法", 101),
	
	/***
	 * 四舍五入法
	 */
	ROUNDING_METHOD("四舍五入法", 100);
	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;
	
	
	private CalApproximationEnum(String desc, int value) {
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

	public static CalApproximationEnum getEnum(int value) {
		CalApproximationEnum resultEnum = null;
		CalApproximationEnum[] enumAry = CalApproximationEnum.values();
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
		CalApproximationEnum[] ary = CalApproximationEnum.values();
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
		CalApproximationEnum[] ary = CalApproximationEnum.values();
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
