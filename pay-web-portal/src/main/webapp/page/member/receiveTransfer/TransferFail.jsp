<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>转账失败</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
  <div class="container">
<div class="bd-container">
	<div class="headline">
	<div class="title">会员转账</div>
</div>

	<div class="tipsBox">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg errorTipsImg"></li>
				<li class="tipTxt markRed">很抱歉，转账失败！</li>

			</ul>
		</div>
		<div class="tipsCont">${error} </div>
		
	</div>
</div>

	
	
	
	<div class="ht"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>

<script type="text/javascript">

/*页面分类*/
 $(document).ready(function() { setPageType('.men-account', '.men-account-info '); })
 </script>