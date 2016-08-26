<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<input type="hidden" name="navTabId" value=""> <input type="hidden" name="callbackType" value="closeCurrent"> <input type="hidden" name="forwardUrl" value="">

		<div class="unit">
			<div style="float: left; width: 10%;">
				<label style="width: 90px;">支付产品列表：</label>
			</div>
			<div style="width: 100%; float: left;">
				<c:forEach items="${listFeeDimensionBindVOs }" var="model" varStatus="index">
					<div style="width: 250px; float: left; border: 1px solid #999999; margin-left: 10px;margin-top: 10px;">
						<!-- 循环产品名称 -->
						<label style="width: 240px;background-color: #c3e0f4;" title="产品名称（产品编号）"> <input type="checkbox" id="${model.payProductCode }" name="productCodeCk" <c:if test="${model.selected eq true }">checked="checked"</c:if> onclick="clickPayProductCk(this);"
							value="${model.payProductCode }" />支付产品${index.index+1}：${model.payProductName }-${model.payProductCode } <a href="###" id="showDiv" style="float: right;padding-top: 5px; color:blue;" onclick="hidePayWayDiv('${index.index+1 }');">显示/隐藏</a>
						</label>
						<div id="table_${index.index+1 }">
							<table class="table" style="width: 100%;">
								<c:if test="${not empty model.listPayWay }">
									<c:forEach items="${model.listPayWay }" var="payWay" varStatus="index1">
										<!-- 循环产品下的支付规则 -->
										<tr>
											<td style="width: 100%;margin: 0px;"><label style="width: 5px;">&nbsp;</label> <label> <input type="checkbox" id="${nodeId }-${model.payProductCode }-${payWay.payWayCode }" name="payWayCode" value="${payWay.payWayCode }"
													onclick="addDimension('${nodeId }', '${model.payProductCode }', '${payWay.payWayCode }');" <c:if test="${payWay.selected eq true }">checked="checked"</c:if> />${payWay.payWayCode }
											</label></td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty model.listPayWay }">
									<tr>
										<td style="width: 100%;margin: 0px; color: red;"><label style="width: 5px;">&nbsp;</label> 暂无绑定支付方式!</td>
									</tr>
								</c:if>
							</table>
						</div>
					</div>

					<!-- 清除浮动 -->
					<c:if test="${(index.index+1)%3 == 0 }">
						<br style="clear:both" />
					</c:if>

				</c:forEach>
			</div>
		</div>
	</div>

	<div class="formBar">
		<ul>
			<li><div class="button">
					<div class="buttonContent">
						<button type="button" class="close">关闭</button>
					</div>
				</div></li>
		</ul>
	</div>
</div>


<script>
	// 点击产品事件
	function addDimension(nodeId, productCode, payWayCode) {
		if (nodeId == null || productCode == null || payWayCode == null)
			return;

		$.post("FeeDimension_operateFeeDimension.action", {
			"nodeId" : nodeId,
			"payProductCode" : productCode,
			"payWayCode" : payWayCode
		}, function(res) {
			$.pdialog.reload("FeeDimension_bindPayProductList.action?feeNodeId=${nodeId}", {
				data : {},
				dialogId : "dialogDimension",
				callback : null
			});
		}, "json");
	}

	// 点击支付产品复选框事件
	function clickPayProductCk(obj) {
		if (obj == null)
			return;
		var nodeId = "${nodeId}"; // 规则ID
		var productCode = obj.value; // 支付产品编号
		$.post("FeeDimension_batchFeeDimension.action", {
			"nodeId" : nodeId,
			"payProductCode" : productCode
		}, function(res) {
			$.pdialog.reload("FeeDimension_bindPayProductList.action?feeNodeId=${nodeId}", {
				data : {},
				dialogId : "dialogDimension",
				callback : null
			});
		}, "json");
				
	}

	// 隐藏支付方式div
	function hidePayWayDiv(objNo) {
		$("#table_" + objNo).toggle("normal");
	}
</script>
