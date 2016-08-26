<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="calCostInterface_calCostInterfaceSave.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="listCalCostInterface">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		<div class="pageFormContent" layoutH="60" >
			<div class="unit">
				<label>计费接口类型：</label>
				<label><input type="radio" name="costInterfaceType" value="bank" checked="checked"/>银行渠道</label>
				<label><input type="radio" name="costInterfaceType" value="other"/>其他渠道</label>
			</div>
			
			<div class="unit">
				<label>计费接口编号：</label>
				<select name="interfaceCode" id="interfaceCodeSelect" >
					<option value="">请选择</option>
					<c:forEach items="${channelList }" var="channel">
						<option value="${channel.bankChannelCode }" >${channel.bankChannelCode}</option>
					</c:forEach>
				</select>
				<input type="text" id="interfaceCodeInput" value="${interfaceCode}"   size="30" name="interfaceCode" />
			</div>
			
			<div class="unit">
				<label>计费接口名称：</label>
				<input type="text" value="${interfaceName}" required  size="30" name="interfaceName"  class="required " minlength="1" maxlength="100" />
			</div>
			
			<%-- <div class="unit">
				<label>计费接口业务类型：</label>
				<input type="text" value="${interfaceType}" required  size="30" name="interfaceType"  class="required " minlength="1" maxlength="100" />
			</div> --%>
			
			<div class="unit">
				<label>扣费类型：</label>
				<c:forEach items="${costInterfaceChargeTypeEnumList }" var="costInterfaceChargeTypeEnum" varStatus="index">
				    <label><input type="radio" name="chargeType" value="${costInterfaceChargeTypeEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${costInterfaceChargeTypeEnum.desc}</label>
				</c:forEach>
			</div>
			
			<div class="unit">
				<label>计费接口状态：</label>
				<c:forEach items="${costInterfaceStatusEnumList }" var="costInterfaceStatusEnum" varStatus="index">
					<label><input type="radio" name="status" value="${costInterfaceStatusEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${costInterfaceStatusEnum.desc} </label>
				</c:forEach>
			</div>
			
			<div class="unit" id="costInterfacePolicy">
				<label>成本计费策略：</label>
				<c:forEach items="${costInterfacePolicyEnumList }" var="costInterfacePolicyEnum" varStatus="index">
					<label><input type="radio" name="policy" value="${costInterfacePolicyEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${costInterfacePolicyEnum.desc}  </label>
				</c:forEach>
			</div>
			
			<div class="unit" id="billCycleSelect">
				<label>账单周期：</label>
				<select name="billCycle" id="billCycle">
					<option value="">请选择</option>
					<c:forEach items="${costBillCycleEnumList }" var="costBillCycleEnum">
						<option value="${costBillCycleEnum.value }" >${costBillCycleEnum.desc}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit" id="customBillCycle">
				<label>自定义账单周期：</label>
				<input type="text" value="${customBillCycle}" id="customBillCycleInput"  size="30" name="customBillCycle"    class="number" />
			</div>
			
			<div class="unit" id="customBillDay">
				<label>自定义账单日：</label>
				<input type="text" id="customBillDayInput" name="customBillDay" value="${fn:substring(customizeDay,0,10)}"  dateFmt="yyyy-MM-dd"  class="date" size="30" readonly="true" />
			</div>
			
			<div class="unit">
				<label>备注：</label>
              	<s:textarea cols="50" rows="4" name="remark" ></s:textarea>
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addSave" >添加</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
		$("#interfaceCodeInput").hide();
		
		$("#customBillCycle").hide();
		$("#customBillDay").hide();
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
		
		$("input[name='costInterfaceType']").click(function(){
			$("#interfaceCodeSelect").empty();
			if($(this).val()=="bank"){
				$("#interfaceCodeSelect").show();
				$("#interfaceCodeInput").hide();
				$.post("calCostInterface_getChannelList.action",{costInterfaceType:'bank'},function(result){
					$("#interfaceCodeSelect").append("<option value=''>请选择</option>");
					for(var i=0;i<result.channelList.length;i++){
						$("#interfaceCodeSelect").append("<option value='"+result.channelList[i].bankChannelCode+"'>"+result.channelList[i].bankChannelCode+"</option>");
					}
				},"json");
				
			}else{
				//$("#interfaceCode").append("<option value=''>请选择</option>");
				$("#interfaceCodeSelect").hide();
				$("#interfaceCodeInput").show();
			}
		});
		
		$("#addSave").click(function(){
			if($("#billCycle").val()==5){ //自定义
				if($("#customBillCycleInput").val()==""||$("#customBillDayInput").val()==""||$("#customBillCycleInput").val()<0){
					alertMsg.error("自定义账单周期或自定义账单日有误");
					return;
				}
			}
			if($("input:radio[name='costInterfaceType']:checked").val()=="bank"){
				if($("#interfaceCodeSelect").val()==""){
					alertMsg.error("计费接口编码不允许为空");
					return;
				}
			}else{
				if($("#interfaceCodeInput").val()==""){
					alertMsg.error("计费接口编码不允许为空");
					return;
				}
			}
			
			$("#form").submit();
		});
	});
</script>
