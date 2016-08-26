<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户注册 2.设置身份信息 </title>
<style type="text/css">
.message {color: red;}
</style>
<script type="text/javascript">
	/** 表单数据校验 */
$(document).ready(function(){
	$('#buttonid').bind('click',function(){
		// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
      sendSms($('#bindingPhone').val(), '${loginName}', 'merchant', 'unbinding', '', 'buttonid','phoneCodeMsg');
		});
	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){
		//alert(msg)
		},inIframe:true});
       // 登录密码
	$("#loginPwd").formValidator({onShow:"",onFocus:"请输入密码"})
	.functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     });
	$("#reLoginPwd").formValidator({onShow:"",onFocus:"必填项，两次输入密码必须一致"})
	.functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     })
     .compareValidator({desID:"loginPwd",operateor:"=",onError:"两次输入的密码不一致"});
	// 支付密码
	$("#tradePwd").formValidator({onShow:"",onFocus:"请输入密码"})
	.functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     })
     .compareValidator({desID:"loginPwd",operateor:"!=",onError:"支付密码与登录密码不能相同"});

	$("#reTradePwd").formValidator({onShow:"",onFocus:"必填项，两次输入密码必须一致"})
	.functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     })
	.compareValidator({desID:"tradePwd",operateor:"=",onError:"两次输入的密码不一致"});

	$("#question").formValidator({onShow:"",onFocus:"请选择"}).functionValidator({
	    fun:function(val,elem){
	        if(val==""||val=="0"){
	         return "请选择"
		    }else{
			 return true;
		    }
		}
	});
	// 安全保护答案
	$("#answer").formValidator({onShow:"",onFocus:"必填项，长度为2-16个字符"}).inputValidator({min:2,max:16,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-16个字符"});
	// 预留信息
	$("#greeting").formValidator({onShow:"",onFocus:"必填项，长度为5-20个字符"}).inputValidator({min:5,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为5-20个字符"});
	// 手机号码
	$("#bindingPhone").formValidator({onShow:"",onFocus:"必填项，请输入11位的手机号码"}).inputValidator({min:11,max:11,onError:"手机号码必须是11位的"}).regexValidator({regExp:"mobile",dataType:"enum",onError:"手机号码格式不正确"});
	// 手机验证码
	$("#phoneCode").formValidator({onShow:"",onFocus:"必填项，请输入6位的手机验证码"}).inputValidator({min:6,max:6,onError:"请输入6位的手机验证码"}).regexValidator({regExp:"num",dataType:"enum",onError:"手机验证码必须为数字"})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_phoneCodeValidate.action",
	data: "loginName="+'${loginName}',
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
	
function messageOut() {
	  var phoneCode = document.getElementById('phoneCode').value;
	     if(phoneCode != ""){
	       $("#phoneCodeMsg").html("");
	     }
	}
</script>
</head>
<body>
	<jsp:include page="../../IndexHead.jsp" />

	<div class="container">
<div class="bd-container">
<div class="headline">
	<div class="title">商户注册</div>
</div>
		<div class="merSecondSetpFlow"></div>
		<div class="merFlowTex">
		<ul>
			<li  class="green">验证账户名</li>
			<li  class="red" style="width: 220px">设置身份信息</li>
			<li  style="width: 220px">设置商户信息</li>
			<li  >注册成功</li>
			</ul>
		</div>

		<div class="frm-comon reg-inputW mtop15">
		<form id="form1" action="merchantRegister_setIdentityInfo.action" method="post">
			<input type="hidden" name="token" value="${token}">
			<p class="clearfix">
			<label>	登录密码：</label>
				<input id="loginPwd" type="password" name="loginPwd" value="${loginPwd}" maxlength="20" />
			</p>
			<p class="clearfix">
				<label>确认登录密码：</label>
				<input id="reLoginPwd" type="password" name="reLoginPwd" value="${reLoginPwd}" maxlength="20"/>
			</p>
			<p class="clearfix">
				<label>支付密码：</label>
				<input id="tradePwd" type="password" name="tradePwd" value="${tradePwd}" maxlength="20" >
			</p>
			<p class="clearfix">
				<label>确认支付密码：</label>
				<input id="reTradePwd" type="password" name="reTradePwd" value="${reTradePwd}" maxlength="20" >
			</p>
			<p class="clearfix">
				<label>安全保护问题：</label>
				<span  class="select_border">
                            <span class="select_cont">
				<select name="question" id="question" class="select" 	>
						<option value="">请选择</option>
					<c:forEach items="${questions}" var="qu">
						<option value="${qu.value}"
							<c:if test="${qu.value == question }">selected="selected"</c:if>>${qu.desc}</option>
					</c:forEach>
				</select>
				</span></span>
			</p>
			<p class="clearfix">
				<label>安全保护答案：</label>
				<input id="answer" type="text" name="answer" value="${answer}" maxlength="16"/>
			</p>
			<p class="clearfix">
				<label>预留信息设置：</label>
				<input id="greeting" type="text" name="greeting" value="${greeting}" maxlength="20"/>
			</p>
			<p class="clearfix">
				<label>输入手机号码：</label>
				<input type="text" name="bindingPhone" id="bindingPhone" value="${bindingPhone}" maxlength="16"/>
			</p>
			<p class="clearfix">
				<label>短信验证码：</label>
				<input id="phoneCode" type="text" name="phoneCode" value="${phoneCode}" maxlength="6" onblur="messageOut()">

				<input type="button" class="btn btn-secondary" id="buttonid" value="获取验证码" style="margin-left: 5px;">


			</p>
			<p class="clearfix">
			<label> &nbsp; </label>
			<span class="text-warning" id="phoneCodeMsg">${phoneCodeMsg}</span>
			</p>
			<p class="clearfix">
				<label> &nbsp; </label>

				<input type="submit" value="下一步" class="btn btn-primary"/>

			</p>
		</form>
		</div>
		<p class='clear'></p>
	</div></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
