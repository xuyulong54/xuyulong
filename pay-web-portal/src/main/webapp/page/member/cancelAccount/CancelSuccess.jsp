<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>提交完成</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<%
	String url = "login_loginUI.action";
	response.setHeader("refresh", "5;url=" + url);
%>
<body>
<jsp:include page="../../IndexHead.jsp" />
<div class="container">
	<div class="ht"></div>
	<div class="headline">
	<div class="title">会员注销</div>
</div>
	 <div class="tipsBox"> 	
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen"> 注销申请已提交，系统会在三个工作日内审核处理 </li>

			</ul>
		</div>
		<div class="tipsCont"> 
		<p>注销结果将会以短信或者邮件的形式发送给您</p>
		<p>感想您对支付平台的支持，如有需要，可以重新注册新账户。</p>
		</div>
		<div class="clear"></div>
		</div>

	<div class="ht"></div>
	</div>
	 <jsp:include page="../../foot.jsp" />
</body>
</html>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info ');
})
</script>
