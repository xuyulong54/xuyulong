<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="remitChannelForm" onsubmit="return dwzSearch(this, 'dialog');" action="remitChannel_remitChannelFLowRuleLookUp.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<%-- <td>
						<label>打款通道编号：</label>
						<input type="text" name="channelCode" value="${channelCode }" size="25" alt="精确搜索"/>
					</td> --%>
					<td>
						<label>打款通道名称：</label>
						<input type="text" name="channelName" value="${channelName }" size="25" alt="模糊搜索"/>
					</td>
					<td>
						<label>所属银行：</label>
						<select name="bankTypeCode" id="bankTypeCode">
							<option value="">请选择</option>
							<c:forEach items="${remitBankTypeList }" var="remitBankType">
								<option value="${remitBankType.typeCode}" <c:if test="${bankTypeCode eq remitBankType.typeCode }">selected="selected"</c:if>>${remitBankType.typeName}</option>
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
	<div class="panelBar">
	<z:permission value="boss:remitChannel:add">
		<ul class="toolBar">
			<li><a href="remitChannel_remitChannelAdd.action" class="add" target="dialog" rel="input" width="800" height="366"  title="添加打款通道"><span>添加</span></a></li>
		</ul>
	</z:permission>
	</div>
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="133">
		<thead>
			<tr>
				<th>序号</th>
				<th>打款通道编号</th>
				<th>打款通道名称</th>
				<th>账户类型</th>
				<th>所属银行</th>
				<th>总笔数限制</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${channelCode}</td>
				    <td>${channelName}</td>
				    <td>
				    	<c:forEach items="${bankAccountTypeEnumList }" var="bankAccountTypeEnum">
							<c:if test="${bankAccountTypeEnum.value eq accountType }">${bankAccountTypeEnum.desc }</c:if>
						</c:forEach>
				    </td>
				    <td>
						<c:forEach items="${remitBankTypeList }" var="remitBankType">
							<c:if test="${remitBankType.typeCode eq bankTypeCode }">${remitBankType.typeName }</c:if>
						</c:forEach>
					</td>
					<td>${limitNum}</td>
					<td>
						<c:forEach items="${publicStatusEnumList }" var="publicStatusEnum">
							<c:if test="${publicStatusEnum.value eq status }">${publicStatusEnum.desc}</c:if>
						</c:forEach>
					</td>
					
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({channelCode:'${channelCode}' ,backFlag:'1' })" title="查找带回">选择</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
</script>