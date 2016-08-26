package wusc.edu.pay.facade.user.enums;

/**
 * 用户交易类型，用来区分用户密钥表中是那种交易的签名
 * @author： Peter
 * @ClassName: MerchantTradeTypeEnum.java
 * @Date： 2015-3-9 下午2:17:12 
 * @version：  V1.0
 */
public enum MerchantTradeTypeEnum {

	MOBILE_TRADE("移动支付",1) , ONLINE_TRADE("在线交易",2);
	
	private String desc;
	private int value;
	
	private MerchantTradeTypeEnum(String desc , int value){
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
