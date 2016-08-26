package wusc.edu.pay.common.enums;

/**
 * 银行个人信息鉴权、签约结果枚举类
 * @author： Peter
 * @ClassName: AuthenticationResultEnum.java
 * @Date： 2014-11-6 上午11:00:55 
 * @version：  V1.0
 */
public enum BankAuthenticationResultEnum {
	
	SIGN_SUCCESS("签约成功",100),
	
	SIGN_FAIL("签约失败",101),
	
	AUTHENTICATION_UNDO("未鉴权",102) ,
	
	AUTHENTICATION_SUCCESS("鉴权成功",103) , 
	
	AUTHENTICATION_FAIL("鉴权失败",104),
	
	SIGN_ACCESS("签约请求已接受",105),
	
	AUTHENTICATION_ACCESS("鉴权请求已接受",106);

	
	/**描述*/
	private String desc;
	
	/**枚举值*/
	private int value;
	
	private BankAuthenticationResultEnum (String desc , int value){
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
