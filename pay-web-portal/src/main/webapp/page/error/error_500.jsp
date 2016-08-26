<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>抱歉,找不到您要的页面...</title>
<%@include file="/page/include/headScript.jsp"%>
<meta name="description" content="" />
<meta name="keywords" content="" />
</head>

<body>

	<div class="errorpg">
		<div class="content">
			<ul>
				<li class="topTitle">抱歉,找不到您要的页面．．．</li>
				<li class="ordinaryTitle">最可能的原因是：地址中存在错误、连接已过期</li>
				<li class="ordinaryTitle">点击以下链接继续浏览${COMPANY_FOR}支付平台：</li>

				<li><span class="imgReturn page fl "></span> <!-- <a class="fl ml5"
					href="javascript:history.go(-1);"> 返回 </a> --> <span
					class="imgReturn home fl" style="margin-left:30px;"> </span><a
					class="fl ml5" href="login_loginUI.action">首页</a></li>
			</ul>
		</div>
	</div>



	<div class="ht1"></div>
<%-- <jsp:include page="foot.jsp" /> --%>

</body>
</html>
