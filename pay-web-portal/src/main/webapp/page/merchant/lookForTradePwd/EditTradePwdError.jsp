<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
			<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回密码失败</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
<jsp:include page="/page/include/unlogMenu.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">
	<div class="headline">
	<div class="title">找回支付密码</div>
</div>

<div class="ht"></div>
	
	<div class="tipsBox">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg errorTipsImg"></li>
				<li class="tipTxt markRed">很抱歉，找回密码失败！</li>

			</ul>
		</div>
		<div class="tipsCont">${errorMsg}    </div>
		<div class="clear"></div>
	</div>
	
</div></div>
<jsp:include page="../../foot.jsp" />
</body>
</html>

<script type="text/javascript">

/*页面分类*/
 $(document).ready(function() {  setPageType('.mer-security', '.mer-security-info ');  })
 </script>