<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<table class="table" style="width:100%">
		<tr>
			<td width="10%"><strong>打款批次号：</strong></td><td width="15%">${remitExportBatch.batchNo }</td>
			<td width="10%"><strong>状态：</strong></td>
			<td width="15%">
				<c:forEach items='${remitBatchStatusEnumList }' var='remitExportBatchStatusEnum'>
					<c:if test='${remitExportBatchStatusEnum.value eq remitExportBatch.status }'>${remitExportBatchStatusEnum.desc }</c:if>
				</c:forEach>
			</td>
			<td width="10%"><strong>总笔数：</strong></td><td width="15%">${remitExportBatch.totalNum }</td>
			<td width="10%"><strong>总金额：</strong></td><td width="15%">${remitExportBatch.totalAmount }</td>
		</tr> 
		<tr>
			<td><strong>确认人：</strong></td><td>${remitExportBatch.confirm}</td>
			<td><strong>确认时间：</strong></td><td width="26%"><fmt:formatDate value="${remitExportBatch.confirmDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><strong>创建时间：</strong></td><td width="26%"><fmt:formatDate value="${remitExportBatch.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	    </tr>
	</table>
</div>
<div class="pageContent">
	<form id="pagerForm" action="remitExportBatch_remitExportBatchDetail.action" onsubmit="return dwzSearch(this, 'dialog');" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<input type="hidden" name="batchNo"  value="${remitExportBatch.batchNo }" />
	</form>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="150">
		<thead>
			<tr>
				<th>打款请求号</th>
				<th>制单类型</th>
				<th>业务发起方</th>
				<th>开户银行名称</th>
				<th>开户银行行号</th>
				<th>收款帐户名</th>
				<th>收款帐户</th>
				<th align="right">打款金额</th>
				<th>打款通道编号</th>
				<th>状态</th>
				<!-- <th>是否加急</th> -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${requestNo}</td>
				    <td>
						<c:if test='${isAutoProcess eq 100 }'>自动</c:if>
						<c:if test='${isAutoProcess eq 101 }'>手动</c:if>
				    </td>
				    <td>
				    	<c:forEach items='${tradeSourcesEnumList }' var='tradeSourcesEnum'>
							<c:if test='${tradeSourcesEnum.value eq tradeInitiator }'>${tradeSourcesEnum.desc }</c:if>
						</c:forEach>
				    </td>
				    <td>${bankName}</td>
				    <td>${bankChannelNo}</td>
				    <td>${accountName}</td>
					<td>${accountNo}</td>
				    <td name="amount">${amount}</td>
					<td>${channelCode}</td>
					<td>
						<c:forEach items="${remitProcessStatusEnumMap }" var="remitProcess">
							<c:if test='${remitProcess.value eq status }'>${remitProcess.desc }</c:if>
						</c:forEach>
					</td>
					<%-- <td>
						<c:forEach items='${isOrNotEnumList }' var='isOrNotEnum'>
							<c:if test='${isOrNotEnum.value eq isUrgent }'>${isOrNotEnum.desc }</c:if>
						</c:forEach>
					</td> --%>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
    <%@include file="/page/inc/pageBarLookup.jsp"%>
    
    	<div class="formBar">
			<ul>
				<c:if test="${remitExportBatch.status eq 1}">
					<z:permission value="boss:remitExportBatch:edit">
						<li><div class="button">
								<div class="buttonContent">
									<button type="button" onclick="onSubmit(${remitExportBatch.exportBatchNo },'success')" >打款成功</button>
								</div>
							</div>
						</li>
						<li><div class="button">
								<div class="buttonContent">
									<button type="button" onclick="onSubmit(${remitExportBatch.exportBatchNo },'fail')" >打款失败</button>
								</div>
							</div>
						</li>
					</z:permission>
				</c:if>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
</div>
<script type="text/javascript">
	
function onSubmit(exportBatchNo, result) {
	var msg = "";
	if (result == "success") {
		msg = "您确定要将该批次状态改成打款成功？";
	} else {
		msg = "您确定要将该批次状态改成打款失败？";
	}
	alertMsg.confirm(msg, {
		okCall : function() {
			$.post("remitExportBatch_remitExportBatchStatusChange.action", {
				exportBatchNo : exportBatchNo,
				result : result
			}, function(res) {
				if(res.STATUS=="success"){
					alertMsg.correct(res.MSG);
				}else{
					alertMsg.error(res.MSG);
				}
				$("form[name='remitExportBatchForm']").submit();
				$.pdialog.closeCurrent(); 
			}, "json");
		}
	});
}

</script>