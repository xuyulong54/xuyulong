<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="FeeLadder_editLadderInfo.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="feeCalculateWayList"> <input type="hidden" name="callbackType" value="closeCurrent"> <input type="hidden" name="forwardUrl" value=""><input type="hidden" name="wayId" value="${wayId }">
			<p>
				<label>阶梯名称：</label><input type="text" name="ladderName" value="${ladderInfo.ladderName }" />
			</p>
			<p>
				<label>阶梯周期类型：</label> <select id="ladderCycleType" name="ladderCycleType" onchange="changeCycleType()">
					<option label="--请选择--" />
					<c:forEach items="${LadderCycleTypeEnum }" var="LadderCycleType">
						<option value="${LadderCycleType.value.value }" <c:if test="${LadderCycleType.value.value eq ladderInfo.ladderCycleType }">selected="selected"</c:if>>${LadderCycleType.value.desc}</option>
					</c:forEach>
				</select>
			</p>
			<p class="custCycle" <c:if test="${ladderInfo.ladderCycleType !=4}">style="visibility: hidden;"</c:if>>
				<label>自定义周期类型：</label> <select name="custLadderCycleType">
					<option label="--请选择--" />
					<c:forEach items="${LadderCycleTypeEnum }" var="LadderCycleType">
						<c:if test="${LadderCycleType.value.value eq 1 || LadderCycleType.value.value eq 2}">
							<option value="${LadderCycleType.value.value }" <c:if test="${LadderCycleType.value.value eq ladderInfo.customizeCycleType }">selected="selected"</c:if>>${LadderCycleType.value.desc}</option>
						</c:if>
					</c:forEach>
				</select>
			</p>
			<p class="custCycle" <c:if test="${ladderInfo.ladderCycleType !=4}">style="visibility: hidden;"</c:if>>
				<label>自定义周期日：</label><input type="text" name="custLadderCycleDay" value="${ladderInfo.customizeDay }" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
	<script type="text/javascript">
		function changeCycleType() {
			var selectValue = $("#ladderCycleType option:selected").val();
			if (selectValue == 4) {
				$(".custCycle").removeAttr("style");
			}else{
				$(".custCycle").attr("style","visibility: hidden;");
			}
		}
	</script>
</div>