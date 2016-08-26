<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>注销确认</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">

$(function(){
	//发送短信验证码
	$('#buttonid').bind('click',function(){
		// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
      sendSms('${currentUserInfo.bindMobileNo}', '${currentUserInfo.loginName}', 'merchant', 'binding', '', 'buttonid','phoneCodeMsg');
	});
	formValidator();
})

function formValidator(){
	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
	//注销原因
	$("#requestDesc").formValidator({onShow:"",onFocus:"必填项，长度为2-30个字符"}).inputValidator({min:2,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-30个字符"});
	//身份证号
	$("#cardNo").formValidator({onShow:"",onFocus:"必填项，长度为18个字符"}).inputValidator({min:18,max:18,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为18个字符"}).functionValidator({fun:isCardID})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_cardNoValidate.action",
	success : function(data){
		if( data.STATE!='FAIL') {
            return true;}
			return data.MSG;
	},
	onError : "身份证号错误，请重新输入",
	onWait : "正在校验，请稍候..."
	});
	// 手机验证码
	$("#phoneCode").formValidator({onShow:"",onFocus:"必填项，请输入6位的手机验证码"}).inputValidator({min:6,max:6,onError:"请输入6位的手机验证码"}).regexValidator({regExp:"num",dataType:"enum",onError:"手机验证码必须为数字"})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_phoneCodeValidate.action",
	success : function(data){
		if( data.STATE!='FAIL') {
            return true;}
			return data.MSG;
	},
	onError : "校验未通过，请重新输入",
	onWait : "正在校验，请稍候..."
	});
	$.formValidator.reloadAutoTip();
}
function messageOut() {
	  var phoneCode = document.getElementById('phoneCode').value;
	     if(phoneCode != ""){
	       $("#phoneCodeMsg").html("");
	     }
	}
</script>
<body>
	<div id="FakeCryptoAgent"></div>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<div class="headline">
				<div class="title">商户注销</div>
			</div>
			<div class='accounts-yellow'>
				<h3><i class="iconfont" style='color:#e60707;font-size:20px;'> &#xe600;</i>注销说明</h3>
				<div class='pWrap'>
					<p >销户申请提交后，系统将立即关闭您的${COMPANY_FOR}支付账户。</p>
					<p >关闭后，您将不能再使用充值、转账、会员支付、结算、交易收款功能。</p>
					<p >系统将在审核后才将您当前的账户余额一次清算完成，并将余额划至您的结算银行卡中。</p>
					<p >如在审核过程中发现您的账户存在风险交易情形的，系统将无法为您注销账户</p>
				</div>
			</div>
			<p class="clearfix" style="line-height: 30px"><span>您当前的账户 可用余额为：</span>
				<span class='num-red'><fmt:formatNumber value="${availableBalance }" pattern="#0.00" />
				</span><span>元，不可用余额为：</span>
				<span class='num-red'><fmt:formatNumber value="${unBalance}" pattern="#0.00" /></span><span>元</span>
			</p>
			<P class='text-warning'>注意：账户余额仅供参考，因在销户审核过程中，可能会有未完成的交易，如退款的情况，会影响您账户余额的变动。请以实际打款金额为准！
			</P>
			<div class='ada-memberinfo'>
				<c:choose>
					<c:when test="${currentUserInfo.isRealNameAuth!=PublicStatusEnum.ACTIVE.value }">
						<div class="tipsBox infoWrap">
							<div class="tipsTitle">
								<ul>
									<li><i class="iconfont" >&#xe602;</i></li>
									<li class="tipTxt" >亲，先&nbsp;<a href="#">实名认证</a>&nbsp;哦！</li>
								</ul>
							</div>
							<div class="clear"></div>
						</div>
					</c:when>
					<c:when test="${currentUserInfo.isMobileAuth!=PublicStatusEnum.ACTIVE.value }">
				    <div class="tipsBox infoWrap">
							<div class="tipsTitle">
								<ul>
									<li><i class="iconfont" >&#xe602;</i></li>
									<li class="tipTxt" > 检测到您账户未绑定手机，请先<a href="merchantmobilebind_bindingMobileUI.action">绑定手机</a></li>
								</ul>
							</div>
							<div class="clear"></div>
						</div>
					</c:when>
					<c:otherwise>
					<form id="form1" action="merchantcancelaccount_authenticate.action" method="post">
						<div class="frm-comon reg-inputW mtop15">
					    <p class="clearfix">
								<label >注销原因：</label>
			        	<input type="text" name="requestDesc" id="requestDesc"  value="${requestDesc}" class="ada-input" >
					    </p>
					    <p class="clearfix">
								<label >身份证号：</label>
			          <input type="text" name="cardNo" id="cardNo" value="${cardNo}" class="ada-input" >
					    </p>
					    <p class="clearfix">
								<label>绑定手机号：</label> <span>${showBindMobile}</span>
									<input type="button" id="buttonid"
										value="获取验证码"  class="btn btn-secondary"  style="margin-left: 5px;"/>
								<span class="markRed" id="phoneCodeMsg"></span>
							</p>
							<p class="clearfix">
								<label>短信验证码：</label>
								<input type="text" name="phoneCode" id="phoneCode" value="${phoneCode}" onblur="messageOut()">
							</p>
					    <jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>
					    <c:if test="${!empty  PAGE_ERROR_MSG }">
						   	<p class="clearfix">
			    			  <label> &nbsp; </label>
							   	<span class="text-warning" id="textWarning">${PAGE_ERROR_MSG}</span>
								</p>
							</c:if>
					    <p class="clearfix">
								<label> &nbsp; </label>
								<input type="button" value="确 认" class="btn btn-primary" onclick="submitForm();return false;">
							</p>
						</div>
					</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="ht"></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.mer-security', '.mer-security-info '); })
function submitForm(){
  var text = "";
  document.getElementById("errorMsg").innerHTML=text;
    var textWarning= document.getElementById("textWarning");
    if (textWarning!=null){
      textWarning .innerHTML=text
    } subForm();
}
</script>
