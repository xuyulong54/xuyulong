package wusc.edu.pay.facade.agent.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 代理商系统
 * 商户，代理商 变更申请类型
 * @author Administrator
 *
 */
public enum AgentRequestTypeEnum {
	
	MERCHANT_BASE("商户基本资料变更", 1),
	MERCHANT_RATE("商户费率变更", 2),
	MERCHANT_SETT("商户结算信息变更", 3),
	MERCHANT_TERMINAL("商户增机变更", 4),
	AGENT_BASE("代理商基本资料变更", 5), 
	AGENT_SETT("代理商结算信息变更", 6);
	

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private AgentRequestTypeEnum(String desc, int value) {
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

	public static AgentRequestTypeEnum getEnum(int value) {
		AgentRequestTypeEnum resultEnum = null;
		AgentRequestTypeEnum[] enumAry = AgentRequestTypeEnum.values();
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
		AgentRequestTypeEnum[] ary = AgentRequestTypeEnum.values();
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
		AgentRequestTypeEnum[] ary = AgentRequestTypeEnum.values();
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
