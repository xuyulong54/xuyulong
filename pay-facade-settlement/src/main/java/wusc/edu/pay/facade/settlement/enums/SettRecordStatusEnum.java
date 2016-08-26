package wusc.edu.pay.facade.settlement.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算记录，状态
 * 
 * @author huqian
 * @date 2014-01-07
 * @version 1.0
 */
public enum SettRecordStatusEnum {

	/**
	 * 等待确认
	 */
	WAIT_CONFIRM("等待审核", 200),

	/**
	 * 已确认
	 */
	CONFIRMED("已确认", 201),

	/**
	 * 已扣款
	 */
	WITHHOLDING("已扣款", 202),

	/**
	 * 打款中
	 */
	REMITTING("打款中", 203),

	/**
	 * 打款成功
	 */
	REMIT_SUCCESS("打款成功", 204),

	/**
	 * 打款失败
	 */
	REMIT_FAIL("打款失败", 205),

	/**
	 * 人工取消
	 */
	CANCEL("审核不通过", 206),

	/**
	 * 失败退回
	 */
	FAIL_RETURN("失败退回", 207);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private SettRecordStatusEnum(String desc, int value) {
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

	public static SettRecordStatusEnum getEnum(int value) {
		SettRecordStatusEnum resultEnum = null;
		SettRecordStatusEnum[] enumAry = SettRecordStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		SettRecordStatusEnum[] ary = SettRecordStatusEnum.values();
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
		SettRecordStatusEnum[] ary = SettRecordStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
