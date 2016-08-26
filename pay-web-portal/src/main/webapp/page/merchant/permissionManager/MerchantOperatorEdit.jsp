<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改操作员</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">
	/*页面分类*/
	$(document).ready(function() {
		setPageType('.mer-system', '.mer-system-operator ');
		setSecondNav('.secondNav2');
	})

	$(function() {
		validatorFrom(); //表单验证
		var str = "${owenedRoleIds}";
		var array = new Array();
		array = str.split(",");
		for ( var i = 0; i < array.length; i++) {
			$("#" + array[i]).attr("checked", "checked");
		}

		hideRoles();
	});
	//表单验证
	function validatorFrom() {
		$.formValidator.initConfig({
			formID : "form",
			submitOnce : "true",
			theme : "ArrowSolidBox",
			mode : "AutoTip",
			onError : function(msg) {
				alert(msg);
			},
			inIframe : true
		});
		$("#mobileNo").formValidator({
			onShow : "",
			onFocus : "必填项，请输入11位的手机号码"
		}).inputValidator({
			min : 11,
			max : 11,
			onError : "手机号码必须是11位的"
		}).regexValidator({
			regExp : "mobile",
			dataType : "enum",
			onError : "手机号码格式不正确"
		});
	}

	function submitForm() {
		  var str = "";
		  document.getElementById("errorMsg").innerHTML=str;
            var textWarning= document.getElementById("textWarning");
            if (textWarning!=null){
              textWarning .innerHTML=str
            }             		  
		    $(":checkbox:checked").each(function() {
		      str += $(this).attr("id");
		      str += ",";
		    });
		    if ("${type}" == "0") {
		      if (str == '') {
		        $(".wronginfos").text("请至少选择一项角色");
		        $(".wronginfos").show();
		        return;
		      }
		    } else {
		      $('.wronginfos').hide();
		    }
		var password = funGetPassword();
		if (password == null) {
			alert("支付密码为空或者长度不足");
			return;
		}
    $("#tradePwd").val(password);
		$("#selectVal").val(str);
		$("#form").submit();
	}
	//如果是操作员，将移除设置角色
	function hideRoles() {
		if ("${type}" == "1") {
			$("#roles").remove();
		}
	}
</script>

<body>
<div id="FakeCryptoAgent"></div>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<br />

	<div class="pageContent">
		<jsp:include page="/page/merchant/permissionManager/PmsMenu.jsp"></jsp:include>

		<div class="operator-box">

			<div class="tab_box frm-comon">
				<form id="form" action="permission_editMerchantOperator.action"
					method="post">
					<input type="hidden" name="selectVal" id="selectVal" /> <input
						type="hidden" name="id" value="${id}" /> <input type="hidden"
						name="loginName" value="${loginName}" />
					<p class="clearfix">
						<label> &nbsp; </label> <span class="text-warning"display:none;"></span>
					</p>
					<p class="clearfix">
						<label>姓名：</label> <span>${realName }</span>
					</p>
					<p class="clearfix">
						<label>登录名：</label> <span>${loginName}</span>
					</p>
					<p class="clearfix">
						<label>手机号：</label> <input type="text" name="mobileNo"
							id="mobileNo" value="${mobileNo}" maxlength="11" />
					</p>
					<p class="clearfix">
						<label>类型：</label>
						<c:forEach items="${operatorTypeList}" var="operatorType">
							<c:if test="${type==operatorType.value}">${operatorType.desc}</c:if>
						</c:forEach>
					</p>
					<p class="clearfix">
						<label>状态：</label>
						<c:forEach items="${operatorStatuslist}" var="operatorStatus">
							<c:if test="${status==operatorStatus.value}">${operatorStatus.desc}</c:if>
						</c:forEach>
					</p>

					<div class="leftbox" id="roles">选择角色 ：</div>
					<div class="rightcont">
						<s:iterator value="rolesList" status="st">
							<c:if test="${id!=0}">

								<input type="checkbox" name="selectRole" class="check"
									id="${id }">&nbsp;&nbsp;${roleName}
									</c:if>
						</s:iterator>
					</div>

					<p class="clearfix">
						<jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>
					</p>
					<c:if test="${!empty msg }">
						<p class="clearfix" id="textWarning" style="color:red;">
							<label> &nbsp; </label><span> ${msg}</span>
						</p>
					</c:if>
					<p class="clearfix">
						<label> &nbsp; </label> <input type="button" value="提 交"
							onclick="submitForm();" class="btn btn-primary" />
					</p>
				</form>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>

</html>