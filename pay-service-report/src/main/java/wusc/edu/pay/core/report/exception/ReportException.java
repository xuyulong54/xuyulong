package wusc.edu.pay.core.report.exception;

/**
 * 
 * @描述: 报表系统自定义异常类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-1,上午11:14:34 .
 * @版本: 1.0 .
 */
public class ReportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3099103384270268627L;
	
	/**
	 * 异常提示信息.
	 */
	public String errMsg;
	
	/**
	 * 默认构造函数.
	 */
	public ReportException() {
		super();
	}
	
	/**
	 * 带消息提示构造函数.
	 * @param errMsg .
	 */
	public ReportException(String errMsg) {
		super();
		this.errMsg = errMsg;
	}
}
