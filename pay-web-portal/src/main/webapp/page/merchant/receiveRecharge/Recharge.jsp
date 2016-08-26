<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户充值</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<style type="text/css">
.message {
	color: red;
}
</style>
<script type="text/javascript">



	/* 表单数据校验 */
	$(document).ready(function(){
	//充值金额只能输入少于2位的数字。
	var readOnly='';
	$('.chargeM').bind('keydown ',function(){	
	var value=$(this).val();
	if(value.indexOf('.')!==-1){
	var l=value.indexOf('.');
	var str =value.substr(l);
	if(str.length==2){
	readOnly=$(this).val();
	}
	if(str.length>=2){
	$(this).val(readOnly);
	}
	}
	});
	setPageType('.mer-account', '.mer-account-info ');/*页面分类*/
		$.formValidator.initConfig({formID:"rechargeForm",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onSuccess:function(){
			//1.获取DIV元素，并且将其显示。
			$("#rechargeFormDiv").hide();
			$("#rechargeFormInfo").show();
			//2.提交表单
			$("#rechargeForm").submit();
		},onError:function(msg){alert(msg)},inIframe:true});
        // 登录密码
		$("#addAmount").formValidator({onShow:"",onFocus:"充值金额须为整数或小数,小数点后不超过2位"}).regexValidator({regExp:"^(([0-9]+\.[0-9]{1,2})|([0-9]*[1-9][0-9]*))$",onError:"充值金额须为整数或小数,小数点后不超过2位"});
		$.formValidator.reloadAutoTip();
		$("#Button1").removeAttr("disabled");
	});
</script>

<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	  <div class="bd-container ">
	 <div class="men-recharge">
	 <div class="panel-trade">
	 <div class="panel-head">
                    <div class="menu">
                        <span class="fixed"></span>
                        <h4>
                            商户充值</h4>
                    </div>
                    <a class="link" href="merchantReceiveRecharge_listReceiveRechargeRecord.action"><span class="fixed"></span>
                        <h4 class="fl">
                            充值记录查询</h4>
                    </a>
                    <div style="clear: both;">
                    </div>
                </div>
	    <div class="clear">
                </div>		
	  <div class="clear">
                </div>
	    
	     <div class="panel-body">
	     
	     <div id="rechargeFormInfo" style="display: none;">
	     <div class="tipsBox"> 	
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen"> 充值申请已提交,请在打开的页面中完成充值 </li>

			</ul>
		</div>
		<div class="tipsCont"> 
		 您可以<a href="merchantReceiveRecharge_rechargePage.action">继续充值</a>  、
	     <a href="merchantReceiveRecharge_listReceiveRechargeRecord.action">查看充值记录</a>
		</div>
		<div class="clear"></div>
		</div>
	     

</div>
	     
	     <div class="frm-comon" id="rechargeFormDiv">
	<form id="rechargeForm" action="merchantReceiveRecharge_checkAddAmount.action" method="post"
		target="_blank">
		<input name="type" value="${type}" type="hidden">
				<c:if test="${not empty  PAGE_ERROR_MSG }">
				<p class="clearfix">
					<strong ></strong>
				<span class="markRed">	${PAGE_ERROR_MSG}</span>
					</p>
				</c:if>
				<p class="clearfix">
				<label>账户名：</label>
					<span>${currentUser.loginName }</span>	
						
					</p>
					<p class="clearfix">
					<label >账户余额(元)：</label>
					 <fmt:formatNumber pattern="#0.00" value="${availableBalance}"> </fmt:formatNumber>元
						
					</p>
					<p class="clearfix">
					<label >充值金额(元)：</label>
					 <input type="text" name="addAmount" id="addAmount"
						value="${addAmount}" class='chargeM'/>
						
					</p>
					<p class="clearfix">
					<label> &nbsp; </label>
				
					 <input type="submit" value="下一步" id="Button1"
						class="btn btn-primary">
					</p>

		<div class="ht"></div>
	</form>
	</div>
	</div>

	<div class="clear"></div>
	</div>
	 <div class="recharge-foot">
                <h3>
                    新手帮助：</h3>
                <div class="help-bg png">
                </div>
            </div>
	</div>
	<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>