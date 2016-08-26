<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="sales_moveMerchant.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="salesId" value="${salesId}" />
			<div class="unit">
				<label>新销售人员：</label>
				<input type="hidden" name="salesNameTag2.backSalesId" id="backSalesId22" value="${salesParam.id }" /> 
				<input  class="required readonly" id="backSalesNam22" name="salesNameTag2.backSalesName" value="${salesParam.salesName }" type="text" 
				size="25" readonly />
				<a class="btnLook" href="sales_listSalesBy.action" lookupGroup="salesNameTag2">搜索</a>	
				<span class="info">搜索</span>
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

<script>
// 查找带回的拓展功能
	$.extend({
		bringBackSuggest : function(args) {
			alert(args["backSalesId"]);
			alert(args["backSalesName"]);
			$("#backSalesId22").val(args["backSalesId"]);
			$("#backSalesName22").val(args["backSalesName"]);
		},
		bringBack : function(args) {
			$.bringBackSuggest(args);
			$.pdialog.closeCurrent();
		}
	});
</script>
