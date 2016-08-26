<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户修改支付，登录密码页面</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript">
/*页面分类*/
/*$(document).ready(function() { setPageType('.mer-security', '.mer-security-info '); })*/

$(document).ready(function(){
		$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
     	$("#lgoldPwd").formValidator({onShow:"",onFocus:"必填项，请输入原登录密码"}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"请输入原登录密码"})
     	.ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_oldLoginPwdValidate.action",
		success : function(data){
			if( data.STATE!='FAIL') {
	            return true;}
				return data.MSG;
		},
		onError : "校验未通过，请重新输入",
		onWait : "正在校验，请稍候..."
		});
		$("#lgnewPwd1").formValidator({onShow:"",onFocus:"必填项，密码为8-20位数字,字母和特殊符号组合"}).inputValidator({min:8,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"密码为8-20位数字,字母和特殊符号组合"}).compareValidator({desID:"oldPwd",operateor:"!=",onError:"不能与原密码一致"}).regexValidator({regExp:"[a-zA-Z]+",onError:"密码为8-20位数字,字母和特殊符号组合"}).regexValidator({regExp:"[0-9]+",onError:"密码为8-20位数字,字母和特殊符号组合"})
		.functionValidator({
            fun: function (val, elem) {
                if (/^[a-zA-Z0-9]+$/.test(val)) {
                    return "密码应为8-20位数字,字母和特殊符号组合";
                }
                return true;
            }
     	}).ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_loginEqTradePwd.action",
		success : function(data){
			if( data.STATE!='FAIL') {
	            return true;}
				return data.MSG;
		},
		onError : "校验未通过，请重新输入",
		onWait : "正在校验，请稍候..."
		});
		$("#lgnewPwd2").formValidator({onShow:"",onFocus:"必填项，两次输入密码必须一致"}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"密码为8-20位数字,字母和特殊符号组合"}).compareValidator({desID:"lgnewPwd1",operateor:"=",onError:"两次输入的密码不一致"})
		.ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_loginEqTradePwd.action",
		success : function(data){
			if( data.STATE!='FAIL') {
	            return true;}
				return data.MSG;
		},
		onError : "校验未通过，请重新输入",
		onWait : "正在校验，请稍候..."
		});
		
		$("#troldPwd").formValidator({onShow:"",onFocus:"必填项，请输入原支付密码"}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"请输入原支付密码"})
     	.ajaxValidator({
     	    dataType : "json",
     	    async : true,
     	    url : "ajaxvalidate_merchantOldTradePwdValidate.action",
     	    success : function(data){
     	      if( data.STATE!='FAIL') {
     	              return true;}
     	        return data.MSG;
     	    },
     	    onError : "校验未通过，请重新输入",
     	    onWait : "正在校验，请稍候..."
     	    });
		
		$("#trnewPwd1").formValidator({onShow:"",onFocus:"必填项，密码应为8-20位数字,字母和特殊符号组合"}).inputValidator({min:8,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"密码应为8-20位数字,字母和特殊符号组合"}).compareValidator({desID:"lgnewPwd1",operateor:"!=",onError:"不能与登录密码一致"}).regexValidator({regExp:"[a-zA-Z]+",onError:"密码应为8-20位数字,字母和特殊符号组合"}).regexValidator({regExp:"[0-9]+",onError:"密码应为8-20位数字,字母和特殊符号组合"})
		.functionValidator({
            fun: function (val, elem) {
                if (/^[a-zA-Z0-9]+$/.test(val)) {
                    return "密码应为8-20位数字,字母和特殊符号组合";
                }
                return true;
            }
     	}).ajaxValidator({
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
		});
		$("#trnewPwd2").formValidator({onShow:"",onFocus:"必填项，两次输入密码必须一致"}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"密码应为8-20位数字,字母和特殊符号组合"})
		.functionValidator({
            fun: function (val, elem) {
                if (/^[a-zA-Z0-9]+$/.test(val)) {
                    return "密码应为8-20位数字,字母和特殊符号组合";
                }
                return true;
            }
     	}).compareValidator({desID:"trnewPwd1",operateor:"=",onError:"两次输入的密码不一致"}).ajaxValidator({
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
		});
		
		
		$.formValidator.reloadAutoTip();
	});
</script>
<body>
	<div class="container">
<div class="bd-container">

		
			<div class="headline">
	<div class="title">修改登录、支付密码</div>
</div>
			
			
			<div class="frm-comon reg-inputW mtop15">


				<form id="form1" action="merchantpwdedit_firstEditLoginTradePwd.action" method="post">
					<input type="hidden" name="type" value="${type}" />

					<c:if test="${!empty  PAGE_ERROR_MSG }">
						<p class="clearfix">
							<label> &nbsp; </label> <span class="text-warning"> ${PAGE_ERROR_MSG} </span>

						</p>
					</c:if>
					<p class="clearfix">
						<label> &nbsp; </label> ${currentUserVo.userName}
					</p>
					<p class="clearfix">
						<label> 初始登录密码： </label> <input type="password" name="lgoldPwd"
							id="lgoldPwd" value="${lgoldPwd}" maxlength="20"/>
					</p>
					<p class="clearfix">
						<label> 新登录密码： </label> <input type="password" name="lgnewPwd1"
							id="lgnewPwd1" value="${lgnewPwd1}" maxlength="20"/>
					</p>
					<p class="clearfix">
						<label> 确认新登录密码： </label> <input type="password" name="lgnewPwd2"
							id="lgnewPwd2" value="${lgnewPwd2}" maxlength="20"/>

					</p>
					<p class="clearfix">
						<label> 初始支付密码： </label> <input type="password" name="troldPwd"
							id="troldPwd" value="${troldPwd}" maxlength="20"/>

					</p>
					<p class="clearfix">
						<label> 新支付密码： </label> <input type="password" name="trnewPwd1"
							id="trnewPwd1" value="${trnewPwd1}" maxlength="20"/>

					</p>
					<p class="clearfix">
						<label> 确认新支付密码： </label> <input type="password" name="trnewPwd2"
							id="trnewPwd2" value="${trnewPwd2}" maxlength="20"/>
					</p>
					<p class="clearfix">
						<label> &nbsp; </label>  <input id="Button2" class="btn btn-primary"
								type="submit" value="提 交" /> 
					</p>
					<div class="ht"></div>
				</form>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>