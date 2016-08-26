package wusc.edu.pay.facade.trade.enums;

/**
 * 序列类型
 * 
 * @author Administrator
 * 
 */
public enum SeqTypeEnum {

	/**
	 * PAYMENT_ORDER_NO
	 */
	PAYMENT_ORDER_NO("PAYMENT_ORDER_NO", 1001),

	/**
	 * PAYMENT_TRX_NO
	 */
	PAYMENT_TRX_NO("PAYMENT_TRX_NO", 1002),

	/**
	 * TRANSFER_TRX_NO
	 */
	TRANSFER_TRX_NO("TRANSFER_TRX_NO", 1003),

	/**
	 * RECHARGE_ORDER_NO
	 */
	RECHARGE_ORDER_NO("RECHARGE_ORDER_NO", 1004),

	/**
	 * REFUND_ORDER_NO
	 */
	REFUND_ORDER_NO("REFUND_ORDER_NO", 1005),

	/**
	 * REFUND_TRX_NO
	 */
	REFUND_TRX_NO("REFUND_TRX_NO", 1006),

	/**
	 * BANK_ORDER_NO
	 */
	BANK_ORDER_NO("BANK_ORDER_NO", 1007);

	/* 描述 */
	private String desc;
	/* 枚举值 */
	private int value;

	private SeqTypeEnum(String desc, int value) {
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
