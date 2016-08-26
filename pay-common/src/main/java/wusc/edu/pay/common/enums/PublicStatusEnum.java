package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易类型枚举. 适用于所有表.
 * 
 * @author Hill
 */
public enum PublicStatusEnum {
	
	/** ACTIVE:100 **/
	ACTIVE("激活,有效,开通,有权,已完成,已结算,已打款", 100), 
	/** INACTIVE:101 **/
	INACTIVE("冻结,无效,未开通,无权,未完成,未结算,未打款", 101);

	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private PublicStatusEnum(String desc, int value) {
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

	public static PublicStatusEnum getEnum(int value) {
		PublicStatusEnum resultEnum = null;
		PublicStatusEnum[] enumAry = PublicStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		PublicStatusEnum[] ary = PublicStatusEnum.values();
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
		PublicStatusEnum[] ary = PublicStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toCalStatusList() {
		PublicStatusEnum[] ary = PublicStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			if(ary[i].getValue() == PublicStatusEnum.ACTIVE.value){
				map.put("desc", "计费成功");
			}
			if(ary[i].getValue() == PublicStatusEnum.INACTIVE.value){
				map.put("desc", "计费失败");
			}
			list.add(map);
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toCalPayWayList() {
		PublicStatusEnum[] ary = PublicStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			if(ary[i].getValue() == PublicStatusEnum.ACTIVE.value){
				map.put("desc", "有效");
			}
			if(ary[i].getValue() == PublicStatusEnum.INACTIVE.value){
				map.put("desc", "无效");
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * @Title: 100-是，101-否 
	 * @Description: 
	 * @param @return    
	 * @return List  
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toIsOrNotList() {
		PublicStatusEnum[] ary = PublicStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			if(ary[i].getValue() == PublicStatusEnum.ACTIVE.value){
				map.put("desc", "是");
			}
			if(ary[i].getValue() == PublicStatusEnum.INACTIVE.value){
				map.put("desc", "否");
			}
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
		PublicStatusEnum[] enums = PublicStatusEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (PublicStatusEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum.toString()).append("',desc:'").append(senum.getDesc()).append("',value:'").append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
}
