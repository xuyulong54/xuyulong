<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="calFeeRateFormula_addCalFeeRateFormula.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="calFeeRateFormula">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		<input type="hidden" name="id" value="${id}" />
		<input type="hidden" name="feeWayId" value="${feeWayId}" />
		<input type="hidden" name="calType" value="${calType}" />
		<div class="pageFormContent" layoutH="60" >
			<div class="unit">
				<label style="width: 40%;text-align: right;">计费模式：</label>
				<select name="model" class="required" onchange="changeModel(this.value);">
						<option value="">请选择</option>
						<c:forEach items="${calModelList }" var="calModel">
							<option value="${calModel.value }">${calModel.desc }</option>
						</c:forEach>
				</select>
			</div>
			<div class="unit">
				<div id="fixFeeDiv" hidden="hidden">
					<label style="width: 40%;text-align: right;">固定手续费(单位：元)：</label>
					<input type="text" value="${fixedFee}" size="30" name="fixedFee"  class="number " minlength="1" maxlength="10" />
				</div>
			</div>
			<div class="unit">
				<div id="ratioFeeDiv" hidden="hidden">
					<label style="width: 40%;text-align: right;">手续费率(%)：</label>
					<input type="text" value="${percentFee}" size="30" name="percentFee"  class="number " minlength="1" maxlength="4" min="0" max="100" />
				</div>
			</div>
			<!-- 当选择单笔类型时将区间、阶梯全部隐藏 -->
			<div id="singleOrd" <c:if test="${calType == 1 }">hidden="hidden"</c:if>>
				<!-- 当选择的计费类型为阶梯时，隐藏单笔区间设置 -->
				<div id="sidingOrd" <c:if test="${calType != 2 }">hidden="hidden"</c:if>>
					<div class="unit" >
						<label style="width: 40%;text-align: right;">区间下限(单位：元)：</label>
						<input type="text" value="${minAmount}" size="30" name="minAmount"  class="number" minlength="1" maxlength="12" min="0" max="10000000000" />(含)
					</div>
					<div class="unit">
						<label style="width: 40%;text-align: right;">区间上限(单位：元)：</label>
						<input type="text" value="${maxAmount}" size="30" name="maxAmount"  class="number" minlength="1" maxlength="12" min="0" max="10000000000" />
					</div>
				</div>
				<!-- 当选择的计费类型为区间时，隐藏单笔阶梯设置 -->
				<div id="stairOrd" <c:if test="${calType != 3 && calType != 4 }">hidden="hidden"</c:if>>
					<div class="unit">
						<label style="width: 40%;text-align: right;">阶梯下限(单位：元)：</label>
						<input type="text" value="${minLadderAmount}" size="30" name="minLadderAmount"  class="number" minlength="1" maxlength="12" />(含)
					</div>
					<div class="unit">
						<label style="width: 40%;text-align: right;">阶梯上限(单位：元)：</label>
						<input type="text" value="${maxLadderAmount}" size="30" name="maxLadderAmount"  class="number" minlength="1" maxlength="12" />
					</div>
				</div>
			</div>
			
			<div class="unit">
				<div id="singleLowDiv" hidden="hidden">
					<label style="width: 40%;text-align: right;">最低手续费(单位：元)：</label>
					<input type="text" value="${singleMinFee}" size="30" name="singleMinFee"  class="number" minlength="1" maxlength="12" />
				</div>
			</div>
			<div class="unit">
				<div id="singleTalDiv" hidden="hidden">
					<label style="width: 40%;text-align: right;">最高手续费(单位：元)：</label>
					<input type="text" value="${singleMaxFee}" size="30" name="singleMaxFee"  class="number" minlength="1" maxlength="12" />
				</div>
			</div>
			</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >添加</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
	function changeModel(model) {
		if(model == ""){
			return;
		}else{
			if(model == 1){//选择比例收费
				$("#fixFeeDiv").hide("slow");//固定金额隐藏
				$("#ratioFeeDiv").show("slow");//比例金额显示
				$("#singleLowDiv").show("slow");//单笔最低显示
				$("#singleTalDiv").show("slow");//单笔最高显示
			}else if(model == 2){//选择固定金额
				$("#ratioFeeDiv").hide("slow");//比例金额隐藏
				$("#singleLowDiv").hide("slow");//单笔最低隐藏
				$("#singleTalDiv").hide("slow");//单笔最高隐藏
				$("#fixFeeDiv").show("slow");//固定金额显示
			}
		}
	}
	
	$(function(){
		$("input[name='minAmount']").blur(function(){
			$("input[name='singleMinFee']").val($(this).val()*$("input[name='percentFee']").val());
		});
		
		$("input[name='maxAmount']").blur(function(){
			$("input[name='singleMaxFee']").val($(this).val()*$("input[name='percentFee']").val());
		});
	});
	
</script>
