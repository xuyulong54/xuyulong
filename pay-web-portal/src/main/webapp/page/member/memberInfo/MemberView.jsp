<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员基本信息</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript" src="<%=path%>/statics/cfca/js/SignCertManager.js"></script>
</head>
<script language="javascript" type="text/javascript">
/*页面分类*/
$(document).ready(function() {  setPageType('.men-account', '.men-account-bsinfo '); })

$(function(){
	//获取当前用户的CA,并SET到SESSION中
	getCurrentUserCA('${CURRENT_SN_LIST}');

})

</script>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="pageContent">
	<div class="clear"></div>
	<div class="ht"></div>
	<div class="showinfo">

		 <div class="column columnW850">
		<div class="title">会员基本信息</div>
		</div>
		<div class="info-box">
			<table cellpadding="0" cellspacing="0" class="tbls" id="tbl">
				<tr>
					<th>账户名：</th>
					<td class="rt_td">${currentUser.loginName }</td>
				</tr>
				<tr>
					<th>会员编号：</th>
					<td class="rt_td">${currentUser.userNo}</td>
				</tr>
				<tr>
					<th>实名认证状态：</th>
					<td><c:choose>
							<c:when test="${userBean.isRealNameAuth ==  PublicStatusEnum.ACTIVE.value}">已认证</c:when>
							<c:when test="${isNotWaitRealName}">认证中</c:when>
							<c:otherwise>未认证</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>真实姓名：</th>
					<td>${showRealName}</td>
				</tr>

				<tr>
					<th>身份证号：</th>
					<td>${showCardNo}</td>
				</tr>
				<tr>
					<th>安全问题：</th>
					<td><c:forEach items="${questions}" var="question">
							<c:if test="${question.value==currentUser.question}">${question.desc}</c:if>
						</c:forEach>
					</td>
				</tr>
				<c:if test="${ currentUser.isRealNameAuth != PublicStatusEnum.ACTIVE.value && !isNotWaitRealName}">
				<tr>
					<th></th>
					<td><input class="btn btn-primary" id="Button7" type="button" value="修 改" onclick="window.location.href='memberinfo_editMemberUI.action'" />
					</td>
				</tr>
				</c:if>
			</table>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
