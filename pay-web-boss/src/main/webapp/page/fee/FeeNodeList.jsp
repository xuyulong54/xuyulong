<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<div class="pageHeader">
	<form id="pagerForm" name="memberForm" onsubmit="return navTabSearch(this);" action="FeeNode_listNode.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						节点名称：
						<input type="text" name="feeNodeName" value="${proMap.feeNodeName}" />
					</td>
					<td>
						计费项： <select name="calculateFeeItem">
							<option value="">请选择</option>
							<c:forEach items="${CalculateFeeItemEnums }" var="model">
								<option value="${model.value }" <c:if test="${proMap.calculateFeeItem eq model.value }">selected="selected"</c:if>>${model.desc}</option>
							</c:forEach>
						</select>
					</td>

					<td>
						节点类型： <select name="nodeType" id="nodeType">
							<option value="">请选择</option>
							<c:forEach items="${feeModelTypeEnums }" var="models">
								<option value="${models.value }" <c:if test="${proMap.nodeType eq models.value }">selected="selected"</c:if>>${models.desc}</option>
							</c:forEach>
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
			<z:permission value="fee:node:add">
				<li>
					<a class="add" href="FeeNode_addNodeUI.action" target="dialog" title="新建节点"><span>新建节点</span> </a>
				</li>
			</z:permission>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>节点名称</th>
				<th>节点类型</th>
				<th>计费项</th>
				<th>描述</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${nodeName}</td>
					<td>
						<c:forEach items="${feeModelTypeEnums}" var="feeModelTypes">
							<c:if test="${feeModelTypes.value eq nodeType}"> ${feeModelTypes.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${CalculateFeeItemEnums}" var="CalFeeItemEnum">
							<c:if test="${CalFeeItemEnum.value eq calculateFeeItem}"> ${CalFeeItemEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>${remark}</td>
					<td>
						<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td nowrap>
						<c:forEach items="${payEnums }" var="enums">
							<c:if test="${enums.value eq calculateFeeItem }">
								<z:permission value="feenode:productswitch:bindMcc">
									<c:if test="${posFeeItem.value eq calculateFeeItem }">
										<a href="FeeDimension_bindPayProductList.action?feeNodeId=${id}" width="900" height="500" title="绑定支付产品" target="dialog" rel="dialogDimension" style="color:blue">【绑定pos支付产品】</a>
									</c:if>
								</z:permission>
								
								<z:permission value="feenode:productswitch:add">
									<c:if test="${posFeeItem.value ne calculateFeeItem }">
										<a href="FeeDimension_bindPayProductList.action?feeNodeId=${id}" width="900" height="500" title="绑定支付产品" target="dialog" rel="dialogDimension" style="color:blue">【绑定支付产品】</a>
									</c:if>
								</z:permission>
								
							</c:if>
						</c:forEach>
						
						<c:forEach items="${specialEnums }" var="enums">
							<c:if test="${enums.value eq calculateFeeItem }">
								<z:permission value="feenode:productswitch:edit">
									<a href="FeeCalculateWay_addCalculateWayByDimensionIdUI.action?feeNodeId=${id}" target="dialog" rel="feeCalculateWayList" height="300" style="color:blue" title="设置计费方式">【设置${nodeName }计费方式】</a>
								</z:permission>
							</c:if>
						</c:forEach>
						
						<z:permission value="fee:calculateWay:view">
						<a href="FeeCalculateWay_feeCalculateWayByNodeIdList.action?feeNodeId=${id}" target="navTab" rel="feeCalculateWayListByNodeId" height="300" style="color:blue" title="查看计费方式">【查看其下计费方式】</a>
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
	function setCalWay(feeNodeId) {
		$
				.post(
						"FeeCalculateWay_judgeCalculateWayByDimensionId.action",
						{
							feeNodeId : feeNodeId
						},
						function(res) {
							if (res.STATE == "FAIL") {
								alertMsg.error(res.MSG);
							} else if (res.STATE == "SUCCESS") {
								$.pdialog.open(
												"FeeCalculateWay_addCalculateWayByDimensionIdUI.action?feeNodeId="
														+ feeNodeId,
												"feeCalculateWayAllList",
												"设置计费方式",
												{
													max : false,
													mask : false,
													resizable : true,
													drawable : true,
													maxable : true,
													minable : true,
													fresh : true,
													width : 1024,
													height : 320,
												});
							}

						}, "json");
	}
</script>

