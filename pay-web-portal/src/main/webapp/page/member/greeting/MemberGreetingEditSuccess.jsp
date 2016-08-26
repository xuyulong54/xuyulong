<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="statics/themes/default/css/style.css" />
<title>会员预留信息设置成功页面</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">

   <div class="headline">
	<div class="title">设置预留信息</div>
</div>

     <div class="tipsBox">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen">恭喜您，预留信息设置成功！！</li>
			</ul>
		</div>
		<div class="tipsCont">
			    您可以返回 <a href="membersecuritycenter_securityCenter.action">安全中心</a>
		</div>
		<div class="clear"></div>
	</div>
    <div class="ht"></div>
    </div></div>
      <jsp:include page="../../foot.jsp" />
</body>
</html>

<script type="text/javascript">

/*页面分类*/
 $(document).ready(function() { setPageType('.men-security', '.men-security-info '); })
 </script>