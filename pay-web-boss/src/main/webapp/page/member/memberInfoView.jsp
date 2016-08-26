<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<div class="pageContent">
	<form id="form" method="post" class="pageForm">
		<div class="pageFormContent" layoutH="58">

			<p>
				<label>用户名：</label>
				<s:textfield name="loginName" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>真实姓名：</label>
				<s:textfield name="realName" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>注册时间：</label>
				<fmt:formatDate var="fmtCreateTime" value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
				<input type="text" value="${fmtCreateTime }" readonly="true" size="30" />
			</p>
			<p>
				<label>绑定手机：</label>
				<s:textfield name="bindMobileNo" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>绑定邮箱：</label>
				<s:textfield name="bindEmail" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>身份证号码：</label>
				<s:textfield name="cardNo" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>国籍：</label>
				<input  type="text" name="country" readonly="readonly" value="${country }" size="30"/>
			</p>
			<p>
				<label>职业：</label>
				<input  type="text" name="profession" readonly="readonly" value="${profession }" size="30"/>
			</p>
			<p>
				<label>是否实名认证：</label> <input type="text" name="realName"
					id="realName" readonly="readonly" value="<c:if test="${isRealNameAuth == UserCheckRealNameStatusEnum.SECCUSS.value }">是</c:if><c:if test="${isRealNameAuth == UserCheckRealNameStatusEnum.FAILED.value }">否</c:if>
					<c:if test="${isRealNameAuth == UserCheckRealNameStatusEnum.WAIT.value }">认证中</c:if>"
					size="30" />
			</p>
		</div>

	</form>
</div>