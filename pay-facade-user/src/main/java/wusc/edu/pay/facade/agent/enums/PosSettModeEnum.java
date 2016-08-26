package wusc.edu.pay.facade.agent.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * @ClassName: PosSettModeEnum
 * @Description: 结算方式枚举 - POS交易用到
 * @author huangbin
 * @date 2015-3-3 下午1:52:14
 *
 */
public enum PosSettModeEnum {
	
	T_0("T+0", 1),
	T_1("T+1", 2),
	T_5("T+5", 3);
	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private PosSettModeEnum(String desc, int value) {
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

	public static PosSettModeEnum getEnum(int value) {
		PosSettModeEnum resultEnum = null;
		PosSettModeEnum[] enumAry = PosSettModeEnum.values();
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
		PosSettModeEnum[] ary = PosSettModeEnum.values();
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
		PosSettModeEnum[] ary = PosSettModeEnum.values();
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
