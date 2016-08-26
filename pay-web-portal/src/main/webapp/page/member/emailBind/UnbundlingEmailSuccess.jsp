<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送邮件</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() { 
setPageType('.men-security', '.men-security-info '); })


$(function(){
	$('#resendEmailId').bind('click',function(){
		resendEmail('${userNo}','${userType}','${loginName}','${toMail}','${mailType}');
	})
})
</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">
	<div class="headline">
	<div class="title">解绑邮箱</div>
</div>
	    <div class="tipsBox"> 	
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen"> 邮件已经发送!</li>
			</ul>
		</div>
		<div class="tipsCont"> 
		<p>邮件已经发送到邮箱${bindingEmail}</p>
		<p> 打开邮箱中的链接，完成操作。 如未收到邮件，请点击<a href="javascript:;" id="resendEmailId">重新发送</a></p>
		<input type="hidden" value="${url}"/>
		</div>
		<div class="clear"></div>
		</div>
	<div class="ht"></div>
	</div></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>