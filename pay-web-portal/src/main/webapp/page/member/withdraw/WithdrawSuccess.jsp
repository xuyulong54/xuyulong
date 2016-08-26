<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提现成功页面</title>
 <%@include file="/page/include/headScript.jsp"%>
</head>
<body>
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">
	<div class="headline">
	<div class="title">会员提现</div>
</div>
	
	
	 <div class="tipsBox"> 	
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen"> 提现申请已提交，银行将在3个工作日内受理请求</li>
			</ul>
		</div>
		<div class="tipsCont"> 
		<p>您可以<a href="withdraw_withdrawUI.action">继续提现</a></p>
		<p>或查看<a href="withdraw_listRemittanceRecord.action">提现记录</a></p>
		
		</div>

		</div>
	
		
		
		
</div></div>




<div class="ht"></div>
<div class="ht"></div>
	<jsp:include page="../../foot.jsp" />

<!-- 提现申请已提交，银行将在3个工作日内受理请求<br/> -->
<!-- 您可以<a href="withdraw_withdrawUI.action">继续提现</a> ，或查看<a href="withdraw_listRemittanceRecord.action">提现记录</a> -->
</body>
</html>
<script type="text/javascript">
/*页面分类*/
    	setPageType('.men-account', '.men-account-info ');
    	</script>