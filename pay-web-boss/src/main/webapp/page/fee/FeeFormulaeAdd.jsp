<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="FeeFormulae_addFeeFormulae.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent">
			<input type="hidden" name="navTabId" value="FeeFormulaeList">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="calculateWayId" value="${calculateWayId }">
			<input type="hidden" name="calculateType" id="calculateType" value="${calculateType }">
			<p>
				<label>计费方式类型:</label>
				<input name="calculateTypeName" value="${calculateTypeName }" type="text" readonly="readonly" />
			</p>
			<p>
				<label>公式类型:</label>
				<select name="formulaType" id="formulaType" onchange="selectFixOrPer();" class="required combox">
					<option value="">请选择</option>
					<c:forEach items="${FeeFormulaTypeEnum }" var="models">
						<option value="${models.value }" <c:if test="${formulaType eq models.value }">selected="selected"</c:if>>${models.desc}</option>
					</c:forEach>
				</select>

			</p>
			<p id="fixedFeePP">
				<label>固定手续费:</label>
				<input name="fixedFee" class="number" max="999999999" maxlength="25" size="25" />
			</p>
			<p id="percentFeePP">
				<label>手续费率（%）:</label>
				<input name="percentFee" class="number" max="100" maxlength="25" size="25" />
			</p>
			<p id="singleMinFeePP">
				<label>单笔最低手续费:</label>
				<input name="singleMinFee" class="number"  maxlength="25" size="25" />
			</p>
			<p id="singleMaxFeePP">
				<label>单笔最高手续费:</label>
				<input name="singleMaxFee" class="number" max="999999999" maxlength="25" size="25" />
			</p>
			<p id="minLadderAmountPP">
				<label>阶梯金额下限:</label>
				<input name="minLadderAmount" class="number" maxlength="25" size="25" />
			</p>
			<p id="maxLadderAmountPP">
				<label>阶梯金额上限（包含）:</label>
				<input name="maxLadderAmount" class="number" max="999999999" maxlength="25" size="25" />
			</p>
			<p id="minAmountPP">
				<label>单笔金额下限:</label>
				<input name="minAmount" class="number" maxlength="25" size="25" />
			</p>
			<p id="maxAmountPP">
				<label>单笔金额上限（包含）:</label>
				<input name="maxAmount" class="number" max="999999999" maxlength="25" size="25" />
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
</div>
<script type="text/javascript">
$(function($) {
	$("#fixedFeePP").hide();
	$("#percentFeePP").hide();
	$("#singleMinFeePP").hide();
	$("#singleMaxFeePP").hide();
	$("#minAmountPP").hide();
	$("#maxAmountPP").hide();
	$("#minLadderAmountPP").hide();
	$("#maxLadderAmountPP").hide();
	});
function selectFixOrPer(){
	if($("#formulaType").val()==1){
		$("#fixedFeePP").show();
		$("#percentFeePP").hide();
		$("#singleMinFeePP").hide();
		$("#singleMaxFeePP").hide();
		if($("#calculateType").val()==2){
			$("#minAmountPP").show();
			$("#maxAmountPP").show();
		}else{
			$("#minAmountPP").hide();
			$("#maxAmountPP").hide();
		}
		if($("#calculateType").val()==3 || $("#calculateType").val()==4){
			$("#minLadderAmountPP").show();
			$("#maxLadderAmountPP").show();
		}else{
			$("#minLadderAmountPP").hide();
			$("#maxLadderAmountPP").hide();
		}
	}else{
		$("#fixedFeePP").hide();
		$("#percentFeePP").show();
		$("#singleMinFeePP").show();
		$("#singleMaxFeePP").show();
		if($("#calculateType").val()==2){
			$("#minAmountPP").show();
			$("#maxAmountPP").show();
		}else{
			$("#minAmountPP").hide();
			$("#maxAmountPP").hide();
		}
		if($("#calculateType").val()==3 || $("#calculateType").val()==4){
			$("#minLadderAmountPP").show();
			$("#maxLadderAmountPP").show();
		}else{
			$("#minLadderAmountPP").hide();
			$("#maxLadderAmountPP").hide();
		}
	}
}
</script>