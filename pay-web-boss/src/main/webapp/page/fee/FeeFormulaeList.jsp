<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

 <div class="pageHeader">
	<form id="pagerForm" name="feeFormulaeListForm" onsubmit="return dialogSearch(this);" action="FeeFormulae_feeFormulaeList.action" method="post">
		<!-- 分页表单参数 -->
		<input value="${calculateType }" name="calculateType" type="hidden"/>
		<input value="${calculateWayId }" name="id" type="hidden"/>
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>计费方式名称： <input name="calculateTypeName" value="${calculateTypeName }" type="text" readonly="readonly" />
					</td>
					<td>公式类型： <select name="formulaType">
							<option value="">请选择</option>
							<c:forEach items="${FeeFormulaTypeEnum }" var="models">
								<option value="${models.value }" <c:if test="${formulaType eq models.value }">selected="selected"</c:if>>${models.desc}</option>
							</c:forEach>
						</select>
					</td>
					<td>是否有效： 
					<select name="status">
							<option value="">请选择</option>
							<c:forEach items="${StatusEnum }" var="models">
								<option value="${models.value }" <c:if test="${status eq models.value }">selected="selected"</c:if>>${models.desc}</option>
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
			<li>	
			<z:permission value="fee:formula:add">
				<a class="add" href="FeeFormulae_addFeeFormulaeUI.action?calculateWayId=${calculateWayId }&calculateType=${calculateType}" target="dialog" height="400" width="600" rel="feeFormulaeList" close="dialogAjax" title="新增计费公式"><span>新增计费公式</span></a>
			</z:permission>
			</li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>创建时间</th>
				<th>公式类型</th>
				<th>固定手续费</th>
				<th>手续费率（%）</th>
				<th>单笔最低手续费</th>
				<th>单笔最高手续费</th>
				<c:if test="${calculateType == 3 || calculateType == 4}">
				<th>阶梯金额下限</th>
				<th>阶梯金额上限</th>
				</c:if>
				<c:if test="${calculateType == 2}">
				<th>区间金额下限</th>
				<th>区间金额上限</th>
				</c:if>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
							<c:forEach items="${FeeFormulaTypeEnum }" var="models">
								<c:if test="${formulaType eq models.value }">${models.desc }</c:if>
							</c:forEach>
					</td>
					<td><fmt:formatNumber value="${fixedFee }" pattern="#,##0.00#" /></td>
					<td><fmt:formatNumber value="${percentFee==null?percentFee:(percentFee*100) }" pattern="#,##0.00####" /></td>
					<td><fmt:formatNumber value="${percentFee==null?null:singleMinFee }" pattern="#,##0.00#" /></td>
					<td><fmt:formatNumber value="${singleMaxFee }" pattern="#,##0.00#" /></td>
					<c:if test="${calculateType == 3 || calculateType == 4}">
					<td><fmt:formatNumber value="${minLadderAmount }" pattern="#,##0.00#" /></td>
					<td><fmt:formatNumber value="${maxLadderAmount }" pattern="#,##0.00#" /></td>
					</c:if>
					<c:if test="${calculateType == 2}">
					<td><fmt:formatNumber value="${minAmount }" pattern="#,##0.00#" /></td>
					<td><fmt:formatNumber value="${maxAmount }" pattern="#,##0.00#" /></td>
					</c:if>
					<td>
					<c:forEach items="${StatusEnum }" var="enums">
					<c:if test="${status eq enums.value }">${enums.desc }</c:if>
					</c:forEach>
					</td>
					<td>
					<z:permission value="fee:formula:add">
					<c:if test="${status eq 100 }"><a href="javascript:changeStatus('${id }','${status }');" style="color: blue;">设置无效</a></c:if>
					<c:if test="${status eq 101 }"><a href="javascript:changeStatus('${id }','${status }');" style="color: blue;">设置有效</a></c:if>
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
function dialogAjax(){
	var calculateWayId = $("input[name='calculateWayId']").val();
	var calculateType = $("input[name='calculateType']").val();
	var url = "FeeFormulae_feeFormulaeList.action";
	$.pdialog.reload(url,{
		data:{id:calculateWayId,calculateType:calculateType},
		dialogId:"FeeFormulaeList",
		callback:null
	});	
	return true;
}

function changeStatus(id,status){
	$.post("FeeFormulae_changeStatus.action", {
		status : status,
		id : id
	}, function(res) {
		if(res.STATE == "SUCCESS"){
			$("form[name='feeFormulaeListForm']").submit();
		}else{
			alertMsg.error("设置出错！");
		}
		
	}, "json");
	
}

</script>
