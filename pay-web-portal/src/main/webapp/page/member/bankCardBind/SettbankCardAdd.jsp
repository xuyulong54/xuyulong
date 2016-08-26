<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增银行卡</title>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() {  setPageType('.men-account', '.men-account-card '); })

	$(function () {
		validatorFrom();
		showCardNo();
	})
	//扩大显示银行卡号
	function showCardNo(){
		$("#bankAccountNo").keyup(function () {
			$("#bankcardRrror").html("");
			var name = $("#bankAccountNo").val();
			var leng = name.length;
			if (name == "") {
				$("#bankCardHtml").hide();
			} else {
				$("#bankCardHtml").show();
			}
			$("#bankCardHtml").html(
				this.value.replace(/\s/g, '').replace(/(\d{4})(?=\d)/g, "$1 "));
		});
	}
	//------------------------表单验证------------------------
	function validatorFrom() {
		$.formValidator.initConfig({formID:"form",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
		//省
		$("#province").formValidator({onShow : "",onFocus : "请选择"})
		.functionValidator({
			fun : function(val, elem) {
				if (val == "" || val == "0") {
					return "请选择"
				} else {
					return true;
				}
			}
		});
		//市
		$("#city").formValidator({onShow : "",onFocus : "请选择"})
		.functionValidator({
			fun : function(val, elem) {
				if (val == "" || val == "0") {
					return "请选择"
				} else {
					return true;
				}
			}
		});
		$("#bankType").formValidator({onShow : "",onFocus : "请选择"})
		.functionValidator({
			fun : function(val, elem) {
				if (val == "" || val == "0") {
					return "请选择"
				} else {
					return true;
				}
			}
		});
		$("#bankChannelNo").formValidator({onShow : "",onFocus : "请选择"})
		.functionValidator({
			fun : function(val, elem) {
				if (val == "" || val == "0") {
					return "请选择"
				} else {
					return true;
				}
			}
		});


		$("#bankAccountNo").formValidator({onShow: "",onFocus: "必填项，请输入正确的借记卡号"})
		.inputValidator({min: 8,max: 19,empty: {leftEmpty: false,rightEmpty: false,emptyError: "输入内容两边不能有空符号"},onError: "长度为8到19个字符"})
		.ajaxValidator({
				dataType: "json",
				async: true,
				url: "ajaxvalidate_bindSettBankCard.action",
				success: function (data) {
					if (data.STATE=="SUCC") {
						$("#bankNamep").show();
						$("#bankName").html(data.bankName);
						var bankType=$("#bankType").val();
						if (bankType == "" || bankType == "0") {
							return "请先选择开户银行"
						}
						if(bankType!=data.MSG){
							return "请输入与开户银行对应的卡号"
						}
						return true;

					}
					return data.MSG;
				},
				onError: "校验未通过，请重新输入",
				onWait: "正在校验，请稍候..."
			});
		$.formValidator.reloadAutoTip();
	}



	//加载支行
	$(function(){
		function showBankChannelNo(){
			var bankTypeCode=$("#bankType").val();//行别
			var province=$("#province").val();//省
			var city=$("#city").val();//市
			if(bankTypeCode!=""&&province!=""&&city!=""){
				$.post("bankCardBind_getBankInfoListByBankTypeCodeAndArea.action",{
					'bankTypeCode':bankTypeCode,
					'province':province,
					'city':city
					},function(result){
						$("#bankChannelNo").empty();
						$("#bankChannelNo").append("<option value=''>请选择</option>");
						if(result.remitBankInfoList.length>0){
							for(var i=0;i<result.remitBankInfoList.length;i++){
								$("#bankChannelNo").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
							}
						}
					},"json");
			}
		};
		$("#province").change(function(){
			$.post("bankCardBind_getCityByProvince.action",{province:$(this).children('option:selected').val()},function(result){
				$("#city").empty();
				$("#city").append("<option value=''>请选择</option>");
				for(var i=0;i<result.cityList.length;i++){
					$("#city").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
				}
			},"json");
			showBankChannelNo();
		});
	$("#city").change(function(){showBankChannelNo();});
	$("#bankType").change(function(){showBankChannelNo();});

	})
</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">
		<form id="form" action="bankCardBind_addSettbankCard.action" method="post">
			<div class="frm-comon">
				<div class="headline">
	<div class="title">新增银行卡</div>
</div>

				<c:choose>
					<c:when test="${userInfo.isRealNameAuth == PublicStatusEnum.ACTIVE.value}">
							<div class="ada-wrap mtop10">
							<p class="clearfix">
							<label>银行卡类型：</label>
							<span>借记卡 </span>
							</p>
							<p class="clearfix">
								<label>持卡人姓名：</label>${userInfo.realName}
							</p>
							<p class="clearfix" >
								<label>开户银行地址：</label>
								  <span  class="select_border fl">
                    <span class="select_cont">
											<select name="province" id="province" class="required select"  style="width:150px">
												<option value="">请选择</option>
												<c:forEach items="${provinceList }" var="province">
													<option value="${province.province }" >${province.province}</option>
												</c:forEach>
											</select>
										</span>
									</span>
							</p>
							<p class="clearfix" >
								<label> &nbsp; </label>
								<span  class="select_border">
                  <span class="select_cont">
										<select name="city" id="city" class="required select"  style="width:150px">
											<option value="">请选择</option>
										</select>
									</span>
								</span>
							</p>
							<p style="display: none;">
								<label> &nbsp; </label>
								<label style="width:20px">区</label>
								<select style="width:150px" class="select" name="area" id="area">
									<option value="0">请选择</option>
								</select>
							</p>
							<p class="clearfix">
								<label>开户银行：</label>
								<span  class="select_border fl">
                  <span class="select_cont">
										<select  class="select"  style="width:150px" name="bankType" id="bankType">
											<option value="">请选择</option>
											<c:forEach items="${remitBankTypeList }" var="remitBankType">
												<option value="${remitBankType.typeCode}"> ${remitBankType.typeName}</option>
											</c:forEach>
										</select>
									</span>
								</span>
							</p>
							<p class="clearfix">
								<label> &nbsp; </label>
								<span  class="select_border fl">
                  <span class="select_cont">
										<select style="width:300px" name="bankChannelNo" id="bankChannelNo" class="select" >
											<option value="">请选择</option>
										</select>
									</span>
								</span>
							</p>
							<p class="clearfix">
								<label> &nbsp; </label>
								<span class="text-warning"  name="bankCardHtml" id="bankCardHtml"></span>
							</p>
							<p class="clearfix">
								<label>银行卡号：</label>
								<input style="width:360px;" id="bankAccountNo" value="${bankAccountNo}" type="text" name="bankAccountNo" maxlength="19"/>
							</p>
							<p class="clearfix">
								<label>&nbsp;</label><span class="small">此银行卡的开户名必须为“${userInfo.realName}”，否则提现会失败</span>
							</p>
							<p class="clearfix">
								<label> &nbsp; </label>

								<input id="button1" type="submit"  value="确 定" class="btn btn-primary" />

							</p>
							<br style="clear: both;" />
						</div>
					</c:when>
					<c:otherwise>
					<div class="tipsBox infoWrap">
						<div class="tipsTitle">
							<ul>
								<li><i class="iconfont" >&#xe602;</i></li>
								<li class="tipTxt" >亲，先进行&nbsp;<a href="realnameauth_consentAgreementUI.action">实名认证</a>&nbsp;哦!</li>

							</ul>
						</div>

						<div class="clear"></div>
						</div>
					</c:otherwise>
				</c:choose>

			</div>
		</form>
		<div class="clear"></div>

	</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
<sc
