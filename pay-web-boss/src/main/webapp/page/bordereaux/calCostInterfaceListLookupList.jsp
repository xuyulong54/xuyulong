<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="bordereaux_calCostInterfaceListLookupList.action">
<%@include file="/page/inc/pageForm.jsp"%>
</form>
<div class="pageHeader">
	<form id="pagerForm" name ="interfaceForm" onsubmit="return navTabSearch(this);" action="bordereaux_calCostInterfaceListLookupList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>计费接口编号：</label>
						<input type="text" name="interfaceCode" value="${interfaceCode }" size="25" alt="精确搜索"/>
					</td>
					<td>
						<label>计费接口名称：</label>
						<input type="text" name="interfaceName" value="${interfaceName }" size="25" alt="精确搜索"/>
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="100">
		<thead>
			<tr>
				<th>序号</th>
				<th>计费接口编号</th>
				<th>计费接口名称</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}" ondblclick="$.bringBack({interfaceCode:'${interfaceCode }'})" >
					<td>${st.index+1}</td>
				    <td>${interfaceCode}</td>
				    <td>${interfaceName}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBarLookup.jsp"%>
</div>
