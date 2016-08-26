<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="remitRequestForm" onsubmit="return navTabSearch(this);" action="remitRequest_reRemitRequestList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>制单时间：</label>
						<input name="beginDate" value="${beginDate }" dateFmt="yyyy-MM-dd" type="text"  style="width: 77px;" class="date"  /> 
						至<input name="endDate" value="${endDate }" dateFmt="yyyy-MM-dd" type="text" class="date" id="endDate" style="width: 77px;" />
					</td>
					<td>
						<label>业务发起方：</label>
						<select name="tradeInitiator">
							<option value="">请选择 </option>
							<c:forEach items="${TradeSourcesEnums }" var="v">
							<option value="${v.value}" <c:if test="${tradeInitiator==v.value }"> selected="selected" </c:if>>${v.desc }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label>打款请求号：</label>
						<input type="text" name="requestNo" value="${requestNo }" size="25" alt="精确搜索"/>
					</td>
					</tr><tr>
					<td>
						<label>收款户名：</label>
						<input type="text" name="accountName" value="${accountName }" size="25" alt="模糊搜索"/>
					</td>
					<td>
						<label>收款账号：</label>
						<input type="text" name="accountNo" value="${accountNo }" size="25" alt="模糊搜索"/>
					</td>
					<td>
						<label>收款发卡行：</label>
						<input type="text" name="bankName" value="${bankName}" size="25" alt="模糊搜索"/>
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
<div class="panelBar">
	<ul class="toolBar">
		<z:permission value="boss:remitOrder:reAdd">
			<li><a class="icon" type="button" onclick="batchSub()" style="color:blue"><span>批量提交</span></a></li>
		</z:permission>
	</ul>
</div>
<div class="pageContent">
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160" nowrapTD="false">
		<thead>
			<tr>
				<th>序号</th>
				<th>
					<label style="float:left"><input type="checkbox" id="allPass" onclick="selectAll(this)" />全选</label> 
				</th>
				<th>业务发起方</th>
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
					<td>
						<label><input type="checkbox" name="idList" value="${id}"></label>
					</td>
					<td>
					<c:forEach items="${TradeSourcesEnums }" var="v">
					<c:if test="${v.value==tradeInitiator }">${v.desc }</c:if>
					</c:forEach>
					</td>
				    <td>${requestNo}</td>
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
					<td><s:date name="confirmDate" format="yyyy-MM-dd HH:mm:ss" /><br />${confirm}</td>
				   
				    <td>
				    	<z:permission value="boss:remitRequest:reView">
				    	<a href="remitRequest_reRemitRequestView.action?id=${id}" title="重新制单记录详情" target="dialog" rel="input" width="801" height="400" style="color:blue">查看</a>
				    	&nbsp;&nbsp;|
				    	<a href="remitRequest_remitRequestSingleEditUI.action?id=${id}" title="单笔重新制单页面" target="dialog" rel="input" width="801" height="400" style="color:blue">重新制单</a>
				    	</z:permission>	
				    </td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>

<form id="reRemitOrderForm" onsubmit="return validateCallback(this, dialogAjaxDone);" method="post">
	<input type="hidden" name="navTabId" value="reRemitRequestList" />
	<input type="hidden" name="callbackType" value="" />
	<input type="hidden" name="forwardUrl" value="" />
	
	<input type="hidden" name="remitOrderIdList" id="remitOrderIdList">
</form>

<script type="text/javascript">
	/**
		批量提交
	*/
	function batchSub(){
		var passCheckBoxes = document.getElementsByName('idList');
		var remitOrderIdList = "";
		//拼装通过的数据id
		for (var i = 0; i < passCheckBoxes.length; i++) {
			if(passCheckBoxes[i].checked){
				remitOrderIdList += passCheckBoxes[i].value + ","
			}
		}
		if(remitOrderIdList == ""){
			alertMsg.warn('您还未选择需要重新制单的数据！')
		}else{
			var url = "remitOrder_reRemitOrder.action";
			document.getElementById('remitOrderIdList').value=remitOrderIdList;
			$("#reRemitOrderForm").attr("action", url).submit();
		}
	}
	/**全选通过*/
	function selectAll(checkbox) {
		var passCheckBoxes = document.getElementsByName('idList');
		if(checkbox.checked){ //选择全选通过
			changeCheckBoxForVar(passCheckBoxes,true);
		}else{//取消全选通过
			changeCheckBoxForVar(passCheckBoxes,false);
		}
	}
</script>