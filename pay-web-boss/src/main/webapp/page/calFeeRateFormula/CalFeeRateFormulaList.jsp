<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="calFeeRateFormula_calFeeRateFormulaList.action">
    <%@include file="/page/inc/pageForm.jsp"%>
</form>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="calFeeRateFormula_calFeeRateFormulaList.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<input type="hidden" name="feeWayId" value="${feeWayId }">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					当前计费类型：
					<c:forEach items="${calTypeEnum }" var="calTypeEnum">
						<c:if test="${calType eq calTypeEnum.value }">${calTypeEnum.desc }</c:if>
					</c:forEach>
				</td>	
			</tr> 
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
	<z:permission value="boss:calFeeRateFormula:add">
		<ul class="toolBar">
			<li><a class="add" close="dialogAjax" href="calFeeRateFormula_addCalFeeRateFormulaUI.action?feeWayId=${feeWayId }&calType=${calType}" target="dialog" height="400" width="500" rel="input"  title="添加计费公式"><span>添加</span></a></li>
		</ul>
	</z:permission>
	</div>
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="133">
		<thead>
			<tr>
				<th >序号</th>
				<th >计费约束</th>
				<th >计费模式</th>
				<th >固定手续费</th>
				<th >手续费率(%)</th>
				<c:if test="${calType eq calTypeMap.INTERVAL.value }">
					<th>区间下限</th>
					<th>区间上限</th>
				</c:if>
				<c:if test="${calType eq calTypeMap.LADDER_SINGLE.value || calType eq calTypeMap.LADDER_MULTIPLE.value }">
					<th>阶梯下限</th>
					<th>阶梯上限</th>
				</c:if>
				<th>最低手续费</th>
				<th>最高手续费</th>
				<th >创建时间</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>
				    	<c:forEach items="${calFeeWayList }" var="calFeeWay">
							<c:if test="${calFeeWay.id eq feeWayId }">${calFeeWay.wayName }</c:if>
						</c:forEach>
			    	</td>
				    <td>
				    	<c:forEach items="${calModelEnum }" var="help">
							<c:if test="${help.value eq model }">${help.desc }</c:if>
						</c:forEach>
				    </td>
				    <td align="right"><fmt:formatNumber value="${fixedFee}" pattern="0.00"></fmt:formatNumber></td>
					<td align="right">
					<c:if test="${percentFee == null }">${percentFee }</c:if>
					<c:if test="${percentFee == '' }">${percentFee }</c:if>
					<c:if test="${percentFee != null }"><fmt:formatNumber value="${percentFee*100 }" pattern="0.00"/></c:if>
					</td>
					
					<c:if test="${calType eq calTypeMap.INTERVAL.value }">
						<td align="right">${minAmount}</td>
						<td align="right">${maxAmount}</td>
					</c:if>
					<c:if test="${calType eq calTypeMap.LADDER_SINGLE.value || calType eq calTypeMap.LADDER_MULTIPLE.value }">
						<td align="right">${minLadderAmount}</td>
						<td align="right">${maxLadderAmount}</td>
					</c:if>
				    <td align="right"><fmt:formatNumber value="${singleMinFee}" pattern="0.00"/></td>
				    <td align="right"><fmt:formatNumber value="${singleMaxFee}" pattern="0.00"/></td>
					<td align="center"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
	function dialogAjax(){
		var url = "calFeeRateFormula_calFeeRateFormulaList.action?feeWayId=${feeWayId }&calType=${calType }";
		$.pdialog.reload(url,{
			data:{},
			dialogId:"calFeeRateFormula",
			callback:null
		});
		return true;
	}
</script>