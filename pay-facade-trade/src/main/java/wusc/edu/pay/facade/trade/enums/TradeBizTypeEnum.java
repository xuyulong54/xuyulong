package wusc.edu.pay.facade.trade.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易业务类型
 * 
 * @author Administrator
 * 
 */
public enum TradeBizTypeEnum {

	/**
	 * 充值
	 */
	RECHARGE("充值", 1001),

	/**
	 * 在线收单
	 */
	ONLINE_ACQUIRING("在线收单", 1002),

	/**
	 * 移动收单
	 */
	MOBILE_ACQUIRING("移动收单", 1003),

	/**
	 * POS收单
	 */
	POS_ACQUIRING("POS收单", 1004),

	/**
	 * 外卡收单
	 */
	ICC_ACQUIRING("外卡收单", 1005),

	/**
	 * 会员转账
	 */
	ACCOUNT_TRANSFER("转账", 1006),
	/**
	 * 代扣
	 */
	ACCOUNT_AGENCYDEBIT("代扣", 1007);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	/** 构造函数 */
	private TradeBizTypeEnum(String desc, int value) {
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

	public static TradeBizTypeEnum getEnum(int value) {
		TradeBizTypeEnum resultEnum = null;
		TradeBizTypeEnum[] enumAry = TradeBizTypeEnum.values();
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
		TradeBizTypeEnum[] ary = TradeBizTypeEnum.values();
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
		TradeBizTypeEnum[] ary = TradeBizTypeEnum.values();
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
}
