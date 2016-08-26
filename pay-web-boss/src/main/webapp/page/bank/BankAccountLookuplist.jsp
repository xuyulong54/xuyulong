<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="bankAccount_bankAccountLookupList.action">
    <%@include file="/page/inc/pageForm.jsp"%>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="bankAccount_bankAccountLookupList.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					账户性质：<select name="accountNature" >
							<option value="">全部</option>
							<option value="1" <c:if test="${accountNature eq 1 }">selected="selected"</c:if>>备付金存管账户</option>
							<option value="2" <c:if test="${accountNature eq 2 }">selected="selected"</c:if>>自有资金账户</option>
							<option value="3" <c:if test="${accountNature eq 3 }">selected="selected"</c:if>>备付金收付账户</option>
							<option value="4" <c:if test="${accountNature eq 4 }">selected="selected"</c:if>>备付金汇缴账户</option>
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="95">
		<thead>
			<tr>
				<th>序号</th>
				<th>合作方式</th>
				<th>账户性质</th>
				<th>开户银行</th>
				<th>银行账户名称</th>
				<th>账户状态</th>
				<th>操作</th> <!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}">
					<td>${st.index+1}</td>
					<td>
						<s:if test="cooperationWay==1">存管银行</s:if>
						<s:if test="cooperationWay==2">合作银行</s:if>
					</td>
					<td>
						<s:if test="accountNature==1">备付金存管账户</s:if>
						<s:if test="accountNature==2">自有资金账户</s:if>
						<s:if test="accountNature==3">备付金收付账户</s:if>
						<s:if test="accountNature==4">备付金汇缴账户</s:if>
					</td>
				    <td>${openBank}</td>
				    <td>${userName}</td>
					<td>
						<s:if test="accountStatus==1">正常</s:if>
						<s:if test="accountStatus==2">待销户</s:if>
						<s:if test="accountStatus==3">已销户</s:if>
					</td>
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({bankAccountId:'${id}', bankAccountNo:'${bankAccount}', backFlag:'2', bankAccountName:'${openBank}'+'_'+'${userName}' })" title="查找带回">选择</a>
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
