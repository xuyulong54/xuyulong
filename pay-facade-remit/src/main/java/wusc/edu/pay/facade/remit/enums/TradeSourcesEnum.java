package wusc.edu.pay.facade.remit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务来源
 * @author： Peter
 * @ClassName: TradeSources.java
 * @Date： 2014-7-25 下午2:00:33 
 * @version：  V1.0
 */
public enum TradeSourcesEnum {
	/**
	 * 会员提现
	 */
	MEMBER_CASH(1, "会员提现"),

	/**
	 * 商户结算
	 */
	MERCHANT_SETTLEMENT(2,"商户结算"),
	
	/**
	 * boss运营系统
	 */
	BOSS_SYSTEM(3,"boss运营系统");
	
	private int value;
	private String desc;
	
	private TradeSourcesEnum(int value , String desc){
		this.value = value;
		this.desc = desc;
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
	
	public static TradeSourcesEnum getEnum(int value) {
		TradeSourcesEnum resultEnum = null;
		TradeSourcesEnum[] enumAry = TradeSourcesEnum.values();
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
		TradeSourcesEnum[] ary = TradeSourcesEnum.values();
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
		TradeSourcesEnum[] ary = TradeSourcesEnum.values();
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
