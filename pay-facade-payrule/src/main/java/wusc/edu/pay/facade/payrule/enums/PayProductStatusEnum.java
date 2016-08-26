package wusc.edu.pay.facade.payrule.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: PayProductStatusEnum 支付产品状态枚举<br/>
 * Function:  <br/>
 * date: 2014-6-27 下午10:31:45 <br/>
 * 
 * @author laich
 */
public enum PayProductStatusEnum {

	/** 正常状态 */
	ACTIVITY("打开",100),

	/** 非活跃状态 */
	INACTIVITY("关闭",101);

	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private PayProductStatusEnum(String desc, int value) {
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

	public static PayProductStatusEnum getEnum(int value) {
		PayProductStatusEnum resultEnum = null;
		PayProductStatusEnum[] enumAry = PayProductStatusEnum.values();
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
		PayProductStatusEnum[] ary = PayProductStatusEnum.values();
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
		PayProductStatusEnum[] ary = PayProductStatusEnum.values();
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
