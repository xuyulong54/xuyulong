<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>实名认证：提交申请成功</title>
	<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
<div class="container">
	<div class="bd-container">
		<div class="frm-comon">
		  <div class=" realNameAuth">
				<div class="headline">
					<div class="title">提交申请</div>
				</div>
				<div class="memThirdSetpFlow setpFlow"></div>
				<div class='memFlowTex'>
					<ul>
						<li class='green'>实名认证说明</li>
						<li class='green middle'>填写申请信息</li>
						<li class='red'>提交申请，等待审核</li>
					</ul>
				</div>
				<div class="ada-wrap mtop15">
					<p class="clearfix">
						<label>  申请人：</label>
						<span>${currentUser.loginName}</span>
						<label>  申请时间：</label>
						<span><fmt:formatDate value="${createTime}"   pattern="yyyy-MM-dd HH:mm:ss" type="both"/></span>
						<label>  目前状态：</label>
						<span>接受处理</span>
					</p>
		      <div class="h10"></div>
			    <div class="tipsBox" style="min-height: 100px;">
						<div class="tipsTitle">
							<ul>
								<li class="TipsImg SuccTipsImg"></li>
								<li class="tipTxt markGreen">   提交成功，等待客服审核。</li>
							</ul>
						</div>
						<div class="tipsCont"> 系统将在2天内为您审核。审核完成后，您可以返回支付平台查看实名认证审核进度</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
		    </div>
		  </div>
		</div>
	</div>
</div>
 <jsp:include page="../../foot.jsp" />
</body>
</html>

<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info ');  })
</script>
