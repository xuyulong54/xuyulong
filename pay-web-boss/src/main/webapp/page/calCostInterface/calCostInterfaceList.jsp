<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="interfaceForm" onsubmit="return navTabSearch(this);" action="calCostInterface_calCostInterfaceList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>计费接口编号：</label>
						<input type="text" name="interfaceCode" value="${interfaceCode }" size="25" alt="精确搜索"/>
					</td>
					<td>
						<label>计费接口名称：</label>
						<input type="text" name="interfaceName" value="${interfaceName }" size="25" alt="精确搜索"/>
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
	<z:permission value="boss:calCostInterface:add">
		<ul class="toolBar">
			<li><a href="calCostInterface_calCostInterfaceAdd.action" class="add" target="dialog" rel="input" width="509" height="460"  title="添加计费接口"><span>添加</span></a></li>
		</ul>
	</z:permission>
	</div>
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>计费接口编号</th>
				<th>计费接口名称</th>
				<!-- <th>计费接口业务类型</th> -->
				<th>扣费类型</th>
				<th>账单周期</th>
				<th>自定义账单周期</th>
				<th>自定义账单日</th>
				<th>计费接口状态</th>
				<th>成本计费策略</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${interfaceCode}</td>
				    <td>${interfaceName}</td>
				    <%-- <td>${interfaceType}</td> --%>
				    <td>
						<c:forEach items="${costInterfaceChargeTypeEnumList }" var="costInterfaceChargeTypeEnum">
							<c:if test="${costInterfaceChargeTypeEnum.value eq chargeType }">${costInterfaceChargeTypeEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${costBillCycleEnumList }" var="costBillCycleEnum">
							<c:if test="${costBillCycleEnum.value eq billCycle }">${costBillCycleEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>${customBillCycle}</td>
					<td align="center">
						<s:date name="customBillDay" format="yyyy-MM-dd" />
					</td>
					<td>
						<c:forEach items="${costInterfaceStatusEnumList }" var="costInterfaceStatusEnum">
							<c:if test="${costInterfaceStatusEnum.value eq status }">${costInterfaceStatusEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${costInterfacePolicyEnumList }" var="costInterfacePolicyEnum">
							<c:if test="${costInterfacePolicyEnum.value eq policy }">${costInterfacePolicyEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>${remark}</td>
					<td>
						<z:permission value="boss:calCostInterface:edit">
							<a  href="calCostInterface_calCostInterfaceEdit.action?id=${id }" title="修改计费接口" target="dialog" rel="input" width="509" height="485" style="color:blue">修改</a>
							&nbsp;|&nbsp;
							<a  href="javascript:changeInterfaceStatus(${id},${status});" title="修改接口状态" style="color:blue">修改状态</a>
							&nbsp;|&nbsp;
						</z:permission>
						<z:permission value="boss:calCostInterface:delete">
							<a  href="javascript:deleteCalCostInterface('${interfaceCode }')" title="删除计费接口" style="color:blue">删除</a>
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
	function deleteCalCostInterface(calCostInterfaceCode){
		alertMsg.confirm("确定要删除该计费接口?", {
			okCall: function(){
				$.post("calCostInterface_deleteCalCostInterface.action",{"calCostInterfaceCode":calCostInterfaceCode},function(res){
					 if(res.STATE=="SUCCESS"){
						 alertMsg.correct(res.MSG);
						 $("#pagerForm").submit();
					 }else if(res.STATE == "FAIL"){
					 	alertMsg.error(res.MSG);
					 }
				 },"json");
			}
		});
	}
	
	
	function changeInterfaceStatus(id, status) {
		var msg = "";
		if ('100' == status) {
			msg = "您确定要将该计费接口置为关闭状态吗？";
			status = '101';//转换为要修改成的状态值，传到后台
		} else {
			msg = "您确定要将该计费接口置为开启状态吗？";
			status = '100';//转换为要修改成的状态值，传到后台
		}
		alertMsg.confirm(msg, {
			okCall : function() {
				$.post("calCostInterface_changeInterfaceStatus.action", {
					id : id,
					status : status
				}, function(res) {
					$("form[name='interfaceForm']").submit();
					if (res.STATE == "SUCCESS") {
						alertMsg.correct(res.MSG);
					} else {
						alertMsg.error(res.MSG);
					}
				}, "json");
			}
		});
	}
</script>