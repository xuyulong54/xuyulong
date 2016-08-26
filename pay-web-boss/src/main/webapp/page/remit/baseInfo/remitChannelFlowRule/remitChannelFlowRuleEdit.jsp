<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"
		action="remitChannelFlowRule_editRemitChannelFlowRule.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="navTabId" value="remitChannelFlowRuleList"> <input
			type="hidden" name="callbackType" value="closeCurrent"> <input
			type="hidden" name="forwardUrl" value="">
		<input type="hidden" name="id" value="${id }">
		<input type="hidden" name="version" value="${version }">
		<div class="pageFormContent" layoutH="60">
			<table height="324" width="547">
					<tr>
						<td><label style="text-align: right;">打款通道编号：</label></td>
							<td>
								<input type="text" id="channelCode" name="channelCode" readonly="readonly" value="${channelCode}" size="30" class="required" />
							</td>
							<td>
							<label style="text-align: right;">打款通道名称：</label>
							</td>
							<td>
								<input type="text" id="channelFlowName" value="${channelFlowName}"
								 readonly="readonly" class="required" size="30" name="channelFlowName"  minlength="1" maxlength="120" />
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
									value="${tradeTypeEnum.value}" />${tradeTypeEnum.desc}</br>
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
									value="${bankAccountTypeEnum.value}" />${bankAccountTypeEnum.desc}</br>
							</c:forEach>
							<%-- <c:forEach items="${bankAccountTypeEnumList }"
								var="bankAccountTypeEnum" varStatus="index">
								<input type="radio" name="accountType"
									value="${bankAccountTypeEnum.value}"
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
							<input type="hidden" name="includeBankTypeCode" id="includeBankTypeCode" value="${includeBankCode}"/>
							<button type="button" onclick="getIncludeBankCode()" id="11"><font color="blue">添加</font></button>
						</td>
					</tr>
					<tr>
						<td><label style="text-align: right;">排除的收款银行编号：</label>
						</td>
						<td colspan="3">
							<textarea name="excludeBankCode" id="excludeBankCode" readonly="true" cols="80" rows="3">
								${excludeBankCodes}
							</textarea>
							<input type="hidden" name="excludeBankTypeCode" id="excludeBankTypeCode" value="${excludeBankCode}"/>
						</td>
					</tr>
					<tr>
						<td><label style="text-align: right;">最小额：</label>
						</td>
						<td><input type="text" id="minAmount" value="${minAmount}"
							class="number" size="30" name="minAmount" min="0" minlength="1" maxlength="12"/>
						</td>
						<td>
							<label style="text-align: right;">最大额：</label>
						</td>
						<td>
							<input type="text" id="maxAmount" value="${maxAmount}"
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
								<input type="radio" name="isUrgent"value="${remitUrgentEnum.value}"
									<c:if test="${remitUrgentEnum.value eq isUrgent }">checked="checked"</c:if> />${remitUrgentEnum.desc}
							</c:forEach>
						</td> --%>
						<td><label style="text-align: right;">状态：</label></td>
							<td>
								<c:forEach items="${publicStatusEnumList }"
									var="publicStatusEnum" varStatus="index">
									<input type="radio" name="status"
										value="${publicStatusEnum.value}"
										<c:if test="${publicStatusEnum.value eq status }">checked="checked"</c:if> />${publicStatusEnum.desc}
								</c:forEach>
							</td>
					</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" id="addSave">确定</button>
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
	
	/**获取包含的银行编号*/
	function getIncludeBankCode(){
		var windowSize = "left=100,top=100,height=500,width=600,status=no,toolbar=no,menubar=no,location=no";
		var includeBankCode = $("#includeBankCode").val();
		var excludeBankCode = $("#excludeBankCode").val();
		var url = "remitChannelFlowRule_bankChannelSelectList.action?includeBankStr="+includeBankCode+"&excludeBankStr="+excludeBankCode;
		var child=window .open(url,"child",windowSize); 
		
	}
	
	$.extend({
			bringBackSuggest : function(args) {
				var backFlag = args["backFlag"];
				if(backFlag == '1'){
					$("input[id='channelCode']").val(args["channelCode"]);
				}else{
					alert("异常");
				}
			},
			bringBack : function(args) {
				$.bringBackSuggest(args);
				$.pdialog.closeCurrent();
			}
		});
		
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
</script>
