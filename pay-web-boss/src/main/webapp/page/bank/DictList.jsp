<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="bank_listDict.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					银行名称：<input type="text" name="bankName" value="${bankName}" size="30" alt="模糊查询"  />
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
			<li><a class="add" href="bank_addDictUI.action" target="navTab" rel="input" title="新建字典"><span>新建字典</span></a></li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="112">
		<thead>
			<tr>
				<th>序号</th>
				<th>银行编号</th>
				<th >银行名称</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
					<td>${code }</td>
					<td>${name }</td>
					<td>
					<a href="bank_editDictUI.action?code=${code}" title="修改字典" target="navTab" rel="input" class="btnEdit"></a>
					<a href="bank_deleteDict.action?code=${code}" title="删除" target="ajaxTodo" class="btnDel"></a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    