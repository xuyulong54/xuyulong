package wusc.edu.pay.facade.cost.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成本,计费项
 */
public enum CostItemEnum {

	/**
	 * 在线收单
	 */
	ONLINE_ACQUIRING("在线收单", 1),

	/**
	 * 快捷支付收单
	 */
	FP_ACQUIRING("快捷支付收单", 2),

	/**
	 * 代扣收单
	 */
	DEBIT_ACQUIRING("代扣收单", 4),

	/**
	 * 非银行卡收单
	 */
	VC_ACQUIRING("非银行卡收单", 5),

	/**
	 * 外卡收单
	 */
	ICC_ACQUIRING("外卡收单", 6),

	/**
	 * POS收单
	 */
	POS_ACQUIRING("POS收单", 7),

	/**
	 * 结算
	 */
	SETTLEMENT_ACQUIRING("结算", 8),

	/**
	 * 充值
	 */
	RECHARGE_ACQUIRING("充值", 9),

	/**
	 * 转账
	 */
	TRANSFER_ACQUIRING("转账", 10),

	/**
	 * 提现
	 */
	ATM_ACQUIRING("提现", 11),

	/**
	 * 退款
	 */
	REFUND("退款", 12);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private CostItemEnum(String desc, int value) {
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

	public static CostItemEnum getEnum(int value) {
		CostItemEnum resultEnum = null;
		CostItemEnum[] enumAry = CostItemEnum.values();
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
		CostItemEnum[] ary = CostItemEnum.values();
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
		CostItemEnum[] ary = CostItemEnum.values();
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
