package wusc.edu.pay.facade.payrule.entity.vo;

import java.io.Serializable;

public class PayWayVo implements Serializable{
	
	private static final long serialVersionUID = -7413152243731483044L;
	private String payProductCode;
	private String payProductName;
	private String payWayName;
	/**支付方式编号**/
	private String payWayCode;
	private String  sorts;
	//银行编号
	private String bankCode;
	/**渠道类型**/
	private String busType;
	   /** 默认支付接口    **/
    private String defaultPayInterface;
	
	public String getPayProductName() {
		return payProductName;
	}
	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName;
	}
	public String getPayWayName() {
		return payWayName;
	}
	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}
	public String getPayWayCode() {
		return payWayCode;
	}
	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}
	public String getSorts() {
		return sorts;
	}
	public void setSorts(String sorts) {
		this.sorts = sorts;
	}
	public String getPayProductCode() {
		return payProductCode;
	}
	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getDefaultPayInterface() {
		return defaultPayInterface;
	}
	public void setDefaultPayInterface(String defaultPayInterface) {
		this.defaultPayInterface = defaultPayInterface;
	}
	

}
