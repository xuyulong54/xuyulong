package wusc.edu.pay.facade.cost.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 计费接口状态.
 * @作者: WuShuicheng.
 * @创建: 2014-7-9,下午2:55:24
 * @版本: V1.0
 *
 */
public enum CostInterfaceStatusEnum {
	
	/** 开启（生效）  **/
	OPEN("开启", 100),
	
	/** 关闭（失效）**/
	CLOSE("关闭", 101);
	
	/** 描述 */
	private String desc;
	
	/** 枚举值 */
	private int value;
	
	private CostInterfaceStatusEnum(String desc, int value) {
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

	public static CostInterfaceStatusEnum getEnum(int value) {
		CostInterfaceStatusEnum resultEnum = null;
		CostInterfaceStatusEnum[] enumAry = CostInterfaceStatusEnum.values();
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
		CostInterfaceStatusEnum[] ary = CostInterfaceStatusEnum.values();
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
		CostInterfaceStatusEnum[] ary = CostInterfaceStatusEnum.values();
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
