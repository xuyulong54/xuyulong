<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录密码，发送验证邮箱成功页面</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">
$(function(){
	$('#resendEmailId').bind('click',function(){
		resendEmail('${userNo}','${userType}','${loginName}','${toMail}','${mailType}');
	})
})
</script>
<body>
	<jsp:include page="../../IndexHead.jsp" />
		<div class="container">
<div class="bd-container">
		<div class="headline mbt20">
	<div class="title">找回登录密码</div>
</div>
	<div class="h10"></div>
	
			<div class="merSecondSetpFlow">
        </div>
          <div class="merFlowTex">
       <ul>
				<li class="green">验证身份</li>
				<li class="red" style="width: 220px">选择找回方式</li>
				<li style="width: 220px">重置密码</li>
				<li>重置成功</li>
			</ul>
        </div>
<div class="ht"></div>
<div class="frm-comon">
		<div class="ht"></div>
          	<strong class="text-warning">亲爱的${loginName},为了验证您的身份,支付平台已经向您的邮箱${email}发送了邮件,请查收。</strong>
      
      <div class='accounts-yellow'>
		<h3><i class="iconfont" style='color:#e60707;font-size:20px;'>&#xe600;</i>没有收到邮件？</h3>
		<div class='pWrap'>
						<p><span>请确认是否在垃圾邮件中，如果还未收到，</span><a class="link-color" href="#" id="resendEmailId">重新发送</a>
					<input type="hidden" name="url" id="url" value="${url }" />
						</p>
						<p><span>如果还未收到，重新发送 还是收不到？</span>
               <a class="link-color"  href="page/merchant/lookForLoginPwd/LoginPwdListWay.jsp?loginName=${loginName}&token=${token}">选择其他验证方式</a></p>				
		</div>
		<div class="clear"></div>
				</div>
 
        <div class="clear"></div>
    </div>				
	 <div class="clear"></div>		
</div></div>

 <jsp:include page="../../foot.jsp" />
</body>
</html>