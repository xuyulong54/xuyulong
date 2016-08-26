<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营管理系统登录</title>
<link href="statics/dwz/themes/css/login.css" rel="stylesheet" type="text/css" />
<script src="statics/dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<style type="text/css">
.info{font-size: 12px;color: red;margin-left: 80px;}
</style>
</head>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<!-- <a href="#"><img src="statics/dwz/themes/default/images/login_logo.gif" /></a> -->
				<a style="font-size:25px">${COMPANY_FOR}-支付运营后台</a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#" target="_blank"></a></li>
					</ul>
				</div>
				<h2 class="login_title" style="font-size:16px">用户登录</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form action="login_operatorLogin.action" method="post">
					<p>
						<label>用户名：</label>
						<input name="loginName" type="text" style="width:140px;height:20px;" class="login_input" value="${loginName }" />
						<br/>
						<span class="info">${loginNameMsg}</span>
					</p>
					<p>
						<label>密&nbsp;&nbsp;&nbsp;码：</label>
						<input name="loginPwd" type="password" style="width:140px;height:20px;" class="login_input" />
						<br/>
						<span class="info">${loginPwdMsg}</span>
					</p>
					<div style="width:260px;height:50px;">
						<span style="float:left;width:70px;height:25px;line-height:25px;padding-left:10px;font-size:14px;color:#4c4c4c">验证码：</span>
						<input name="code" class="code" type="text" style="width:60px;height:20px;float:left;" />
						<img id="kaptchaImage" src="randomCode.jpg" style="width:75px;height:26px;float:left;" />
						<div style="height:0px;overflow: hidden;clear:both;margin-bottom:10px;"></div>
						<span class="info">${codeMsg}</span>						
					</div>
					<p><br/><span class="info">${errorMsg}</span></p>
					<div class="login_bar" style="margin-left:10px;">
						<input class="sub" type="submit" value="" />
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="statics/dwz/themes/default/images/login_banner.jpg" /></div>
			<div class="login_main">
				<%
				/*<ul class="helpList">
					<li><a href="#">测试帐号：admin</a></li>
					<li><a href="#">默认密码：Adm829.1</a></li>
				</ul>  
				*/
				%>
			</div>
		</div>
		<div id="login_footer">
			&copy; ${COMPANY_NET_ICP } ${COMPANY_NAME}  版权所有
		</div>
	</div>
<script type="text/javascript">
$('#kaptchaImage').click(function(){$(this).hide().attr('src','randomCode.jpg?'+Math.floor(Math.random()*100)).fadeIn(100);});
</script>
</body>
</html>