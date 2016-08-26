/**
 * wusc.edu.pay.facade.limit.enums.LimitTypeEnum.java
 */
package wusc.edu.pay.facade.limit.enums;

/**
 * 
 * 
 * <ul>
 * <li>Title: 限制类型枚举</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-4
 */
public enum LimitTypeEnum {

	/** 单笔 */
	SINGLE("SINGLE", "单笔"),

	/** 日累计 */
	CUMULATE_DAILY("CUMULATE_DAILY", "日累计"),

	/** 月累计 */
	CUMULATE_PER_MONTH("CUMULATE_PER_MONTH", "月累计");

	/**
	 * 存储值
	 */
	private String value;

	/**
	 * 显示值
	 */
	private String label;

	private LimitTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
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

}
