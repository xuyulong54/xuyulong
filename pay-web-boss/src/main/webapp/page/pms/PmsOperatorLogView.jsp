<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="60">
		<div class="unit">
			<label>创建时间：</label>
			<fmt:formatDate value="${operatorLog.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
		</div>
		<div  class="unit">
			<label>操作员姓名：</label>
			<label>${operator.realName }</label>
		</div>
		<div class="unit">
			<label>操作员名称：</label>
			<label>${operatorLog.operatorName }</label>
		</div>
		<div class="unit">
			<label>操作类型：</label>
			<c:forEach items="${OperatorLogTypeEnumList}" var="logTypeEnum">
				<c:if test="${operatorLog.operateType eq logTypeEnum.value}">${logTypeEnum.desc}</c:if>
			</c:forEach>
		</div>
		<div class="unit">
			<label>操作状态：</label>
			<c:forEach items="${OperatorLogStatusEnumList}" var="logStatusEnum">
				<c:if test="${operatorLog.status eq logStatusEnum.value}">${logStatusEnum.desc}</c:if>
			</c:forEach>
		</div>
		<div class="unit">
			<label>IP地址：</label>
			<label>${operatorLog.ip }</label>
		</div>

		<div class="unit">
			<label>操作内容：</label>
			<textarea rows="8" cols="88" readonly="true">${operatorLog.content }</textarea>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>