<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style>
.newNav li a{font-size:16px!important;color:#000!important;}
.newNav li a:hover{color:#e60707!important;text-decoration: underline!important;}
</style>
</head>
<%@include file="/page/include/headScript.jsp"%>
<body>
<div class="navWrap">
		<div class="newNav">
			<div class="logo">
				<img  src="<%=path %>${COMPANY_LOGO }" />
			</div>
			<ul>
				<li class=''><a href="login_loginUI.action">登录</a></li>
				<c:if test="${PORTAL_IS_REGISTER }"><li class=''><a href="merchantRegister_checkLoginNameUI.action">注册</a></li></c:if>
			</ul>
		</div>
	</div>
</body>
</html>
