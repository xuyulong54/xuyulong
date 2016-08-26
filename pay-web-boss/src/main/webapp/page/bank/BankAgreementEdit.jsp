<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="bank_addBankAgreement.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id }" id="id">
			<input type="hidden" id="cardType" value="${cardType }" >
			<input type="hidden" id="communicationMode"  value="${communicationMode }" >
			<input type="hidden" id="merchantType1"  value="${merchantType }" >
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">商户编号：</label>
				<s:textfield name="merchantNo" readonly="true" cssClass="required" minlength="1" maxlength="30" style="width: 40%" />
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">合同编号：</label>
				<s:textfield name="agreementNo" readonly="true" cssClass="required" minlength="1" maxlength="50" style="width: 40%" />
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">银行序号：</label>
				<s:textfield name="bankSequence" readonly="true" cssClass="required" minlength="1" maxlength="50" style="width: 40%" />
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">接口名称：</label>
				<s:textfield name="interfaceName" readonly="true"  cssClass="required" minlength="1" maxlength="50" style="width: 40%" />
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">上线时间：</label>
				<input type="text" name="onlineTime" class="date" value='<fmt:formatDate value="${onlineTime }" pattern="yyyy-MM-dd"/>'  style="width: 40%" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">下线时间：</label>
				<input type="text" name="offlineTime" class="date" value='<fmt:formatDate value="${offlineTime }" pattern="yyyy-MM-dd"/>' style="width: 40%" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">合同OA：</label>
				<s:textfield name="contractOANo" readonly="true" cssClass="required" minlength="1" maxlength="50" style="width: 40%" />
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">银行名称：</label>
				<label style="width: 20%;text-align: right;">
					<c:forEach items="${bankCodeWithName }" var="bankName">
						<c:if test="${bankCode eq bankName.key }">${bankName.value }</c:if>
					</c:forEach>
				</label>
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">通信地址：</label>
				<input type="text" name="province" id="province" disabled="disabled" width="65px" value="${provinceName }"/>
				<input type="text" name="city" id="city" disabled="disabled" width="65px" value="${cityName }"/>
				<input type="text" name="area" id="area" disabled="disabled" width="65px" value="${areaName }"/>
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">业务类型：</label>
				<label><input type="radio" name="linkType" <c:if test="${linkType eq 1 }">checked="checked"</c:if> value="1"> B2B</label>
				<label><input type="radio" name="linkType" <c:if test="${linkType eq 2 }">checked="checked"</c:if> value="2"> B2C</label>
				<label><input type="radio" name="linkType" <c:if test="${linkType eq 3 }">checked="checked"</c:if> value="3"> 快捷支付</label>
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">卡种：</label>
				<label><input type="checkbox" id = "cardType1" name="cardType" value="1"> 信用卡</label>
				<label><input type="checkbox" id = "cardType2" name="cardType" value="2"> 借记卡</label>
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">支持的商户类型：</label>
				<label><input type="checkbox" name="merchantType1" value="1"> 个人</label>
				<label><input type="checkbox" name="merchantType1" value="2"> 企业</label>
				<label><input type="checkbox" name="merchantType1" value="3"> 个体工商户</label>
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">大小额限额点：</label>
				<s:textfield name="amountSystem" cssClass="required" minlength="1" maxlength="50" style="width: 40%" />
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">通讯方式：</label>
				<label><input type="checkbox" name="communicationMode" value="1"> HTTP</label>
				<label><input type="checkbox" name="communicationMode" value="2"> HTTPS</label>
				<label><input type="checkbox" name="communicationMode" value="3"> SFTP</label>
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">通讯地址：</label>
				<s:textfield name="communicationAddress" cssClass="required" minlength="1" maxlength="50" style="width: 40%" />
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">银行联系方式：</label>
				<s:textarea name="linkMan" cssClass="required" maxlength="100" rows="3" cols="45"></s:textarea>
			</p>
			<p>
				</br>
			</p>
			<p style="width:99%">
				<label style="width: 20%;text-align: right;">描述：</label>
				<s:textarea name="remark" maxlength="100" rows="3" cols="45"></s:textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	
	var communicationMode = $("#communicationMode").val();
	var communicationModes = communicationMode.split("|");
	var tempCommunicationMode = document.getElementsByName("communicationMode");
	
	for(var i = 0 ; i < communicationModes.length ; i++){
		for(var j = 0 ; j < tempCommunicationMode.length ; j++){
			if(communicationModes[i] == tempCommunicationMode[j].value){
				tempCommunicationMode[j].checked="checked";
			}
		}
	}
	
	var cardType = $("#cardType").val();
	var cardTypes = cardType.split("|");
	var tempCardTypes = document.getElementsByName("cardType");
	for(var i = 0 ; i < cardTypes.length ; i++){
		for(var j = 0 ; j < tempCardTypes.length ; j++){
			if(cardTypes[i] == tempCardTypes[j].value){
				tempCardTypes[j].checked="checked";
			}
		}
	}
	
 	var merchantType = $("#merchantType1").val();
	var merchantTypes = merchantType.split("|");
	var tempMerchantTypes = document.getElementsByName("merchantType1");
	for(var i = 0 ; i < merchantTypes.length ; i++){
		for(var j = 0 ; j < tempMerchantTypes.length ; j++){
			if(merchantTypes[i] == tempMerchantTypes[j].value){
				tempMerchantTypes[j].checked="checked";
			}
		}
	} 
	
	
	
	
	function submitForm() {
		var id = $("#id").val();
		if (id.length > 0) {
			var url = "bank_editBankAgreement.action";
			$("#form").attr("action", url);
		}

		$("#form").submit();
	}

</script>
