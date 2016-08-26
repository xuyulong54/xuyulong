package wusc.edu.pay.web.permission.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  日志中操作类型
 * @desc  
 * @author shenjialong
 * @date  2014-2-14,上午10:29:50
 */
public enum OperatorLogTypeEnum {

	ADD("增加", 1),
	EDIT("修改", 2),
	DELETE("删除", 3),
	QUERYA("查询", 4),
	LOGIN("登录/退出", 5);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private OperatorLogTypeEnum(String desc, int value) {
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
	
	public static OperatorLogTypeEnum getEnum(int value){
		OperatorLogTypeEnum resultEnum = null;
		OperatorLogTypeEnum[] enumAry = OperatorLogTypeEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue() == value){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		OperatorLogTypeEnum[] ary = OperatorLogTypeEnum.values();
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
	public static List toList(){
		OperatorLogTypeEnum[] ary = OperatorLogTypeEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

}
