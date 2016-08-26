<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript"
	src="<%=path%>/statics/cfca/js/SignCertManager.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提现</title>

</head>
<script type="text/javascript">
  $(document).ready(function(){
  	/*页面分类*/
  	setPageType('.men-account', '.men-account-info ');
    $('.selectcard:odd').after("</br>");
  //  $('#withdrawAmount').focus();
    });

	$(function(){
		validatorFrom();
	})

	//------------------------表单验证------------------------
	function validatorFrom(){
		$.formValidator.initConfig({formID : "form1",submitOnce:"true",errorFocus:false,theme : "ArrowSolidBox",mode : "AutoTip",onError : function(msg) {alert(msg);},inIframe : true});
		$("#withdrawAmount").formValidator({onShow : "金额必须为正整数或小数,小数点后不超过2位",onFocus : ""})
		.regexValidator({regExp : "^(([0]\.[0-9]{1,2})|([1-9][0-9]*\.[0-9]{1,2})|([1-9][0-9]*))$",onError : "金额必须为正整数或小数,小数点后不超过2位"})
		.ajaxValidator({
			dataType : "json",
			async : true,
			url : "ajaxvalidate_withdrawAmountValidate.action",
			success : function(data) {
				if( data.STATE!='FAIL') {
					// $('.submit').bind('click',subForm);
		            return true;}
					return data.MSG;
			},
			onError : "校验未通过，请重新输入",
			onWait : "正在校验，请稍候..."
		});
		$.formValidator.reloadAutoTip();
	}
	function submitForm() {
	  var text = "";
	  document.getElementById("errorMsg").innerHTML=text;
	    var textWarning= document.getElementById("textWarning");
	    if (textWarning!=null){
	      textWarning .innerHTML=text
	    }

		var password = funGetPassword();
		if (password == null) {
			alert("支付密码为空或者长度不足");
			return;
		}
		//验证是否安装证书
		//是否为数字证书用户
		if('${IS_SN_USER}'=='false'){
			$("#form1").submit();
		}else{
				if ('${CURRENT_SN}' == '') {
				alert("未检测到证书,请先安装证书!");
				$("#addCaIntro").show();
				return;
				}
				//设置签名信息
				SetMemberSignMessage();
				//选择证书
			if (!SelectSignCert('${CURRENT_SN}')) {
				alert("未检测到证书,请先安装证书!");
				$("#addCaIntro").show();
				return;
			}
			//对数据签名
			if (SignPKCS7()) {
				var withdrawAmount = $("#withdrawAmount").val();
				var radioValue = $("input:radio:checked").val();
				var bankCardInfoArr = radioValue.split("$");
				ChangeDiv('#MyDiv',bankCardInfoArr[0],bankCardInfoArr[1],bankCardInfoArr[2],withdrawAmount);
			}
		}

		//$("#form1").submit();
	}

	function submitRefundData(){
		CloseDownDiv('#MyDiv');
		$("#form1").submit();
	}


	 //弹出隐藏层
	function ChangeDiv(show_div,cardName,cardNo,realName,amount){
    showAlert(show_div,'.popupMask');//show弹出框
    centerMask('.popupMask',show_div);//居中弹出框
		$('#powerpass').hide(); //隐藏密码控件
		$("#cardName").html(cardName);
		$("#cardNo").html(cardNo);
		$("#realName").html(realName);
		$("#amount").html(amount);
		$("#button1").attr({"disabled":"disabled"});//禁用按钮
	}
	//关闭弹出层
	function CloseDownDiv(show_div){
    $('#powerpass').show();       //显示密码控件
    hideAlert(show_div,'.popupMask')//隐藏弹出框
		$("#cardName").html("");
		$("#cardNo").html("");
		$("#realName").html("");
		$("#amount").html("");
		$("#button1").removeAttr("disabled");//启用按钮
	}
