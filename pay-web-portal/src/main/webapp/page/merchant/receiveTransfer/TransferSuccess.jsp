<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>转账成功</title>
</head>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">
	<form action="" method="post">

	<div class="pageContent">

		<div class="headline">
	<div class="title">商户转账</div>
</div>

		 <div class="tipsBox" style="min-height: 200px;">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen"> 恭喜您，已成功转账 <span class='markRed'>&nbsp;&nbsp;${amount}&nbsp;&nbsp;</span>元</li>
			</ul>
		</div>
		<div class="tipsCont">
		<p>您可以继续<a href="merchantReceiveTransfer_ransferPage.action">&nbsp;&nbsp;转账 </a></p>
		<p>或查看<a href="merchantTransferRecord_listTransfer.action">&nbsp;&nbsp;转账记录</p>

		</div>

		</div>
		</div>

	</form>
	</div></div>
<jsp:include page="../../foot.jsp" />
</body>
</html>

<script type="text/javascript">
/*页面分类*/
 $(document).ready(function() { setPageType('.mer-account', '.mer-account-info ');  })
 </script>
