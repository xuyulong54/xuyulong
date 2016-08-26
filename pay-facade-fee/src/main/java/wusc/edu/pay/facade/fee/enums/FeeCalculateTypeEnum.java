package wusc.edu.pay.facade.fee.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 费率计费方式
 * 
 * @author zws
 * 
 */
public enum FeeCalculateTypeEnum {
	/**
	 * 单笔
	 */
	SINGLE("单笔", 1),
	/**
	 * 区间
	 */
	RANGE("区间",2),
	/**
	 * 单阶梯（B阶梯--周期内处于不同区间的累加总量周期结束时使用最终所处区间费率、费额）
	 */
	LADDER_ACC_B("单阶梯", 3),
	/**
	 * 多阶梯（A阶梯--周期内处于不同区间的累加总量使用不同区间费率、费额）
	 */
	LADDER_ACC_A("多阶梯", 4);
	/**
	 * 预付费包量
	 *//*
	PREPAID_AMOUNT("预付费包量", 7),
	*//**
	 * 预付费包笔
	 *//*
	PREPAID_QUANTITY("预付费包笔", 8);*/

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private FeeCalculateTypeEnum(String desc, int value) {
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

	public static FeeCalculateTypeEnum getEnum(int value) {
		FeeCalculateTypeEnum resultEnum = null;
		FeeCalculateTypeEnum[] enumAry = FeeCalculateTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		FeeCalculateTypeEnum[] ary = FeeCalculateTypeEnum.values();
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
		FeeCalculateTypeEnum[] ary = FeeCalculateTypeEnum.values();
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
	 * 判断是否为阶梯累加策略
	 * 
	 * @param enu
	 * @return
	 */
	public static boolean isLadderAcc(Integer enu) {
		if (enu == FeeCalculateTypeEnum.LADDER_ACC_A.getValue() || enu == FeeCalculateTypeEnum.LADDER_ACC_B.getValue()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否为包量
	 * 
	 * @param enu
	 * @return
	 */
	/*public static boolean isPrepaidFlow(Integer enu) {
		return enu == FeeCalculateTypeEnum.PREPAID_AMOUNT.getValue() || enu == FeeCalculateTypeEnum.PREPAID_QUANTITY.getValue();
	}*/

	/**
	 * 判断是否为B阶梯
	 * 
	 * @param enu
	 * @return
	 */
	public static boolean isLadderB(Integer enu) {
		if (enu == FeeCalculateTypeEnum.LADDER_ACC_B.getValue()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 确认是否需要进行流量累加
	 * 
	 * @param enu
	 * @return
	 */
/*	public static boolean isNeedConfirmingFlow(Integer enu) {
		if (enu == FeeCalculateTypeEnum.LADDER_ACC_A.getValue()) {
			return true;
		} else if (enu == FeeCalculateTypeEnum.PREPAID_AMOUNT.getValue() || enu == FeeCalculateTypeEnum.PREPAID_QUANTITY.getValue()) {
			return true;
		} else {
			return false;
		}
	}*/
}
