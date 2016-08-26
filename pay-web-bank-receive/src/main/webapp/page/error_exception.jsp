<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="64kb"%>
<%@include file="/page/inc/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>抱歉，出错啦!</title>
<%@include file="/page/inc/headScript.jsp"%>
<link rel="Stylesheet" href="statics/css/GatewayExceptionError.css" />

</head>
<body>
	<div class="errorpg">
		<div class="content ">
			<ul>
				<li class="topTitle">抱歉,系统异常！</li>
				<li class="ordinaryTitle">系统异常</li>
<!-- 				<li>异常代码： <%=request.getAttribute("errCode")%></li> -->
				<li style="display: none"><%=request.getAttribute("errMsg")%></li>
				<!-- <li><a class="btnReturn pt10" href="javascript:history.go(-1);"></a>
				</li> -->
			</ul>
		</div>
	</div>

</body>
</html>
