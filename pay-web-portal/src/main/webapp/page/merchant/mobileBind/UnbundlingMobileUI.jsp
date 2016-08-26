<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>解绑手机</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() {  setPageType('.mer-security', '.mer-security-info ');  })

function submitForm(){
  var text = "";
  document.getElementById("errorMsg").innerHTML=text;
  var textWarning= document.getElementById("phoneCodeMsg");
  if (textWarning!=null){
	  textWarning .innerHTML=text
  }
  subForm();
}

$(function() {
	validatorFrom();//表单验证
	//发送短信验证码
	$('#buttonid').bind('click',function(){
		// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
      sendSms('${currentUser.bindMobileNo}', '${currentUser.loginName}', 'merchant', 'binding', '', 'buttonid','phoneCodeMsg');
	});

});
//------------------------表单验证------------------------
function validatorFrom(){
	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:
		function(msg)
		{
		  //	alert(msg)
		},inIframe:true});
	// 手机验证码
	$("#phoneCode").formValidator({onShow:"",onFocus:"必填项，请输入6位的手机验证码"}).inputValidator({min:6,max:6,onError:"请输入6位的手机验证码"})
	.regexValidator({regExp:"num",dataType:"enum",onError:"手机验证码必须为数字"})
	.ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_phoneCodeValidate.action",
		data:"loginName="+'${merchant.loginName}',
		success : function(data){
			if( data.STATE!='FAIL') {
	            return true;}
				return data.MSG;
		},
		onError : "校验未通过，请重新输入",
		onWait : "正在校验，请稍候..."
	});
	$.formValidator.reloadAutoTip();
}
</script>
<body>
<div id="FakeCryptoAgent"></div> <div id="tr_DigestAlgorithm" style="display: none"></div><!-- 不能删除这个DIV -->
<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
<div class="container">
  <div class="bd-container">
    <form id="form1" action="merchantmobilebind_unbundlingMobile.action" method="post">
    	<input type="hidden" name="type" value="${type}"/>
      <div class="ada-memberinfo">
    		<div class="headline">
    	    <div class="title">解绑手机</div>
        </div>
        <div class="ada-wrap frm-comon">
          <p class="clearfix">
            <label > 绑定手机：</label>
      			<span>${showBindingPhone}</span>
          </p>
          <p class="clearfix" >
            <label> 短信验证码：</label>
            <input class="fl" style="width:150px;" type="text" value="${phoneCode}" name="phoneCode"  id="phoneCode" maxlength="6"/>
                <input	type="button"  class="btn btn-secondary" id="buttonid" value="获取验证码" />
          </p>
    			<jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>
    			<c:if test="${!empty  PAGE_ERROR_MSG }">
          <p class="clearfix" id="phoneCodeMsg">
            <label> &nbsp; </label>
            <span class="text-warning" id="phoneCodeMsg">${PAGE_ERROR_MSG}  </span>
          </p>
          </c:if>
          <p class="clearfix">
            <label> &nbsp; </label>
            <input	type="submit" value="下一步"  class="btn btn-primary" onclick="submitForm();return false;">
          </p>
        </div>
        <div class="clear"></div>
    	</div>
      <div class="ht"></div>
  	</form>
	</div>
</div>
<jsp:include page="../../foot.jsp" />

</body>
</html>
