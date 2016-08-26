<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="paymentRecordStat_listPaymentRecordStat.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					通知状态：<input type="text" name="status" value="${status}" size="30" alt="精确查询"  />
					交易流水号：<input type="text" name="trxNo" value="${trxNo}" size="30" alt="精确查询"  />
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="112">
		<thead>
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>创建时间</th>
				<th>商户订单号</th>
				<th>交易流水号</th>
				<th>银行订单号</th>
				<th>银行流水号</th>
				<th>支付金额</th>
				<th>销售Id</th>
				<th>销售名称</th>
				<th>银行协议ID</th>
				<th>银行名称</th>
				<th>银行渠道编号</th>
				<th>银行费率</th>
				<th>商户费率</th>
				<th>付款方费率</th>
				<th>收款方费率</th>
				<th>代理商费率</th>
				<th>商户名称</th>
				<th>商户ID</th>
				<th>收入</th>
				<th>成本</th>
				<th>毛利</th>
				<th>描述</th>
				<th>支付渠道编号</th>
				<th>业务类型</th>
				<th>客户ID</th>
				<th>客户名称</th>
				<th>银行退回金额</th>				
				<th>商户手续费</th>
				<th>付款方手续费</th>
				<th>收款方手续费</th>
				<th>代理商手续费</th>
				<th>代理商ID</th>
				<th>代理商名称</th>
				<th>订单金额</th>
				<th>平台收入</th>
				<th>来源账户编号</th>
				<th>目标账户编号</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td >${st.index+1}</td>
					<td >${id }</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td >${orderNo}</td>
					<td >${trxNo}</td>
					<td >${bankOrderNo}</td>
					<td>${bankTrxNo }</td>
					<td>${payAmount }</td>
					<td>${sellerId }</td>
					<td>${sellerName}</td>
					<td>${bankAgreementId }</td>
					<td>${bankName }</td>
					<td>${bankChannelCode }</td>
					<td>${bankRate }</td>
					<td>${merchantRate }</td>
					<td>${targetRate }</td>
					<td>${sourceRate }</td>
					<td>${agentRate }</td>
					<td>${merchantName }</td>
					<td>${merchantId }</td>
					<td>${income }</td>
					<td>${cost }</td>
					<td>${profit}</td>
					<td>${remark}</td>
					<td>${frpCode}</td>
					<td>${trxType}</td>
					<td>${customerId}</td>
					<td>${customerName}</td>
					<td>${bankFee}</td>
					<td>${merchantFee }</td>
					<td>${targetFee}</td>
					<td>${sourceFee}</td>
					<td>${agentFee}</td>
					<td>${agentId}</td>
					<td>${agentName}</td>
					<td>${orderAmount}</td>
					<td>${platFee}</td>
					<td>${source_AccountId}</td>
					<td>${target_AccountId}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>