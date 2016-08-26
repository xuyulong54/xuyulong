/**
 * wusc.edu.pay.facade.limit.dto.SwitchValidateResultDTO.java
 */
package wusc.edu.pay.facade.limit.dto;

import java.io.Serializable;

/**
 * 
 * <ul>
 * <li>Title: 开关限制验证传输类</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
public class SwitchValidateResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6568997456130931154L;

	/**
	 * 支付产品限制开关  (如果为真 就可以通过)
	 */
	private boolean payProductAvailable;

	/**
	 * 支付方式限制开关 (如果为真 就可以通过)
	 */
	private boolean payWayAvailable;

	public boolean isPayProductAvailable() {
		return payProductAvailable;
	}

	public void setPayProductAvailable(boolean payProductAvailable) {
		this.payProductAvailable = payProductAvailable;
	}

	public boolean isPayWayAvailable() {
		return payWayAvailable;
	}

	public void setPayWayAvailable(boolean payWayAvailable) {
		this.payWayAvailable = payWayAvailable;
	}

}
