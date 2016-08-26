<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付密码，修改支付密码</title>
<%@include file="/page/include/headScript.jsp"%>
<style type="text/css">
.message{color:rde;}
</style>

</head>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() {  })


$(document).ready(function(){
		$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
		$("#trnewPwd1").formValidator({onShow:"",onFocus:"请输入密码"})
		.functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     	}).ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_tradeEqLoginPwd.action",
		data:"loginName="+'${loginName}',
		success : function(data){
			if( data.STATE!='FAIL') {
	            return true;}
				return data.MSG;
		},
		onError : "校验未通过，请重新输入",
		onWait : "正在校验，请稍候..."
		});
		$("#trnewPwd2").formValidator({onShow:"",onFocus:"必填项，两次输入密码必须一致"})
		.functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     	})
		.compareValidator({desID:"trnewPwd1",operateor:"=",onError:"两次输入的密码不一致"});
		$.formValidator.reloadAutoTip();
		//setPageType('.men-security', '.men-security-info ');
	});
</script>
<body>
<jsp:include page="/page/include/unlogMenu.jsp"></jsp:include>
       <div class="container">
<div class="bd-container">
		<div class="headline">
	<div class="title">找回支付密码</div>
</div>

	<div class="ht"></div>

			<div class="merThirdSetpFlow">
        </div>
        <div class="merFlowTex">
        <ul>
            <li class="green">选择找回方式</li>
            <li  class="green" style="width: 220px"> 验证身份</li>
             <li class="red"  style="width: 220px">重置密码</li>
            <li>重置成功</li>
        </ul>
        </div>
     <div class="ht"></div>
	<form id="form1" action="memberlookfortradepwd_editTradePwd.action" method="post">
	<div class="frm-comon">
			<input type="hidden" name="token" value="${token }">
				<p class="clearfix">
					<label> &nbsp; </label>
					<span class="text-warning">${PAGE_ERROR_MSG}</span>
				</p>
				<p class="clearfix">
					<label>新的支付密码：</label>
					<input type="password" name="trnewPwd1" id="trnewPwd1" maxlength="20"
						value="${trnewPwd1 }">
				</p>
				<p class="clearfix">
					<label>确认新的支付密码：</label>
					<input type="password" name="trnewPwd2" id="trnewPwd2" maxlength="20"
						value="${trnewPwd2}">
				</p>
				<p class="clearfix">
					<label> &nbsp; </label>

					<input class="btn btn-primary" type="submit"
						value="确 定">

				</p>
			<div class="clear"></div>
			</div>
			</form>

		</div></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
