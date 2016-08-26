<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录密码：找回密码方式</title>
</head>
<body>
	<jsp:include page="../../IndexHead.jsp" />
  	<div class="container">
<div class="bd-container">
	 <div class="headline mbt20">
	<div class="title">找回登录密码</div>
</div>
		<div class="setting settingW850">
		<div class="merSecondSetpFlow">
        </div>
        <div class="merFlowTex">
       <ul>
            <li class="green"> 验证身份</li>
            <li  class="red" style="width: 220px"> 选择找回方式 </li>
             <li style="width: 220px">重置密码</li>
            <li>重置成功</li>
         </ul>
        </div>
	<div class="ht"></div>

			<p class="clearfix"><strong style="width:80px;">用户名：</strong>${empty loginName?param.loginName:loginName}
			<span class="markRed"> ${PAGE_ERROR_MSG} </span>
			</p>
			<div class="wayBack">
					<img src="<%=path%>/statics/themes/default/images/ico_phone.png" style="float:left;" />
					<span class="font" style="width:230px;">
				<a	href="merchantLookForLoginPwd_loginPwdSendPhoneCodeCheckUI.action?loginName=${empty loginName?param.loginName:loginName}&token=${empty token ?param.token:token}">
							验证手机号</a>
							</span>
							<span class="commonBtn"><span class="btn_lfRed">
							<a	class="btn_rtRed"  style="color:white;" href="merchantLookForLoginPwd_loginPwdSendPhoneCodeCheckUI.action?loginName=${empty loginName?param.loginName:loginName}&token=${empty token ?param.token:token}">
							立即找回</a>
							</span></span>
			</div>
			<div class="wayBack">
					<img src="<%=path%>/statics/themes/default/images/ico_mail.png" style="float:left;" />
					<span class="font" style="width:230px;">
					<a href="merchantLookForLoginPwd_loginPwdSendEmailCheck.action?loginName=${empty loginName?param.loginName:loginName}&token=${empty token ?param.token:token}">验证电子邮箱</a>
					</span>
					<span class="commonBtn"><span class="btn_lfRed">
				<a  class="btn_rtRed"  style="color:white;" href="merchantLookForLoginPwd_loginPwdSendEmailCheck.action?loginName=${empty loginName?param.loginName:loginName}&token=${empty token ?param.token:token}">立即找回</a>
			</span></span>
			</div>
			<div class="wayBack">
					<img src="<%=path%>/statics/themes/default/images/ico_person.png" style="float:left;" />
					<span class="font" style="width:230px;">
					<a href="merchantLookForLoginPwd_humanServicesUI.action">人工服务</a>
					</span>
					<span class="commonBtn"><span class="btn_lfRed">
				<a class="btn_rtRed"  style="color:white;" href="merchantLookForLoginPwd_humanServicesUI.action">人工服务</a>
				</span></span>
			</div>
			<div class="clear"></div>
			</div>
			<div class="clear"></div>
		</div></div>
		 <jsp:include page="../../foot.jsp" />
</body>
</html>