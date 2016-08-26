<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回支付密码成功页面</title>
</head>
<body>
<jsp:include page="/page/include/unlogMenu.jsp"></jsp:include>
        <div class="container">
<div class="bd-container">
		<div class="headline">
	<div class="title">找回支付密码</div>
</div>

		<div class="ht"></div>
			<div class="merFourthSetpFlow">
        </div>
        
        <div class="merFlowTex">
         <ul>
            <li class="green">
                 选择找回方式
            </li>
            <li class="green"  style="width: 220px">
               验证身份
            </li>
             <li  class="green" style="width: 220px">
               重置密码
            </li>
           
            <li class="red">
               重置成功
            </li>
            </ul>
        </div>

			<div class="ht"></div>
	 	<div class="tipsBox">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen">    恭喜您，找回密码成功！</li>

			</ul>
		</div>
		<div class="tipsCont">  <a href="login_loginUI.action">重新登录!</a></div>
		<div class="clear"></div>
</div>
	
		
       
        <div class="clear"></div>
    </div></div>
		
 <jsp:include page="../../foot.jsp" />	


</body>
</html>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info '); })
</script>