<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="remitProcessForm" onsubmit="return navTabSearch(this);" action="remitProcess_remitProcessList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>收款户名：</label>
						<input type="text" name="accountName" value="${accountName }"  alt="模糊搜索"/>
					</td>
					<td>
						<label>收款账号：</label>
						<input type="text" name="accountNo" value="${accountNo }" alt="模糊搜索"/>
					</td>
					<td>
						<label>收款发卡行：</label>
						<input type="text" name="bankName" value="${bankName}"  alt="模糊搜索"/>
					</td>
					<td>
						<label>打款通道：</label>
						<select name="remitBankChannelCode" id="remitBankChannelCode">
							<option value="">请选择</option>
							<c:forEach items="${remitChannelList }" var="remitChannel">
								<option value="${remitChannel.channelCode }" <c:if test="${channelCode eq remitChannel.channelCode }">selected="selected"</c:if>>${remitChannel.channelName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>创建时间：</label>
						<input name="beginDate" value="${beginDate }" dateFmt="yyyy-MM-dd" type="text"  style="width: 77px;" class="date"  /> 
						至<input name="endDate" value="${endDate }" dateFmt="yyyy-MM-dd" type="text" class="date" id="endDate" style="width: 77px;" />
					</td>
					<td>
						<label>打款请求号：</label>
						<input type="text" name="requestNo" value="${requestNo }"  alt="精确搜索"/>
					</td>
					<td>
						<label>状态：</label>
						<select name="status" id="status">
							<option value="">请选择</option>
							<c:forEach items="${remitProcessStatusEnumList }" var="remitProcessStatusEnum">
								<option value="${remitProcessStatusEnum.value }" <c:if test="${status eq remitProcessStatusEnum.value }">selected="selected"</c:if>>${remitProcessStatusEnum.desc}</option>
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

	<div class="panelBar" >
		<ul class="toolBar">
		</ul>
	</div>

<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="160">
		<thead>
			<tr>
				<th>序号</th>
				<th width="80">创建时间</th>
				<th>审核批次号</th>
				<th>打款请求号</th>
				<th>打款通道</th>
				<th>收款信息</th>
				<th align="right">收款金额</th>
				<th>类型</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td><fmt:formatDate value="${createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				    <td>${batchNo }</td>
				    <td>${requestNo}</td>
				    <td>
				    	<c:forEach items="${remitChannelList }" var="remitChannel">
							<c:if test="${channelCode eq remitChannel.channelCode }">${remitChannel.channelName}</c:if>
						</c:forEach>
					</td>
				    <td>
				    	${accountName } <br/>
				    	${accountNo }<br/>
				    	${bankName}(${bankChannelNo})
				    </td>
				    <td>
				    	<fmt:formatNumber value="${amount }" type="currency" />
				    </td>
				    <td><c:forEach items="${tradeTypeEnumList }"
							var="tradeTypeEnum">
							<c:if test="${tradeTypeEnum.value eq businessType }">${tradeTypeEnum.desc }</c:if>
						</c:forEach></td>
				    <td>
					    <c:forEach items="${RemitProcessStatusEnums }" var="v">
					    	<c:if test="${status==v.value }">${v.desc}</c:if>
					    </c:forEach>
				    </td>
				    <td>
				    <z:permission value="boss:remitProcess:view">
				    	<a  href="remitProcess_remitProcessView.action?id=${id}" title="打款记录详情" target="dialog" rel="input" width="801" height="450" style="color:blue">查看</a>
				    </z:permission>
				    </td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
