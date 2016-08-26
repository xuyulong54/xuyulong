<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form1" method="post" action="payRule_addPayRule.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
		    <input type="hidden" name="navTabId" value="listPayRule">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<div class="unit">
				<label>规则名称：</label>
				<input type="text" name="ruleName" class="required" minlength="2" maxlength="100" size="40" />
			</div>
			<!-- 
			<div class="unit">
				<label>关联支付产品：</label>
				<input type="hidden" id="relatePayProduct" name="payProduct.payProductCode">
				<input type="text" name="payProduct.payProductName" class="readonly" size="40" />
				<a class="btnLook" href="payProduct_listPayProductForLookup.action" lookupGroup="payProduct">搜索</a>	
				<span class="info">搜索</span>
			</div> -->
			<div class="unit">
				<label>规则类型：</label>
				<select name="ruleType" class="required">
					<option value="">--请选择--</option>
					<c:forEach items="${PayRuleTypeEnum }" var="PayRuleType">
						<option value="${PayRuleType.value }">${PayRuleType.desc }</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</div>
			<div class="unit">
				<label>规则描述：</label>
				<textarea rows="6" cols="35" name="description" minlength="3" maxlength="300"></textarea>
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
