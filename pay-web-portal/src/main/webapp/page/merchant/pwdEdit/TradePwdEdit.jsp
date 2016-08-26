<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户修改支付密码页面</title>
<%@include file="/page/include/headScript.jsp"%>
<style type="text/css">
.message {
	color: red;
}
</style>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.mer-security', '.mer-security-info '); })

	$(document).ready(function() {
		$.formValidator.initConfig({
			formID : "form",
			submitOnce : "true",
			theme : "ArrowSolidBox",
			mode : "AutoTip",
			onError : function(msg) {
				//alert(msg)
			},
			inIframe : true
		});
		 $("#troldPwd").formValidator({ onShow : "",
	          onFocus : "请输入支付密码"
	    }).ajaxValidator(
	    		{
	    		      dataType : "json",
	    		      async : true,
	    		      url : "ajaxvalidate_merchantOldTradePwdValidate.action",
	    		      success : function(data) {
	    		        if (data.STATE != 'FAIL') {
	    		          return true;
	    		        }
	    		        return data.MSG;
	    		      },
	    		      onError : "校验未通过，请重新输入",
	    		      onWait : "正在校验，请稍候..."
	    		    }
	    );
		
		$("#trnewPwd1").formValidator({
			onShow : "",
			onFocus : "请输入支付密码"
		}).functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     	}).compareValidator({
			desID : "oldPwd",
			operateor : "!=",
			onError : "不能与原密码一致"
		}).ajaxValidator({
			dataType : "json",
			async : true,
			url : "ajaxvalidate_tradeEqLoginPwd.action",
			success : function(data) {
				if (data.STATE != 'FAIL') {
					return true;
				}
				return data.MSG;
			},
			onError : "校验未通过，请重新输入",
			onWait : "正在校验，请稍候..."
		});
		$("#trnewPwd2").formValidator({
			onShow : "",
			onFocus : "必填项，两次输入密码必须一致"
		}).functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     	}).compareValidator({
			desID : "trnewPwd1",
			operateor : "=",
			onError : "两次输入的密码不一致"
		});
		$.formValidator.reloadAutoTip();
	});
</script>
<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">

			<div class="headline">
	<div class="title">修改支付密码</div>
</div>
			<div class="frm-comon reg-inputW mtop15">


				<form id="form" action="merchantpwdedit_editTradePwd.action" method="post">
					<input type="hidden" name="type" value="${type}" />

					<p class="clearfix">
						<label>账户名：</label> <span> ${currentUser.loginName}</span>
					</p>

					<p class="clearfix">
						<label>输入现支付密码：</label> <input type="password" name="troldPwd" id="troldPwd" value="${oldPwd}" maxlength="20"/>
					</p>
					<p class="clearfix">
						<label>输入新支付密码：</label> <input type="password" name="trnewPwd1" id="trnewPwd1" value="${trnewPwd1}" maxlength="20"/>
					</p>
					<p class="clearfix">
						<label>再次确认新支付密码：</label> <input type="password" name="trnewPwd2" id="trnewPwd2" value="${trnewPwd2}" maxlength="20"/>
					</p>
					<c:if test="${!empty  PAGE_ERROR_MSG }">
						<p class="clearfix">
							<label> &nbsp; </label> <span class="markRed">${PAGE_ERROR_MSG}</span>
						</p>
					</c:if>
					<p class="clearfix">
						<label> &nbsp; </label> <input id="Button2" class="btn btn-primary"
								type="submit" value="提 交" /> 
					</p>

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