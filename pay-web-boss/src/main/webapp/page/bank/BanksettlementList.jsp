<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="banksettlement_listBanksettlement.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					银行渠道编码：<input type="text" name="bankChannelCode" value="${bankChannelCode}" alt="精确查询"  size="30" />
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="102">
		<thead>
			<tr>
				<th>序号</th>
				<th>银行渠道编码</th>
				<th>结算周期</th>
				<th>手续费描述</th>
				<th>退款手续费规则</th>
				<th>手续费扣收方式</th>
				<th>手续费扣收周期</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
				    <td>${bankChannelCode }</td>
				    <td>${settleCycle }</td>
					<td>${chargeomment }</td>
					<td>${refundChargeRule }</td>
					<td>
						<s:if test="chargeDeductWay==1">内扣</s:if>
						<s:if test="chargeDeductWay==2">外扣</s:if>
					</td>
					<td>${chargeDeductCycle }</td>
					<td>
					<a href="banksettlement_viewBanksettlement.action?id=${id}" title="查看银行证书信息" target="dialog" rel="input" style="color:blue" >查看</a>&nbsp;&nbsp;
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    