package wusc.edu.pay.facade.remit.enums;

/**
 * 打款撤销原因枚举
 * @author： Peter
 * @ClassName: RemitCancelReasonEnum.java
 * @Date： 2014-7-25 下午2:19:10 
 * @version：  V1.0
 */
public enum RemitCancelReasonEnum {
	
	/**
	 * 账户被冻结
	 * */
	ACCOUNT_STATUS_INACTIVE("账户被冻结"),
	/**
	 * 账户余额不足
	 * */
	ACCOUNT_INSUFFICIENT_BALANCE("账户余额不足");
	
	private String desc;
	
	private RemitCancelReasonEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
