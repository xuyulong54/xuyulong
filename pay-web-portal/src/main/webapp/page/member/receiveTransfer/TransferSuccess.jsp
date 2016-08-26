<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>转账成功</title>
</head>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<form action="" method="post">
	<div class="container">
<div class="bd-container">

		<div class="headline">
	<div class="title">会员转账</div>
</div>
		 <div class="tipsBox">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen"> 恭喜您，已成功转账 <span class='markRed'>&nbsp;&nbsp;${amount}&nbsp;&nbsp;</span>元</li>
			</ul>
		</div>
		<div class="tipsCont">
		<p>您可以继续<a href="memberReceiveTransfer_ransferPage.action">&nbsp;&nbsp;转账 </a></p>
		<p>或查看<a href="memberPayTransfer_listTransfer.action">&nbsp;&nbsp;转账记录</p>

		</div>

		</div>
		</div>
		</div>
	</form>
	<div class="ht"></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
