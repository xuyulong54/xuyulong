package wusc.edu.pay.facade.remit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: RemitExportBatchStatus.java 
 * @Description: 打款导出批次
 * @author zzh
 * @date 2014-10-15 上午10:03:56
 */
public enum RemitExportBatchStatusEnum {
	
	/**
	 * 未确认
	 */
	NOCONFIRM("未确认", 1),
	
	/**
	 * 打款成功
	 */
	SUCCESS("打款成功", 2),
	
	/**
	 * 打款失败
	 */
	FAIL("打款失败", 3);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;
	private RemitExportBatchStatusEnum(String desc, int value) {
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

	public static RemitExportBatchStatusEnum getEnum(int value) {
		RemitExportBatchStatusEnum resultEnum = null;
		RemitExportBatchStatusEnum[] enumAry = RemitExportBatchStatusEnum.values();
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
		RemitExportBatchStatusEnum[] ary = RemitExportBatchStatusEnum.values();
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
		RemitExportBatchStatusEnum[] ary = RemitExportBatchStatusEnum.values();
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
