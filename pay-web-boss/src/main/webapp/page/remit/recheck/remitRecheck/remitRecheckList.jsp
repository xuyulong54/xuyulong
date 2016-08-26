<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name="remitRecheckForm"
		onsubmit="return navTabSearch(this);"
		action="remitRecheck_remitRecheckList.action" method="post">

		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>创建时间：</label>
						<input name="beginDate" value="${beginDate }" dateFmt="yyyy-MM-dd" type="text"  style="width: 77px;" class="date"  /> 
						至<input name="endDate" value="${endDate }" dateFmt="yyyy-MM-dd" type="text" class="date" id="endDate" style="width: 77px;" />
					</td>
					<td><label>打款请求号：</label> <input type="text" name="requestNo"
						value="${requestNo }" size="25" alt="精确搜索" />
					</td>
					<td>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div></li>
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
			<li><a class="icon" type="button" onclick="batchRecheckSub()"
				style="color:blue"><span>批量提交</span> </a></li>
				<li><span> 总笔数：<b style="color:red;">${totalCount } </b>笔&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<span> 总金额：<b style="color:red;"><fmt:formatNumber value=" ${ empty countResultMap.totalAmount ?0.00: countResultMap.totalAmount}" type="currency" />&nbsp;&nbsp;&nbsp;&nbsp;</b></span>
				</li>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th><label style="float:left"><input type="checkbox"
						id="allBatchPass" onclick="selectRecheckAllPass(this)" />全通过</label> <label
					style="float:left"><input type="checkbox"
						id="allBatchReject" onclick="selectRecheckAllreject(this)" />全拒绝</label>
				</th>
				<th>打款请求号</th>
				<th>收款账户</th>
				<th>开户行</th>
				<th>打款金额</th>
				<th>类型</th>
				<th>状态</th>
				<th>创建人</th>
				<th>审核人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td><z:permission value="boss:remitRecheck:recheck">
							<%--<c:if test="${status eq  remitBatchStatusEnumMap.SUSPENDING.value}"> --%>
							<label><input type="checkbox" name="recheckPass"
								value="${id}" id="recheckPass${st.index+1}"
								onclick="changeSingeRecheck(${st.index+1},true);">通过</label>
							<label><input type="checkbox" name="recheckReject"
								value="${id}" id="recheckReject${st.index+1}"
								onclick="changeSingeRecheck(${st.index+1},false);">拒绝</label>
							<%--</c:if>--%>
						</z:permission>
					</td>
					<td>${requestNo }</td>
					<td>${accountName}<br /> ${accountNo} (<c:forEach
							items="${bankAccountTypeEnumList }" var="bankAccountTypeEnum">
							<c:if test="${bankAccountTypeEnum.value eq accountType }">${bankAccountTypeEnum.desc }</c:if>
						</c:forEach>)</td>
					<td>${bankChannelNo }<br />${bankName}</td>
					<td>${amount}</td>
					<td><c:forEach items="${tradeTypeEnumList }"
							var="tradeTypeEnum">
							<c:if test="${tradeTypeEnum.value eq businessType }">${tradeTypeEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td><c:forEach items="${remitRequestStatusEnumList }"
							var="remitRequestStatusEnum">
							<c:if test="${remitRequestStatusEnum.value eq status }">${remitRequestStatusEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /><br />${creator}</td>
					<td><s:date name="confirmDate" format="yyyy-MM-dd HH:mm:ss" /><br />${confirm}</td>

					<td><z:permission value="boss:remitRecheck:detail">
							<a href="remitRecheck_remitRecheckDetail.action?id=${id }"
								title="查看明细" target="dialog" rel="input" width="800"
								height="376" style="color:blue">查看明细</a>
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>

<form id="remitCheckForm" onsubmit="return navTabSearch(this);"
	method="post">
	<input type="hidden" name="flag" value="1" />
</form>

<script type="text/javascript">
		/**
			批量提交
		*/
		function batchRecheckSub(){
			var passCheckBoxes = document.getElementsByName('recheckPass');
			var rejectCheckBoxes = document.getElementsByName('recheckReject');
			var passOrders = "";
			var rejectOrders = "";
			var j = 0;
			//拼装通过的数据id
			for (var i = 0; i < passCheckBoxes.length; i++) {
				if(passCheckBoxes[i].checked){
					if(j==0){
						passOrders += passCheckBoxes[i].value;
					}else{
						passOrders +=","+passCheckBoxes[i].value;
					}
					j++;
				}
			}
			//拼装不通过的数据id
			j=0;
			for (var i = 0; i < rejectCheckBoxes.length; i++) {
				if(rejectCheckBoxes[i].checked){
					if(j==0){
						rejectOrders += rejectCheckBoxes[i].value;
					}else{
						rejectOrders += ","+rejectCheckBoxes[i].value;
					}
					j++;
				}
			}
			
			if(passOrders == "" && rejectOrders == ""){
				alertMsg.warn("您提交的数据有误，请检查后重新提交！");
			}else{
				
				alertMsg.confirm("确定要批量操作？确定后将不可更改！", {
					okCall: function(){
							var url = "remitRecheck_remitBatchRecheck.action?passOrders="+passOrders+"&rejectOrders="+rejectOrders;
							//document.getElementById("passOrders").value=passOrders;
							//document.getElementById("rejectOrders").value=rejectOrders;
							$("#remitCheckForm").attr("action", url).submit();
						}
				});
				
				//$.post("remitRecheck_remitBatchRecheck.action",{
				//	passOrders:passOrders,
				//	rejectOrders:rejectOrders,
				//	channelCode:$("#bankChannelCode").children('option:selected').val()
				//},function(result){
				//	if(result.STATE=="SUCCESS"){
				//		 alertMsg.info(result.MSG);
				//		 $("form[name='remitRecheckForm']").submit();
				//	 }else if(result.STATE == "FAIL"){
				//	 	alertMsg.error(result.MSG);
				//	 }
				//},"json");
			}
		}
		
		/**全选通过*/
		function selectRecheckAllPass(checkbox) {
			document.getElementById('allBatchReject').checked = false;
			var passCheckBoxes = document.getElementsByName('recheckPass');
			var rejectCheckBoxes = document.getElementsByName('recheckReject');
			if(checkbox.checked){ //选择全选通过
				changeRecheckBoxForVar(passCheckBoxes,true);
			}else{//取消全选通过
				changeRecheckBoxForVar(passCheckBoxes,false);
			}
			changeRecheckBoxForVar(rejectCheckBoxes,false);
			updateBatchRemitInfo();
		}
		
		/**全选拒绝*/
		function selectRecheckAllreject(checkbox) {
			document.getElementById('allBatchPass').checked = false;
			var passCheckBoxes = document.getElementsByName('recheckPass');
			var rejectCheckBoxes = document.getElementsByName('recheckReject');
			if(checkbox.checked){ //选择全选通过
				changeRecheckBoxForVar(rejectCheckBoxes,true);
			}else{//取消全选通过
				changeRecheckBoxForVar(rejectCheckBoxes,false);
			}
			changeRecheckBoxForVar(passCheckBoxes,false);
			updateBatchRemitInfo();
		}
		
		/**
			单条勾选
		*/
		function changeSingeRecheck(index,taskVar){
			var passObj = document.getElementById('recheckPass'+index);
			var rejectObj = document.getElementById('recheckReject'+index);
			if(taskVar){ //点击的单条通过
				if(passObj.checked){//现在选中，之前为未选中
					rejectObj.checked = false;
					document.getElementById('allBatchReject').checked = false;
				}
			}else{ //点击的单条拒绝
				if(rejectObj.checked){//现在选中，之前为未选中
					passObj.checked = false;
					document.getElementById('allBatchPass').checked = false;
				}
			}
			document.getElementById('allBatchReject').checked = false;
			document.getElementById('allBatchPass').checked = false;
			updateBatchRemitInfo();
		}
		
		/**将传入的多选列表的选中值更改为传入参数*/
		function changeRecheckBoxForVar(arrObject,changeVar){
			for (var i = 0; i < arrObject.length; i++) {
				arrObject[i].checked = changeVar;
			}
		}
		
		function updateBatchRemitInfo(){
			$("#batchNumber").text($("input[name='recheckPass']:checked").length);
			var totalAmount = 0;
			for(var i=0;i<$("input[name='recheckPass']:checked").length;i++){
				var passBatch = $("input[name='recheckPass']:checked")[i];
				totalAmount+=parseFloat($(passBatch).parent().parent().parent().parent().find("td[name='totalAmount']").text());
			}
			$("#batchAmount").text(totalAmount.toFixed(2));
		}
		
		$(function(){
			updateBatchRemitInfo();
		});
</script>