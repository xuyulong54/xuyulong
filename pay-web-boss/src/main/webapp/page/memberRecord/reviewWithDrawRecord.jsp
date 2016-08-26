<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<div class="pageContent">
	<form id="form" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="recordId" value="${id}">

			<p>
				<label>收款银行名称：</label>
				<c:forEach items="${bankList }" var="bankList">
					<c:if test="${bankList.bankCode eq bankCode }">
						<input name="bankName" value="${bankList.typeName}" readonly="readonly" />
					</c:if>
				</c:forEach>
			</p>
			<p>
				<label>收款账户开户行名称：</label>
				<s:textfield id="bankAccountAddress" name="bankAccountAddress" readonly="true" />
			</p>
			<%-- <p>
				<label>开户行国家：</label>
				<input type="text" value="${country }" readonly="readonly" />
				</td>
			</p> --%>
			<p>
				<label>开户行省市：</label>
				<input type="text" value="${province }" readonly="readonly" />
				</td>
			</p>
			<p>
				<label>开户行城市：</label>
				<input type="text" value="${city }" readonly="readonly" />
				</td>
			</p>
			<p>
				<label>收款账户银行卡号：</label>
				<s:textfield id="bankAccountNo" name="bankAccountNo" readonly="true" />
			</p>

			<p>
				<label>收款账户开户名：</label>
				<s:textfield id="bankAccountName" name="bankAccountName" readonly="true" />
			</p>
			<p>
				<label>提现状态：</label>
				<c:forEach items="${SettRecordStatusEnum }" var="enums">
					<c:if test="${settStatus eq enums.value }">
						<input name="settStatus" value="${enums.desc }" readonly="readonly" />
					</c:if>
				</c:forEach>
			</p>
			<p>
				<label>打款金额：</label>
				<s:textfield id="remitAmount" name="remitAmount" readonly="true" />
			</p>
			<p>
				<label>交易手续费：</label>
				<input type="text" value="${tradeFee }" readonly="readonly" />
				</td>
			<p>
				<label>打款发送时间：</label>
				<input value="<fmt:formatDate value='${remitRequestTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly="readonly" />
				</td>
			<p>
				<label>打款确认时间：</label>
				<input value="<fmt:formatDate value='${remitConfirmTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly="readonly" />
				</td>
			<p>
				<label>打款备注：</label>
				<input value="${remitRemark }" readonly="readonly" />
				</td>
			<p>
				<label>用户编号：</label>
				<input name="userNo" value="${userNo}" readonly="readonly" />
			</p>
			<p>
				<label>用户姓名：</label>
				<input name="userName" value="${userName}" readonly="readonly" />
			</p>
			<p>
				<label>账户编号：</label>
				<input name="accountNo" value="${accountNo}" readonly="readonly" />
			</p>
			<p>
				<label>创建时间：</label>
				<input name="createTime" value="<fmt:formatDate value='${createTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly="readonly" />
			</p>

			<%-- <p>
				<label>操作员登录名：</label>
				<input value="${operatorLoginname }" readonly="readonly" />
				</td>
			<p>
				<label>操作员真实姓名：</label>
				<input value="${operatorRealname }" readonly="readonly" />
				</td>
			<p> --%>
				<label>描述 ：</label>
				<input value="${remark }" readonly="readonly" />
				</td>
		</div>
	</form>
</div>
