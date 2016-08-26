package wusc.edu.pay.facade.pms.enums;

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
public enum PmsRoleTypeEnum {
	
	/** 超级管理员角色 **/
	SUPER_ADMIN("超级管理员角色", "1"),
	
	/** 普通管理员角色 **/
	ADMIN("普通管理员角色", "2"),
	
	/** 默认操作员角色 **/
	DEFAULT_USER("默认操作员角色", "3"),

	/** 普通操作员角色 **/
	USER("普通操作员角色", "4");

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private String value;

	private PmsRoleTypeEnum(String desc, String value) {
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
	
	public static PmsRoleTypeEnum getEnum(String value){
		PmsRoleTypeEnum resultEnum = null;
		PmsRoleTypeEnum[] enumAry = PmsRoleTypeEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue().equals(value)){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		PmsRoleTypeEnum[] ary = PmsRoleTypeEnum.values();
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
		PmsRoleTypeEnum[] ary = PmsRoleTypeEnum.values();
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
