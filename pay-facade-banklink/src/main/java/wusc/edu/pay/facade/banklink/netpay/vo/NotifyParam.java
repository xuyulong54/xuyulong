package wusc.edu.pay.facade.banklink.netpay.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付完成后验证vo
 * 
 * @author Administrator
 * 
 */
public class NotifyParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3929565355870158716L;

	/** 支付接口（非空） */
	private String payInterface;

	/**
	 * 银行返回的原始串
	 */
	private String url;

	private Map<String, Object> paramMap = new HashMap<String, Object>();

	public String getPayInterface() {
		return payInterface;
	}

	public void setPayInterface(String payInterface) {
		this.payInterface = payInterface;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
