<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/page/include/headScript.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改登录密码成功页面</title>
</head>
<body>
	<jsp:include page="../../IndexHead.jsp" />
  	<div class="container">
<div class="bd-container">
	 <div class="headline mbt20">
	<div class="title">找回登录密码</div>
</div>
		<div class="h10"></div>
			<div class="merFourthSetpFlow">
        </div>
        <div class="merFlowTex">
        <ul>
            <li class="red"> 验证身份</li>
            <li  style="width: 220px"> 选择找回方式 </li>
             <li style="width: 220px">重置密码</li>
            <li>重置成功</li>
         </ul>
        </div>
<div class="ht"></div>
   	<div class="tipsBox" style="min-height:100px;">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen">  亲，恭喜您修改密码成功！</li>

			</ul>
		</div>
		<div class="tipsCont">  <a href="login_loginUI.action">快登录吧!</a></div>
		<div class="clear"></div>
</div>
        <div class="clear"></div>
    </div></div>
 <jsp:include page="../../foot.jsp" />	

</body>
</html>