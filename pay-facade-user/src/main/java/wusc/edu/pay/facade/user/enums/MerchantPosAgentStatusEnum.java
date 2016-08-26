package wusc.edu.pay.facade.user.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * POS代理商商户状态枚举
 * 
 * @author huangbin
 * 
 */
public enum MerchantPosAgentStatusEnum {

	ACTIVE("激活", 100), INACTIVE("冻结", 101), PASSONE("二级代理审核通过", 102), NOPASSONE("二级代理审核不通过", 103), PASSTWO(
			"一级代理审核通过", 104), NOPASSTWO("一级代理审核不通过", 105), NOPASSTHREE("运营商审核不通过", 107), CREATED(
			"已创建", 108), CLOSE("已注销", 109), SIGNING("注册中", 110);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private MerchantPosAgentStatusEnum(String desc, int value) {
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

	public static MerchantPosAgentStatusEnum getEnum(int value) {
		MerchantPosAgentStatusEnum resultEnum = null;
		MerchantPosAgentStatusEnum[] enumAry = MerchantPosAgentStatusEnum.values();
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
		MerchantPosAgentStatusEnum[] ary = MerchantPosAgentStatusEnum.values();
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
		MerchantPosAgentStatusEnum[] ary = MerchantPosAgentStatusEnum.values();
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

	/**
	 * 初审使用
	 */
	public static List<Map<String, String>> toOneVerifyList() {
		MerchantPosAgentStatusEnum[] ary = MerchantPosAgentStatusEnum.values();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			int tempIntValue = ary[i].getValue();
			if (tempIntValue != 102 && tempIntValue != 103) {
				continue;
			}
			map.put("value", String.valueOf(tempIntValue));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	/**
	 * 二审使用
	 */
	public static List<Map<String, String>> toTwoVerifyList() {
		MerchantPosAgentStatusEnum[] ary = MerchantPosAgentStatusEnum.values();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			int tempIntValue = ary[i].getValue();
			if (tempIntValue != 104 && tempIntValue != 105) {
				continue;
			}
			map.put("value", String.valueOf(tempIntValue));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
