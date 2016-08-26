<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
 <%@include file="/page/inc/pageForm.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="bankBranch_doAdd.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="bankBranchList">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}" />
			<div class="unit">
				<label>支付渠道编号：</label> 
				<select name="frpCode" onchange="getFrpCode(this.value)" id="frpCode" style="font-size:14px;">
					<option value="">请选择</option>
					<c:forEach items="${frpList }" var="model">
						<option value="${model.frpCode }" <c:if test="${model.frpCode eq frpCode }">selected="selected"</c:if>>${model.frpCode }</option>
					</c:forEach>
				</select>
			</div>
			<div class="unit">
			<label>加载银行渠道： </label><input type="radio" checked="checked" onclick="loadSlect()"  name="frps">本行 
			<input type="radio" onclick="loadAll()" name="frps">其它银行
			</div>
			<div class="unit">
				<label>默认银行渠道：</label>
				<select name="defaultBankChannelCode" id="defaultBankChannelCode" style="font-size:14px;">
					<option value="">请选择</option>
					<c:forEach items="${bankChannelList }" var="model">
						<option value="${model.bankChannelCode }" <c:if test="${model.bankChannelCode eq defaultBankChannelCode }">selected="selected"</c:if>>${model.bankChannelCode }</option>
					</c:forEach>
				</select>
			</div>
			<div class="unit">
				<label>备用银行渠道：</label>
				<select name="spareBankChannelCode" id="spareBankChannelCode" style="font-size:14px;">
					<option value="">请选择</option>
					<c:forEach items="${bankChannelList }" var="model">
						<option value="${model.bankChannelCode }" <c:if test="${model.bankChannelCode eq spareBankChannelCode }">selected="selected"</c:if> >${model.bankChannelCode }</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>描述：</label>
				<textarea id="elm1" name="desc" maxlength="300" cols="40" rows="6" class="required">${remark }</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
<!--
	function getFrpCode(frpCode){
		var tempDefaultBankChannelCode = document.getElementById("defaultBankChannelCode");
		var tempSpareBankChannelCode = document.getElementById("spareBankChannelCode");
			tempDefaultBankChannelCode.innerHTML = "";
			tempSpareBankChannelCode.innerHTML = "";
		if(frpCode == null || frpCode == ""){
				tempDefaultBankChannelCode.options.add(new Option("请选择", ""));
				tempSpareBankChannelCode.options.add(new Option("请选择", ""));
			return;
		}
		$.post("bankBranch_getbankChannelCodeByFrpCode.action", {
			'frpCode' : frpCode
		}, function(objarray) {
			if(objarray == null){
				tempDefaultBankChannelCode.options.add(new Option("请选择", ""));
				tempSpareBankChannelCode.options.add(new Option("请选择", ""));
			}else{
				for ( var i = 0; i < objarray.length; i++) {
					tempDefaultBankChannelCode.options.add(new Option(objarray[i].bankChannelCode, objarray[i].bankChannelCode));
					tempSpareBankChannelCode.options.add(new Option(objarray[i].bankChannelCode, objarray[i].bankChannelCode));
				}
			}
		}, "json");
	}
//-->
function loadSlect(){
	var frpCode = $("#frpCode").val();
	getFrpCode(frpCode);
	
}
function loadAll(){
	var tempDefaultBankChannelCode = document.getElementById("defaultBankChannelCode");
	var tempSpareBankChannelCode = document.getElementById("spareBankChannelCode");
	tempDefaultBankChannelCode.innerHTML = "";
	tempSpareBankChannelCode.innerHTML = "";
	$.post("bankBranch_getbankChannelCodeAll.action", {
		'frpCode' : '0'
	}, function(objarray) {
		if(objarray == null){
			tempDefaultBankChannelCode.options.add(new Option("请选择", ""));
			tempSpareBankChannelCode.options.add(new Option("请选择", ""));
		}else{
			for ( var i = 0; i < objarray.length; i++) {
				tempDefaultBankChannelCode.options.add(new Option(objarray[i].bankChannelCode, objarray[i].bankChannelCode));
				tempSpareBankChannelCode.options.add(new Option(objarray[i].bankChannelCode, objarray[i].bankChannelCode));
			}
		}
	}, "json");
}
</script>
