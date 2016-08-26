<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	/** 对变量 path 的定义 */
	//是否采用https
	ServletContext context = this.getServletContext();
	final boolean supportHttps = Boolean.parseBoolean(context
			.getAttribute("IS_SSL") + "");
	//是否 是 域名 + 应用名 
	final boolean is_use_app_name = Boolean.parseBoolean(context
			.getAttribute("IS_USE_DOMAIN_NAME") + "");
	final String serverName = request.getServerName();
	String path = "";
	if ("localhost".equals(serverName)) {
		// 本地调试时使用
		path = "http://localhost:8080" + request.getContextPath();
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
<script type="text/javascript">
var webpath='<%=path%>';
</script>
<link href="<%=path%>/statics/css/global.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/statics/css/style.css" type="text/css"
	rel="stylesheet" />


<script type="text/javascript"
	src="<%=path%>/statics/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/statics/js/index.js"></script>
<script type="text/javascript" src="<%=path%>/statics/js/xmlhttp.js"></script>
<c:if test="${USE_KEYBOARD }">
	<script type="text/javascript"
		src="<%=path%>/statics/js/writeObject.js"></script>
</c:if>