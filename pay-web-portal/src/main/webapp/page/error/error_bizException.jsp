<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="64kb"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>抱歉，出错啦!</title>
<%@include file="/page/include/headScript.jsp"%>
<meta name="description" content="" />
<meta name="keywords" content="" />
</head>

<body>
<%-- <jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include> --%>

	<div class="errorpg">
		<div class="content">
			<ul>
				<li class="topTitle">抱歉,出错啦！</li>
				<li class="ordinaryTitle">业务运行中出现问题，请等待业务自动恢复或联系客服解决</li>
				<li>异常代码： <%=request.getAttribute("errCode")%></li> 

				<li><span class="imgReturn page fl "></span> <!-- <a class="fl ml5"
					href="javascript:history.go(-1);"> 返回 </a> --> <span
					class="imgReturn home fl" style="margin-left:30px;"> </span><a
					class="fl ml5" href="login_loginUI.action">首页</a></li>
			</ul>
		</div>
	</div>

  
 
    <div class="ht1"></div>
<%-- 	<jsp:include page="foot.jsp" /> --%>
</body>
</html>
