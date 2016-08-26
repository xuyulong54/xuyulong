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
					<select name="ruleType" >
						<option value="">--全部类型--</option>
						<option value="${PayRuleTypeEnum.SYSTEM.value }" <c:if test="${ruleType==PayRuleTypeEnum.SYSTEM.value}">selected="selected"</c:if> >${PayRuleTypeEnum.SYSTEM.desc }</option>
						<option value="${PayRuleTypeEnum.CUSTOM.value }" <c:if test="${ruleType==PayRuleTypeEnum.CUSTOM.value}">selected="selected"</c:if> >${PayRuleTypeEnum.CUSTOM.desc }</option>
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
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th >规则名称</th>
				<th >规则类型</th>
				<th >规则描述</th>
				<th>创建时间 </th>
				<th >更新时间</th>
				<th style="width: 200px;">操作</th>
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
					
					<td><fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<fmt:formatDate value="${modifyTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<!-- 
						<a href="payRule_bindPayProductSwitchUI.action?ruleId=${id }" title="绑定支付产品/支付产品列表" target="dialog" width="800" height="400" style="color:blue">绑定支付产品</a>
						| <a href="payRule_bindPayWaySwitchUI.action?ruleId=${id }" title="支付产品列表" target="dialog" width="900" height="500" style="color:blue">支付产品列表</a>
						 -->
						<z:permission value="payrule:payrule:bindPro">
						<a href="payRule_bindAllSwitchUI.action?ruleId=${id }" title="支付产品列表" target="dialog" width="900" height="500" style="color:blue">绑定支付产品</a>
						</z:permission>
						<!-- | <a title="支付产品列表" target="dialog" width="600" height="400" style="color:blue">支付产品列表</a> -->
						<!-- 
						&nbsp;
						[<a href="payProduct_editPayProductUI.action?ruleId=${id}" title="查看/修改支付产品" target="navTab" rel="listPayProductByRuleId"  style="color:blue">查看/修改支付产品</a>]
					 	-->
						<z:permission value="payrule:payrule:edit">
							| <a href="payRule_editPayRuleUI.action?id=${id}" title="修改支付规则" target="dialog" width="600" height="400" rel="input" style="color:blue">修改</a>
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    