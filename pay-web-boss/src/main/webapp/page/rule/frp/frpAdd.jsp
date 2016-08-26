<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
 <%@include file="/page/inc/pageForm.jsp"%>
<div class="pageContent">
	<label><input type="radio" name="payType" checked="checked" onclick="tab()" value="bankCard">银行卡</label>&nbsp;&nbsp; 
	<label><input type="radio" name="payType" onclick="tab()" value="noBankCard">非银行卡</label>
	<div id="CardFrom">
		<form id="form" method="post" action="frp_addFrp.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="70">
				<input type="hidden" name="navTabId" value="listFrp"> 
				<input type="hidden" name="callbackType" value="closeCurrent"> 
				<input type="hidden" name="forwardUrl" value=""> 
				<input type="hidden" name="payType" value="${PayTypeEnum.BANK_CARD.value}" />
				<div class="unit">
					<label>银行名称：</label>
					<s:select headerValue="请选择" onchange="splicingChannelByBankCode(this.value)" headerKey="" list="#bankDictList" id="bankCode" name="bankCode" listKey="key" listValue="value" />
				</div>
				<div class="unit">
					<label>业务类型：</label>
					<select id="busType" onchange="splicingChannelByBusType(this.value)" name="busType" >
						<option value="">请选择</option>
						<c:forEach items="${bankBusTypeEnumList }" var="bankBusType">
							<option value="${bankBusType.value}">${bankBusType.desc}</option>
						</c:forEach>
					</select>
				</div>
				<div class="unit">
					<label>支付渠道编号：</label>
					<input type="text" id="frpFrpCode" name="frpCode" readonly="readonly" cssClass="required" minlength="1" maxlength="60" size="30" >
				</div>
				<div class="unit">
					<label>状态：</label>
					<select name="status">
						<option value="100" selected="selected">有效</option>
						<option value="101">无效</option>
					</select>	
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
		</div>
		<div id="NoCardFrom">
		<form id="from1" method="post" action="frp_addFrp.action"
			class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="70">
				<input type="hidden" name="navTabId" value="listFrp"> 
				<input type="hidden" name="callbackType" value="closeCurrent"> 
				<input type="hidden" name="forwardUrl" value=""> 
				<input type="hidden" name="payType" value="${PayTypeEnum.NO_CARD.value}" />
				<div class="unit">
					<label>银行名称：</label>
					<input type="text" id="bankCode" value="" name="bankCode"  cssClass="required" minlength="1" maxlength="60" size="30" >
				</div>
				<div class="unit">
					<label>支付渠道编号：</label>
					<input type="text" id="frpFrpCode1" value="" name="frpCode"  cssClass="required" minlength="1" maxlength="60" size="30" >
				</div>
				<div class="unit">
					<label>状态：</label>
					<select name="status">
						<option value="100" selected="selected">有效</option>
						<option value="101">无效</option>
					</select>	
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="submitForm();">保存</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
		
		</div>
</div>
<script type="text/javascript">
	var tempBusType = "未知渠道";
	var tempBankCode = "未知银行";
	//业务类型改变时
	function splicingChannelByBusType(busType){
		tempBusType = busType;
		$("#frpFrpCode").val(tempBankCode + "_" + tempBusType);
	}
	//所选银行改变时
	function splicingChannelByBankCode(bankCode){
		if(bankCode != null && bankCode != ""){
			tempBankCode = bankCode;	
		}else{
			tempBankCode = "未知银行";
		}
		$("#frpFrpCode").val(tempBankCode + "_" + tempBusType);
	}
	function tab(){
		var val=$('input:radio[name="payType"]:checked').val();
		if(val == "bankCard"){
			$("#CardFrom").show();
			$("#NoCardFrom").hide();
		}else if(val == "noBankCard"){
			$("#NoCardFrom").show();
			$("#CardFrom").hide();
		}
	}
	
	function submitForm(){
		var frpFrpCode=  $("#frpFrpCode1").val();
		if(/[\u4e00-\u9fa5]/.test(frpFrpCode)){
			alertMsg.warn("支付渠道编号不能为中文");
			return ;
		}
		$("#from1").submit();
	}
</script>