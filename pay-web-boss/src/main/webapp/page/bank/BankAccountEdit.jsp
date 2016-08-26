<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"
		action="bankAccount_editBankAccount.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			<input type="hidden" name="id" value="${model.id }" id="id">
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户银行：</label>
				<s:textfield name="openBank" readonly="true" cssClass="required" value="%{model.openBank }"  
					minlength="6" maxlength="30" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户行地址：</label> 
				<s:textfield name="openBankAddress" readonly="true" cssClass="required" value="%{model.openBankAddress }"   minlength="6" maxlength="90" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户日期：</label>
				<input type="text" name="opendate" class="date" value='<fmt:formatDate value="${model.opendate }" pattern="yyyy-MM-dd"/>' readonly="true"  style="width: 50%" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行账号：</label> 
				<s:textfield name="bankAccount" readonly="true" cssClass="required" value="%{model.bankAccount }"  
					minlength="5" maxlength="20" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">行号：</label> 
				<s:textfield name="bankNo" cssClass="required" value="%{model.bankNo }"  readonly="true" minlength="5" maxlength="15" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行账户名称：</label> 
				<s:textfield name="userName" cssClass="required" value="%{model.userName }" readonly="true"  minlength="6" maxlength="60" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户经办人：</label>
				<s:textfield name="operator" cssClass="required" value="%{model.operator }" readonly="true" minlength="6" maxlength="60" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">合作方式：</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.cooperationWay eq 1 }">checked="checked"</c:if> name="cooperationWay" value="1">监管行</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.cooperationWay eq 2 }">checked="checked"</c:if> name="cooperationWay" value="2">合作行</label>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户性质：</label> 
				<label><input type="radio"  onclick="return false"  name="accountNature" <c:if test="${model.accountNature eq 4 }">checked="checked"</c:if> value="4">备付金汇缴账户</label>
				<div id="accountNatureDiv1" <c:if test="${model.cooperationWay eq 2 }">hidden="hidden"</c:if>>
					<label><input type="radio"  onclick="return false"  name="accountNature" <c:if test="${model.accountNature eq 1 }">checked="checked"</c:if> value="1">备付金存管账户</label>
					<label><input type="radio"  onclick="return false"  name="accountNature" <c:if test="${model.accountNature eq 2 }">checked="checked"</c:if> value="2">自有资金账户</label>
				</div>
				<div id="accountNatureDiv2" <c:if test="${model.cooperationWay eq 1 }">hidden="hidden"</c:if>>
				<label><input type="radio"  onclick="return false"  name="accountNature" <c:if test="${model.accountNature eq 3 }">checked="checked"</c:if> value="3">备付金收付账户</label>
				</div>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户状态：</label>
				<select name="accountStatus" class="combox" >
								<option value="0">请选择</option>
								<option <c:if test="${model.accountStatus eq 1 }">selected="selected"</c:if> value="1">正常</option>
								<option <c:if test="${model.accountStatus eq 2 }">selected="selected"</c:if> value="2">待销户</option>
								<option <c:if test="${model.accountStatus eq 3 }">selected="selected"</c:if> value="3">已销户</option>
				</select>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户类型：</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.accountType eq 1 }">checked="checked"</c:if>  name="amountType" value="1">活期</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.accountType eq 2 }">checked="checked"</c:if>  name="amountType" value="2">定期</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.accountType eq 3 }">checked="checked"</c:if>  name="amountType" value="3">通支</label>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">是否有网银：</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.isOnlineBank eq 1 }">checked="checked"</c:if> name="isOnlineBank" value="1">是</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.isOnlineBank eq 2 }">checked="checked"</c:if> name="isOnlineBank" value="2">否</label>
			</div>
			<div class="unit">
				<div <c:if test="${model.isOnlineBank eq 2 }">hidden="hidden"</c:if> id="onlineBankFeeDiv">
					<label style="width: 20%;text-align: right;">网银管理费：</label>
					<s:textfield name="onlineBankFee" value="%{model.onlineBankFee }"  readonly="true" cssClass="required number" maxlength="10" style="width: 50%" />
				</div>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户管理费：</label>
				<s:textfield name="amountFee" value="%{model.accountMngFee }" readonly="true" cssClass="required number" maxlength="10" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">回单形式：</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.receiptForm eq 1 }">checked="checked"</c:if> name="receiptForm" value="1">邮寄</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.receiptForm eq 2 }">checked="checked"</c:if> name="receiptForm" value="2">自取</label>
				<label><input type="radio"  onclick="return false" <c:if test="${model.receiptForm eq 3 }">checked="checked"</c:if> name="receiptForm" value="3">打印</label>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">预留印鉴记录：</label>
				<s:textarea name="reserveSeal" value="%{model.reserveSeal }" readonly="true"  maxlength="150" rows="3" cols="45"></s:textarea>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">申请人：</label> 
				<s:textfield name="proposer"  value="%{model.proposer }"  readonly="true" cssClass="required"  minlength="6" maxlength="60" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">银行联系人：</label>
				<s:textfield name="linkMan" value="%{model.linkMan }" readonly="true" cssClass="required"  minlength="1" maxlength="600" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">开户信息预留人：</label>
				<s:textfield name="openAccountObligate" value="%{model.openAccountObligate }" readonly="true" cssClass="required"  minlength="6" maxlength="60" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">网银验证码预留人：</label>
				<s:textfield name="onlineBankObligate" value="%{model.onlineBankObligate }" readonly="true" cssClass="required"  minlength="6" maxlength="60" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">大额转款确定预留人：</label>
				<s:textfield name="bigAmounttransferObligate" readonly="true" value="%{model.bigAmounttransferObligate }"   cssClass="required" minlength="6" maxlength="60" style="width: 50%" />
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">质押保证金：</label>
				<fmt:formatNumber  value="${model.pledgefDeposits }" pattern="0.00"></fmt:formatNumber>
				<%-- <s:textfield name="pledgefDeposits" cssClass="required number" maxlength="10" style="width: 50%" /> --%>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户余额：</label>
				<fmt:formatNumber value="${balance }" pattern="0.00"/>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">账户不可用余额：</label>
				<fmt:formatNumber value="${unBalance }" pattern="0.00"/>
			</div>
			<div class="unit">
				<label style="width: 20%;text-align: right;">备注：</label>
				<s:textarea name="comments" value="%{model.comments }"    maxlength="100" rows="3" cols="45"></s:textarea>
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
		var channelID = $("#bankChannelID").val();
		$("#bankChannel").val(channelID);
		var id = $("#id").val();
		if (id.length > 0) {
			var url = "bankAccount_editBankAccount.action";
			$("#form").attr("action", url);
		}

		$("#form").submit();
	}
</script>