</script>
<body>
	<div id="FakeCryptoAgent"></div>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<div class="men-recharge">
				<div class="panel-trade">
					<div class="panel-head">
						<div class="menu">
							<span class="fixed-bottom"></span>
							<h4>会员提现</h4>
						</div>
						<a class="link" href="withdraw_listRemittanceRecordUI.action"><span
							class="fixed"></span>
							<h4 class="fl">提现记录查询</h4>
						</a>
						<div style="clear: both;"></div>
					</div>
					<div class="clear"></div>
					<div class="panel-body">
						<div class="withdraw-edit">
							<div class="frm-comon">
								<form id="form1" action="withdraw_withdraw.action" method="post">
									<p class="clearfix">
										<label>可用余额：</label> <span class="text-warning num">
										${availableBalance }
											</span> 元
									</p>

									<c:if test="${empty bankCardList }">

										<div class="bank-ico"></div>
										<div class="title">添加绑定银行卡，开启提现之旅！</div>
										<div class="link">
											<a href="bankCardBind_addSettbankCardUI.action"><span>+</span>添加绑定银行卡</a>
										</div>
									</c:if>

									<c:if test="${ not empty bankCardList }">
										<div class="card-info">
											<ul>
												<li><p class="clearfix"><label>提现银行：</label></p>
												</li>
												<li>
													<c:forEach items="${bankCardList }" var="v">
														<a class="selectcard"> <input type="radio"
															name="bankAccountNo"
															value="${v.bankName}$${v.bankAccountNo}$${v.bankAccountName}"
															checked="checked" /> <label for="${v.bankCode}"
															class="${v.bankCode}"></label> (${v.tempBankAccountNo}) </a>
													</c:forEach>
												</li>
												<li>
													<a href="bankCardBind_addSettbankCardUI.action"><span>+</span>添加</a>
												</li>
											</ul>
											<div class="clear"></div>
										</div>
										<p class="clearfix">
											<label>提现金额(元)：</label> <input id="withdrawAmount"
											type="text" name="withdrawAmount" value="${withdrawAmount}" />
										</p>
										<jsp:include page="/page/include/memberTradePwd.jsp"></jsp:include>
										<c:if test="${!empty  PAGE_ERROR_MSG }">
                    <p class='markRed' id="textWarning">
                      ${PAGE_ERROR_MSG}
                    </p>
                    </c:if>
										<p class="clearfix">
											<strong>&nbsp; </strong>
											<input class="btn btn-primary"
												type="button" id="button1" value="确 定" onclick="submitForm();return false;" />
											<span id="addCaIntro" style="display: none;">
												<a href="memberca_addCAIntro.action">申请证书</a>
											</span>
										</p>
									</c:if>
									<br style="clear: both;" />

									<!-- 证书签名信息-->
									<p class="clearfix">
										<!-- 签名原文 -->
										<input type="hidden" id="textareaSignedContent" />
										<!-- 签名结果 -->
										<input type="hidden" name="textareaSignature"
											id="textareaSignature" />
									</p>
								</form>
							</div>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="recharge-foot">
				<h3>新手帮助：</h3>
				<div class="help-withdrawbg png"></div>
			</div>
		</div>
	</div>
	<div class="popupMask"></div>
	<div id="MyDiv" class="popupBox">
		<div class="top">
			<a href="#" class="btnClost" onclick="CloseDownDiv('#MyDiv');"></a>
		</div>
		<p>
			<strong>银行名称：</strong> <span id="cardName" class="font"></span>
		</p>
		<p>
			<strong>银行卡号：</strong> <span id="cardNo" class="font"></span>
		</p>
		<p>
			<strong>持卡人姓名：</strong> <span id="realName" class="font"></span>
		</p>
		<p>
			<strong>提款金额：</strong> <span id="amount" class="font"></span>
		</p>
		<div class="btnDiv">
			<strong> &nbsp; </strong> <input class="submitBtn" id="btn1"
				type="button" value="确定" onclick="submitRefundData();" /> <input
				class="cancelBtn" style=" margin-left:15px;" id="btn2"
				type="button" value="取消" onclick="CloseDownDiv('#MyDiv');" />
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
