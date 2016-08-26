<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="remitChannelFlowRuleForm" onsubmit="return navTabSearch(this);" action="remitChannelFlowRule_listRemitChannelFlowRule.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>打款通道：</label>
						<select name="channelCode" >
							<option value="">请选择</option>
							<c:forEach items="${remitChannelList }" var="remitChannel">
								<option value="${remitChannel.channelCode }" <c:if test="${channelCode eq remitChannel.channelCode}">selected="selected"</c:if> >${remitChannel.channelName}</option>
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
	<z:permission value="boss:remitChannelFlowRule:add">
		<ul class="toolBar">
			<li><a href="remitChannelFlowRule_addRemitChannelFlowRuleUI.action" class="add" target="dialog" rel="input" width="800" height="466"  title="添加分流规则"><span>添加</span></a></li>
		</ul>
	</z:permission>
	</div>
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>分流规则名称</th>
				<th>打款通道</th>
				<th>最小额</th>
				<th>最大额</th>
				<!-- <th>是否加急</th> -->
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${channelFlowName}</td>
				    <td>${channelCode}</td>
				    <td>${minAmount}</td>
				    <td>${maxAmount}</td>
				   <%--  <td>
				    	<c:forEach items="${remitUrgentEnumList }" var="remitUrgentEnum">
							<c:if test="${remitUrgentEnum.value eq isUrgent }">${remitUrgentEnum.desc }</c:if>
						</c:forEach>
				    </td> --%>
				    <td>
						<c:forEach items="${publicStatusEnumList }" var="publicStatusEnum">
							<c:if test="${publicStatusEnum.value eq status }">${publicStatusEnum.desc }</c:if>
						</c:forEach>
					</td>
					
					<td>
						<z:permission value="boss:remitChannelFlowRule:edit">
							<a  href="remitChannelFlowRule_editRemitChannelFlowRuleUI.action?id=${id }" title="修改打款分流规则" target="dialog" rel="input" width="800" height="466" style="color:blue">修改</a>
							&nbsp;|&nbsp;
						</z:permission>
						<z:permission value="boss:remitChannelFlowRule:view">
							<a  href="remitChannelFlowRule_viewRemitChannelFlowRuleUI.action?id=${id }" title="查看打款分流规则" target="dialog" rel="input" width="800" height="466" style="color:blue">查看</a>
							&nbsp;|&nbsp;
						</z:permission>
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