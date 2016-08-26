/**
 * 
 */
package wusc.edu.pay.common.exceptions;

/**
 * <ul>
 * <li>Title:</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-1-10
 */
public class CerSignException extends BizException {

	private static final long serialVersionUID = 3879751553383387726L;
	
	/**
	 * 验签数据为空
	 */
	public static final int SIGN_DATA_IS_NULL = 30010001;
	/**
	 * 签名数据不匹配
	 */
	public static final int SIGN_CONTEXT_NOT_COMPLATE = 30010002;
	
	/**
	 * 证书无效或过期
	 */
	public static final int CERT_NOT_ACTIVE = 30010003;
	
	public CerSignException() {
	}

	public CerSignException(String message) {
		super(-1, message);
		printStackTrace();
	}

	public CerSignException(Integer code, String message) {
		super(code, message);
		printStackTrace();
	}
}
