<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员注册 2.设置身份信息</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">
/** 表单数据校验 */
	$(document).ready(function(){
		$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
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
     	}).compareValidator({desID:"loginPwd",operateor:"=",onError:"两次输入的密码不一致"});
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
		//真实姓名
		$("#realName").formValidator({onShow:"",onFocus:"必填项，长度为2-10个字符"}).inputValidator({min:2,max:10,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-10个字符"}).regexValidator({regExp:"chinese",dataType:"enum",onError:"真实姓名必须为中文"});
		//身份证号
		$("#cardNo").formValidator({onShow:"",onFocus:"必填项，长度为18个字符"}).inputValidator({min:18,max:18,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为18个字符"}).functionValidator({fun:isCardID});
		$.formValidator.reloadAutoTip(); 
	});
</script>
<body>
	<jsp:include page="../../IndexHead.jsp" />
	<div class="container">
<div class="bd-container">
<div class="headline">
	<div class="title">会员注册</div>
</div>
		<div class="memSecondSetpFlow mtop10"></div>
		<div class="memFlowTex">
		<ul>
			<li class="green">验证账户名</li>
			<li class="red" style="width: 420px;">设置身份信息</li>
			<li>注册成功</li>
			</ul>
		</div>
		
		<form id="form1" action="memberRegister_setIdentityInfo.action" method="post">
		<div class="frm-comon reg-inputW mtop15">
			<input type="hidden" name="token" value="${token}"> 
			<input type="hidden" name="registerType" value="${registerType}"> 
			<input type="hidden" name="loginName" value="${loginName}">
			<p class="clearfix">
				<label>登录密码：</label>
				<input type="password" name="loginPwd" id="loginPwd" value="${loginPwd}" maxlength="20">
			</p>
			<p class="clearfix">
				<label>确认登录密码：</label> 
				<input type="password" name="reLoginPwd" id="reLoginPwd" value="${reLoginPwd}" maxlength="20"> 
			</p>
			<p class="clearfix">
				<label>支付密码：</label> 
				<input type="password" name="tradePwd" id="tradePwd" value="${tradePwd}" maxlength="20"> 
			</p>
			<p class="clearfix">
				<label>确认支付密码：</label> 
				<input type="password" name="reTradePwd" id="reTradePwd" value="${reTradePwd}" maxlength="20"> 
			</p>
			<p class="clearfix">
				<label>安全保护问题：</label>
				 <span  class="select_border">  
                            <span class="select_cont"> 
				<select name="question" id="question" class="select" >
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
				<input type="text" name="answer" id="answer" value="${answer}" maxlength="16">
			</p>
			<p class="clearfix">
				<label>预留信息设置：</label>
				<input id="greeting" type="text" name="greeting" value="${greeting}" maxlength="20"/> 
			</p>
			<p class="clearfix">
				<label>真实姓名：</label>
				<input type="text" name="realName" id="realName" value="${realName}" maxlength="10"> 
			</p>
			<p class="clearfix">
				<label>身份证号：</label>
				<input type="text" name="cardNo" id="cardNo" value="${cardNo}" maxlength="18"> 
			</p>
			<p class="clearfix">
			<label> &nbsp; </label>
				
				<input type="submit" value="下一步" class="btn btn-primary" />
				
			</p>
			</div>
		</form>
		<div class="clear"></div>
	</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>