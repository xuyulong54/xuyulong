package wusc.edu.pay.common.enums; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *类描述：投诉建议状态 枚举类/业务对账处理状态
 *@author: huangbin
 *@date： 日期：2013-10-16 时间：下午3:50:41
 *@todo: TODO
 *@version 1.0
 */
public enum ReconDealStatusEnum {
	
	PROCESSED("已处理", 100),
	UNTREATED("未处理", 101),
	IGNORED("无需处理", 102);
	
	
	 /** 描述  */
	 private String desc;
	 /** 枚举值 */
	 private int value;
	 
	 private ReconDealStatusEnum(String desc , int value){
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
	
	public static ReconDealStatusEnum getEnum(int value) {
		ReconDealStatusEnum resultEnum = null;
		ReconDealStatusEnum[] enumAry = ReconDealStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		ReconDealStatusEnum[] ary = ReconDealStatusEnum.values();
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
		ReconDealStatusEnum[] ary = ReconDealStatusEnum.values();
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
 