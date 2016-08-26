<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员注册:手机验证</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript">
$(function (){
	//发送短信验证码
	$('#buttonid').bind('click',function(){
		// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
      sendSms('${loginName}', '${loginName}', 'member', '', '', 'buttonid','phoneCodeMsg');
	});

	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
	// 手机验证码
	$("#phoneCode").formValidator({onShow:"",onFocus:"必填项，请输入6位的手机验证码"}).inputValidator({min:6,max:6,onError:"请输入6位的手机验证码"}).regexValidator({regExp:"num",dataType:"enum",onError:"手机验证码必须为数字"})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_phoneCodeValidate.action",
	data:"loginName="+'${loginName}',
	success : function(data){
		if( data.STATE!='FAIL') {
            return true;}
			return data.MSG;
	},
	onError : "校验未通过，请重新输入",
	onWait : "正在校验，请稍候..."
	});
	$.formValidator.reloadAutoTip();
})

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
	<div class="title">会员注册</div>
</div>
       <div class="memFirstSetpFlow mtop10">
        </div>
        <div class="memFlowTex">
		<ul>
			<li  class="red">验证账户名</li>
			<li  style="width: 420px;">设置身份信息</li>
			<li>注册成功</li>
			</ul>
		</div>

        <form id="form1" action="memberRegister_checkPhoneCode.action" method="post">
        <div class="frm-comon reg-inputW mtop15">
				<input type="hidden" name="loginName" value="${loginName}">
				<input type="hidden" name="registerType" value="${registerType}">
				<input type="hidden" name="token" value="${token}">
		 <p class="clearfix">
           <label> &nbsp; </label>
            <span id="phoneCodeMsg" class="markRed">${PAGE_ERROR_MSG }</span>
        </p>
          <p class="clearfix">
            <label>手机：</label> <span style="text-align: left;">${showPhone}</span>
        </p>
		   <p class="clearfix">
            <label>验证码：</label>
            <input  name="phoneCode" id="phoneCode" type="text" style="width:120px;"
							value="${phoneCode}" maxlength="6" onblur="messageOut()"/><span class="markRed" id="phoneCodeMsg">${phoneCodeMsg}</span>
							<input class="btn btn-secondary" type="button" value="获取验证码" id="buttonid" />
        </p>
          <p class="clearfix">
           <label> &nbsp; </label>

        <input class="btn btn-primary" type="submit" value="下一步" />

         </p>
			</div>
			</form>
     <div class="clear"></div>
    </div>
	<div class="clear"></div>
	</div>
<jsp:include page="../../foot.jsp" />
</body>
</html>
