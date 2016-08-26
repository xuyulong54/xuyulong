package wusc.edu.pay.facade.trade.enums;

/**
 * 
 * @描述: 交易模式类型枚举.
 * @作者: WuShuicheng.
 * @创建: 2014-7-11,下午1:41:40
 * @版本: V1.0
 * 
 */
public enum TrxModelEnum {

	/**
	 * 担保类型
	 */
	GUARANTEE("担保类型", 1),

	/**
	 * 即时到帐
	 */
	IMMEDIATELY("即时到帐", 2),

	/**
	 * 分账
	 */
	SPLIT("分账", 3);

	/* 描述 */
	private String desc;
	/* 枚举值 */
	private int value;

	private TrxModelEnum(String desc, int value) {
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
