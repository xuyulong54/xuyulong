<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"
		action="bankAccount_addBankAccount.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			<input type="hidden" name="id" value="${model.id }" id="id">
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户银行：</label>
				<select name="openBank" class="required combox ">
						<option value="">请选择</option>
						<%-- <c:forEach items="${bankCodeWithName }" var="openBank">
							<option value="${openBank.value }">${openBank.value }</option>
						</c:forEach> --%>
						<c:forEach items="${remitBankTypeList }" var="openBank">
							<option value="${openBank.typeName }">${openBank.typeName }</option>
						</c:forEach>
				</select>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户行地址：</label>
				<s:textfield name="openBankAddress" cssClass="required"
					minlength="6" maxlength="200"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户日期：</label>
				<input type="text" name="opendate" class="date" readonly="true"    style="width: 50%" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行账号：</label> 
				<s:textfield name="bankAccount" cssClass="required"
					minlength="10" maxlength="20"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">行号：</label> 
				<s:textfield name="bankNo" cssClass="required" minlength="5" maxlength="15"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行账户名称：</label> 
				<s:textfield name="userName" cssClass="required" minlength="6" maxlength="60"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户经办人：</label>
				<s:textfield name="operator" cssClass="required" minlength="4" maxlength="30"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">合作方式：</label>
				<label><input type="radio" checked="checked" onchange="cooperationWayChange(this.value);" name="cooperationWay" value="1">存管银行</label>
				<label><input type="radio" onchange="cooperationWayChange(this.value);" name="cooperationWay" value="2">合作银行 </label>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户性质：</label> 
				<div id="accountNatureDiv3">
					<label><input type="radio" id="radio4" name="accountNature" value="4">备付金汇缴账户</label>
				</div>
				<div id="accountNatureDiv1">
					<label><input type="radio" id="radio1" name="accountNature" value="1">备付金存管账户</label>
					<label><input type="radio" id="radio2" name="accountNature" value="2">自有资金账户</label>
				</div>
				<div id="accountNatureDiv2" hidden="hidden">
					<label><input type="radio" id="radio3" name="accountNature" value="3">备付金收付账户</label>
				</div>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户状态：</label>
				<select name="accountStatus" class="combox">
								<option value="0">请选择</option>
								<option value="1">正常</option>
								<option value="2">待销户</option>
								<option value="3">已销户</option>
				</select>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户类型：</label>
				<label><input type="radio" name="accountType" value="1">活期</label>
				<label><input type="radio" checked="checked" name="accountType" value="2">定期</label>
				<label><input type="radio" name="accountType" value="3">通支</label>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">是否有网银：</label>
				<label><input type="radio" name="isOnlineBank" onchange="isOnlineBankChange(this.value);" value="1">是</label>
				<label><input type="radio" checked="checked" onchange="isOnlineBankChange(this.value);" name="isOnlineBank" value="2">否</label>
			</div>
			<div class="unit">
				<div hidden="hidden" id="onlineBankFeeDiv">
					<label style="width: 20%;text-align: right;">网银管理费：</label>
					<s:textfield name="onlineBankFee" id="onlineBankFee" cssClass="number" maxlength="10"   style="width: 50%" />					
				</div>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户管理费：</label>
				<s:textfield name="accountMngFee" cssClass="required number" maxlength="10"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">回单形式：</label>
				<label><input type="radio" checked="checked" name="receiptForm" value="1">邮寄</label>
				<label><input type="radio" name="receiptForm" value="2">自取</label>
				<label><input type="radio" name="receiptForm" value="3">打印</label>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">预留印鉴记录：</label>
				<s:textfield name="reserveSeal" maxlength="150"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">申请人：</label> 
				<s:textfield name="proposer" cssClass="required"  minlength="4" maxlength="60"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行联系人：</label>
				<s:textfield name="linkMan" cssClass="required"  minlength="4" maxlength="600"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户信息预留人：</label>
				<s:textfield name="openAccountObligate" cssClass="required"  minlength="4" maxlength="60"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">网银验证码预留人：</label>
				<s:textfield name="onlineBankObligate" cssClass="required"  minlength="4" maxlength="60"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">大额转款确定预留人：</label>
				<s:textfield name="bigAmounttransferObligate" cssClass="required" minlength="4" maxlength="60"   style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">质押保证金：</label>
				<s:textfield name="pledgefDeposits" cssClass="required number" maxlength="10"  style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">初始金额：</label>
				<s:textfield name="balance" cssClass="required number" maxlength="10"  style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">备注：</label>
				<s:textarea name="comments" maxlength="100" rows="3" cols="45"></s:textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="submitForm()">保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitForm() {
		///////////////////////////////获取银行类型数值//////////////////////////////////////////
		var cooperationWay = '';
		var cooperationWayObjs = document.getElementsByName("cooperationWay");
                for(var i=0;i<cooperationWayObjs.length;i++){
                    if(cooperationWayObjs[i].checked){
                    	cooperationWay = cooperationWayObjs[i].value;
                            break;
                    }
                }
         if(cooperationWay == ''){
        	 alertMsg.error("请选择银行合作类型");
         	return;
         }
         ///////////////////////////////获取银行账户性质数值/////////////////////////////////////////////////////
         var accountNature = '';
		 var accountNatureObjs = document.getElementsByName("accountNature");
                for(var i=0;i<accountNatureObjs.length;i++){
                    if(accountNatureObjs[i].checked){
                    	accountNature = accountNatureObjs[i].value;
                            break;
                    }
                }
          if(accountNature == ''){
          	alertMsg.error("请选择银行账户性质");
         	return;
         }else{
         	if(cooperationWay == '1'){
         		if(accountNature != '1' && accountNature != '2' && accountNature != '4'){
         			alertMsg.error("请选择存管银行账户性质" + accountNature);
         			return;
         		}
         	}else if(cooperationWay == '2'){
         		if(accountNature != '3' && accountNature != '4'){
         			alertMsg.error("请选择合作银行账户性质");
         			return;
         		}
         	}
         }
         /////////////////////////////获取是否有网银///////////////////////////////////////////////////
         var isOnlineBank = '';
		var isOnlineBankObjs = document.getElementsByName("isOnlineBank");
                for(var i=0;i<isOnlineBankObjs.length;i++){
                    if(isOnlineBankObjs[i].checked){
                    	isOnlineBank = isOnlineBankObjs[i].value;
                            break;
                    }
                }
         if(isOnlineBank == '1'){
         	var onlineBankFee = $("#onlineBankFee").val();
         	if(onlineBankFee == null || onlineBankFee ==""){
         		alertMsg.error("请输入网银管理费");
         		return;
         	}
         	
         }
		$("#form").submit();
	}
	
	/**合作类型更改时的操作*/
	function cooperationWayChange(value){
		if(value=='1'){ //存管银行
			$("#accountNatureDiv2").hide("slow");
			$("#accountNatureDiv1").show("slow");
			$("radio4").attr("checked","checked");
		}else if(value=='2'){ //合作银行
			$("#accountNatureDiv2").show("slow");
			$("#accountNatureDiv1").hide("slow");
			$("radio4").attr("checked","checked");
		}
		//
	}
	
	function isOnlineBankChange(value){
		if(value == '1'){
			$("#onlineBankFeeDiv").show("slow");
		}else if(value == '2'){
		    $("#onlineBankFeeDiv").hide("slow");
		}
	}
</script>
