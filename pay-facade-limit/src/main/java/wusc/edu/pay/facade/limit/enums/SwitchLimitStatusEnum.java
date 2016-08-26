/**
 * wusc.edu.pay.facade.limit.enums.SwitchLimitStatusEnum.java
 */
package wusc.edu.pay.facade.limit.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * <ul>
 * <li>Title: 开关限制状态枚举</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
public enum SwitchLimitStatusEnum {

	/** 开启 */
	ON("ON", "开启"),

	/** 关闭 */
	OFF("OFF", "关闭");

	/**
	 * 存储值
	 */
	private String value;
	/**
	 * 显示值
	 */
	private String label;

	private SwitchLimitStatusEnum(String value, String label) {
		this.label = label;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		SwitchLimitStatusEnum[] ary = SwitchLimitStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("label", ary[i].getLabel());
			list.add(map);
		}
		return list;
	}

}
