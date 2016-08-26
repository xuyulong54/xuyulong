<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="bank_listAccount.action" method="post">
		 <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					账户名称：<input type="text" name="bankAccountname" value="${bankAccountname}" size="30" alt="模糊查询"  />
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
			<li><a class="add" href="bank_addAccountUI.action" target="dialog" rel="input" title="新建资金账户"><span>新建资金账户</span></a></li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="112">
		<thead>
			<tr>
				<th>序号</th>
				<th >开户账户</th>
				<th>账户名称</th>
				<th>账户余额</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>状态</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
					<td>${bankAccount }</td>
					<td>${bankAccountname }</td>
					<td>${balance }</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><s:date name="lastTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${status }</td>
					<td>
					<a href="bank_editAccountUI.action?bankAccount=${bankAccount}" title="修改字典" target="dialog" rel="input" class="btnEdit"></a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    