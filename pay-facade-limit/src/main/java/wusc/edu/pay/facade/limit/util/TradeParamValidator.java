/**
 * wusc.edu.pay.facade.limit.util.TradeParamValidator.java
 */
package wusc.edu.pay.facade.limit.util;

import wusc.edu.pay.facade.limit.exceptions.LimitBizException;

/**
 * 
 * <ul>
 * <li>Title: 交易验证工具类</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
public class TradeParamValidator {

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @param msg
	 */
	@SuppressWarnings("unused")
	public static void checkStrNotNull(String str, String msg) {
		if (str == null && str == "") {
			throw new LimitBizException(LimitBizException.PARAM_IS_NULL, msg);
		}
	}

}
