package wusc.edu.pay.common.web.exception;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * Web应用异常基类，所有Web应用异常都必须继承于此异常
 * 
 * @author healy
 * 
 */
public class WebException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5636524086210777927L;

	/**
	 * 异常ID，用于表示某一异常实例，每一个异常实例都有一个唯一的异常ID
	 */
	protected String id;

	/**
	 * 异常信息，包含必要的上下文业务信息，用于打印日志
	 */
	protected String message;

	/**
	 * 具体异常码，由各具体异常实例化时自己定义
	 */
	protected String defineCode;

	/**
	 * 异常类名
	 */
	protected String realClassName;

	public WebException(String defineCode, String message) {
		super();
		this.defineCode = defineCode;
		this.id = UUID.randomUUID().toString().toUpperCase()
				.replaceAll("-", "");
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message, Object... args) {
		this.message = MessageFormat.format(message, args);
	}

	public String getDefineCode() {
		return defineCode;
	}
}
