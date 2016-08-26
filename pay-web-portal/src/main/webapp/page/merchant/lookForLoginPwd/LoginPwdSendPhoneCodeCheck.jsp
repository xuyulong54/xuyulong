<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>验证手机</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">
	/** 表单数据校验 */
	$(document).ready(function() {
		//发送短信验证码
		$('#buttonid').bind('click',function(){
			var bindMobileNo = document.getElementById("bindingPhone").value;//绑定手机
			// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
	      sendSms(bindMobileNo, '${loginName}', 'merchant', 'binding', '', 'buttonid','phoneCodeMsg');
		});

		$.formValidator.initConfig({
			formID : "form1",
			submitOnce : "true",
			theme : "ArrowSolidBox",
			mode : "AutoTip",
			onError : function(msg) {
				alert(msg)
			},
			inIframe : true
		});
		// 手机号码
		$("#bindingPhone").formValidator({
			onShow : "",
			onFocus : "必填项，请输入11位的手机号码"
		}).inputValidator({
			min : 11,
			max : 11,
			onError : "手机号码必须是11位的"
		}).regexValidator({
			regExp : "mobile",
			dataType : "enum",
			onError : "手机号码格式不正确"
		}).ajaxValidator({
			dataType : "json",
			async : true,
			url : "ajaxvalidate_oldbindMobile.action",
			data : "loginName=" + '${loginName}',
			success : function(data) {
				if (data.STATE != 'FAIL') {
					return true;
				}
				return data.MSG;
			},
			onError : "校验未通过，请重新输入",
			onWait : "正在校验，请稍候..."
		});
		// 手机验证码
		$("#phoneCode").formValidator({
			onShow : "",
			onFocus : "必填项，请输入6位的手机验证码"
		}).inputValidator({
			min : 6,
			max : 6,
			onError : "请输入6位的手机验证码"
		}).regexValidator({
			regExp : "num",
			dataType : "enum",
			onError : "手机验证码必须为数字"
		}).ajaxValidator({
			dataType : "json",
			async : true,
			url : "ajaxvalidate_phoneCodeValidate.action",
			data : "loginName=" + '${loginName}',
			success : function(data) {
				if (data.STATE != 'FAIL') {
					return true;
				}
				return data.MSG;
			},
			onError : "校验未通过，请重新输入",
			onWait : "正在校验，请稍候..."
		});
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
	<jsp:include page="../../IndexHead.jsp" />
	  	<div class="container">
<div class="bd-container">
	<div class="headline mbt20">
	<div class="title">找回登录密码</div>
</div>
		<div class="h10"></div>
		<div class="merSecondSetpFlow"></div>
		<div class="merFlowTex">
			<ul>
				<li class="green">验证身份</li>
				<li class="red" style="width: 220px">选择找回方式</li>
				<li style="width: 220px">重置密码</li>
				<li>重置成功</li>
			</ul>
		</div>
		<div class="ht"></div>

		<div class=" setting frm-comon">
			<form id="form1" action="merchantLookForLoginPwd_loginPwdSendPhoneCodeCheck.action" method="post">
				<input type="hidden" name="loginName" value="${loginName}">
				<input type="hidden" name="token" value="${token}">
				<p class="clearfix">
					<label> &nbsp; </label> <span class="message text-warning" id="phoneCodeMsg">${PAGE_ERROR_MSG}</span>
				</p>
				<p class="clearfix">
					<label>绑定手机：</label> <input name="bindingPhone" type="text"
						 value="${bindingPhone}" id="bindingPhone" />
				</p>
				<p class="clearfix">
					<label>短信验证码：</label> <input id="phoneCode" name="phoneCode"
						type="text" style='width:200px;'value="${phoneCode}" onblur="messageOut()"/>

							<input type="button" class="btn btn-secondary" id="buttonid"  value="获取验证码" style="margin-left: 5px;" />

				</p>
				<p class="clearfix">
					<label> &nbsp; </label> <input class="btn btn-primary" type="submit"
							value="确 定" />
				</p>
				<div class="clear"></div>
				</form>
		</div>
		<div class="clear"></div>
	</div></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
