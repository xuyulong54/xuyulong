<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/page/include/taglib.jsp" %>
<%@include file="/page/include/headScript.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>密钥管理</title>
</head>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() {  
	setPageType('.mer-publicinfo', '.mer-publicinfo-keymanage');  
	$("#changeSignType").hide(); 
})

$(function(){
	//发送短信验证码
	$('#buttonid').bind('click',function(){
		// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
      sendSms('${currentUser.bindMobileNo}', '${currentUser.loginName}', 'merchant', 'binding', '', 'buttonid','phoneCodeMsg');
      $(this).addClass('disable');
	});
})
function showMerchantKey() {
	var phoneCode = $("#phoneCode").val();
	$.post("merchantKey_showMerchantKey.action", {
		'phoneCode': phoneCode
	}, function (data) {
		if (data.STATE != 'FAIL') { //成功
			$("#merchantKey").val(data.merchantKey);
			$("#merchantPublicKey").text(data.merchantPublicKey);
			$("#tr1").hide();
			$("#tr2").hide();
			$("#tr3").hide();
			$("#changeSignType").show();
		} else {
			$("#phoneCodeMsg").html(data.MSG);
		}
	}, "json");
}

function messageOut() {
	var phoneCode = document.getElementById('phoneCode').value;
	   if(phoneCode != ""){
		   $("#phoneCodeMsg").html("");
	   }
}
</script>

<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<div class="clear"></div>
			<div class="headline">
				<div class="title">密钥管理</div>
			</div>

			<div class="ada-wrap frm-comon mtop20" >
				<p class="clearfix">
					<label>	商户编号：</label>
					<span>${currentUser.userNo}</span>
				</p>
				<p class="clearfix">
					<label> &nbsp; </label>
					<span class="text-warning">温馨提示：通过短信验证后才可以看到商户密钥</span>
					<c:if test="${isMobileAuth == PublicStatusEnum.INACTIVE.value}">
						, &nbsp; <a href="merchantmobilebind_bindingMobileUI.action">绑定手机</a>
					</c:if>
				</p>
				<p class="clearfix">
					<label>
							商户MD5签名密钥：
					</label>
					<input type="text" style="width: 300px;" disabled="disabled" id="merchantKey" value="******">
				</p>
				<p class="clearfix">
					<label>
							商户RSA签名公钥：
					</label>
					<textarea rows="5" cols="40" disabled="disabled" id="merchantPublicKey">******</textarea>
					<a href="merchantKey_showPlatPubKey.action" style="color: blue ; ">查看支付平台公钥</a>
				</p>
				
				<p id="changeSignType">
					<label>&nbsp;</label>
					<span class="lbl">
						<a href="merchantKey_changeMerchantSignType.action" style="margin-left: 28px; color: blue ; ">重新上传</a>
					</span>
				</p>
				
				<c:if test="${isMobileAuth == PublicStatusEnum.ACTIVE.value}">
					<p class="clearfix" id="tr1">
						<label>绑定手机号：</label>
						<span style="float:left;">	${bindMobileNoShow} </span>
						<input class="btn btn-secondary" id="buttonid" type="button"
								value="获取验证码" style="margin-left: 28px;" />
					</p>
					<p class="clearfix" id="tr2">
						<label>短信验证码：</label>
						<input id="phoneCode" name="phoneCode" type="text"  style="width: 300px;" onblur="messageOut()"/>
					</p>
					<p>
						<label for="">&nbsp;</label>
						<span  class="markRed" id="phoneCodeMsg"></span>
					</p>
					<p class="clearfix" id="tr3">
						<label> &nbsp; </label>
							<input class="btn btn-primary" id="Button7"
								type="button" onclick="showMerchantKey();" value="确 定" />

					</p>
				</c:if>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
