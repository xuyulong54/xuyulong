<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name="remitOrderCheckForm"
		onsubmit="return navTabSearch(this);"
		action="remitOrderCheck_listRemitOrderCheck.action" method="post">
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
						value="${requestNo }" size="25" alt="精确搜索" /></td>
					<%-- <td>
						<label>打款流水号：</label>
						<input type="text" name="flowNo" value="${flowNo }" size="25" alt="精确搜索"/>
					</td> --%>
					<td><label>开户银行名称：</label> <input type="text" name="bankName"
						value="${bankName }" size="25" alt="精确搜索" /></td>
					<td>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
							</ul>
						</div></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<z:permission value="boss:remitOrderCheck:check">
			<li><a class="icon" type="button" onclick="batchSub()"
				style="color:blue"><span>批量提交</span>
			</a>
			</li>
		</z:permission>
	</ul>
</div>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th><label style="float:left"><input type="checkbox"
						id="allPass" onclick="selectAllPass(this)" />全通过</label> <label
					style="float:left"><input type="checkbox" id="allReject"
						onclick="selectAllreject(this)" />全拒绝</label></th>
				<th>打款请求号</th>
				<th>收款账户</th>
				<th>开户行</th>
				<th>打款金额</th>
				<th>类型</th>
				<th>状态</th>
				<th>创建人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td><label><input type="checkbox" name="pass"
							value="${id}" id="pass${st.index+1}"
							onclick="changeSingeCheck(${st.index+1},true);">通过</label> <label><input
							type="checkbox" name="reject" value="${id}"
							id="reject${st.index+1}"
							onclick="changeSingeCheck(${st.index+1},false);">拒绝</label></td>
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
						</c:forEach></td>
					<td><c:forEach items="${remitRequestStatusEnumList }"
							var="remitRequestStatusEnum">
							<c:if test="${remitRequestStatusEnum.value eq status }">${remitRequestStatusEnum.desc }</c:if>
						</c:forEach></td>
					<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /><br />${creator}</td>
					<td><z:permission value="boss:remitOrderCheck:view">
							<a href="remitOrderCheck_viewRemitDetail.action?id=${id }"
								title="审核数据" target="dialog" rel="input" width="800"
								height="376" style="color:blue">审核</a>
						</z:permission></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
<form id="batchForm" onsubmit="return navTabSearch(this);" method="post">
	<input type="hidden" name="flag" value="1" />
</form>
<script type="text/javascript">
		/**
			批量提交
		*/
		function batchSub(){
			var passCheckBoxes = document.getElementsByName('pass');
			var rejectCheckBoxes = document.getElementsByName('reject');
			var passOrders = "";
			var rejectOrders = "";
			//拼装通过的数据id
			for (var i = 0; i < passCheckBoxes.length; i++) {
				if(passCheckBoxes[i].checked){
					passOrders += passCheckBoxes[i].value + "|";
				}
			}
			//拼装不通过的数据id
			for (var i = 0; i < rejectCheckBoxes.length; i++) {
				if(rejectCheckBoxes[i].checked){
					rejectOrders += rejectCheckBoxes[i].value + "|";
				}
			}
			if(passOrders == "" && rejectOrders == ""){
				alertMsg.warn("您尚未勾选要批量操作的数据");
			}else{
				alertMsg.confirm("确定要批量操作？确定后将不可更改！", {
					okCall: function(){
							var url = "remitOrderCheck_remitDoCheck.action?passOrders="+passOrders+"&rejectOrders="+rejectOrders;
							//document.getElementById('passOrders').value=passOrders;
							//document.getElementById('rejectOrders').value=rejectOrders;
							$("#batchForm").attr("action", url).submit();
						}
				});
			}
		}	
		/**全选通过*/
		function selectAllPass(checkbox) {
			document.getElementById('allReject').checked = false;
			var passCheckBoxes = document.getElementsByName('pass');
			var rejectCheckBoxes = document.getElementsByName('reject');
			if(checkbox.checked){ //选择全选通过
				changeCheckBoxForVar(passCheckBoxes,true);
			}else{//取消全选通过
				changeCheckBoxForVar(passCheckBoxes,false);
			}
			changeCheckBoxForVar(rejectCheckBoxes,false);
		}
		
		/**全选拒绝*/
		function selectAllreject(checkbox) {
			document.getElementById('allPass').checked = false;
			var passCheckBoxes = document.getElementsByName('pass');
			var rejectCheckBoxes = document.getElementsByName('reject');
			if(checkbox.checked){ //选择全选通过
				changeCheckBoxForVar(rejectCheckBoxes,true);
			}else{//取消全选通过
				changeCheckBoxForVar(rejectCheckBoxes,false);
			}
			changeCheckBoxForVar(passCheckBoxes,false);
		}
		
		/**
			单条勾选
		*/
		function changeSingeCheck(index,taskVar){
			var passObj = document.getElementById('pass'+index);
			var rejectObj = document.getElementById('reject'+index);
			if(taskVar){ //点击的单条通过
				if(passObj.checked){//现在选中，之前为未选中
					rejectObj.checked = false;
					document.getElementById('allReject').checked = false;
				}
			}else{ //点击的单条拒绝
				if(rejectObj.checked){//现在选中，之前为未选中
					passObj.checked = false;
					document.getElementById('allPass').checked = false;
				}
			}
			document.getElementById('allReject').checked = false;
			document.getElementById('allPass').checked = false;
		}
		
		/**将传入的多选列表的选中值更改为传入参数*/
		function changeCheckBoxForVar(arrObject,changeVar){
			for (var i = 0; i < arrObject.length; i++) {
				arrObject[i].checked = changeVar;
			}
		}
</script>