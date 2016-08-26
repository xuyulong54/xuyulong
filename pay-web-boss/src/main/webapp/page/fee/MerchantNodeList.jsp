<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name="merchantNodeListForm" onsubmit="return navTabSearch(this);" action="merchantNode_merchantNodeList.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<input type="hidden" name="userNo" value="${userNo }" size="40" alt="精确搜索"/>
		<table class="searchContent">
			<tr>
				<td>
					是否有效：
					<select name="status" >
						<option value="">请选择</option>
							<option value="100" <c:if test="${status eq 100 }">selected="selected"</c:if>>有效</option>
							<option value="101" <c:if test="${status eq 101 }">selected="selected"</c:if>>无效</option>
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
				<li><a class="add" href="merchantNode_addMerchantNodeUI.action?userNo=${userNo }&userName=${userName}" target="dialog" rel="merchantNodeList"  title="为商户选择计费节点"><span>选择计费节点</span></a></li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="160" >
		<thead>
			<tr>
				<th>序号</th>
				<th>节点名称</th>
				<th>节点类型</th> 
				<th>计费项</th> 
				<th>用户编号</th>
				<th>是否有效</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
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
					<td>${userNo}</td>
					<td>
					<c:if test="${status eq 100 }">
					有效
					</c:if>
					<c:if test="${status eq 101 }">
					无效
					</c:if>
					</td>
					<td>
					<c:if test="${status eq 100 }">
						<a href="javascript:isValid('${userSettingId}','${userNo}','${status }');" title="设置" style="color:blue">设置无效</a>
					</c:if>
					<c:if test="${status eq 101 }">
						<a href="javascript:isValid('${userSettingId}','${userNo}','${status }');" title="设置" style="color:blue">设置有效</a>
					</c:if>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
function isValid(id,userNo,status){
	$.post("merchantNode_isValid.action",{id:id,userNo:userNo,status:status},function(res){
		$("form[name='merchantNodeListForm']").submit();
	},"json");
}
</script>

