package wusc.edu.pay.facade.fee.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计费项枚举
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-26,下午3:45:36
 */
public enum CalculateFeeItemEnum {
	/**
	 * 在线收单
	 */
	ONLINE_ACQUIRING("在线收单", 1),
	/**
	 * 快捷支付收单
	 */
	FP_ACQUIRING("快捷支付收单", 2),
	/**
	 * EPOS收单
	 */
	EPOS_ACQUIRING("EPOS收单", 3),
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
	REFUND_ACQUIRING("退款", 12),

	/**
	 * 现金
	 */
	CASH_ACQUIRING("现金", 13),
	
	/**
	 * 结算T+0
	 */
	SETTLEMENT_ACQUIRING_T_0("T+0结算", 14),
	/**
	 * 结算T+1
	 */
	SETTLEMENT_ACQUIRING_T_1("T+1结算", 15),
	/**
	 * 结算T+5
	 */
	SETTLEMENT_ACQUIRING_T_5("T+5结算", 16);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private CalculateFeeItemEnum(String desc, int value) {
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

	public static CalculateFeeItemEnum getEnum(int value) {
		CalculateFeeItemEnum resultEnum = null;
		CalculateFeeItemEnum[] enumAry = CalculateFeeItemEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		CalculateFeeItemEnum[] ary = CalculateFeeItemEnum.values();
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
		CalculateFeeItemEnum[] ary = CalculateFeeItemEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	/**
	 * 提现、结算、转账、现金、代扣
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForSpecial() {
		int[] ary = { CalculateFeeItemEnum.ATM_ACQUIRING.getValue(), CalculateFeeItemEnum.SETTLEMENT_ACQUIRING.getValue(),CalculateFeeItemEnum.SETTLEMENT_ACQUIRING_T_0.getValue(),CalculateFeeItemEnum.SETTLEMENT_ACQUIRING_T_1.getValue(),CalculateFeeItemEnum.SETTLEMENT_ACQUIRING_T_5.getValue(),
				CalculateFeeItemEnum.TRANSFER_ACQUIRING.getValue() ,CalculateFeeItemEnum.CASH_ACQUIRING.getValue() ,CalculateFeeItemEnum.DEBIT_ACQUIRING.getValue()};
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i]));
			map.put("desc", CalculateFeeItemEnum.getEnum(ary[i]).getDesc());
			list.add(map);
		}
		return list;
	}

	/**
	 * 收单、充值
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForPay() {
		int[] ary = {CalculateFeeItemEnum.EPOS_ACQUIRING.getValue(),
				CalculateFeeItemEnum.FP_ACQUIRING.getValue(), CalculateFeeItemEnum.ICC_ACQUIRING.getValue(),
				CalculateFeeItemEnum.ONLINE_ACQUIRING.getValue(), CalculateFeeItemEnum.POS_ACQUIRING.getValue(),
				CalculateFeeItemEnum.VC_ACQUIRING.getValue(), CalculateFeeItemEnum.RECHARGE_ACQUIRING.getValue() };
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i]));
			map.put("desc", CalculateFeeItemEnum.getEnum(ary[i]).getDesc());
			list.add(map);
		}
		return list;
	}

}
