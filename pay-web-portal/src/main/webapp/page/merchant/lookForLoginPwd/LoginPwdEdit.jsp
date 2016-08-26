<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改登录密码</title>
<%@include file="/page/include/headScript.jsp"%>

<script type="text/javascript">
	/** 表单数据校验 */
	$(document).ready(function() {

		$.formValidator.initConfig({
			formID : "form1",
			submitOnce:"true",errorFocus:false,
			theme : "ArrowSolidBox",
			mode : "AutoTip",
			onError : function(msg) {
				alert(msg)
			},
			inIframe : true
		});
		// 登录密码
		$("#lgnewPwd1").formValidator({onShow:"",onFocus:"请输入密码"}).functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     	}).ajaxValidator({
			dataType : "json",
			async : true,
			url : "ajaxvalidate_loginEqTradePwd.action",
			data : "loginName=" + '${loginName}',
			success : function(data) {
				if( data.STATE!='FAIL') {
		            return true;}
					return data.MSG;
			},
			onError : "校验未通过，请重新输入",
			onWait : "正在校验，请稍候..."
		});

		$("#lgnewPwd2").formValidator({
			onShow : "",
			onFocus : "必填项，两次输入密码必须一致"
		}).functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     	}).compareValidator({
			desID : "lgnewPwd1",
			operateor : "=",
			onError : "两次输入的密码不一致"
		});

		$.formValidator.reloadAutoTip();
	});
</script>
<body>
<jsp:include page="../../IndexHead.jsp" />

  	<div class="container">
<div class="bd-container">
 	 <div class="headline mbt20">
	<div class="title">找回登录密码</div>
</div>
		<div class="h10"></div>
		
			<div class="merThirdSetpFlow">
        </div>
        <div class="merFlowTex">
       <ul>
            <li class="red"> 验证身份</li>
            <li  style="width: 220px"> 选择找回方式 </li>
             <li style="width: 220px">重置密码</li>
            <li>重置成功</li>
         </ul>
        </div>
<div class="ht"></div>
<div class="frm-comon">	
	<form id="form1" action="merchantLookForLoginPwd_editLoginPwd.action"
		method="post">		
			<input id="loginName" type="hidden" name="loginName" value="${loginName}" /> 
			<input type="hidden" name="token" value="${token}" /> 
			<input type="hidden" name="lookForType" value="${lookForType}" />
			    <p class="clearfix">
					    <label> &nbsp; </label>
					<span class="text-warning">${PAGE_ERROR_MSG} </span>
				</p>
				<p class="clearfix">
					<label>登录名：</label>
				<span>	${loginName}</span>
				</p>
				
				<p class="clearfix">
					<label>新密码：</label>
					<input id="lgnewPwd1" type="password" name="lgnewPwd1" value="${newPwd1}" maxlength="20" style='width:268px'/> 
				</p>
				<p class="clearfix">
					<label>确认新密码：</label>
					<input id="lgnewPwd2" type="password" name="lgnewPwd2" value="${newPwd2}" maxlength="20" style='width:268px'/>
				</p>
				<p class="clearfix">
					<label> &nbsp; </label>
				
					<input class="btn btn-primary" type="submit"
						value="提 交" />
						
				</p>
	</form>
	<div class="clear"></div>
	</div>
	<div class="clear"></div>
	</div></div>
	<div class="ht"></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>