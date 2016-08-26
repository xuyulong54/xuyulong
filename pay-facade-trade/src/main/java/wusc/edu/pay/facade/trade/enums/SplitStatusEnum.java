package wusc.edu.pay.facade.trade.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分账状态
 * 
 * @author Administrator
 * 
 */
public enum SplitStatusEnum {

	/**
	 * 分账成功
	 */
	SUCCESS("分账成功", 100),

	/**
	 * 未分账
	 */
	CREATED("未分账", 102);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	/** 构造函数 */
	private SplitStatusEnum(String desc, int value) {
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

	public static SplitStatusEnum getEnum(int value) {
		SplitStatusEnum resultEnum = null;
		SplitStatusEnum[] enumAry = SplitStatusEnum.values();
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
		SplitStatusEnum[] ary = SplitStatusEnum.values();
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
