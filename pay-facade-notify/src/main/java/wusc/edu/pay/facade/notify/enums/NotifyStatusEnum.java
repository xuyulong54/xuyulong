package wusc.edu.pay.facade.notify.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 商户通知状态 .
 * 适用于通知记录表.
 * 
 * @作者: WuShuicheng .
 * @创建时间: 2013-9-12,上午10:34:40 .
 * @版本: 1.0 .
 */
public enum NotifyStatusEnum {
	
	SUCCESS("通知成功", 100),
	FAILED("通知失败", 101),
	CREATED("通知记录已创建", 102),
	HTTP_REQUEST_SUCCESS("http请求响应成功", 200), 
	HTTP_REQUEST_FALIED("http请求失败", 201);
	
	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value; 
	/** 构造函数 */
	private NotifyStatusEnum(String desc,int value) {
		this.desc = desc;
		this.value = value;
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

	public static NotifyStatusEnum getEnum(int value){
		NotifyStatusEnum resultEnum = null;
		NotifyStatusEnum[] enumAry = NotifyStatusEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue() == value){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		NotifyStatusEnum[] ary = NotifyStatusEnum.values();
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
	
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	public static List toList(){
		NotifyStatusEnum[] ary = NotifyStatusEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 取枚举的json字符串
	 * @return
	 */
	public static String getJsonStr(){
		NotifyStatusEnum[] enums = NotifyStatusEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (NotifyStatusEnum senum : enums) {
			if(!"[".equals(jsonStr.toString())){
				jsonStr.append(",");
			}
			jsonStr.append("{id:'")
					.append(senum.toString())
					.append("',desc:'")
					.append(senum.getDesc())
					.append("',value:'")
					.append(senum.getValue())
					.append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}

}
