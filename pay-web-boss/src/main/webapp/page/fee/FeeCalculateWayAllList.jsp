<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<div class="pageHeader">
	<form id="pagerForm" name="feeCalculateWayAllListForm" onsubmit="return navTabSearch(this);" action="FeeCalculateWay_feeCalculateWayAllList.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						节点名称：
						<input type="text" name="feeNodeName" value="${feeNodeName }" alt="模糊查询" />
					</td>
					<td>
						计费维度：
						<input type="hidden" id="dimension.id" name="dimension.id" value="${feeDimensionId }" />
						<input type="text" id="dimension.payProductName" name="dimension.payProductName" value="${payProductName }" readonly="readonly" required="required" />
					</td>
					<td>
						<a class="btnLook" id="findDimensionBtn" title="选择维度" href="FeeDimension_dimensionLookupList.action" lookupGroup="dimension" style="display: block;">搜索</a> 
						<a class="btnDel" href="javascript:clear();" title="清空维度查询条件">清空</a>
					</td>
				</tr>
				<tr>
					<%-- <td>计费角色: <select name="feeRole">
							<option value="">请选择</option>
							<c:forEach items="${FeeRoleTypeEnum }" var="models">
								<option value="${models.value }" <c:if test="${feeRole eq models.value }">selected="selected"</c:if>>${models.desc}</option>
							</c:forEach>
						</select>
					</td> --%>
					<td>
						计费类型： <select name="calculateType">
							<option value="">请选择</option>
							<c:forEach items="${FeeCalculateTypeEnum }" var="models">
								<option value="${models.value }" <c:if test="${calculateType eq models.value }">selected="selected"</c:if>>${models.desc}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						收费方式： <select name="chargeType">
							<option value="">请选择</option>
							<c:forEach items="${FeeChargeTypeEnum }" var="model">
								<option value="${model.value }" <c:if test="${chargeType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						是否退还手续费： <select name="isRefundFee">
							<option value="">请选择</option>
							<option value="1" <c:if test="${isRefundFee eq 1 }">selected="selected"</c:if>>是</option>
							<option value="0" <c:if test="${isRefundFee eq 0 }">selected="selected"</c:if>>否</option>
						</select>
					</td>
					<td>
						是否有效： <select name="isDelete">
							<option value="">请选择</option>
							<option value="1" <c:if test="${isDelete eq 1 }">selected="selected"</c:if>>否</option>
							<option value="0" <c:if test="${isDelete eq 0 }">selected="selected"</c:if>>是</option>
						</select>
					</td>

					<td>
						<div class="subBar" style="float: right;">
							<ul>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<z:permission value="fee:calculateWay:add">
				<li>
					<a class="add" href="FeeCalculateWay_addCalculateWaySelectDimensionUI.action" target="dialog" rel="feeCalculateWayList" height="300" title="新增计费方式"><span>新增计费方式</span> </a>
				</li>
			</z:permission>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160" nowrapTD="false">
		<thead>
			<tr>
				<th>序号</th>
				<!-- <th>创建时间</th> -->
				<th>节点名称</th>
				<th>产品名称</th>
				<th>方式编号/方式/接口</th>
				<th>计费类型</th>
				<th>收费方式</th>
				<!-- <th>计费角色</th> -->
				<th>免计费金额(包含)</th>
				<th>退还手续费</th>
				<th>生效日期</th>
				<th>失效日期</th>
				<th>是否有效</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${calculateWayId}">
					<td>${st.index+1}</td>
					<%-- <td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
					<td>${nodeName }</td>
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
					<%-- <td><c:forEach items="${FeeRoleTypeEnum}" var="enums">
							<c:if test="${enums.value eq feeRole}"> ${enums.desc}</c:if>
						</c:forEach></td> --%>
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
						<z:permission value="fee:calculateWay:freeze">
							<c:if test='${isDelete eq true}'>
								<a href="javascript:isDel(1,${calculateWayId});" title="设置有效" style="color:blue">设置有效</a>
							</c:if>
							<c:if test="${isDelete eq false }">
								<a href="javascript:isDel(0,${calculateWayId});" title="设置无效" style="color:blue">设置无效</a>
							</c:if>
						</z:permission>
						|
						<z:permission value="fee:formula:view">
							<a href="FeeFormulae_feeFormulaeList.action?id=${calculateWayId}&calculateType=${calculateType }&feeRole=${feeRole}" title="对应计费公式" rel="FeeFormulaeList" target="dialog" width="900"
								style="color:blue">设置计费公式</a>
						</z:permission>
						|&nbsp;<a href="FeeCalculateWay_feeCalculateWayView.action?id=${calculateWayId}" title="详情" rel="FeeFormulaeList" target="dialog" height="300"
								style="color:blue">详情</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
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
