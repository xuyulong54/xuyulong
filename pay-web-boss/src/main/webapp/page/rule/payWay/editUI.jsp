<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
function pushValue(id,payProductCode){
	var  frpId=$("#box"+id).val();
	var payWayId = $("#payWayId"+id).val();
	if($("#box"+id).attr("checked")=='checked'){
		selectFrpCode(frpId,"add",payProductCode);
	}else{
		deleteFrpCode(payWayId,"delete");
	}
}
//绑定
function selectFrpCode(frpCode,sign,payProductCode){
	$.ajax({
		url: "payWay_payWaySave.action",
		type: "POST",
		data:{"frpCode" : frpCode,"sign" : sign, "payProductCode" : payProductCode},
		dataType: "json",
		success: function(data){
			if(data[0].msg=="success"){
				var payWayId=data[0].payWayId;
				$("#payWayId"+frpCode).attr("value",payWayId);
					dialogAjax();
			}else{	
				alert("操作失败");
			}
		},error : function(data){
			alert("操作失败");
		}
	});
}
//删除
function deleteFrpCode(payWayId,sign){
	$.ajax({
		url: "payWay_payWaySave.action",
		type: "POST",
		data:{"payWayId" : payWayId,"sign" : sign},
		dataType: "json",
		success: function(data){
			if(data[0].msg=="success"){
				dialogAjax();
			}else{	
				alert("操作失败");
			}
		},
		error : function(data){
			alert("操作失败");
		}
	});
}
</script>
<div class="pageContent">
	<form id="form" method="post" action="payWay_payWaySave.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<c:forEach items="${frps }" var="item">
				<div class="unit unitReset">
					<input type="hidden" id="payWayId${item.frpCode }"
						value="${item.payWayId }" /> <input type="checkbox"
						value="${item.frpCode }" name="frpCodes"
						id="box${item.frpCode }"
						<c:if test="${item.payWayId!=null && item.payWayId!=''}"> checked="checked" </c:if>
						onclick="pushValue('${item.frpCode }','${payProductCode}');">${item.bankName}  (${item.frpCode }) [${item.sorts }] &nbsp; &nbsp;&nbsp;
					<z:permission value="payrule:payway:edit">
						<span id="update${item.frpCode }"></span>
						<c:if test="${item.payWayId!=null && item.payWayId!=''}">
							<a class="edit"
								href="payWay_editView.action?payWayCode=${item.frpCode }&payProductCode=${payProductCode}"
								target="dialog" title="支付产品-${payProductName }设置支付方式"
								rel="payWay-edit" width="400" height="400"
								id="deleteId${item.payWayId  }" style="color:blue" close="dialogAjax"><span>设置</span>
							</a>
						</c:if>
					</z:permission>

					<br />
				</div>
			</c:forEach>
		</div>
	</form>
</div>
 <script type="text/javascript">
			function dialogAjax() {
				var url = "${pageContext.request.contextPath }/payWay_editUI.action?payProductCode=${payProductCode}&payProductName=${payProductName}";
				$.pdialog.reload(url, {
					data : {},
					dialogId : "payWayList",
					callback : null
				});
				return true;
			}
		</script>

