<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<div class="pageFormContent">
		<form action="FeeDimension_bindOtherProductUI.action" name="bindOtherProducForm" method="post">
			<input type="hidden" name="feeNodeId" value="${feeNodeId }">
			<input type="hidden" name="trxType" value="${trxType }">
		</form>
		<p>
			<label>支付产品列表：</label>
		</p>
		<table class="table">
			<thead>
				<tr>
					<th>绑定</th>
					<th>产品名称</th>
					<th>产品编号</th>
					<th>方式</th>
					<th>接口</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${FeeOtherProductEnumName }" var="enumName">
					<tr>
						<td><input type="checkbox" id="${enumName.value }" 
						<c:forEach items="${listFeeDimensionVO }" var="vo">
							<c:if test="${vo.status eq 100 && vo.payProduct == enumName.value }">
							checked="checked"  
							onclick="bindOtherProduct('${enumName.value }','${enumName.desc }','${feeNodeId }','${vo.id}');"
							</c:if>
						</c:forEach>
						onclick="bindOtherProduct('${enumName.value }','${enumName.desc }','${feeNodeId }','');"  /></td>

						<td><input name="payProductName" type="text" readonly="readonly" value="${enumName.desc }" /></td>
						<td><input name="payProduct" type="text" readonly="readonly" value="${enumName.value }" /></td>
						<td><input name="frpCode" type="text" readonly="readonly" value="${enumName.value }" /></td>
						<td><input name="bankChannelCode" type="text" readonly="readonly" value="${enumName.value }" /></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript">
	function bindOtherProduct(productCode, productName, feeNodeId, dimensionId) {
		var status;
		if ($("#" + productCode).attr("checked") == "checked") {
			status = 100;
		} else {
			status = 101;
		}
		$.post("FeeDimension_bindOtherProduct.action", {
			feeNodeId : feeNodeId,
			productCode : productCode,
			status : status,
			productName : productName,
			dimensionId : dimensionId
		}, function(res) {
			//$("form[name='bindOtherProducForm']").submit();
		}, "json");
	}
</script>

