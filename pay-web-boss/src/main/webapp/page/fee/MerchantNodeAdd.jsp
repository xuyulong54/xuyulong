<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm"  name="addMerchantNodeForm" onsubmit="return dwzSearch(this, 'dialog');" action="merchantNode_addMerchantNodeUI.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
		<input value="${userNo }" id="userNo" name="userNo" type="hidden"/>
			<table class="searchContent">
				<tr>
					<td>
						<label>节点名称：</label>
						<input type="text" name="name" id="name" value="${nodeName }" alt="精确搜索"/>
					</td>
					<td>
						<label>计费项：</label>
						<select name="calculateFeeItem" id="calculateFeeItem" >
							<option value="">请选择</option>
							<c:forEach items="${CalculateFeeItemEnum }" var="model">
								<option value="${model.value }" <c:if test="${calculateFeeItem eq model.value }">selected="selected"</c:if>>${model.desc}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<div class="subBar" style="float: right;">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
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
				<li><a class="add" href="javascript:;" onclick="addNodeForMerchant('${userNo}');"><span>添加</span></a></li>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="120">
		<thead>
			<tr>
				<th><input class="checkboxCtrl" type="checkbox" group="ids"></th>
				<th>节点名称</th>
				<th>节点类型</th>
				<th>计费项</th> 
				<th>描述</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}" >
					<td><input type="checkbox" value="${id}$${calculateFeeItem }" name="ids">
					</td>
					<td>${nodeName}</td>
					<td>
					<c:forEach items="${FeeModelTypeEnum }" var="enums">
					<c:if test="${nodeType eq enums.value }">${enums.desc }</c:if>
					</c:forEach>
					</td>
					<td>
					<c:forEach items="${CalculateFeeItemEnum }" var="enums">
					<c:if test="${enums.value eq calculateFeeItem }">${enums.desc }</c:if>
					</c:forEach>
					</td>
					<td>${remark}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/page/inc/pageBarLookup.jsp"%>
</div>
<script type="text/javascript">
function addNodeForMerchant(userNo){
	var nodeList = $("input[name='ids']:checked");
	var id = "-";
	var ids = "";
	for(var i=0; i<nodeList.length; i++){
		ids=ids+nodeList[i].value+id;
	}
	$.post("merchantNode_addMerchantNode.action",{nodeIds:ids,userNo:userNo},function(res){
		$.pdialog.closeCurrent();
		$("form[name='merchantNodeListForm']").submit();
	},"json");
}
</script>



