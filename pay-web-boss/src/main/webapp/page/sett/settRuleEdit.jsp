<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
$(function() {
	var cycles = $("#settCycleData").val();
	var arr= cycles.split(",");
	for(var i=0;i<arr.length;i++){
		var settCycleType = $("#settCycleType").val()
		if(settCycleType == 2){
		$("#settCycleData"+arr[i]).attr("checked",true);
		}else{
		$("#settCycleDataM"+arr[i]).attr("checked",true);
		}
	}
	var isView = "${isView}"; // 查看权限
	if(isView != null && isView != ""){
		$(":checkbox").attr("disabled", "true");
		$("input[type='text']").attr("disabled", "true");
		$("input[type='checkbox']").attr("disabled", "true");
	}else{
		$(":checkbox").removeAttr("disabled");
		$("input[type='text']").removeAttr("disabled");
		$("input[type='checkbox']").removeAttr("disabled");
	}
});

//改变结算周期
function changeCycleType(obj){
	if(obj != null){
		if(obj.value == "1"){
			$("#autoType3").removeAttr("style");
			$("#autoType2").attr("style", "display: none;");
		}else{
			$("#autoType3").attr("style", "display: none;");
			$("#autoType2").removeAttr("style");
		}
	}
}

//改变结算方式
function changeType1(obj){
	if(obj != null){
		if(obj.value == "1"){
			$("#autoType1").removeAttr("style");
			$("#autoType").removeAttr("style");
			
		}else{
			$("#autoType1").attr("style", "display: none;");
			$("#autoType").attr("style", "display: none;");
		}
	}
}

</script>

<div class="unit">
	<label>结算方式：</label>
	<select name="settType" id="status" onchange="changeType1(this)" <c:if test="${not empty isView }">disabled="disabled"</c:if>>
		<option value="1" <c:if test="${settRule.settType eq 1 }">selected="selected"</c:if>>自动结算</option>
		<option value="2" <c:if test="${settRule.settType eq 2 }">selected="selected"</c:if>>手工结算</option>
	</select>
</div>
<div id="autoType1" <c:if test="${settRule.settType eq 2 }">style="display: none;" </c:if>>
<div class="unit">
	<div id="autoType" <c:if test="${settRule.settType eq 2}">style="display: none;" </c:if>>
		<label>结算周期：</label>
		<select name="settCycleType"  id="settCycleType" <c:if test="${not empty isView }">disabled="disabled"</c:if>  onchange="changeCycleType(this)">
			<option value="2" <c:if test="${settRule.settCycleType eq 2 }">selected="selected"</c:if>>周结</option>
			<option value="1" <c:if test="${settRule.settCycleType eq 1 }">selected="selected"</c:if>>月结</option>
		</select>
	</div>
</div>

<div  class="unit" id="autoType2" <c:if test="${settRule.settCycleType eq 1}">style="display: none;" </c:if>>
	<label>
		结算周期(周)
	</label>
	<input type="hidden" id="settCycleData" value="${settRule.settCycleData}" />
	<input type="checkbox" value="1" id="settCycleData1" name="settCycleData" />周日&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="checkbox" value="2" id="settCycleData2" name="settCycleData" />周一&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="checkbox" value="3" id="settCycleData3" name="settCycleData" />周二&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="checkbox" value="4" id="settCycleData4" name="settCycleData" />周三&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="checkbox" value="5" id="settCycleData5" name="settCycleData" />周四&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="checkbox" value="6" id="settCycleData6" name="settCycleData" />周五&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="checkbox" value="7" id="settCycleData7" name="settCycleData" />周六&nbsp;&nbsp;&nbsp;&nbsp;
</div>

<div  class="unit" id="autoType3" <c:if test="${settRule.settCycleType eq 2 or empty settRule}">style="display: none;"</c:if>>
	<label>
		结算周期(月)
	</label>
	<c:forEach begin="1" end="31" step="1" var="settCycle">
	<input type="checkbox" value="${settCycle}" id="settCycleDataM${settCycle}" name="settCycleDataM" />${settCycle}日&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${settCycle eq 16 }">
				<br/>
		</c:if>
	</c:forEach>
</div>
</div>
<div class="unit">
	<label>风险预存期：</label>
	<input type="text" name="riskDay" id="riskTimelimitDay" class="required number" maxlength="3" size="30" value="${settRule.riskDay}" />
	天
</div>
<div class="unit">
	<label>描述：</label>
	<textarea rows="6" cols="28" name="remark" minlength="1" maxlength="800" class="required"
	<c:if test="${not empty isView }">disabled="disabled"</c:if>>${settRule.remark}</textarea>
</div>
