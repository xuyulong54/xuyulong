<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ZeroClipboard.min.js"></script>

<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="notifyRecord_listNotifyRecord.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<input type="hidden" value="${flag}" name="flag" /> 
						通知状态：
						<select name="status" id="status">
							<option value="all">全部</option>
							<c:forEach items="${notifyStatusEnumList }" var="v">
								<option value="${v.value }" <c:if test="${status eq v.value }">selected="selected"</c:if>>${v.desc}</option>
							</c:forEach>
						</select> 
						通知类型： 
						<select name="notifyType" id="notifyType">
							<option value="">全部</option>
							<c:forEach items="${notifyTypeEnumList }" var="v">
								<c:if test="${v.value ne flag }">
									<option value="${v.value }" <c:if test="${notifyType eq v.value }">selected="selected"</c:if>>${v.desc}</option>
								</c:if>
							</c:forEach>
						</select>
						所属编号：<input type="text" name="merchantNo" value="${merchantNo}" id="merchantNo" /> 
						请求号：<input type="text" name="merchantOrderNo" value="${merchantOrderNo}" id="merchantOrderNo" />
					</td>
					<td>
						<div class="subBar">
							<ul>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
							</ul>
							<ul>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="button" onclick="submitSelectRow();">批量补发</button>
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
	<form id="tableRowForm" action="notifyRecord_messageSenderMQ.action" onsubmit="return validateCallback(this, dialogAjaxDone);" method="post">
		<input type="hidden" name="navTabId" value="list"> 
		<input type="hidden" name="callbackType" value=""> 
		<input type="hidden" name="forwardUrl" value=""> 
		<input type="hidden" name="entityJson" id="entityJson" />
	</form>
</div>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="103">
		<thead>
			<tr>
				<th><input type="checkbox" name="selectItems" id="selectItems" onclick="selectAll()">序号</th>
				<th>创建时间</th>
				<th>最后通知时间</th>
				<th>所属编号</th>
				<th>请求号</th>
				<th>通知次数</th>
				<th>最大通知次数</th>
				<th>通知状态</th>
				<th>通知类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="tbodyContent">
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td><input type="checkbox" name="items" value="${url}" onchange="selectItems(this,null)"> ${st.index+1}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><s:date name="lastNotifyTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${merchantNo }</td>
					<td>${merchantOrderNo }</td>
					<td>${notifyTimes }</td>
					<td>${limitNotifyTimes }</td>
					<td>
						<c:forEach items="${notifyStatusEnumList }" var="v">
							<c:if test="${status eq v.value }">${v.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${notifyTypeEnumList }" var="v">
							<c:if test="${notifyType eq v.value }">${v.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<a href="notifyRecord_sendNotify.action?id=${id}" target="ajaxTodo" title="确定要补发交易通知吗？" style="color:blue">通知</a> &nbsp;
						
						<a href="notifyRecord_listNotifyRecordLog.action?notifyId=${id }"
							title="查看通知记录" target="dialog" rel="input" width="1000" style="color:blue">查看</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
	//全选 反选
	function selectAll() {
		$("input[name='items']").each(function() {
			if (this.checked) {
				this.checked = false;
				$(this).attr("select", false);
			} else {
				this.checked = true;
				$(this).attr("select", true);
			}
		});
	}
	//选择数据行
	function selectItems(objItem, tr) {
		if (objItem != null) {
			if (objItem.checked) {
				objItem.checked = true;
				$(objItem).attr("select", true);
			} else {
				objItem.checked = false;
				$(objItem).attr("select", false);
			}
		}
		if (tr != null) {
			var td = $(tr).find("td").eq(0);
			if ($(td.find("input[type='checkbox']")).attr("select") == "true") {
				$(td.find("input[type='checkbox']")).attr("checked", null);
				$(td.find("input[type='checkbox']")).attr("select", false);
			} else {
				$(td.find("input[type='checkbox']")).attr("checked", "checked");
				$(td.find("input[type='checkbox']")).attr("select", true);
			}
		}
	}

	//批量提交选择中的行数据
	function submitSelectRow() {
		var selectRow = $("input[name='items']:checked");
		if (selectRow.length == 0) {
			alertMsg.info("请选择数据,再执行该操作");
		} else {
			for ( var i = 0; i < selectRow.length; i++) {
				var url = selectRow[i].value.split("?")[0];
				var data = selectRow[i].value.split("?")[1];
				$.ajax({
					type : "GET",
					url : url,
					data : data,
					dataType : "jsonp",
					jsonp : "jsoncallback",
					success : function(msg) {
					},
					complete : function(msg) {
					}
				});
			}
			alertMsg.correct("共补发了" + selectRow.length + "条通知");
		}

	}

	//加载数据
	function searchDatas() {
		$("#pagerForm").submit();
	}
</script>
