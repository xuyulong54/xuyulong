<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/page/include/headScript.jsp"%>
<title>支付密码，发送验证邮箱成功页面</title>

</head>
<body>
<script type="text/javascript">
$(function(){
	$('#resendEmailId').bind('click',function(){
		resendEmail('${userNo}','${userType}','${loginName}','${toMail}','${mailType}');
	})
})
</script>
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
<div class="container">
<div class="bd-container">
	<div class="headline">
	<div class="title">找回支付密码</div>
</div>
		<div class="ht"></div>
	<div class=" editBoxWrap">
			<div class="merSecondSetpFlow">
        </div>
      <div class="merFlowTex">
        <ul>
            <li class="green">选择找回方式</li>
            <li class="red"  style="width: 220px">验证身份</li>
             <li  style="width: 220px">重置密码</li>
            <li>  重置成功</li>
            </ul>
        </div>
       <div class="ht"></div>
		<div class="tipsBox" style="min-height:80px">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen">   邮件发送成功，请查收！</li>

			</ul>
		</div>
		<div class="clear"></div>
</div>
		
		
		<div class="accounts-yellow">
		<h3> 
<i class="iconfont" style="color:#e60707;font-size:20px;">&#xe600;</i>
    没有收到邮件？</h3>
		<div class="pWrap">
		<p>请确认是否在垃圾邮件中，如果还未收到，<a class="link-color" href="javascript:;" id="resendEmailId">重新发送</a></p>
		<p>或者<a class="link-color" href="memberlookfortradepwd_tradePwdListWay.action">请选择其他验证方式</a>
				<input type="hidden" value="${url }"/></p>		
		</div>
</div>
		<div class="clear"></div>
	</div>
	<div class="ht"></div>
	</div></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info '); })
</script>