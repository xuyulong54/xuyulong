package wusc.edu.pay.facade.trade.enums;

/**
 * 
 * @描述: 订单加密方式枚举.
 * @作者: WuShuicheng.
 * @创建: 2014-7-11,下午2:00:12
 * @版本: V1.0
 * 
 */
public enum OrderEncryptTypeEnum {

	/**
	 * HMAC
	 */
	HMAC("HMAC", 1),
	/**
	 * CERT
	 */
	CERT("CERT", 2);

	/* 描述 */
	private String desc;
	/* 枚举值 */
	private int value;

	private OrderEncryptTypeEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
