<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headScript.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Stylesheet" href="statics/themes/default/css/login.css" />
<title>商户注册：进行账户名验证</title>
<script type="text/javascript">
	$(document).ready(function() {
		$('.test').bind('focus', function() {
			$(this).css('float', 'right');
			$(this).css('width', '90px');
			$(this).css('height', '24px');
			$(this).css('line-height', '24px');
		});
		$('.user').bind('focus', function() {
			$(this).css('float', 'right');
			$(this).css('width', '200px');
			$(this).css('height', '24px');
			$(this).css('line-height', '24px');
		});
		$('.commonBtnLong').bind('click', function() {
			$('.test').css('float', 'right');
			$('.test').css('width', '90px');
			$('.test').css('height', '24px');
			$('.user').css('float', 'right');
			$('.user').css('width', '200px');
			$('.user').css('height', '24px');
		});
	});

	function changeCode(code) {
		code.src = 'randomCode.jpg?' + Math.floor(Math.random() * 100);
	}
	function disabledbut() {
		if ($("#ty").attr("checked") == true) {
			$('#Button1').removeAttr("disabled");
			$('.commonBtnLong').css('background', '#C70606');
		} else {
			$('#Button1').attr('disabled', "true");
			$('.commonBtnLong').css('background', '#959595');
		}
	}

	/** 表单数据校验 */
	$(document).ready(function() {
		//点击阅读协议的的同意按钮
		$('.agreenBtn').bind('click', function() {
			$("#ty").attr("checked", true);
			$('#Button1').removeAttr("disabled");
			disabledbut();
		});
		disabledbut();
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
		//用户名
		$("#loginName").formValidator({
			tipID : "logintip",
			onShow : "",
			onFocus : "输入邮箱作为账户名"
		}).inputValidator({
			min : 8,
			max : 30,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "邮箱应为8-30个字符"
		}).regexValidator({
			regExp : "email",
			dataType : "enum",
			onError : "输入的邮箱格式不正确"
		}).ajaxValidator({
			dataType : "json",
			async : true,
			url : "ajaxvalidate_userRegloginNameValidate.action",
			success : function(data) {
				if (data.STATE != 'FAIL') {
					return true;
				}
				return data.MSG;
			},
			onError : "校验未通过，请重新输入",
			onWait : "正在校验，请稍候..."
		});
		//$(":radio[name='merchantBusType']").formValidator({onShow:"",onFocus:"请选择类型"}).inputValidator({min:1,max:1,onError:"请选择类型"});
		//验证码
		$("#randomCode").formValidator({
			tipID : "logintip",
			onShow : "",
			onFocus : "输入图中的字符,不区分大小写"
		}).inputValidator({
			min : 1,
			max : 4,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "验证码应为4个字符"
		}).ajaxValidator({
			dataType : "json",
			async : true,
			url : "ajaxvalidate_randomCodeValidate.action",
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
</script>
<body>
	<jsp:include page="../../IndexHead.jsp" />
	<form id="form1" action="merchantRegister_checkLoginName.action" method="post">
		<input id="type" type="hidden" name="type" value="${type}" />
		<span class="markRed">${typeMsg }</span>

		<div class="registerwrap">
			<c:if test="${PORTAL_IS_REGISTER }">
				<div class='registerLogoWrap'>
					<div class="logofont">注册</div>
				</div>
			</c:if>
			<div class="content">
				<div class="registerpic">
					<img src="<%=path%>/statics/themes/default/images/registerpic.jpg" />
				</div>
				<div class="registerbox">
					<ul class="tabHead">
						<li><a href="memberRegister_checkLoginNameUI.action"> 个人账户</a></li>
						<li class="now"><a href="merchantRegister_checkLoginNameUI.action"> 企业账户</a></li>
					</ul>
					<div class="register">

						<div class="userwrap">
							<input type="text" class="ada-input user" name="loginName" id="loginName" value="${loginName }" title="请输入邮箱" />
						</div>
						<div class="yanzhengma">
							<div class="testwrap">
								<input class="ada-input test" type="text" id="randomCode" name="randomCode" value="${randomCode }" size="8" />
							</div>
							<img alt="验证码" src="randomCode.jpg" onclick="changeCode(this);" height="34" width="90px" />
						</div>
						<span id="logintip"></span>
						<div class="checkbox">
							<input type="checkbox" id="ty" onclick="disabledbut();" class="check" />我同意 <a href="#" onclick="ShowDiv('MyDiv','fade')">《支付平台服务协议》</a>
						</div>
						<div class="submit" style="*margin-left:0px;">
							<input id="Button1" class="commonBtnLong" type="submit" value="下一步" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="fade" class="black_overlay"></div>
		<div id="MyDiv" class="skipZF">
			<div class="top">
				<div class="skipTit">支付平台协议</div>
				<div class="closebtn">
					<img src="<%=path%>/statics/themes/default/images/closebtn2.png" onclick="CloseDiv('MyDiv','fade')" />
				</div>
			</div>
			<div class="maincont">
				<div class="content1">
					<div class="tit2">${COMPANY_FOR}支付服务协议</div>
					<p style="padding: top">
						内容略……
					</p>
				</div>
			</div>
      <div class='btn-position'>
        <input class="btn btn-primary agreenBtn" id="Button5" type="button" onclick="CloseDiv('MyDiv','fade')" value="已阅读并同意此协议" />
      </div>
		</div>
		<jsp:include page="../../foot.jsp" />
	</form>
</body>
</html>
