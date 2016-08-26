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
 
 $("#platDiv").hide();
 
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
	var merchantPubKey = $("#merchantPubKey").val();
	var merchantKey = $("#merchantKey").val();
	var isRSA = "";
	if($("#isRSA").attr("checked")==true){
		isRSA = 1;
	}else{
		isRSA = 2;
	}
	
	$.post("merchantKey_changeSignTyepe.action", {
		'phoneCode': phoneCode,
		'merchantPubKey':merchantPubKey,
		'merchantKey':merchantKey,
		'isRSA':isRSA
	}, function (data) {
		if (data.STATE != 'FAIL') { //成功
				
					$("#merchantDiv").hide();
					$("#platDiv").show();
					
					$("#platformPubKeyPText").val(data.platformPubKey);
					$("#platformSourceStrText").val(data.sourceStr);
					$("#platformSignStrText").val(data.signStr);
				
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
				
				<div id="merchantDiv">
					<p class="clearfix">
						<label> &nbsp; </label>
						<span class="text-warning">
								温馨提示：请谨慎修改如下数据,并确保MD5密钥信息的私密性。以免对您的账户引起危险
						</span>
					</p>
					
					<p class="clearfix" id="merchentKey" >
						
						<label>商户MD5签名密钥：</label>
						<input type="text" style="width: 300px;" id="merchantKey" value="">
					</p>
					
					<p class="clearfix" id="merchentPubKeyP" >
						
						<label>商户RSA签名公钥：</label>
						<textarea rows="5" cols="40" id="merchantPubKey" name="merchantPubKey">
							
						</textarea>
						<input type="checkbox" value="1" id="isRSA"> 是否添加RSA
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
				<div id="platDiv">
					<p class="clearfix">
					<label> &nbsp; </label>
						<span class="text-warning">温馨提示：请谨记平台公钥，可以使用该公钥对下面的明文及密文进行验签实验</span>
					</p>
					
					<p class="clearfix" id="platformPubKeyP">
						<label>平台公钥：</label>
						<textarea rows="5" cols="50" id="platformPubKeyPText" readonly="readonly" name="platformPubKeyPText">
							
						</textarea>
					</p>
					
					<p class="clearfix" id="platformSourceStr">
						<label>平台签名明文：</label>
						<textarea rows="5" cols="50" id="platformSourceStrText" readonly="readonly" name="platformSourceStrText">
							
						</textarea>
					</p>
					
					<p class="clearfix" id="platformSignStr">
						<label>平台签名后密文：</label>
						<textarea rows="5" cols="50" id="platformSignStrText" readonly="readonly" name="platformSignStrText">
							
						</textarea>
					</p>
					
					<p class="clearfix">
						<label> &nbsp; </label>
						<a href="merchantKey_showMerchantKeyUI.action"  style="margin-left: 28px; color: blue ; ">返回</a>
					</p>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
