<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>解绑邮箱</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
<div id="FakeCryptoAgent"></div> <div id="tr_DigestAlgorithm" style="display: none"></div><!-- 不能删除这个DIV -->
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">
<div class="frm-comon">
	<form id="form1" action="memberemailbind_unbundlingEmail.action" method="post">
		<input type="hidden" name="bindingEmail" value="${bindingEmail}">
		<div class="ada-memberinfo">
			<div class="headline">
				<div class="title">解绑邮箱</div>
			</div>
			<p class="clearfix">
				<label>绑定邮箱：</label>
				<span>${bindingEmail}</span>
			</p>
			<span id="tradePwdShow">
				<jsp:include page="/page/include/memberTradePwd.jsp"></jsp:include>
			</span>
          <c:if test="${!empty  PAGE_ERROR_MSG }">
			<p class='text-warning marginL150' id="textWarning">${PAGE_ERROR_MSG}</P>
			</c:if>
			<p class="clearfix">
				<label> &nbsp; </label>
				<input type="button" value="下一步" class="btn btn-primary" onclick="submitForm();return false;">
			</p>
			<div class="clear"></div>
		</div>
	</form>
	</div>
	<div class="clear"></div>
	</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() {
setPageType('.men-security', '.men-security-info '); })
 function submitForm(){
  var text = "";
  document.getElementById("errorMsg").innerHTML=text;
  var textWarning= document.getElementById("textWarning");
  if (textWarning!=null){
    textWarning .innerHTML=text
  }  subForm();
}
</script>
