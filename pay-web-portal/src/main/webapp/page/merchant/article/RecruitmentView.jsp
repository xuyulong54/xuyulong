<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人才招聘详情</title>
</head>
<body>
<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
	<div class="reg_top100">
		<div class="reg_top">
			<ul>
				<li class="title titleW">| 人才招聘详情</li>
				<li class="login_reg"><a href="login_loginUI.action">登录 </a>| <a
					href="merchantRegister_checkLoginNameUI.action">注册 </a>
				</li>
				<li><a href="article_listArticle.action?type=3">帮助中心 </a>
				</li>
			</ul>
		</div>
	</div>
	<div class="ht"></div>
	<div class="article">
   <p style="font-size: 150%;">${article.title}</p>
		<div class="content">${article.content}</div>
	</div>
	<div class="ht1">
    </div>
    <jsp:include page="../../foot.jsp" />
</body>
</html>