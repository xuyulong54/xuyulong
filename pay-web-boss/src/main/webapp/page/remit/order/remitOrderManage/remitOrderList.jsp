<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name="remitOrderForm"
		onsubmit="return navTabSearch(this);"
		action="remitOrder_remitOrderList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>收款户名：</label> <input type="text" name="accountName"
						value="${accountName }" size="25" alt="模糊搜索" /></td>
					<td><label>收款账号：</label> <input type="text" name="accountNo"
						value="${accountNo }" size="25" alt="模糊搜索" /></td>
					<td><label>开户行号：</label> <input type="text"
						name="bankChannelNo" value="${bankChannelNo }" size="25"
						alt="模糊搜索" /></td>
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
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<z:permission value="boss:remitOrder:add">
				<li><a href="remitOrder_remitOrderAdd.action" class="add"
					target="dialog" rel="input" width="600" height="360" title="添加制单信息"><span>添加</span>
				</a>
				</li>
				<li><a href="remitOrder_importRemitOrderUI.action" class="add"
					target="dialog" width="600" height="242" title="批量导入制单信息"><span>导入</span>
				</a>
				</li>
			</z:permission>
			<li><a href="statics/mould/remitOrderModel.xls"
				style="color:blue" class="icon"><span>下载模板</span> </a>
			</li>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>打款请求号</th>
				<th>收款账户</th>
				<th>开户行</th>
				<th>打款金额</th>
				<th>类型</th>
				<th>状态</th>
				<th>创建人</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
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
					<%-- <td>
						<z:permission value="boss:remitOrder:edit">
							<a  href="javascript:editRemitOrder('${id }')" title="修改制单信息"  style="color:blue">修改</a>
							&nbsp;|&nbsp;
						</z:permission>
						<z:permission value="boss:remitOrder:delete">
							<a  href="javascript:deleteRemitOrder('${id}')" title="删除制单信息" style="color:blue">删除</a>
						</z:permission>
					</td> --%>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
	function editRemitOrder(id) {
		$.post("remitOrder_judgeRemitOrderStatus.action", {
			"id" : id
		}, function(res) {
			if (res.STATE == "FAIL") {
				alertMsg.error(res.MSG);
			} else {
				$.pdialog.open("remitOrder_remitOrderEdit.action?id=" + id,
						"remitOrderInfo", "修改制单信息", {
							max : false,
							mask : false,
							resizable : true,
							drawable : true,
							maxable : true,
							minable : true,
							fresh : true,
							width : 600,
							height : 385,
							close : function closeLimit() {
								return true;
							}
						});
			}
		}, "json");
	}

	function deleteRemitOrder(id) {
		alertMsg.confirm("确定要删除该制单信息?", {
			okCall : function() {
				$.post("remitOrder_deleteRemitOrder.action", {
					"id" : id
				}, function(res) {
					if (res.STATE == "SUCCESS") {
						alertMsg.correct(res.MSG);
						$("form[name='remitOrderForm']").submit();
					} else if (res.STATE == "FAIL") {
						alertMsg.error(res.MSG);
					}
				}, "json");
			}
		});
	}
</script>