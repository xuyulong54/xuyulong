<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<%-- <script type="text/javascript" src="<%=path%>/statics/js/writeObject.js"></script> --%>
<script type="text/javascript">
		/*页面分类*/
		 $(document).ready(function() {
		  setPageType('.mer-system', '.mer-system-operator ');
		  setSecondNav('.secondNav1');
		  $("#btn0").hide();
		  })

			var ts = "<%=System.currentTimeMillis()%>";
	//弹出隐藏层
	function ChangeDiv(show_div, id, roleName) {
		showAlert(show_div, '.popupMask');//show弹出层
		centerMask('.popupMask', show_div);//居中弹出层
		$("#id").val(id);
		$("#hint-content").html("您确定要删除" + roleName + "角色么？");
		$("#errorMsg").html("");

	}
	//重置密码,激活冻结操作员
	function deleteRole() {
		var password = funGetPassword();
		if (password == null) {
			alert("支付密码不能为空");
			return;
		}
		var id = $("#id").val();
		$.post("permission_deleteMerchantRole.action", {
			id : id,
			tradePwd : password
		}, function(res) {
			if (res.STATE != "FAIL") {
				$("#hint-content").html(res.MSG);
				$("#p-tradePwd").remove();
				$("#p-errorMsg").remove();
		    $("#btn1").remove();
		    $("#btn0").show();
				//$("#btn1").click(refreshPage());
				$("#btn2").remove();
			} else {
				$("#errorMsg").html(res.MSG);
			}
		}, "json");
	}
	//刷新页面
	function refreshPage() {
		window.location.href = "permission_listMerchantRole.action";
		//window.location.reload();
	}
	//关闭弹出层

	function CloseDownDiv(show_div) {
		hideAlert(show_div, '.popupMask');//关闭弹出层。
		document.getElementById("powerpass").clear();
	}
</script>

<body>
<div id="FakeCryptoAgent"></div>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<jsp:include page="/page/merchant/permissionManager/PmsMenu.jsp"></jsp:include>
			<form action="permission_listMerchantRole.action" method="post">
				<div class="query-conditions query-reset">
					<div class="frm-horizontal">

						<z:permission value="Pms:Role:View">


							<div class="frm-group">
								<label>角色名：</label> <input type="text" name="roleName"
									title="模糊查询" value="${roleName}" /> <input type="submit"
									class="btn btn-primary" value="查 询" />
								<z:permission value="Pms:Role:Operation">

									<a class="link-color mleft10"
										href="permission_addMerchantRoleUI.action">添加角色</a>
								</z:permission>
							</div>

						</z:permission>
						<div class="markRed">${msg}</div>
					</div>
				</div>
			</form>
			<table class="table table-border mtop10">
				<thead>
					<tr>
						<th>角色名</th>
						<th>描述</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="recordList" status="st">
						<tr id="${id}">
							<td>${roleName }</td>
							<td>${remark}</td>
							<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td><z:permission value="Pms:Role:Operation">
									<c:if test="${id!=0}">
										<a class="link-color mleft10"
											href="permission_editMerchantRoleUI.action?id=${id}">修改</a>
									</c:if>
								</z:permission>&nbsp; <z:permission value="Pms:Role:Operation">
									<c:if test="${id!=0}">
										<a class="link-color mleft10" href="javascript:;"
											onclick="ChangeDiv('#MyDiv','${id}','${roleName}')">删除</a>
									</c:if>
								</z:permission></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<span class="message">${idMsg}${objMsg}</span>
			<c:if test="${pageBean.totalCount>0}">
				<div class="pageCla">
					<z:page pageBean="${pageBean }"
						url="permission_listMerchantRole.action" currentPage="${pageNum }"
						parameter="&roleName=${roleName}"></z:page>
				</div>
			</c:if>


			<div class="clear"></div>

			<div class="popupMask"></div>
			<div id="MyDiv" class="popupBox">
				<input type="hidden" id="id">
				<div class="top">
					<a href="#" class="btnClost" onclick="CloseDownDiv('#MyDiv');"></a>
				</div>
				<div class="cont">
				<div class="left-icon">
        <i class="iconfont">&#xe600;</i>
        </div>
        <div class="right-box">
         <h3 id="hint-title">温馨提示：</h3>
         <p id="p-hint">

        <font id="hint-content" class="font"></font>
       </p>
       </div>
					<p id="p-tradePwd">
						<strong>支付密码：</strong> <c:choose>
							<c:when test="${USE_KEYBOARD }">
								<input type="hidden" name="tradePwd" id="password"
									class="password" maxlength="20" />
								<script type="text/javascript">
									writePassObject("powerpass", {
										"width" : 173,
										"height" : 35,
										" margin-top" : 10,
										"accepts" : "[:graph:]+",
										"x" : -50,
										"maxlength" : 20
									});
								</script>
							</c:when>
							<c:otherwise>
								<input type="password" name="tradePwd" id="password"
									class="password" maxlength="20" />
							</c:otherwise>
						</c:choose>
					</p>
					<p id="p-errorMsg">
						<strong> &nbsp; </strong><span id="errorMsg" class="font markRed"></span>
					</p>
					<div class="btnDiv">
						<strong> &nbsp; </strong> 
						<input class="submitBtn" id="btn0"
              type="button" value="确 定" onclick="refreshPage();" />
						<input class="submitBtn" id="btn1"
							type="button" value="确 定" onclick="deleteRole();" /> <input
							class="cancelBtn" style=" margin-left:15px;" id="btn2"
							type="button" value="取 消" onclick="CloseDownDiv('#MyDiv');" />
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
				</div>
			</div>

			<div class="clear"></div>
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>

</html>
