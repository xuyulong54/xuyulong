<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" 
		action="calFeeRateFormula_editCalFeeRateFormula.action" 
		class="pageForm required-validate" 
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		<input type="hidden" name="navTabId" value="calFeeRateFormula">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}" />
			 <p>
				<label>计费约束：</label>
				<input type="text" value="${feeWayId}"  size="30" name="feeWayId"  cssClass="required " minlength="1" maxlength="10" />
			</p>
			
			<p>
				<label>计费模式：</label>
				<input type="text" value="${model}"  size="30" name="model"  cssClass="required " minlength="1" maxlength="25" />
			</p>
			<p>
				<label>固定手续费：</label>
				<input type="text" value="${fixedFee}"  size="30" name="fixedFee"  cssClass="required " minlength="1" maxlength="50" />
			</p>
			<p>
				<label>手续费率：</label>
				<input type="text" value="${percentFee}"  size="30" name="percentFee"  cssClass="required " minlength="1" maxlength="50" />
			</p>
			<p>
				<label>单笔区间下限：</label>
				<input type="text" value="${minAmount}"  size="30" name="minAmount"  cssClass="required " minlength="1" maxlength="1000" />(含)
			</p>
			<p>
				<label>单笔区间上限：</label>
				<input type="text" value="${maxAmount}"  size="30" name="maxAmount"  cssClass="required " minlength="1" maxlength="1000" />
			</p>
			<p>
				<label>单笔阶梯下限：</label>
				<input type="text" value="${minLadderAmount}"  size="30" name="minLadderAmount"  cssClass="required " minlength="1" maxlength="1000" />(含)
			</p>
			<p>
				<label>单笔阶梯上限：</label>
				<input type="text" value="${maxLadderAmount}"  size="30" name="maxLadderAmount"  cssClass="required " minlength="1" maxlength="1000" />
			</p>
			<p>
				<label>单笔最低手续费：</label>
				<input type="text" value="${singleMinFee}"  size="30" name="singleMinFee"  cssClass="required " minlength="1" maxlength="1000" />
			</p>
			<p>
				<label>单笔最高手续费：</label>
				<input type="text" value="${singleMaxFee}"  size="30" name="singleMaxFee"  cssClass="required " minlength="1" maxlength="1000" />
			</p>
			
			</div>
		
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</ul>
			</div>
	</form>
</div>
<script type="text/javascript">
ty($("#terminalType"));
function ty(terminalTypeId){
		if(terminalTypeId.val()==2){
			$("#phone1").css("display", "none");
			$("#phone2").css("display", "none");
			$("#phone3").css("display", "none");
			$("#sim").css("display", "");
		}else if(terminalTypeId.val()==3){
			$("#sim").css("display", "none");
			$("#phone1").css("display", "");
			$("#phone2").css("display", "");
			$("#phone3").css("display", "");
		}else{
			$("#phone1").css("display", "none");
			$("#phone2").css("display", "none");
			$("#phone3").css("display", "none");
			$("#sim").css("display", "none");
		}
	}
</script>