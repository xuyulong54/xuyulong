<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headScript.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会员注册:进行账户名验证</title>
	<link rel="Stylesheet" href="<%=path%>/statics/themes/default/css/style.css" />
	<link rel="Stylesheet" href="<%=path%>/statics/themes/default/css/login.css" />
	<script>
		$(document).ready(function(){
				$('.test').bind('focus',function(){
				$(this).css('float','right');
				$(this).css('width','90px');
				$(this).css('height','24px');
				$(this).css('line-height','22px');
				});
				$('.user').bind('focus',function(){
				$(this).css('float','right');
				$(this).css('width','200px');
				$(this).css('height','22px');
				$(this).css('line-height','22px');
				});
				$('.commonBtnLong').bind('click',function(){
				$('.test').css('float','right');
				$('.test').css('width','90px');
				$('.test').css('height','22px');
				$('.user').css('float','right');
				$('.user').css('width','200px');
				$('.user').css('height','22px');
				});
			});
		</script>
		<script type="text/javascript">
			function changeCode(code) {
				code.src = 'randomCode.jpg?' + Math.floor(Math.random() * 100);
			}
			function disabledbut(){
				if($("#ty").attr("checked")){
					$('#Button1').removeAttr("disabled");
					$('.commonBtnLong').css('background','#C70606');
				}else{
					$('#Button1').attr('disabled',"true");
					$('.commonBtnLong').css('background','#959595');
				}
			}
			/** 表单数据校验 */
			$(document).ready(function(){
			//点击阅读协议的的同意按钮
			$('.agreenBtn').bind('click',function(){
			$("#ty").attr("checked",true);
			$('#Button1').removeAttr("disabled");
			disabledbut();
			});
			disabledbut();
				$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
		        //用户名
				$("#loginName").formValidator({tipID:"logintip", onShow:"",onFocus:"输入邮箱或手机作为账户名"}).inputValidator({min:8,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"输入邮箱或手机作为账户名"})
				.regexValidator({regExp:"(^1[3|4|5|6|7|8|9][0-9]{1}[0-9]{8}$)|(^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$)",onError:"请输入正确格式的手机或邮箱"})
				.ajaxValidator({
				dataType : "json",
				async : true,
				url : "ajaxvalidate_userRegloginNameValidate.action",
				success : function(data){
					if( data.STATE!='FAIL') {
			            return true;}
						return data.MSG;
				},
				onError : "校验未通过，请重新输入",
				onWait : "正在校验，请稍候..."
				});
				 //验证码
				$("#randomCode").formValidator({tipID:"logintip", onShow:"",onFocus:"输入左图中的字符,不区分大小写"}).inputValidator({min:1,max:4,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"验证码应为4个字符"})
				.ajaxValidator({
				dataType : "json",
				async : true,
				url : "ajaxvalidate_randomCodeValidate.action",
				success : function(data){
					if( data.STATE!='FAIL') {
			            return true;}
						return data.MSG;
				},
				onError : "校验未通过，请重新输入",
				onWait : "正在校验，请稍候..."
				});
				$.formValidator.reloadAutoTip();
			});
	</script>
</head>
<body>
<jsp:include page="../../IndexHead.jsp" />
<form id="form1" action="memberRegister_checkLoginName.action" method="post">
	<div class="registerwrap">
		<div class='registerLogoWrap'>
			<span class="logofont">注册</span>
		</div>
		<div class="content">
	    <div class="registerpic">
	    	<img src="<%=path%>/statics/themes/default/images/registerpic.jpg" />
	    </div>
	    <div class="registerbox">
	      <ul class="tabHead">
	        <li class="now"><a href="memberRegister_checkLoginNameUI.action"> 个人账户</a></li>
	        <li><a href="merchantRegister_checkLoginNameUI.action"> 企业账户</a></li>
	      </ul>
	      <div class="register">
	        <div class="userwrap">
	        	<input type="text" class="ada-input user" name="loginName" id="loginName" value="${loginName }" title="请输入手机或者邮箱" maxlength="30" >
	        </div>
	        <div class="yanzhengma" style="margin-bottom: 30px;">
	          <div class="testwrap">
	         		 <input class="ada-input test"  type="text" name="randomCode" id="randomCode" value="${randomCode}" size="8" maxlength="4"/>
	          </div>
	          <img alt="验证码" src="randomCode.jpg" onclick="changeCode(this);" height="34" width="90px">
	        </div>
	        <span id="logintip"></span>
	        <div class="checkbox">
	        	<input type="checkbox" id="ty" onclick="disabledbut();" class="check"/>我同意
	        	<a  href="#" onclick="ShowDiv('MyDiv','fade')">《支付平台服务协议》</a>
	        </div>
	        <div class="submit" style="*margin-left:0px;">
	       		 <input id="Button1"  class="commonBtnLong" type="submit" value="下一步" />
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
				<img src="<%=path%>/statics/themes/default/images/closebtn2.png"
					onclick="CloseDiv('MyDiv','fade')" />
			</div>
		</div>
		<div class="maincont">
			<div class="content1">
				<div class="tit2">${COMPANY_FOR}支付服务协议</div>
				内容略……
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
