<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="payRule_listPayRule.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			    <td>
					规则名称：<input type="text" name="ruleName" value="${ruleName}" size="30" alt="精确查询"  />
				</td>
				<td>
					规则类型：
					<select name="ruleType" style="font-size:14px;">
						<option value="">--全部类型--</option>
						<option value="100" <c:if test="${ruleType==PayRuleTypeEnum.SYSTEM.value}">selected="selected"</c:if> >${PayRuleTypeEnum.SYSTEM.desc }</option>
						<option value="101" <c:if test="${ruleType==PayRuleTypeEnum.CUSTOM.value}">selected="selected"</c:if> >${PayRuleTypeEnum.CUSTOM.desc }</option>
					</select>
				</td>
				<td>
					<div class="subBar">
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
		<z:permission value="payrule:payrule:add">
			<li><a class="add" href="payRule_addPayRuleUI.action" target="dialog" width="600" height="400" rel="input" title="添加支付规则"><span>添加支付规则</span></a></li>
		</z:permission>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="131">
		<thead>
			<tr>
				<th>序号</th>
				<th>规则名称</th>
				<th>规则类型</th>
				<th>规则描述</th>
				<th>关联支付产品</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
					<td>${ruleName }</td>
					<td>
						<c:choose>
							<c:when test="${ruleType == PayRuleTypeEnum.SYSTEM.value }">${PayRuleTypeEnum.SYSTEM.desc }</c:when>
							<c:when test="${ruleType == PayRuleTypeEnum.CUSTOM.value }">${PayRuleTypeEnum.CUSTOM.desc }</c:when>
							<c:otherwise>--</c:otherwise>
						</c:choose>
					</td>
					<td>${description}</td>
					<td>
						[<a href="payProduct_editPayProductUI.action?ruleId=${id}" title="查看/修改支付产品" target="navTab" rel="listPayProductByRuleId"  style="color:blue">查看/修改支付产品</a>]
					</td>
					<td>
						<fmt:formatDate value="${modifyTime }" pattern="yyyy-MM-dd"/>
					</td>
					<td>
						<z:permission value="payrule:payrule:edit">
							[<a href="payRule_editPayRuleUI.action?id=${id}" title="修改支付规则" target="dialog" width="600" height="400" rel="input"  style="color:blue">修改</a>]
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    