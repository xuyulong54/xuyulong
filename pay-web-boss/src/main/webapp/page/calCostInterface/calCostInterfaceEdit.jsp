<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" 
		action="calCostInterface_calCostInterfaceUpdate.action" 
		class="pageForm required-validate" 
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		<input type="hidden" name="navTabId" value="listCalCostInterface">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}" />
			
			<div class="unit">
				<label>计费接口编号：</label>
				<input type="text" value="${interfaceCode}"   size="30" name="interfaceCode" readonly="true"/>
			</div>
			
			<div class="unit">
				<label>计费接口名称：</label>
				<input type="text" value="${interfaceName}" required  size="30" name="interfaceName"  cssClass="required " minlength="1" maxlength="100" />
				
			</div>
			
		<%-- 	<div class="unit">
				<label>计费接口业务类型：</label>
				<input type="text" value="${interfaceType}" required  size="30" name="interfaceType"  cssClass="required " minlength="1" maxlength="100" />
			</div> --%>
			
			<div class="unit">
				<label>扣费类型：</label>
				<c:forEach items="${costInterfaceChargeTypeEnumList }" var="costInterfaceChargeTypeEnum" varStatus="index">
					<label><input type="radio" name="chargeType" value="${costInterfaceChargeTypeEnum.value}" 
					<c:if test="${chargeType eq costInterfaceChargeTypeEnum.value }">checked="checked"</c:if>
					 />${costInterfaceChargeTypeEnum.desc} </label>
				</c:forEach>
			</div>
			
			<div class="unit">
				<label>计费接口状态：</label>
				<c:forEach items="${costInterfaceStatusEnumList }" var="costInterfaceStatusEnum" varStatus="index">
					<label><input type="radio" name="status" value="${costInterfaceStatusEnum.value}" 
					<c:if test="${status eq costInterfaceStatusEnum.value }">checked="checked"</c:if>
					 />${costInterfaceStatusEnum.desc} </label>
				</c:forEach>
			</div>
			
			<div class="unit" id="costInterfacePolicy">
				<label>成本计费策略：</label>
				<c:forEach items="${costInterfacePolicyEnumList }" var="costInterfacePolicyEnum" varStatus="index">
					<label><input type="radio" name="policy" value="${costInterfacePolicyEnum.value}" 
					<c:if test="${policy eq costInterfacePolicyEnum.value }">checked="checked"</c:if>
					 />${costInterfacePolicyEnum.desc} </label>
				</c:forEach>
			</div>
			
			<div class="unit" id="billCycleSelect">
				<label>账单周期：</label>
				<select name="billCycle" id="billCycle">
					<option value="">请选择</option>
					<c:forEach items="${costBillCycleEnumList }" var="costBillCycleEnum">
						<option value="${costBillCycleEnum.value }" <c:if test="${billCycle eq costBillCycleEnum.value }">selected="selected"</c:if> >${costBillCycleEnum.desc}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit" id="customBillCycle">
				<label>自定义账单周期：</label>
				<input type="text" value="${customBillCycle}" id="customBillCycleInput"   size="30" name="customBillCycle"   class="number" />
			</div>
			
			<div class="unit" id="customBillDay">
				<label>自定义账单日：</label>
				<input type="text" id="customBillDayInput" name="customBillDay" value="<s:date name='customBillDay' format='yyyy-MM-dd' />"  dateFmt="yyyy-MM-dd"  class="date" size="30" readonly="true" />
			</div>
			<div class="unit">
				<label>备注：</label>
				<s:textarea cols="50" rows="4" name="remark" ></s:textarea>
			</div>
			
			</div>
		
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="editSave">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</ul>
			</div>
	</form>
</div>
<script type="text/javascript">
	$(function(){
		$("#customBillCycle").hide();
		$("#customBillDay").hide();
		if($("#billCycle").children('option:selected').val()=="5"){
			$("#customBillCycle").show();
			$("#customBillDay").show();
		}else{
			$("#customBillCycle").hide();
			$("#customBillDay").hide();
		}
		
		if($("input:radio[name='policy']:checked").val()=="2"){
			$("#billCycleSelect").hide();
		}else{
			$("#billCycleSelect").show();
		}
		
		$("#billCycle").change(function(){
			var val = $(this).children('option:selected').val();
			if(val=="5"){  //自定义周期
				$("#customBillCycle").show();
				$("#customBillDay").show();
			}else{
				$("#customBillCycle").hide();
				$("#customBillDay").hide();
			}
		});
		
		$("input:radio[name='policy']").change(function(){
			var val = $("input:radio[name='policy']:checked").val();
			if(val=="2"){  //包年
				$("#billCycleSelect").hide();
			}else{
				$("#billCycleSelect").show();
			}
		});
		
		$("#editSave").click(function(){
			if($("#billCycle").val()==5){ //自定义
				if($("#customBillCycleInput").val()==""||$("#customBillDayInput").val()==""||$("#customBillCycleInput").val()<0){
					alertMsg.error("自定义账单周期或自定义账单日有误");
					return;
				}
			}
			$("#form").submit();
		});
	});
</script>