<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置预留信息</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.mer-security', '.mer-security-info ');  })

 function submitForm(){
  var text = "";
  document.getElementById("errorMsg").innerHTML=text;
  var textWarning= document.getElementById("textWarning");
  if (textWarning!=null){
    textWarning .innerHTML=text
  }  subForm();
}

$(function(){
	validatorFrom();
})
//------------------------表单验证------------------------
function validatorFrom(){
	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){
		//alert(msg)
		},inIframe:true});
	// 预留信息
	$("#greeting").formValidator({onShow:"",onFocus:"必填项，长度为5-20个字符"}).inputValidator({min:5,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为5-20个字符"})
	.compareValidator({desID:"oldGreeting",operateor:"!=",onError:"不能与原预留信息一致"});
	$.formValidator.reloadAutoTip();
}
</script>
<body>
<div id="FakeCryptoAgent"></div>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<form action="merreservationInfo_editMerchantGreeting.action" method="post" id="form1">
				<div class="ada-memberinfo">
				  <div class="headline">
						<div class="title">设置预留信息</div>
					</div>
					<div class='accounts-yellow'>
						<h3><i class="iconfont" style='color:#e60707;font-size:20px;'>&#xe600;</i> 预留信息说明</h3>
						<div class='pWrap'>
							<p>当您登录${COMPANY_FOR}支付平台时，预留信息会显示在登录后首页</p>
							<p>如果登录后首页没有显示您的登录欢迎语，或显示登录欢迎语不正确，请注意您的账号安全，以防钓鱼网站。</p>
						</div>
					</div>
					<div class="frm-comon">
						<c:if test="${not empty  currentUser.greeting}">
						<p class="clearfix">
							<label>原预留信息：</label>
								${currentUser.greeting}
								<input type="hidden" value="${currentUser.greeting}" name="oldGreeting" id="oldGreeting">
						</p>
						</c:if>
						<p class="clearfix">
							<label >预留信息：</label>
							<input type="text" style="width:240px" value="${greeting}" name="greeting" id="greeting" maxlength="20"/>
						</p>
						<jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>
						<c:if test="${!empty  PAGE_ERROR_MSG }">
						<p class="markRed marginL150" id="textWarning">${PAGE_ERROR_MSG}</p>
						</c:if>
						<p class="clearfix">
							<label> &nbsp; </label>
							<input type="button" value="下一步" class="btn btn-primary" onclick="submitForm();return false;">
						</p>
					</div>
					<div class="clear"></div>
					<div class="greetingbanner"></div>
				</div>
			</form>
			<div class="clear"></div>
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
