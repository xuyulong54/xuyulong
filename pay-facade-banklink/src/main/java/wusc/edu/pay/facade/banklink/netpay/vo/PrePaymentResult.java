package wusc.edu.pay.facade.banklink.netpay.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 预支付请求返回vo
 * 
 * @author Administrator
 * 
 */
public class PrePaymentResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3980882082796212284L;

	/** 提交方式 */
	private String method;

	/** 提交地址 */
	private String url;
	
	/** 收款方账户编号 */
	private String receiverAccountNo;

	/** 其他个性参数 */
	private Map<String, Object> paramMap = new HashMap<String, Object>();

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}

	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	
}
