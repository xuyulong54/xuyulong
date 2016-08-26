<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript">
function exportExcelChannelTrans() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var ylmerchantno = $("#ylmerchantno").val();
	var channelMerchName = $("#channelMerchName").val();
	var respCode = $("#respCode").val();
	var trxtype = $("#trxtype").val();
	var ylbatchno = $("#ylbatchno").val();
	var yltraceno = $("#yltraceno").val();
	var channelCode = $("#channelCode").val();
	location.href="channelTrans_channelTransExportExecl.action?startDate="+startDate+"&endDate="+endDate+"&ylmerchantno="+ylmerchantno+"&channelMerchName="+encodeURI(encodeURI(channelMerchName))+"&respCode="+respCode+"&trxtype="+trxtype+"&ylbatchno="+ylbatchno+"&yltraceno="+yltraceno+"&channelCode="+channelCode;
}
</script>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="channelTrans_toChannelTransList.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>交易日期：</label>
						<input type="text" name="startDate" value="${startDate}" id="startDate" class="date" size="10" readonly="readonly" />-
						<input type="text" name="endDate" value="${endDate}" id="endDate" class="date" size="10" readonly="readonly" />
					</td>
					<td>
						<label>渠道商户编号：</label>
						<input type="text" id="ylmerchantno" name="ylmerchantno" value="${ylmerchantno}" size="20"  alt="精确搜索"/>
					</td>
					<td>
						<label>渠道商户名称：</label>
						<input type="text" id="channelMerchName" name="channelMerchName" value="${channelMerchName}" size="20"  alt="模糊搜索"/>
					</td>
					<td>
						<label>交易结果 ：</label> 
						<select name="respCode" id="respCode">
							<option value="">全部</option>
							<option value="00" <c:if test="${respCode eq '00' }">selected="selected"</c:if>>成功</option>
							<option value="01" <c:if test="${respCode eq '01' }">selected="selected"</c:if>>失败</option>
						</select>
					</td>
					</tr>
					<tr>
					<td>
						<label>交易类型 ：</label> 
						<select name="trxtype" id="trxtype">
							<option value="0">全部</option>
							<option value="6006" <c:if test="${trxtype == 6006}">selected="selected"</c:if>>消费</option>
							<option value="4006" <c:if test="${trxtype == 4006}">selected="selected"</c:if>>消费撤销</option>
							<option value="4007" <c:if test="${trxtype == 4007}">selected="selected"</c:if>>消费冲正</option>
							<option value="4008" <c:if test="${trxtype == 4008}">selected="selected"</c:if>>退货</option>
							<option value="6008" <c:if test="${trxtype == 6008}">selected="selected"</c:if>>消费撤销冲正</option>
							<option value="6007" <c:if test="${trxtype == 6007}">selected="selected"</c:if>>预授权完成</option>
							<option value="4010" <c:if test="${trxtype == 4010}">selected="selected"</c:if>>预授权完成冲正</option>
							<option value="4009" <c:if test="${trxtype == 4009}">selected="selected"</c:if>>预授权完成撤销</option>
							<option value="6009" <c:if test="${trxtype == 6009}">selected="selected"</c:if>>预授权完成撤销冲正</option>
							<option value="4012" <c:if test="${trxtype == 4012}">selected="selected"</c:if>>预授权撤销</option>
							<option value="6011" <c:if test="${trxtype == 6011}">selected="selected"</c:if>>预授权撤销冲正</option>
							<option value="6010" <c:if test="${trxtype == 6010}">selected="selected"</c:if>>预授权</option>
							<option value="4011" <c:if test="${trxtype == 4011}">selected="selected"</c:if>>预授权冲正</option>
							<option value="9002" <c:if test="${trxtype == 9002}">selected="selected"</c:if>>余额查询</option>
						</select>
					</td>
					
					<td>
						<label>批次号：</label>
						<input type="text" id="ylbatchno" name="ylbatchno" value="${ylbatchno}" size="20"  alt="精确搜索"/>
					</td>
					<td>
						<label>流水号：</label>
						<input type="text" id="yltraceno" name="yltraceno" value="${yltraceno}" size="20"  alt="精确搜索"/>
					</td>
					<td>
						<label>参考号：</label>
						<input type="text" id="ylrefno" name="ylrefno" value="${ylrefno}" size="20"  alt="精确搜索"/>
					</td>
					</tr><tr>
					<td colspan="3">
						<label>渠道名称：</label> 
						<select name="channelCode" id="channelCode">
							<option value="">请选择</option>
							<c:forEach items="${channelInfoList }" var="v">
								<option value="${v.channelCode }" <c:if test="${v.channelCode eq  channelCode}">selected="selected"</c:if> >${v.channelName}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<div class="subBar" style="float: right;">
							<ul>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<z:permission value="channel:detail:export">
			<li>
				<a class="icon" href="###" onclick="exportExcelChannelTrans();" target="dwzExport" targettype="navTab"> <span>导出EXCEL</span> </a>
			</li>
			</z:permission>
			<li class="line">line</li>
			<li>
			<span>成功总笔数：<font color="red">${count.count}</font>笔
			&nbsp;&nbsp;成功总金额：<font color="red"><c:choose><c:when test="${count.sum eq null}">0.00</c:when><c:otherwise>${count.sum}</c:otherwise></c:choose></font>元
			&nbsp;&nbsp;失败总笔数：<font color="red">${countFacade.count}</font>笔
			&nbsp;&nbsp;失败总金额：<font color="red"><c:choose><c:when test="${countFacade.sum eq null}">0.00</c:when><c:otherwise>${countFacade.sum}</c:otherwise></c:choose></font>元
			&nbsp;&nbsp;总手续费：<font color="red"><c:choose><c:when test="${totalBankFee.sum eq null}">0.00</c:when><c:otherwise> <fmt:formatNumber value="${ totalBankFee.sum}" pattern="#0.00"></fmt:formatNumber> </c:otherwise></c:choose></font>元
			</span>
		</ul>
	</div>
