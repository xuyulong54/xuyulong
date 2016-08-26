<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<div class="pageHeader">
	<form id="pagerForm" name="feeCalculateWayAllListForm" onsubmit="return navTabSearch(this);" action="FeeCalculateWay_feeCalculateWayByNodeIdList.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<z:permission value="fee:calculateWay:add">
				<li>
					<a class="add" href="FeeCalculateWay_addCalculateWaySelectDimensionUI.action?feeNodeId=${feeNodeId}" target="dialog" rel="feeCalculateWayListByNodeId" height="300" title="新增计费方式"><span>给当前节点新增计费方式</span> </a>
				</li>
			</z:permission>
		</ul>
	</div>
<input type="hidden" name="feeNodeId" value="${feeNodeId}">
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="100" nowrapTD="false">
		<thead>
			<tr>
				<th>序号</th>
				<th>产品名称/编号</th>
				<th>方式编号/方式/接口</th>
				<th>计费类型</th>
				<th>收费方式</th>
				<th>免计费金额(包含)</th>
				<th>退还手续费</th>
				<th>生效日期</th>
				<th>失效日期</th>
				<th>是否有效</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="FeeCalculateWayVoList" status="st">
				<tr target="sid_user" rel="${calculateWayId}">
					<td>${st.index+1}</td>
					<td>${payProductName }</td>
					<td>产品编号：<span style="color: red">${payProduct }</span><br>
					【${frpCode}】<br>
					【${bankChannelCode}】</td>
					<td>
						<c:forEach items="${FeeCalculateTypeEnum}" var="enums">
							<c:if test="${enums.value eq calculateType}"> ${enums.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${FeeChargeTypeEnum}" var="enums">
							<c:if test="${enums.value eq chargeType}"> ${enums.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<fmt:formatNumber value="${feeFreeAmount}" pattern="#,##0.00#" />
					</td>
					<td>
						<c:if test='${isRefundFee eq true}'>
							是
						</c:if>
						<c:if test="${isRefundFee eq false }">
							否
						</c:if>
					</td>
					<td>
						<fmt:formatDate value="${effectiveDateStart}" pattern="yyyy-MM-dd" />
					</td>
					<td>
						<fmt:formatDate value="${effectiveDateEnd}" pattern="yyyy-MM-dd" />
					</td>
					<td>
						<c:if test='${isDelete eq true}'>
							否
						</c:if>
						<c:if test="${isDelete eq false }">
							是
						</c:if>
					</td>
					<td nowrap>
					<a href="FeeCalculateWay_feeCalculateWayView.action?id=${calculateWayId}" title="详情" rel="FeeFormulaeList" target="dialog" height="350"
								style="color:blue">详情</a>|
						<z:permission value="fee:formula:view">
							<a href="FeeFormulae_feeFormulaeList.action?id=${calculateWayId}&calculateType=${calculateType }&feeRole=${feeRole}" title="对应计费公式" rel="FeeFormulaeList" target="dialog" width="900"
								style="color:blue">设置计费公式</a>|
						</z:permission>
						<z:permission value="fee:calculateWay:freeze">
							<c:if test='${isDelete eq true}'>
								<a href="javascript:isDel(1,${calculateWayId});" title="设置有效" rel="feeCalculateWayListByNodeId" style="color:blue">设置有效</a>
							</c:if>
							<c:if test="${isDelete eq false }">
								<a href="javascript:isDel(0,${calculateWayId});" title="设置无效" rel="feeCalculateWayListByNodeId" style="color:blue">设置无效</a>
							</c:if>
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	$.extend({
		bringBackSuggest : function(args) {
			$("input[id='dimension.id']").val(args["id"]);
			$("input[id='dimension.payProductName']").val(args["payProductName"]);
		},
	});
	function isDel(flag, id) {
		$.post("FeeCalculateWay_isDel.action", {
			flag : flag,
			id : id
		}, function(res) {
			$("form[name='feeCalculateWayAllListForm']").submit();
		}, "json");
	}
	function clear() {
		$("input[name='dimension.id']").val(null);
		$("input[name='dimension.payProductName']").val(null);
	}
</script>
