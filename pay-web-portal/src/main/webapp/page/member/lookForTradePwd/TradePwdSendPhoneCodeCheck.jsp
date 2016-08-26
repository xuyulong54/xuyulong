<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/page/include/headScript.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>找回支付密码，手机</title>
</head>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info '); })

/** 表单数据校验 */
$(document).ready(function(){
	//发送短信验证码
	$('#buttonid').bind('click',function(){
		var bindMobileNo='${currentUserInfo.bindMobileNo}';//绑定手机
		var loginName='${currentUserInfo.loginName}';
		// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
      sendSms(bindMobileNo,loginName, 'member', 'binding', '', 'buttonid','phoneCodeMsg');
	});


	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
	// 手机验证码
	$("#phoneCode").formValidator({onShow:"",onFocus:"必填项，请输入6位的手机验证码"}).inputValidator({min:6,max:6,onError:"请输入6位的手机验证码"}).regexValidator({regExp:"num",dataType:"enum",onError:"手机验证码必须为数字"})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_phoneCodeValidate.action",
	success : function(data){
		if( data.STATE!='FAIL') {
            return true;}
			return data.MSG;
	},
	onError : "校验未通过，请重新输入",
	onWait : "正在校验，请稍候..."
	});
	//身份证号
	$("#cardNo").formValidator({onShow:"",onFocus:"必填项，长度为18个字符"}).inputValidator({min:18,max:18,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为18个字符"}).functionValidator({fun:isCardID})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_cardNoValidate.action",
	success : function(data){
		if( data.STATE!='FAIL') {
            return true;}
			return data.MSG;
	},
	onError : "校验未通过，请重新输入",
	onWait : "正在校验，请稍候..."
	});
	$("#trnewPwd1").formValidator({onShow:"",onFocus:"必填项，密码为8-20位数字,字母和特殊符号组合"}).inputValidator({min:8,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"密码为8-20位数字,字母和特殊符号组合"})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_tradeEqLoginPwd.action",
	success : function(data){
		if( data.STATE!='FAIL') {
            return true;}
			return data.MSG;
	},
	onError : "校验未通过，请重新输入",
	onWait : "正在校验，请稍候..."
	})
	$("#trnewPwd2").formValidator({onShow:"",onFocus:"必填项，两次输入密码必须一致"}).inputValidator({min:8,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"密码为8-20位数字,字母和特殊符号组合"}).compareValidator({desID:"trnewPwd1",operateor:"=",onError:"两次输入的密码不一致"});
	$.formValidator.reloadAutoTip();
});

function messageOut() {
	  var phoneCode = document.getElementById('phoneCode').value;
	     if(phoneCode != ""){
	       $("#phoneCodeMsg").html("");
	     }
	}
</script>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<div class="headline">
				<div class="title">找回支付密码</div>
			</div>
		<c:choose>
			<c:when test="${currentUserInfo.isRealNameAuth == PublicStatusEnum.INACTIVE.value }">
				<div class="ht"></div>
		    <div class="tipsBox infoWrap">
					<div class="tipsTitle">
						<ul>
							<li><i class="iconfont" >&#xe602;</i></li>
							<li class="tipTxt" >亲，先进行&nbsp;<a href="realnameauth_consentAgreementUI.action">实名认证</a>&nbsp;哦!</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
			</c:when>

			<c:when test="${currentUserInfo.isMobileAuth == PublicStatusEnum.INACTIVE.value }">
				<div class="ht"></div>
			    <div class="tipsBox infoWrap">
						<div class="tipsTitle">
							<ul>
								<li><i class="iconfont" >&#xe602;</i></li>
								<li class="tipTxt" >亲，先&nbsp;<a href="membermobilebind_bindingMobileUI.action">绑定手机</a>&nbsp;哦！</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
			</c:when>
			<c:otherwise>
			<form id="form1" action="memberlookfortradepwd_tradePwdSendPhoneCodeCheck.action" method="post">
				<div class="ht"></div>
				<div class=" editBox editBoxWrap setting">
					<div class="memSecondSetpFlow"></div>
					<div class="memFlowTex">
						<ul>
							<li  class="green">选择找回方式</li>
							<li class="red" style="width: 420px;">验证身份,重置密码</li>
							<li>重置成功</li>
						</ul>
					</div>
					<div class="ht"></div>
					<p>
						<strong></strong>
						<span id="phoneCodeMsg" class='markRed'>${PAGE_ERROR_MSG}</span>
					</p>
					<p>
						<strong>手机号：</strong>
						${showBindMobileNo}
					</p>
					<p>
						<strong> 短信验证码： </strong>
						<input name="phoneCode" value="${phoneCode}" id="phoneCode" type="text" style="width:204px;" maxlength="6" onblur="messageOut()"/>
						 <input type="button" class="btn btn-secondary" id="buttonid"
							value="获取验证码" />
					</p>
					<p>
						<strong> 身份证号： </strong>
						<input type="text" name="cardNo" value="${cardNo}" id="cardNo" maxlength="18"/>
					</p>
					<p>
						<strong> 新的支付密码： </strong> <input type="password" name="trnewPwd1" value="${trnewPwd1}" id="trnewPwd1" maxlength="20"/>
					</p>
					<p>
						<strong> 确认新的支付密码： </strong>
						<input type="password" name="trnewPwd2" value="${trnewPwd2}" id="trnewPwd2" maxlength="20"/>
					</p>
					<p>
						<strong> </strong>
						<span class="commonBtn">
							<span class="btn_lfRed">
								<input id="Button2" class="btn_rtRed"
									type="submit" value="确 定" />
							</span>
						</span>
					</p>
					<div class="clear"></div>
				</div>
				<div class="ht"></div>
			</form>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
