package wusc.edu.pay.common.web.themes.dwz;


/**
 * navTabAjaxDone是DWZ框架中预定义的表单提交回调函数．
 * 服务器转回navTabId可以把那个navTab标记为reloadFlag=1, 下次切换到那个navTab时会重新载入内容. 
 * callbackType如果是closeCurrent就会关闭当前tab
 * 只有callbackType="forward"时需要forwardUrl值
 * navTabAjaxDone这个回调函数基本可以通用了，如果还有特殊需要也可以自定义回调函数.
 * 如果表单提交只提示操作是否成功, 就可以不指定回调函数. 框架会默认调用DWZ.ajaxDone()
 * <form action="/user.do?method=save" onsubmit="return validateCallback(this, navTabAjaxDone)">
 * form提交后返回json数据结构statusCode=DWZ.statusCode.ok表示操作成功, 做页面跳转等操作. 
 * statusCode=DWZ.statusCode.error表示操作失败, 提示错误原因. 
 * statusCode=DWZ.statusCode.timeout表示session超时，下次点击时跳转到DWZ.loginUrl
 * {"statusCode":"200", "message":"操作成功", "navTabId":"navNewsLi", "forwardUrl":"", "callbackType":"closeCurrent"}
 * {"statusCode":"300", "message":"操作失败"}
 * {"statusCode":"301", "message":"会话超时"}
 * @author WuShuicheng.
 * @version 1.0, 2013-3-21,下午8:59:31.
 */
public class DwzParam{
	
	/**
	 * Ajax请求的执行状态码.<br/>
	 * statusCode:{ok:200, error:300, timeout:301}.<br/>
	 * 200：成功，300：错误，301:请求超时.
	 */
	private String statusCode;
	
	/**
	 * Ajax提示消息 message 
	 */
	private String message;
	
	/**
	 * navTabId. 
	 * 服务器传回navTabId可以把那个navTab标记为reloadFlag=1,下次切换到那个navTab时会重新载入内容 .
	 */
	private String navTabId;
	
	/**
	 * dialogId. 
	 * 服务器传回dialogId可以把那个dialogId标记为reloadFlag=1,下次切换到那个dialog时会重新载入内容 .
	 */
	private String dialogId;
	
	/**
	 * Ajax请求回调类型. <br/>
	 * callbackType如果是closeCurrent就会关闭当前tab选项, 只有callbackType="forward"时需要forwardUrl值,以重定向到另一个URL.
	 */
	private String callbackType;
	
	/** 
	 * 重定向URL . 
	 */
	private String forwardUrl;
	
	/**
	 * 关联Action .
	 */
	private String rel;
	
	/**
	 * 提示确认信息.
	 */
	private String confirmMsg;
	
	
	/**
	 * 全参构造函数.
	 * @param statusCode .
	 * @param message .
	 * @param navTabId .
	 * @param dialogId .
	 * @param callbackType .
	 * @param forwardUrl .
	 * @param rel .
	 */
	public DwzParam(String statusCode, String message, String navTabId, String dialogId, String callbackType, String forwardUrl, String rel, String confirmMsg) {
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.dialogId = dialogId;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
		this.rel = rel;
		this.confirmMsg = confirmMsg;
	}
	
	/**
	 * DwzAjaxDone所需参数的构造函数.
	 * @param navTabId .
	 * @param callbackType .
	 * @param forwardUrl .
	 * @param rel .
	 */
	public DwzParam(String navTabId, String callbackType, String forwardUrl, String rel) {
		this.navTabId = navTabId;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
		this.rel = rel;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}
	public String getDialogId() {
		return dialogId;
	}

	public void setDialogId(String dialogId) {
		this.dialogId = dialogId;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getConfirmMsg() {
		return confirmMsg;
	}

	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}
	
}
