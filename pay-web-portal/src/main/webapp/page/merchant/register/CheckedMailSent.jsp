<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="/page/include/headScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户注册：1.验证账户名</title>

<script type="text/javascript">
$(function(){
	$('#resendEmailId').bind('click',function(){
		resendEmail('${userNo}','${userType}','${loginName}','${toMail}','${mailType}');
	})
})
</script>
</head>
<body>
<jsp:include page="../../IndexHead.jsp" />
	
	<div class="container">
<div class="bd-container">
<div class="headline">
	<div class="title">商户注册</div>
</div>

<div class="accounts-yellow">
<div class="fl"><i class="iconfont" style="color:#5EB95A;font-size:80px;margin-left: 10px;">&#xe603;</i></div>
		
		
 
		<div class="pWrap fl">
		<h3 class=" mleft10" style="text-indent: 20px; color:#5EB95A; font-size: 18px;margin-top: 10px;"> 
    登录邮箱激活</h3>
		<p>马上登录邮箱激活账号，完成注册！</p>		
		<p>激活邮件已经发送到您的邮箱<strong>${loginName} </strong>,请注意查收</p>
		<p>如果未收到邮件, <a class="link-color" href="#" id="resendEmailId">重新发送</a></p>
		</div>
		<input type="hidden" value="${url}"/></td>
		<div class="clear"></div>
</div>

		<%--<div class="subcont subcontH">
			<table>
				<tr>
					<td class="rt_td">
						<div class="tips">
						&nbsp;	登录邮箱激活 <br />
						</div> &nbsp;  &nbsp; &nbsp;马上登录邮箱激活账号，完成注册！<br />
				     &nbsp;  &nbsp; &nbsp;	 激活邮件已经发送到您的邮箱<strong>${loginName} </strong>,请注意查收.<br/>
				     &nbsp;  &nbsp; &nbsp;  如果未收到邮件, <a href="#" id="resendEmailId">重新发送</a> 
				     </td>
				</tr>
				<Tr class="rt_td">
				<td><input type="hidden" value="${url}"/></td>
				</Tr>
			</table>
		</div>
	--%>
	
	
	</div></div>
	<div class="ht"></div>
 <jsp:include page="../../foot.jsp" />
</body>
</html>