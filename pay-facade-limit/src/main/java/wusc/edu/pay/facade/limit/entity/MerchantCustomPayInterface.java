/**
 * wusc.edu.pay.facade.limit.params.MerchantCustomPayInterface.java
 */
package wusc.edu.pay.facade.limit.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.limit.enums.MerchantCustomPayInterfaceStatusEnum;



/**
 * 
 * 
*<ul>
*<li>Title:支付接口分流-商户定制接口规则<br> </li>
*<li>Description: </li>
*<li>Copyright: www.gzzyzz.com</li>
*<li>Company:</li>
*</ul>
*
 * @author Hill
 * @version 2014-7-8
 */
public class MerchantCustomPayInterface extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	 * 商户号
	 */
	private String merchantNo;

	/**
	 * 支付方式编码
	 */
	private String payWay;

	/**
	 * 支付接口编码
	 */
	private String payInterface;

	/**
	 * 商户定制接口状态
	 */
	private MerchantCustomPayInterfaceStatusEnum status;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;
	
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayInterface() {
		return payInterface;
	}

	public void setPayInterface(String payInterface) {
		this.payInterface = payInterface;
	}

	public MerchantCustomPayInterfaceStatusEnum getStatus() {
		return status;
	}

	public void setStatus(MerchantCustomPayInterfaceStatusEnum status) {
		this.status = status;
	}


	/**
	 * 定制规则当前是否生效
	 * 
	 * @return
	 */
	public boolean isStatusAvailable() {
		if (MerchantCustomPayInterfaceStatusEnum.ACTIVITY.equals(this.status)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
