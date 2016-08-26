<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	/** 对变量 path 的定义 */
	//是否采用https
	ServletContext context = this.getServletContext();
 	final boolean supportHttps = Boolean.parseBoolean(context.getAttribute("IS_SSL") + "");
	//是否 是 域名 + 应用名 
	final boolean is_use_app_name = Boolean.parseBoolean(context.getAttribute("IS_USE_DOMAIN_NAME") + "");
	final String serverName = request.getServerName();
	String path = "";
	if ("localhost".equals(serverName)) {
		// 本地调试时使用
		path = request.getContextPath();
	} else {
		if (supportHttps) {
			path = "https://" + serverName;
		} else {
			if (is_use_app_name) {
				path = "http://" + serverName + request.getContextPath();
			} else {
				path = "http://" + serverName +":"+request.getServerPort() + request.getContextPath();
			}
		}

	}
	
	/**JSP 禁用缓存 */
	response.setHeader("Pragma","No-cache");  
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>