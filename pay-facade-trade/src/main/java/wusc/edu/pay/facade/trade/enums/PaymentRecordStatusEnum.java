package wusc.edu.pay.facade.trade.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 支付记录表状态.
 * 适用于表：
 * TBL_PAY_PAYMENTRECORD，
 * TBL_PAY_PAYMENTRECORDSTAT .
 * 
 * @作者: WuShuicheng .
 * @创建时间: 2013-9-12,上午10:51:55 .
 * @版本: 1.0 .
 */
public enum PaymentRecordStatusEnum {
	
	/**
	 * 交易成功
	 */
	SUCCESS("交易成功", 100),
	/**
	 * 交易失败
	 */
	FAILED("交易失败", 101),
	/**
	 * 订单已创建
	 */
	CREATED("订单已创建", 102);
	
	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value; 
	/** 构造函数 */
	private PaymentRecordStatusEnum(String desc,int value) {
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

	public static PaymentRecordStatusEnum getEnum(int value){
		PaymentRecordStatusEnum resultEnum = null;
		PaymentRecordStatusEnum[] enumAry = PaymentRecordStatusEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue() == value){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		PaymentRecordStatusEnum[] ary = PaymentRecordStatusEnum.values();
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
		PaymentRecordStatusEnum[] ary = PaymentRecordStatusEnum.values();
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
