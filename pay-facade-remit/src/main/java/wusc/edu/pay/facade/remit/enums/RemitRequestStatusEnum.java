package wusc.edu.pay.facade.remit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Title: 打款请求状态枚举
 * @Description: TODO
 * @author zzh
 * @date 2014-7-22 下午2:19:15
 */
public enum RemitRequestStatusEnum {
	/**
	 * 待审核
	 */
	UNAUDIT("待审核", 1),
	
	/**
	 * 已审核
	 */
	AUDITED("已审核", 2),
	
	/**
	 * 处理中
	 */
	REMITING("处理中", 3),
	
	/**
	 * 打款成功
	 */
	REMIT_SUCCESS("打款成功", 4),
	
	/**
	 * 打款失败
	 */
	REMIT_FAILURE("打款失败", 5),
	
	/**
	 * 已撤销
	 */
	CANCEL("已撤销", 6),
	
	/**
	 * 审核未通过
	 */
	UNAPPROVE("审核未通过",7),
	
	/**
	 * 已重新制单
	 */
	REREQUEST("已重新制单",8),
	
	/**
	 * 银行打款中
	 */
	REMIT_FINISH("银行打款中", 9),
	
	/**
	 * 网银打款待处理
	 */
	ONLINE_BANK_WAIT("网银打款待处理",10);
	
	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;
	private RemitRequestStatusEnum(String desc, int value) {
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

	public static RemitRequestStatusEnum getEnum(int value) {
		RemitRequestStatusEnum resultEnum = null;
		RemitRequestStatusEnum[] enumAry = RemitRequestStatusEnum.values();
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
		RemitRequestStatusEnum[] ary = RemitRequestStatusEnum.values();
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
		RemitRequestStatusEnum[] ary = RemitRequestStatusEnum.values();
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
