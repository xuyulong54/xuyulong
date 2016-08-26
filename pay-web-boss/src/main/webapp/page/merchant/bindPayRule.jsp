<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<script>
	function getPayProductInfo(obj){
		if(obj == null || obj == ""){
			return ;
		}
		// 获取支付规则下所有的支付产品列表和支付方式列表
		$.post("payRule_getPayProductInfo.action", {"ruleId": obj}, function(res){
			if(res.STATE=="FAIL"){
				alertMsg.error(res.MSG);
			}else{
				var printOutMsg = res.MSG.printOutMsg;
				
				$("#ruleDiv").show("slow");
				
				$("#ruleDesc").html(printOutMsg);
			}
		},"json");
	}
	
	$(function(){
		var printOutMsg = "${printOutMsg}";
		if(printOutMsg != "" && printOutMsg != null){
			$("#ruleDiv").show("slow");
				
			$("#ruleDesc").html(printOutMsg);
		}
	});
	
</script>

<div class="pageContent">
	<form id="form" method="post" action="payRule_bindPayRule.action" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="merchantList" /> 
			<input type="hidden" name="callbackType" value="closeCurrent" /> 
			<input type="hidden" name="forwardUrl" value="" />
			<s:hidden id="id" name="id" />
			<input type="hidden" name="userNo" id="userNo" value="${merchantNo }" />
			<div class="unit">
				<label>支付规则：</label> 
				<select name="payRuleId" onchange="getPayProductInfo(this.value);">
					<option value="">请选择</option>
					<c:forEach items="${payRuleList }" var="rule">
						<option value="${rule.id }" <c:if test="${rule.id eq payRuleId }">selected="selected"</c:if> >${rule.ruleName }</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit" id="ruleDiv" style="display: none;">
				<label>支付规则描述：</label>
				<label id="ruleDesc" style="width: 500px;"></label>
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
