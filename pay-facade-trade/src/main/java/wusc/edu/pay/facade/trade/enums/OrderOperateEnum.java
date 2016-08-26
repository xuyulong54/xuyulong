package wusc.edu.pay.facade.trade.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: OrderOperateEnum <br/>
 * Function: 订单特殊操作 枚举 <br/>
 * date: 2014-7-11 下午12:07:41 <br/>
 * 
 * @author laich
 */
public enum OrderOperateEnum {

	/**
	 * 正常
	 */
	NORMAL("正常",100), 
	
	/**
	 * 已回收
	 */
	RECYCLE("已回收",101);
	
	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;
	
	private OrderOperateEnum(String desc, int value) {
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
	public static OrderOperateEnum getEnum(int value) {
		OrderOperateEnum resultEnum = null;
		OrderOperateEnum[] enumAry = OrderOperateEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	public static Map<String, Map<String, Object>> toMap() {
		OrderOperateEnum[] ary = OrderOperateEnum.values();
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
