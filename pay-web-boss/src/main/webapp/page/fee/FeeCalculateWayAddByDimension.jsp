<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="js/commonDate.js"></script>
<div class="pageContent">
	<form name="addCalByDimensionform" method="post" action="FeeCalculateWay_addCalculateWaySelectDimension.action" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent">
			<input type="hidden" name="navTabId" value="feeCalculateWayListByNodeId"> <input type="hidden" name="callbackType" value="closeCurrent"> <input
				type="hidden" name="forwardUrl" value="">
			<p>
				<label>计费维度:</label> <input type="hidden" id="dimensionId" name="dimensionId" /> <input type="text" id="payProductName" name="dimension.payProductName"
					readonly="readonly" required="required" /> <a class="btnLook" id="findDimensionBtn" href="FeeDimension_dimensionLookupList.action?feeNodeId=${feeNodeId}"
					lookupGroup="dimension" style="display: block;">搜索</a>
			</p>
			<p>
				<label>计费类型:</label> <select id="calculateType" name="calculateType" class="required combox" onchange="ladder();">
					<option value="">请选择</option>
					<c:forEach items="${FeeCalculateTypeEnum }" var="models">
						<option value="${models.value }" <c:if test="${calculateType eq models.value }">selected="selected"</c:if>>${models.desc}</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>收费方式:</label> <select name="chargeType" class="required combox">
					<option value="">请选择</option>
					<c:forEach items="${FeeChargeTypeEnum }" var="model">
						<option value="${model.value }" <c:if test="${chargeType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
					</c:forEach>
				</select>
			</p>

			<p>
				<label>是否退还手续费:</label> <select id="isRefundFee" name="isRefundFee" class="required combox" onchange="changeFeeRole();">
					<option value="">请选择</option>
					<option value="1" <c:if test="${isRefundFee eq 1 }">selected="selected"</c:if>>是</option>
					<option value="0" <c:if test="${isRefundFee eq 0 }">selected="selected"</c:if>>否</option>
				</select>
			</p>
			<p>
				<label>计费角色:</label> <select id="feeRole" name="feeRole">
				</select>
			</p>
			<p>
				<label>免计费金额(包含):</label> <input name="feeFreeAmount" required="required" class="number" max="999999999" minlength="1" maxlength="25" size="25" />
			</p>
			<p>
				<label>生效日期:</label> <input type="text" id="effectiveDateStart" name="effectiveDateStart" maxdate="{%y+50}-%M-{%d}" mindate="%y-%M-%d" required="required"
					value="${fn:substring(effectiveDateStart,0,10)}" class="date" size="25" readonly="true" />
			</p>
			<p>
				<label>失效日期:</label> <input type="text" id="effectiveDateEnd" name="effectiveDateEnd" maxdate="{%y+50}-%M-{%d}" mindate="%y-%M-{%d+1}" required="required"
					value="${fn:substring(effectiveDateEnd,0,10)}" class="date" size="25" readonly="true" />
			</p>
			<p id="ladderCycleTypePP" type="hidden">
				<label>阶梯周期类型:</label> <select id="ladderCycleType" name="ladderCycleType" onchange="custom();">
					<option value="">请选择</option>
					<c:forEach items="${LadderCycleTypeEnum }" var="model">
						<option value="${model.value }" <c:if test="${ladderCycleType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
					</c:forEach>
				</select>
			</p>
			<p id="customizeCycleType" type="hidden">
				<label>自定义阶梯周期类型:</label> <select name="customizeCycleType">
					<option value="">请选择</option>
					<c:forEach items="${CustomCycleTypeEnum }" var="model">
						<option value="${model.value }" <c:if test="${customizeCycleType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
					</c:forEach>
				</select>
				<%-- <input name="customizeCycleType" value="${customizeCycleType}" minlength="1" maxlength="25" size="25" /> --%>
			</p>
			<p id="customizeDay" type="hidden">
				<label>自定义计费日:</label> <input name="customizeDay" value="${customizeDay}" minlength="1" maxlength="25" size="25" />
			</p>
			<p id="isRound" type="hidden">
				<label>近似值:</label>
				<select name="isRound">
					<option value="">请选择</option>
					<c:forEach items="${isRoundEnums }" var="model">
						<option value="${model.value }" <c:if test="${isRound eq model.value }">selected="selected"</c:if>>${model.desc}</option>
					</c:forEach>
				</select>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="checkDateAndForm('addCalByDimensionform','effectiveDateStart','effectiveDateEnd');">保存</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>

	</form>
</div>
<script type="text/javascript">
	$.extend({
		bringBackSuggest : function(args) {
			$("input[id='dimensionId']").val(args["id"]);
			$("input[id='payProductName']").val(args["payProductName"]);
		},
	});
	function ladder() {
		if (($("#calculateType").val() == 3)
				|| ($("#calculateType").val() == 4)) {
			$("#ladderCycleTypePP").show();
			$("select[name='ladderCycleType']").addClass("required");
			custom();
		} else {
			$("#ladderCycleTypePP").hide();
			$("select[name='ladderCycleType']").removeClass("required");
			$("#customizeCycleType").hide();
			$("#customizeDay").hide();
			$("select[name='customizeCycleType']").removeClass("required");
			$("input[name='customizeDay']").removeClass("required");
		}
	}
	function custom() {
		if ($("#ladderCycleType").val() == 5) {
			$("#customizeCycleType").show();
			$("#customizeDay").show();
			$("select[name='customizeCycleType']").addClass("required");
			$("input[name='customizeDay']").addClass("required");
		} else {
			$("#customizeCycleType").hide();
			$("#customizeDay").hide();
			$("select[name='customizeCycleType']").removeClass("required");
			$("input[name='customizeDay']").removeClass("required");
		}
	}

	function changeFeeRole() {
		if ($("#isRefundFee").val() == 1) {
			$("#feeRole").addClass("disabled");
			$("#feeRole option[value='1']").remove(); //删除值为1的Option
			$("#feeRole option[value='2']").remove(); //删除值为2的Option
			$("#feeRole").append(
					"<option value='3' selected='selected'>平台</option>"); //添加一项option 
		} else if ($("#isRefundFee").val() == 0) {
			$("#feeRole").removeClass("disabled");
			$("#feeRole option[value='3']").remove(); //删除值为3的Option
			$("#feeRole").append("<option value='1' >付款方</option>"); //添加一项option
			$("#feeRole").append("<option value='2' >收款方</option>"); //添加一项option 
		}
	}
</script>