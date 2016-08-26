package wusc.edu.pay.web.permission.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 角色类型.
 * @作者: WuShuicheng.
 * @创建: 2014-8-18,下午4:16:42
 * @版本: V1.0
 *
 */
public enum RoleTypeEnum {

	/** 普通用户角色 **/
	USER("普通用户角色", "0"),
	
	/** 超级管理员角色 **/
	ADMIN("超级管理员角色", "1");

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private String value;

	private RoleTypeEnum(String desc, String value) {
		this.desc = desc;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static RoleTypeEnum getEnum(String value){
		RoleTypeEnum resultEnum = null;
		RoleTypeEnum[] enumAry = RoleTypeEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue().equals(value)){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		RoleTypeEnum[] ary = RoleTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", ary[num].getValue());
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		RoleTypeEnum[] ary = RoleTypeEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",ary[i].getValue());
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

}
