<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="pagerForm" onsubmit="return iframeCallback(this);" enctype="multipart/form-data" action="contract_editContract.action" method="post" class="pageForm required-validate">
		<div class="pageFormContent" layoutH="58">
  			<input type="hidden" name="contracId" value="${contracId} "/>
			<p>
				<label>商户名称：</label>
				<input type="text" name="merchantName" readonly="true" size="35" value="${merchantName }"/>
			</p>
			<p>
				<label>文件编号：</label>
				<input type="text" name="contractNo" value="${contractNo }" size="30" readonly="true"/>
			</p>
			<p>
				<label>文件类型：</label>
				<c:forEach items="${contractTypeList }" var="v">
					<c:if test="${contractType eq v.value }">${v.desc}</c:if>
				</c:forEach>
			</p>
			<p>
				<label>文件扫描件：</label>
				<input id="cardFront1" type="file" name="file"/>
				<div id="message"></div>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
