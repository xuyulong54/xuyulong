<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="bank_lookupBankListName.action">
    <%@include file="/page/inc/pageForm.jsp"%>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="bank_BankChannelLookuplist.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					银行渠道编号：<input type="text" name="bankChannelCode" value="${bankChannelCode}" size="30" alt="精确查询"  />
				</td>
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="106">
		<thead>
			<tr><th>序号</th>
				<!-- <th>ID</th> -->
				<th>创建时间</th>
				<th>银行渠道编号</th>
				<th>银行渠道名称</th>
				<th>银行名称</th>
				<th>银行编号</th><%--
				<th>接入类型</th>
				--%><!-- <th>状态</th> -->
				<%--<th>描述</th>
				--%><th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody> <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
					<!-- <td>${id }</td> -->
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${bankChannelCode }</td>
					<td>${bankChannelName}</td>
					<td>${bankName}</td>
					<td>${bankCode}</td>
				    <%--<td>
				    	<c:if test="${linkType eq 1 }">B2C</c:if>
				    	<c:if test="${linkType eq 2 }">B2B</c:if>
				    </td>
				    --%><!-- <td>${status}</td> -->
					<%-- <td>${remark}</td>--%>
					<td>
					<a class="btnSelect" href="javascript:$.bringBack({bankChannelName:'${bankChannelName}',bankChannelCode:'${bankChannelCode}',bankChannel:'${id}'})" title="查找带回">选择</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
    <div class="panelBar">
	<div class="pages">
		<span>查到&nbsp;${totalCount}&nbsp;条记录，每页</span>
		<select class="combox" name="numPerPage" value="${numPerPage}" onchange="dwzPageBreak({targetType:dialog, numPerPage:this.value})">
		    <option value="15" <c:if test="${numPerPage eq 15 }">selected='selected'</c:if>>15</option>
		</select>
		<span>条，共&nbsp;${pageCount}&nbsp;页</span>
	</div>
	<div class="pagination" 
		targetType="dialog" 
		totalCount="${totalCount}" 
		numPerPage="${numPerPage}" 
		pageNumShown="5" 
		currentPage="${currentPage}">
	</div>
    </div>
</div>
    