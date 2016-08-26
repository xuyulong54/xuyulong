<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"
		action="remitChannelFlowRule_addRemitChannelFlowRule.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="navTabId" value="remitChannel"> <input
			type="hidden" name="callbackType" value="closeCurrent"> <input
			type="hidden" name="forwardUrl" value="">
		<div class="pageFormContent" layoutH="60">
			<table height="324" width="547">
					<tr>
						<td><label style="text-align: right;">打款通道编号：</label></td>
							<td>
								<input type="text" id="channelCode"  name="channelCode" readonly="readonly" value="${channelCode}" size="30" class="required" />
							</td>
							<td>
							<label style="text-align: right;">打款通道名称：</label>
							</td>
							<td>
								<input type="text" id="channelFlowName" value="${channelFlowName}"
								 readonly="readonly"  class="required" size="30" name="channelFlowName"  minlength="1" maxlength="120" />
							</td>
					</tr>
					<tr>
						<td><label style="text-align: right;">业务类型：</label>
						</td>
						<td>
							<!-- 用来底部js循环勾选 -->
							<input type="hidden" id="tempTradeType" value="${tradeType }">
							<c:forEach items="${tradeTypeEnumList }"
								var="tradeTypeEnum" varStatus="index">
								<input type="checkbox" name="tradeType"
								 disabled="disabled"	value="${tradeTypeEnum.value}" />${tradeTypeEnum.desc}</br>
							</c:forEach>
						</td>
						<td><label style="text-align: right;">账户类型：</label>
						</td>
						<td>
							<!-- 用来底部js循环勾选 -->
							<input type="hidden" id="tempAccountType" value="${accountType }">
							<c:forEach items="${bankAccountTypeEnumList }"
								var="bankAccountTypeEnum" varStatus="index">
								<input type="checkbox" name="accountType"
								disabled="disabled"	value="${bankAccountTypeEnum.value}" />${bankAccountTypeEnum.desc}</br>
							</c:forEach>
							<%-- <c:forEach items="${bankAccountTypeEnumList }"
								var="bankAccountTypeEnum" varStatus="index">
								<input type="radio" name="accountType"
								 disabled="disabled"	value="${bankAccountTypeEnum.value}"
									<c:if test="${bankAccountTypeEnum.value eq accountType }">checked="checked"</c:if> />${bankAccountTypeEnum.desc}
							</c:forEach> --%>
						</td>
					</tr>
					<tr>
						<td><label style="text-align: right;">包含的收款银行编号：</label>
						</td>
						<td colspan="3">
							<textarea name="includeBankCode" id="includeBankCode" readonly="true" cols="80" rows="3">
								${includeBankCodes}
							</textarea>
						</td>
					</tr>
					<tr>
						<td><label style="text-align: right;">排除的收款银行编号：</label>
						</td>
						<td colspan="3">
							<textarea name="excludeBankCode" id="excludeBankCode" readonly="true" cols="80" rows="3">
								${excludeBankCodes}
							</textarea>
						</td>
					</tr>
					<tr>
						<td><label style="text-align: right;">最小额：</label>
						</td>
						<td><input type="text" id="minAmount" value="${minAmount}"  disabled="disabled"
							class="number" size="30" name="minAmount" min="0" minlength="1" maxlength="12"/>
						</td>
						<td>
							<label style="text-align: right;">最大额：</label>
						</td>
						<td>
							<input type="text" id="maxAmount" value="${maxAmount}"  disabled="disabled"
							class="number" size="30" name="maxAmount" min="0" minlength="1" maxlength="12" />
						</td>
					</tr>
					<tr>
						<%-- <td>
							<label style="text-align: right;">是否支持加急：</label>
						</td>
						<td>
							<c:forEach items="${remitUrgentEnumList }"
								var="remitUrgentEnum" varStatus="index">
								<input type="radio" name="isUrgent"value="${remitUrgentEnum.value}" disabled="disabled"
									<c:if test="${remitUrgentEnum.value eq isUrgent }">checked="checked"</c:if> />${remitUrgentEnum.desc}
							</c:forEach>
						</td> --%>
						<td><label style="text-align: right;">状态：</label></td>
							<td>
								<c:forEach items="${publicStatusEnumList }"
									var="publicStatusEnum" varStatus="index">
									<input type="radio" name="status"
									 disabled="disabled"	value="${publicStatusEnum.value}"
										<c:if test="${publicStatusEnum.value eq status }">checked="checked"</c:if> />${publicStatusEnum.desc}
								</c:forEach>
							</td>
					</tr>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
		/**业务类型多选框设置*/
		var tradeType = $("#tempTradeType").val();
		var tradeTypes = tradeType.split(",");
		var tempTradeTypes = document.getElementsByName("tradeType");
		for(var i = 0 ; i < tradeTypes.length ; i++){
			for(var j = 0 ; j < tempTradeTypes.length ; j++){
				if(tradeTypes[i] == tempTradeTypes[j].value){
					tempTradeTypes[j].checked="checked";
				}
			}
		}
		
		/**账户类型多选框设置tempAccountType*/
		var accountType = $("#tempAccountType").val();
		var accountTypes = accountType.split(",");
		var tempaccountTypes = document.getElementsByName("accountType");
		for(var i = 0 ; i < accountTypes.length ; i++){
			for(var j = 0 ; j < tempaccountTypes.length ; j++){
				if(accountTypes[i] == tempaccountTypes[j].value){
					tempaccountTypes[j].checked="checked";
				}
			}
		}
	});
</script>
