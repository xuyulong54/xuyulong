package wusc.edu.pay.facade.cost.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 成本账单周期 .
 * @作者: WuShuicheng.
 * @创建: 2014-7-9,下午2:55:24
 * @版本: V1.0
 *
 */
public enum CostBillCycleEnum {
	
	/** 周结算  **/
	WEEK("周结算", 1),
	
	/** 月结 **/
	MONTH("月结算", 2),
	
	/** 季度结 **/
	QUARTER("季度结算", 3),
	
	/** 年结 **/
	YEAR("年结算", 4),
	
	/** 自定义 **/
	CUSTOM("自定义", 5);
	
	/** 描述 */
	private String desc;
	
	/** 枚举值 */
	private int value;
	
	private CostBillCycleEnum(String desc, int value) {
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

	public static CostBillCycleEnum getEnum(int value) {
		CostBillCycleEnum resultEnum = null;
		CostBillCycleEnum[] enumAry = CostBillCycleEnum.values();
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
		CostBillCycleEnum[] ary = CostBillCycleEnum.values();
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
		CostBillCycleEnum[] ary = CostBillCycleEnum.values();
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
