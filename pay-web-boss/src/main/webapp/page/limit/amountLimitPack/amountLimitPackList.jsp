<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="amountLimitPack_listAmountLimitPack.action" method="post" name="amountLimitForm">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
						名称：<input type="text" name="name" value="${name}" size="15" alt="模糊搜索" />
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
		<z:permission value="limit:amount:add">
			<li><a class="add" href="amountLimitPack_toEditAmountLimitPack.action" target="dialog" rel="input"  title="添加金额限制包"><span>添加</span></a></li>
		</z:permission>
		</ul>
	</div>
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>描述</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${name}</td>
				    <td>${description}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
					<z:permission value="limit:amount:addRule">
							<a href="amountLimit_toEditAmountLimit.action?amountLimitPackId=${id}" title="添加金额限制" target="dialog" style="color:blue">添加金额限制</a>
	                </z:permission>
	                
	                <z:permission value="limit:amount:view">
	                       | <a rel="listAmountLimit" href="amountLimit_listAmountLimit.action?amountLimitPackId=${id}" target="navTab" style="color:blue">查看金额限制</a>
					</z:permission>
					
					<z:permission value="limit:amount:edit">
						   | <a href="amountLimitPack_toEditAmountLimitPack.action?id=${id}" title="修改金额限制" target="dialog" style="color:blue">修改</a>
					</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
