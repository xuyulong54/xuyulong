<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" 
		action="calFeeWay_editCalFeeWay.action" 
		class="pageForm required-validate" 
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		<input type="hidden" name="navTabId" value="calFeeWay">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}" />
			 <div class="unit">
				<label>计费维度：</label>
				<select name="dimensionId"   style="width:131px" required>
					<option value="">请选择</option>
					<c:forEach items="${calDimensionList }" var="v">
						<option value="${v.id }" <c:if test="${dimensionId eq v.id }">selected="selected"</c:if> >${v.calProduct}(${v.calInterface })</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>计费方式名称：</label>
				<input type="text" value="${wayName}"  size="30" name="wayName"  cssClass="required " minlength="1" maxlength="25" />
			</div>
			
			<div class="unit">
				<label>免计费金额(包含)：</label>
				<input type="text" value="${feeFreeAmount}"  size="30" name="feeFreeAmount"  class="required number" min="0"  />
			</div>
			
			<div class="unit">
				<label>状态：</label>
				<c:forEach items="${publicStatusEnumList }" var="publicStatusEnum" varStatus="index">
					<input type="radio" name="status" value="${publicStatusEnum.value}" 
					<c:if test="${status eq publicStatusEnum.value }">checked="checked"</c:if> 
					 />${publicStatusEnum.desc} 
				</c:forEach>
			</div>
			
			<div class="unit">
				<label>生效日期：</label>
				<input type="text" name="beginDate" value="<s:date name='beginDate' format='yyyy-MM-dd' />"   id="beginDate" class="date" size="30"  cssClass="required "/>
			</div>
			
			<div class="unit">
				<label>失效日期：</label>
				<input type="text" name="endDate" value="<s:date name='endDate' format='yyyy-MM-dd' />"  id="endDate" class="date" size="30"  cssClass="required "/>
			</div>
			
			<div class="unit">
				<label>计费角色：</label>
				<select name="calRole"  required>
					<option value="">请选择</option>
					<c:forEach items="${calRoleEnumList }" var="calRoleEnum">
						<option value="${calRoleEnum.value }" <c:if test="${calRole eq calRoleEnum.value }">selected="selected"</c:if> >${calRoleEnum.desc}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>收费方式：</label>
				<select name="calPeriod"  required>
					<option value="">请选择</option>
					<c:forEach items="${calPeriodeEnumList }" var="calPeriodeEnum">
						<option value="${calPeriodeEnum.value }" <c:if test="${calPeriod eq calPeriodeEnum.value }">selected="selected"</c:if>  >${calPeriodeEnum.desc}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>近似值：</label>
				<select name="isRound" id="isRound" class="required">
					<option value="">请选择</option>
					
					<c:forEach items="${calApproximationList }" var="app" varStatus="index">
					 	<option value="${app.value }" <c:if test="${isRound eq app.value }">selected="selected"</c:if>>${app.desc }</option>
					</c:forEach>
					
					<%--
					<option value="103" <c:if test="${isRound eq 103 }">selected="selected"</c:if> >无</option>
					<option value="102" <c:if test="${isRound eq 102 }">selected="selected"</c:if> >舍尾法</option>
					<option value="101" <c:if test="${isRound eq 101 }">selected="selected"</c:if> >进一法</option>
					<option value="100" <c:if test="${isRound eq 100 }">selected="selected"</c:if> >四舍五入法</option>
					<c:forEach items="${isOrNotList }" var="isOrNot">
						<option value="${isOrNot.value }" <c:if test="${isRound eq isOrNot.value }">selected="selected"</c:if>  >${isOrNot.desc}</option>
					</c:forEach>
					 --%>
				</select>
			</div>
			
			<div class="unit">
				<label>费率类别：</label>
				<select name="mcc" class="required ">
				<option value="">请选择</option>
					<c:forEach items="${mccTypeList}" var="v">
						<option value="${v.number}" <c:if test="${v.number eq mcc }">selected="selected"</c:if> >${ v.fullName}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>计费类型：</label>
				<select name="calType" id="calTypeSelect"  required>
					<option value="">请选择</option>
					<c:forEach items="${calTypeEnumList }" var="calTypeEnum">
						<option value="${calTypeEnum.value }" <c:if test="${calType eq calTypeEnum.value }">selected="selected"</c:if> >${calTypeEnum.desc}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit" id="cycleTypeSelect">
				<label>计费周期：</label>
				<select name="cycleType" id="cycleType" style="width:131px" >
					<option value="">请选择</option>
					<c:forEach items="${billingCycleEnumList }" var="billingCycleEnum">
						<option value="${billingCycleEnum.value }" <c:if test="${cycleType eq billingCycleEnum.value }">selected="selected"</c:if> >${billingCycleEnum.desc}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit" id="cusCycleType">
				<label>自定义计费周期：</label>
				<input type="text" value="${cusCycleType}"    size="30" name="cusCycleType"   minlength="1" maxlength="1000" />
			</div>
			
			<div class="unit" id="customizeDay">
				<label>自定义计费日：</label>
				<input type="text" name="customizeDay" value="<s:date name='customizeDay' format='yyyy-MM-dd' />" id="customizeDay"  class="date" size="30" />
			</div>
			
			
			
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
	$(function(){
		$("#cusCycleType").hide();
		$("#customizeDay").hide();
		$("#cycleTypeSelect").hide();
		
		if($("#calTypeSelect").children('option:selected').val()=="3"||$("#calTypeSelect").children('option:selected').val()=="4"){
			$("#cycleTypeSelect").show();
		}else{
			$("#cycleTypeSelect").hide();
		}
		
		$("#calTypeSelect").change(function(){
			try{
				var val = $(this).children('option:selected').val();
				if(val=="3"||val=="4"){  //阶梯类型
					$("#cycleTypeSelect").show();
				}else{
					$("#cycleTypeSelect").hide();
				}
			}catch(e){
				alert(e);
			}
			
		});
		
		$("#cycleType").change(function(){
			var val = $(this).children('option:selected').val();
			if(val=="5"){ //自定义周期
				$("#cusCycleType").show();
				$("#customizeDay").show();
			}else{
				$("#cusCycleType").hide();
				$("#customizeDay").hide();
			}
		});
		$("#calType").change(function(){
			var val = $(this).children('option:selected').val();
			if(val=="3"){  //阶梯类型
				$("#cycleTypeSelect").show();
			}else{
				$("#cycleTypeSelect").hide();
			}
		});
	});
</script>