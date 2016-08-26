package wusc.edu.pay.facade.user.entity; 

import wusc.edu.pay.common.entity.BaseEntity;

/**
 *类描述： 商户秘钥信息
 *@author: huangbin
 *@date： 日期：2014-1-6 时间：下午3:27:30
 *@version 1.0
 */
public class MerchantSecret extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1806272167544514536L;
	
	private String merchantNo; //商户ID
	private String publicKey;//平台公钥
	private String privateKey;//平台私钥
	private String merchantPublicKey;//商户公钥
	
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getMerchantPublicKey() {
		return merchantPublicKey;
	}
	public void setMerchantPublicKey(String merchantPublicKey) {
		this.merchantPublicKey = merchantPublicKey;
	}
	
}
 