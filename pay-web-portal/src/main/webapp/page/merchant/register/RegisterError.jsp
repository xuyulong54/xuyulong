<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册错误页面</title>
</head>


<body>
<jsp:include page="../../IndexHead.jsp" />

	<div class="container">
<div class="bd-container">
<div class="headline">
	<div class="title">商户注册</div>
</div>

	<div class="tipsBox" style="min-height: 200px;">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg errorTipsImg"></li>
				<li class="tipTxt markRed">很抱歉，注册失败！</li>

			</ul>
		</div>
		<div class="tipsCont">${errorMsg}  </div>
		<div class="clear"></div>
	</div>
	</div></div>

	<jsp:include page="../../foot.jsp" />
	

</body>
</html>