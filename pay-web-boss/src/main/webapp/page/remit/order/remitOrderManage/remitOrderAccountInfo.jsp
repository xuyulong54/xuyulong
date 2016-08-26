<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="remitOrderAccountInfoForm" onsubmit="return dwzSearch(this, 'dialog');" action="remitOrder_remitOrderAccountInfo.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>用户登录名：</label>
						<input type="text" name="loginName" value="${loginName }" size="25" alt="精确搜索"/>
					</td>
					<td>
						<label>用户编号：</label>
						<input type="text" name="userNo" value="${userNo }" size="25" alt="精确搜索"/>
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
				<th>用户登录名</th>
				<th>用户编号</th>
				<th>收款行名称</th>
				<th>收款户名</th>
				<th>收款账号</th>
				<th>账户类型</th>
<!-- 				<th>收款行所在省</th>
				<th>收款行所在市</th> -->
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}"  ondblclick="$.bringBack({accountName:'${bankAccountName}', accountNo:'${bankAccountNo }',userNo:'${userNo }' ,accountType:'${bankAccountType }',bankType:'${bankName }',bankChannelNo:'${bankChannelNo }',province:'${province }',city:'${city }'});">
				    <td>${st.index+1}</td>
				    <td>${loginName}</td>
				    <td>${userNo}</td>
				    <td>${bankAccountAddress}</td>
				    <td>${bankAccountName} </td>
				    <td>${bankAccountNo}</td>
					<td>
						<c:forEach items="${bankAccountTypeEnumList }" var="bankAccountTypeEnum">
							<c:if test="${bankAccountTypeEnum.value eq bankAccountType }">${bankAccountTypeEnum.desc }</c:if>
						</c:forEach>
					</td>
<%-- 					<td>${province}</td>
					<td>${city}</td> --%>
					<td>
						<a  href="javascript:$.bringBack({accountName:'${bankAccountName}', accountNo:'${bankAccountNo }',userNo:'${userNo }' , accountType:'${bankAccountType }',bankType:'${bankName }',bankChannelNo:'${bankChannelNo }',province:'${province }',city:'${city }'});" title="选择" class="btnSelect">选择</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBarLookup.jsp"%>
</div>

<script type="text/javascript">

</script>
    