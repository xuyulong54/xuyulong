<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="frp_lookupFrpList.action">
	<!-- 分页表单参数 -->
	<%@include file="/page/inc/pageForm.jsp"%>
</form>
<input type="hidden" name="navTabId" value="list">
<input type="hidden" name="callbackType" value="closeCurrent">
<input type="hidden" name="forwardUrl" value="">
<div class="pageHeader">
	<form onsubmit="return dwzSearch(this, 'dialog');" action="frp_lookupFrpList.action" method="post">
		<input type="hidden" name="bankCode" value="${bankCode }" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>支付渠道编号：<input type="text" name="frpCode" value="${frpCode}" size="30" alt="精确查询" /></td>
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
							<label style="float:left"><input type="checkbox"
								class="checkboxCtrl" group="orgId" />全选</label>
							<ul>
								<li><div class="button">
										<div class="buttonContent">
											<button type="button" class="checkboxCtrl" group="orgId"
												selectType="invert">反选</button>
										</div>
									</div>
								</li>
								<li><div class="button">
										<div class="buttonContent">
											<button type="button" multLookup="orgId" warn="请选择银行">确认</button>
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
	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" layoutH="106">
		<thead>
			<tr>
				<th><input type="checkbox" class="checkboxCtrl" group="orgId" />
				</th>
				<th>序号</th>
				<th>业务类型</th>
				<th>银行名称</th>
				<th>支付渠道编号</th>
				<th>银行编号</th>
				<!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}"
					ondblclick="$.bringBack({frpCode:'${frpCode}', bankName:'${bankName}'});" >
					<td>
						<input type="checkbox" name="orgId" value="{bankName:'${bankName }', frpCode:'${frpCode }'}" />
					</td>
					<td>${st.index+1}</td>
					<td>${busType }</td>
					<td>${bankName }</td>
					<td>${frpCode }</td>
					<td>${bankCode }</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<div class="panelBar">
		<div class="pages">
			<span>查到&nbsp;${totalCount}&nbsp;条记录，每页</span> <select class="combox"
				name="numPerPage" value="${numPerPage}"
				onchange="dwzPageBreak({targetType:dialog, numPerPage:this.value})">
				<option value="15"
					<c:if test="${numPerPage eq 15 }">selected='selected'</c:if>>15</option>
			</select> <span>条，共&nbsp;${pageCount}&nbsp;页</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
