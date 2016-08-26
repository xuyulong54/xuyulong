package wusc.edu.pay.common.param;

import java.io.Serializable;

/**
 * 
 * @描述: 短信参数实体.
 * @作者: WuShuicheng.
 * @创建: 2014-9-16,下午6:12:08
 * @版本: V1.0
 *
 */
public class SmsParam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6296401388735702975L;
	
	/**
	 * 接收短信的手机号码
	 */
	String phone;
	/**
	 * 短信内容
	 */
	String content;

	
	/**
	 * 默认构造函数.
	 */
	public SmsParam() {
		super();
	}

	/**
	 * 全参构造函数.
	 * @param phone
	 * @param content
	 */
	public SmsParam(String phone, String content) {
		super();
		this.phone = phone;
		this.content = content;
	}

	/**
	 * 接收短信的手机号码
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 接收短信的手机号码
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 短信内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 短信内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