</div>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="190">
		<thead>
			<tr>
				<th>序号</th>
				<th>交易时间</th>
				<th>渠道名称</th>
				<th>渠道商户编号</th>
				<th>渠道商户名称</th>
				<th>交易类型</th>
				<th>交易金额</th>
				<th>银行手续费</th>
				<th>批次号</th>
				<th>流水号</th>
				<th>参考号</th>
				<th>交易结果</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}">
					<td align="center">${st.index+1}</td>
					<td align="center">${fn:substring(transdate,0,4)}-${fn:substring(transdate,4,6)}-${fn:substring(transdate,6,8)}<br/>
					${fn:substring(transtime,0,2)}:${fn:substring(transtime,2,4)}:${fn:substring(transtime,4,6)}</td>
					<td align="center">${channelCode }</br>
					${channelName} </td>
					<td align="center">${ylmerchantno}</td>
					<td>${channelMerchName}</td>
					<td align="center">${summ}</td>
					<td align="right"><fmt:formatNumber value="${amount}" type="currency" /></td>
					<td align="right"><fmt:formatNumber value="${bankFee}" type="currency" /></td>
					<td align="center">${ylbatchno}</td>
					<td align="center">${yltraceno}</td>
					<td align="center">${ylrefno}</td>
					<td align="center">
						<c:choose>
							<c:when test="${ylrespcode eq '00'}">
							   成功
							</c:when>
							<c:otherwise>
						              失败[${ylrespcode}]
							</c:otherwise>
						</c:choose>
					</td>
					<!-- 
					<td>
						<c:forEach items="${memberStatusList}" var="statu">
							<c:if test="${status eq statu.value}">${statu.desc }</c:if>
						</c:forEach>
					</td>
					 -->
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
