<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="cardBin_addCardBin.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="list">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		<input type="hidden" name="id" value="${id}" />
		<div class="pageFormContent" layoutH="60" >
			 <p>
				<label>卡bin：</label>
				<input type="text" value="${cardBin}" required  size="30" name="cardBin"  cssClass="required " minlength="1" maxlength="10" />
			</p>
			
			<p>
				<label>发卡行代码：</label>
				<input type="text" value="${bankCode}" required  size="30" name="bankCode"  cssClass="required " minlength="1" maxlength="25" />
			</p>
			<p>
				<label>发卡行名称：</label>
				<input type="text" value="${bankName}" required  size="30" name="bankName"  cssClass="required " minlength="1" maxlength="50" />
			</p>
			<p>
				<label>卡名：</label>
				<input type="text" value="${cardName}" required  size="30" name="cardName"  cssClass="required " minlength="1" maxlength="50" />
			</p>
			<p>
				<label>卡种：</label>
				<select name="cardKind" style="width:131px" required>
						<option value="">请选择</option>
						<c:forEach items="${cardKindEnums }" var="v">
							<option value="${v.value }" >${v.desc}</option>
						</c:forEach>
					</select>
			</p>
			<p>
				<label>卡片长度：</label>
				<input type="text" value="${cardLength}" required   size="30" name="cardLength"  cssClass="required " minlength="1" maxlength="2" />
			</p>
			<p>
				<label>状态：</label>
				<select name="status" style="width:131px" id="status" required>
					<option value="${cardBinStatusEnums.YES.value }" <c:if test="${status eq cardBinStatusEnums.YES.value}">selected="selected"</c:if>>${cardBinStatusEnums.YES.desc }</option>
					<option value="${cardBinStatusEnums.NO.value }" <c:if test="${status eq cardBinStatusEnums.NO.value}">selected="selected"</c:if>>${cardBinStatusEnums.NO.desc }</option>
				</select>
			</p>
			
			</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >添加</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

