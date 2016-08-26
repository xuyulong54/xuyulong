<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
		String url =  "login_loginUI.action";
		response.setHeader("refresh", "5;url=" + url);
	%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户登录密码修改成功页面</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
<jsp:include page="../../IndexHead.jsp" />
<div class="container">
<div class="bd-container">
	<div class="headline">
	<div class="title">修改登录密码</div>
</div>
		
  <div class="tipsBox">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen"> 恭喜您，登录密码修改成功!</li>
			</ul>
		</div>
		<div class="tipsCont">
		  <p>
            <label id="tt" class="markRed"></label>
            秒后自动跳到登录页面
          </p>
		</div>
		<div class="clear"></div>
	</div>
	
   </div></div>
        <jsp:include page="../../foot.jsp" />
</body>
</html>
<script type="text/javascript">
/*页面分类*/
 var secs = 5; //倒计时的秒数
for(var i=secs;i>=0;i--)
{
window.setTimeout("doUpdate(" + i + ")", (secs-i) * 1000);
}
function doUpdate(num)
{
document.getElementById("tt").innerHTML = num ;
}
</script>