<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="60">
		<thead>
			<tr>
				<th>序号</th>
				<th>账号开户行名称</th>
				<th>账号开户行行号</th>
				<th>合作方式</th>
				<th>账户性质</th>
				<th>银行账号</th>
				<th>银行账户名称</th>
				<th>账户状态</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}"  ondblclick="$.bringBack({srcAccountNum:'${bankAccount}', srcAccountName:'${userName }', srcBankName:'${openBankAddress }', srcBankChannelNo:'${bankNo }'});">
				    <td>${st.index+1}</td>
				    <td>${openBankAddress}</td>
				    <td>${bankNo}</td>
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
				    <td>${bankAccount }</td>
					<td>${userName }</td>
					<td>
						<s:if test="accountStatus==1">正常</s:if>
						<s:if test="accountStatus==2">待销户</s:if>
						<s:if test="accountStatus==3">已销户</s:if>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    