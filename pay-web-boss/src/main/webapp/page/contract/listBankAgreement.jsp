<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="bordereaux_merchantLookupList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						接口名称：<input type="text" name="interfaceName" value="${interfaceName }" size="30" alt="精确搜索" />
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
				<th >序号</th>
				<th>商户编号</th>
				<th>银行序号</th>
				<th>应用产品</th>
				<th>接口名称</th>
				<th>文件编号</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}"
				onclick="$.bringBack({bankName:'${interfaceName}', bankId:'${bankSequence}', bankNo:'${bankSequence}'})" >
					<td>${st.index+1}</td>
				    <td>${merchantNo }</td>
					<td>${bankSequence }</td>
					<td>
						<s:if test="linkType==1">B2B</s:if>
						<s:if test="linkType==2">B2C</s:if>
						<s:if test="linkType==3">B2C快捷支付</s:if>
					</td>
					<td>${interfaceName }</td>
					<td>${agreementNo }</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
   <%@include file="/page/inc/pageBarLookup.jsp"%>
</div>
    