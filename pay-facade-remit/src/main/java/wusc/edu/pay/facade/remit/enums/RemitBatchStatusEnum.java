package wusc.edu.pay.facade.remit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Title: 打款批次状态枚举 
 * @Description: 
 * @author zzh
 * @date 2014-7-22 下午2:05:10
 */
public enum RemitBatchStatusEnum {
	/**
	 * 待处理
	 */
	SUSPENDING("待处理", 1),
	
	/**
	 * 处理中
	 */
	HANDLING("处理中", 2),
	
	/**
	 * 处理完成
	 */
	SOLVED("处理完成", 3);

	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;
	private RemitBatchStatusEnum(String desc, int value) {
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

	public static RemitBatchStatusEnum getEnum(int value) {
		RemitBatchStatusEnum resultEnum = null;
		RemitBatchStatusEnum[] enumAry = RemitBatchStatusEnum.values();
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
		RemitBatchStatusEnum[] ary = RemitBatchStatusEnum.values();
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
		RemitBatchStatusEnum[] ary = RemitBatchStatusEnum.values();
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
