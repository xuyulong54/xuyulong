<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>验证绑定邮箱</title>
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

$(function() {
	validatorFrom();//表单验证
});

//表单验证
function validatorFrom(){
	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){
		//alert(msg)
		},inIframe:true});
    //用户名
	$("#bindingEmail").formValidator({onShow:"",onFocus:"输入新邮箱"}).inputValidator({min:8,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"邮箱应为8-30个字符"})
	.regexValidator({regExp:"email",dataType:"enum",onError:"输入的邮箱格式不正确"}).compareValidator({desID:"tempBindEmail",operateor:"!=",onError:"不能与原绑定邮箱一致"});
	$.formValidator.reloadAutoTip();
}
</script>
<script type="text/javascript" src="<%=path%>/statics/cfca/js/SignCertManager.js"></script>
<body>
	<div id="FakeCryptoAgent"></div>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<div class="frm-comon">
				<form id="form1" action="merchantemailbind_bindingEmail.action" method="post">
					<div class="ada-memberinfo">
						<div class="headline mbt20">
							<div class="title">验证绑定邮箱</div>
						</div>
						<p class="clearfix">
							<label>绑定新邮箱：</label>
							<input type="hidden" name="tempBindEmail" id="tempBindEmail" value="${bindEmail}" />
							<input name="bindingEmail" type="text" id="bindingEmail" value="${bindingEmail}" maxlength="30">
						</p>
					  <jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>
					  <c:if test="${!empty  PAGE_ERROR_MSG }">
						<p class="clearfix">
							<label for="">&nbsp;</label>
							<span class="text-warning" id="textWarning">${PAGE_ERROR_MSG }</span>
						</p>
						</c:if>
						<p class="clearfix">
							<label> &nbsp; </label>
							<input type="button" value="下一步" class="btn btn-primary" onclick="submitForm();return false;">
						</p>
						<div class="ht"></div>
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
