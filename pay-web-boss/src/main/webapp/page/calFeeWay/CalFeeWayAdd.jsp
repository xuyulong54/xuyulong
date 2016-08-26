<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="calFeeWay_addCalFeeWay.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="calFeeWay">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		<input type="hidden" name="id" value="${id}" />
		<div class="pageFormContent" layoutH="60" >
			 <div class="unit">
				<label>计费维度：</label>
				<select name="dimensionId" class="required "  style="width:131px" required>
					<option value="">请选择</option>
					<c:forEach items="${calDimensionList }" var="v">
						<option value="${v.id }" >${v.calProduct}(${v.calCostInterfaceCode})</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>计费方式名称：</label>
				<input type="text" value="${wayName}" required  size="30" name="wayName"  class="required " minlength="1" maxlength="25" />
			</div>
			
			<div class="unit">
				<label>免计费金额(包含)：</label>
				<input type="text" value="${feeFreeAmount}" required  size="30" name="feeFreeAmount"  class="required number"  min="0" />
			</div>
			
			<div class="unit">
				<label>状态：</label>
				<c:forEach items="${publicStatusEnumList }" var="publicStatusEnum" varStatus="index">
					<input type="radio" name="status" value="${publicStatusEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${publicStatusEnum.desc} 
				</c:forEach>
			</div>
			
			<div class="unit">
				<label>生效日期：</label>
				<input type="text" name="beginDate" value="${fn:substring(beginDate,0,10)}" dateFmt="yyyy-MM-dd"  id="beginDate" class="required date" size="30" required  readonly="true"/>
			</div>
			
			<div class="unit">
				<label>失效日期：</label>
				<input type="text" name="endDate" value="${fn:substring(endDate,0,10)}" dateFmt="yyyy-MM-dd"  id="endDate" class="required date" size="30" required  readonly="true"/>
			</div>
			
			<div class="unit">
				<label>计费角色：</label>
				<c:forEach items="${calRoleEnumList }" var="calRoleEnum" varStatus="index">
					<label><input type="radio" name="calRole" value="${calRoleEnum.value}" 
					<c:if test="${index.index eq 2 }">checked="checked"</c:if> 
					 />${calRoleEnum.desc} </label>
				</c:forEach>
			</div>
			
			<div class="unit">
				<label>收费方式：</label>
				<c:forEach items="${calPeriodeEnumList }" var="calPeriodeEnum" varStatus="index">
					<label><input type="radio" name="calPeriod" value="${calPeriodeEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${calPeriodeEnum.desc} </label>
				</c:forEach>
			</div>
			
			<div class="unit">
				<label>近似值：</label>
				<select name="isRound" id="isRound" class="required">
					<option value="">请选择</option>
					<c:forEach items="${calApproximationList }" var="app" varStatus="index">
					 	<option value="${app.value }">${app.desc }</option>
					</c:forEach>
				</select>
				
				<%-- 
				<c:forEach items="${isOrNotList }" var="isOrNot" varStatus="index">
					<label><input type="radio" name="isRound" value="${isOrNot.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if>  />${isOrNot.desc} </label>
				</c:forEach>
				--%>
			</div>
			
			<div class="unit">
				<label>费率类别(在线系统不需要选择)：</label>
				<select name="mcc">
				<option value="">请选择</option>
					<c:forEach items="${mccTypeList}" var="v">
						<option value="${v.number}">${ v.fullName}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>计费类型：</label>
				<select name="calType" id="calTypeSelect" required>
					<option value="">请选择</option>
					<c:forEach items="${calTypeEnumList }" var="calTypeEnum">
						<option value="${calTypeEnum.value }" >${calTypeEnum.desc}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit" id="cycleTypeSelect">
				<label>计费周期：</label>
				<select name="cycleType" id="cycleType" style="width:131px">
					<option value="">请选择</option>
					<c:forEach items="${billingCycleEnumList }" var="billingCycleEnum">
						<option value="${billingCycleEnum.value }" >${billingCycleEnum.desc}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit" id="cusCycleType">
				<label>自定义计费周期：</label>
				<input type="text" value="${cusCycleType}"  id="cusCycleTypeInput"  size="30" name="cusCycleType"   minlength="1" maxlength="1000"  class="number"/>
			</div>
			
			<div class="unit" id="customizeDay">
				<label>自定义计费日：</label>
				<input type="text" id="customizeDayInput" name="customizeDay" value="${fn:substring(customizeDay,0,10)}" id="customizeDay" dateFmt="yyyy-MM-dd"  class="date" size="30" readonly="true" />
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button id="addSave" type="button" >添加</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
		$("#cusCycleType").hide();
		$("#customizeDay").hide();
		
		$("#calTypeSelect").change(function(){
			var val = $(this).children('option:selected').val();
			if(val=="3"||val=="4"){  //阶梯类型
				$("#cycleTypeSelect").show();
			}else{
				$("#cycleTypeSelect").hide();
			}
			
		});
		
		$("#cycleType").change(function(){
			var val = $(this).children('option:selected').val();
			if(val=="5"){  //自定义周期
				$("#cusCycleType").show();
				$("#customizeDay").show();
			}else{
				$("#cusCycleType").hide();
				$("#customizeDay").hide();
			}
		});
		
		$("#addSave").click(function(){
			var beginDate = new Date($("#beginDate").val().replace("-", "/").replace("-", "/"));
            var endDate = new Date($("#endDate").val().replace("-", "/").replace("-", "/"));
            if(endDate<beginDate){
            	alertMsg.error("失效日期不可以小于有效日期");
            	return;
            }
            if($("#cycleType").val()==5){ //自定义
				if($("#cusCycleTypeInput").val()==""||$("#customizeDayInput").val()==""||$("#cusCycleTypeInput").val()<0){
					alertMsg.error("自定义账单周期或自定义账单日有误");
					return;
				}
			}
            $("#form").submit();
		});
	});
</script>