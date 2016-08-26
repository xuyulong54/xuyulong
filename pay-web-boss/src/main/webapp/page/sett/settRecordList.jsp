<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" class = "exp" onsubmit="return navTabSearch(this);" action="SettRecord_listSettRecord.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						结算日期：
						<input name="startSettDate" value="${startSettDate }" type="text" class="date" readonly="readonly" size="7" /> - 
						<input name="endSettDate" value="${endSettDate }" type="text" class="date" readonly="readonly" size="7" />
					</td>
					<td>
						商户编号：
						<input name="userNo" value="${userNo }" type="text" alt="精确查询" size="13" />
					</td>
					<td>
						账户编号：
						<input name="accountNo" value="${accountNo }" type="text" alt="精确查询" size="15" />
					</td>
					<td>
						发起方式： 
						<select name="settMode">
							<option value="">请选择</option>
							<c:forEach items="${SettModeTypeEnum }" var="enums">
								<option value="${enums.value }" <c:if test="${settMode == enums.value }"> selected="selected" </c:if>>${enums.desc }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						结算状态： 
						<select name="settStatus">
							<option value="">请选择</option>
							<c:forEach items="${SettRecordStatusEnum }" var="enums">
								<option value="${enums.value }" <c:if test="${settStatus == enums.value }"> selected="selected" </c:if>>${enums.desc }</option>
							</c:forEach>
						</select>
					</td>
					
					<td>
						<div class="subBar">
							<ul>
								<li>
									<div class="buttonActive">
										<z:permission value="sett:record:view">
											<div class="buttonContent">
												<button type="submit" id="sub">查询</button>
											</div>
										</z:permission>
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
				<span> 结算总笔数：<b style="color:red;">${countResultMap.totalCount }</b> 笔</span>
				<span> 结算总金额：<b style="color:red;"><fmt:formatNumber value="${countResultMap.totalSettAmount }" type="currency" /></b></span>
				<span> 结算总手续费：<b style="color:red;"><fmt:formatNumber value="${countResultMap.totalSettFee }" type="currency" /></b></span>
				<span> 结算打款总金额：<b style="color:red;"><fmt:formatNumber value="${countResultMap.totalRemitAmount }" type="currency" /></b></span>
			</li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130" nowrapTD="false">
		<thead>
			<tr>
				<th>序号</th>
				<th width="80">创建时间</th>
				<th>商户</th>
				<th>结算日期</th>
				<th>结算金额</th>
				<th>结算手续费</th>
				<th>实际打款金额</th>
				<th>开户信息</th>
				<th>发起方式</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>
						<fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						${userNo } <br/>
						${accountNo }<br/>
						${userName }
					</td>
					<td>
						<fmt:formatDate value="${settDate }" pattern="yyyy-MM-dd" />
					</td>
					<td align="right">
						<fmt:formatNumber value="${settAmount}" type="currency" />
					</td>
					<td align="right">
						<fmt:formatNumber value="${settFee}" type="currency" />
					</td>
					<td align="right">
						<fmt:formatNumber value="${remitAmount}" type="currency" />
					</td>
					<td>
						${bankAccountName }<br/>
						${bankAccountNo }<br/>
						${bankAccountAddress }
					</td>
					<td>
						<c:forEach items="${SettModeTypeEnum }" var="enums">
							<c:if test="${settMode eq enums.value }">${enums.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${SettRecordStatusEnum }" var="enums">
							<c:if test="${enums.value eq settStatus}">${enums.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<a href="SettRecord_detailSettRecord.action?batchNo=${batchNo }&accountNo=${accountNo }" target="dialog" title="详情" style="color:blue">详情</a> 
						<c:if test="${settStatus eq remitFail}">
							<z:permission value="sett:record:audit">
								<a href="javascript:resent('${batchNo }','${accountNo }');" title="重新结算" rel="listSettRecord" style="color:blue">重新结算</a>
								<a href="javascript:confirmFail('${batchNo }','${accountNo }');" title="确认结算失败,结算记录作废" rel="listSettRecord" style="color:blue">确认结算失败</a>
							</z:permission>
						</c:if> 
						<c:if test="${settStatus eq waitConfirm}">
							<z:permission value="sett:record:audit">
								<a href="SettRecord_auditSettUI.action?batchNo=${batchNo }&accountNo=${accountNo }" title="审核" rel="listSettRecord" target="dialog" style="color:blue">审核</a>
							</z:permission>
						</c:if>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
	function resent(batchNo, accountNo) {
		alertMsg.confirm("是否重新发起结算？", {
			okCall : function() {
				$.post("SettRecord_resendSett.action", {
					batchNo : batchNo,
					accountNo : accountNo
				}, function() {
					DWZ.ajaxDone;
					$("form[id='pagerForm'][class = 'exp']").submit();
				}, "json");
			}
		});
	}

	function confirmFail(batchNo, accountNo) {
		alertMsg.confirm("确认结算失败,结算记录作废？", {
			okCall : function() {
				$.post("SettRecord_confirmFail.action", {
					batchNo : batchNo,
					accountNo : accountNo
				}, function() {
					DWZ.ajaxDone;
					$("form[id='pagerForm'][class = 'exp']").submit();
				}, "json");
			}
		});
	}
</script>
