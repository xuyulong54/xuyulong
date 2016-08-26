<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="remitExportBatchForm" onsubmit="return navTabSearch(this);" action="remitExportBatch_remitExportBatchList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>打款批次号：</label>
						<input type="text" name="batchNo" value="${batchNo }" size="25" alt="精确搜索" />
					</td>
					<td> 
						<label>创建时间：</label> 
						<input name="beginDate" id="beginDate" value="${beginDate }" dateFmt="yyyy-MM-dd" type="text"  style="width: 77px;" class="date"  /> 
						至<input name="endDate"  value="${endDate }" dateFmt="yyyy-MM-dd" type="text" class="date" id="endDate" style="width: 77px;" />
					</td>
					<td>
						<label>状态：</label>
						<select name="status" id="status">
							<option value="">请选择</option>
							<c:forEach items="${remitBatchStatusEnumList }" var="remitExportBatchStatusEnum">
								<option value="${remitExportBatchStatusEnum.value}" <c:if test="${status eq remitExportBatchStatusEnum.value }">selected="selected"</c:if>>${remitExportBatchStatusEnum.desc}</option>
							</c:forEach>
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
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="105" nowrapTD="false">
		<thead>
			<tr>
				<th>序号</th>
				<th>批次号</th>
				<th>总金额</th>
				<th>受理金额</th>
				<th>处理金额</th>
				<th>打款通道</th>
				<th>受理时间</th>
				<th>处理时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${batchNo}</td>
				    <td>
				   	总笔数：${totalNum}<br/>总金额：${totalAmount}</td>
				   	<td>
				   	成功笔数：${acceptSucNum}<br/>成功金额：${acceptSucAmount}</td>
				   	<td>
				   	成功笔数：${processSucNum} &nbsp;&nbsp;&nbsp;&nbsp;成功金额：${processSucAmount}<br/>
				       失败笔数：${processFailNum} &nbsp;&nbsp;&nbsp;&nbsp;失败金额：${processFailAmount}</td>
					<td>${remitChannelCode}</td>
					<td><s:date name="acceptDate" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><s:date name="processDate" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<c:forEach items="${remitBatchStatusEnumList }" var="remitExportBatchStatusEnum">
							<c:if test="${remitExportBatchStatusEnum.value eq status }">${remitExportBatchStatusEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<z:permission value="boss:remitExportBatch:view">
							<a  href="remitExportBatch_remitExportBatchDetail.action?batchNo=${batchNo }" title="查看打款批次" target="dialog" rel="input" width="1024" height="450" style="color:blue">查看</a>
							&nbsp;|&nbsp;
						</z:permission>
						
						<!-- (isAutoRemit eq publicStatusEnum.INACTIVE.value) &&  -->
						<c:if test="${(status eq remitBatchStatusEnum.HANDLING.value)}">
							<z:permission value="boss:remitExportBatch:edit">
								<a  href="remitProcessUnremit_exportExcel.action?batchNo=${batchNo }&channelCode=${remitChannelCode}" title="导出打款文件" style="color:blue">导出打款文件</a>
								&nbsp;|&nbsp;
							</z:permission>
						</c:if>
						
						<c:if test="${status eq remitBatchStatusEnum.HANDLING.value}">
							<z:permission value="boss:remitExportBatch:edit">
								<a  href="javascript:changeRemitExportBatchStatus(${batchNo},'success');" title="打款成功" style="color:blue">打款成功</a>
								&nbsp;|&nbsp;
								<a  href="javascript:changeRemitExportBatchStatus(${batchNo},'fail');" title="打款失败" style="color:blue">打款失败</a>
							</z:permission>
						</c:if>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
	
	function changeRemitExportBatchStatus(batchNo, result) {
		var msg = "";
		if (result == "success") {
			msg = "您确定要将该批次状态改成打款成功？";
		} else {
			msg = "您确定要将该批次状态改成打款失败？";
		}
		alertMsg.confirm(msg, {
			okCall : function() {
				$.post("remitExportBatch_remitExportBatchStatusChange.action", {
					batchNo : batchNo,
					result : result
				}, function(res) {
					if(res.STATUS=="success"){
						alertMsg.correct(res.MSG);
					}else{
						alertMsg.correct(res.MSG);
					}
					
					$("form[name='remitExportBatchForm']").submit();
				}, "json");
			}
		});
	}
</script>