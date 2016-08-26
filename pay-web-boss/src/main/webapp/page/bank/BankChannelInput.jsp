<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<div class="pageContent">
	<form id="form" method="post" action="bank_addBankChannel.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value=""> 
			<input type="hidden" name="id" value="${bankChannelCode }" id="id">
			<input type="hidden" name="bankCode" id="bankCode" > 
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行序号：</label> 
				<input type="hidden" name="bankAgreementId" id="bankAgreementId" /> 
				<input type="hidden" name="merchantNo" id="merchantNo" /> 
				<input class="readonly" id=bankSequence name="bankSequence" type="text" size="45" /> 
				<a class="btnLook" href="bank_BankAgreementLookupList.action" lookupGroup="bankChannel">搜索</a> 
				<span class="info">搜索</span>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行账户：</label> <input type="hidden"
					name="bankAccountId" id="bankAccountId" /> <input
					 class="readonly" id=bankAccountName
					name="bankAccountName" type="text" size="45" /> <a id="bankAccountLookupList" class="btnLook"
					href="bankAccount_bankAccountLookupList.action"
					lookupGroup="bankChannel">搜索</a> <span class="info">搜索</span>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行渠道编号：</label>
				<s:textfield id="bankChannelCode" name="bankChannelCode"
					cssClass="required" minlength="1" maxlength="30" size="45" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行渠道名称：</label>
				<s:textfield id="bankChannelName" name="bankChannelName" readonly="true"
					cssClass="required"   minlength="1" maxlength="100" size="45" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行名称： </label>
				<s:textfield id="bankName" name="bankName" readonly="true"
					cssClass="draw required" minlength="1" maxlength="45" size="45" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">状态：</label> <select name="status" class="combox">
					<option value="101"
						<c:if test="${status eq 101 }">selected="selected"</c:if>>无效</option>
					<option value="100"
						<c:if test="${status eq 100 }">selected="selected"</c:if>>有效</option>
				</select>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">落地行名称：</label>
				<s:textfield id="landingBankName" name="landingBankName"
					cssClass="required" minlength="1" maxlength="45" size="45" />
				<input type="hidden" id="province" name="province" />
				<input type="hidden" name="city" id="city" />
				<input	type="hidden" name="area" id="area" /> 
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">描述：</label>
				<s:textarea name="desc" maxlength="100" rows="3" cols="40"></s:textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="submitForm()" >保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close" >取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitForm() {
		var id = $("#id").val();
		if (id.length > 0) {
			var url = "bank_editBankChannel.action";
			$("#form").attr("action", url);
		}

		$("#form").submit();
	}

	//查找带回数据
	 $.extend({
			bringBackSuggest : function(args) {
				var backFlag = args["backFlag"];
				if(backFlag == '1'){
					$.post("bank_getAddressInfo.action", {
						'provinceNo' : args["province"],
						'cityNo' : args["city"],
						'areaNo' :  args["area"],
						'bankChannelCode' : args["bankChannelCode"],
						'linkType' : args["linkType"]
						}, function(objarray) {
							$("input[id='bankAgreementId']").val(args["bankAgreementId"]);
							$("input[id='bankSequence']").val(args["bankSequence"]);
							$("input[id='bankChannelCode']").val(objarray[3]);
							$("input[id='bankChannelName']").val(args["bankChannelName"]+objarray[0]+objarray[1]+args["bankchannelNamePay"]);
							$("input[id='bankName']").val(args["bankName"]);
							$("input[id='landingBankName']").val(objarray[0]+objarray[1]+objarray[2]);
							$("input[id='bankCode']").val(args["bankCode"]);
		//					var a = document.getElementById("bankAccountLookupList");  
		//					a.href = "bankAccount_bankAccountLookupList.action?bankName=" + args["bankName"];
						}, "json");
				}else{
					$("input[id='bankAccountId']").val(args["bankAccountId"]);
					$("input[id='bankAccountName']").val(args["bankAccountName"]);
				}
			},
			bringBack : function(args) {
				$.bringBackSuggest(args);
				$.pdialog.closeCurrent();
			}
		});

	
	
</script>
