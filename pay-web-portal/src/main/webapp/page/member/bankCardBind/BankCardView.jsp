<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的银行卡管理</title>
<%@include file="/page/include/headScript.jsp"%>

<script type="text/javascript" src="<%=path%>/statics/js/common.js"></script>
<script type="text/javascript">
	//弹出隐藏层
	function ChangeDiv(show_div, bankCardId) {
		showAlert(show_div, '.popupMask');//show弹出层
		centerMask('.popupMask', show_div);//居中弹出层
		$('#hint-contents').html('解绑银行卡,为确认您的身份，请输入支付密码：');
		$("#bankCardId").val(bankCardId);
		$("#trId").val(trId);
	}
	//关闭弹出层
	function CloseDownDiv(show_div) {
		hideAlert(show_div, '.popupMask');//关闭弹出层。
		$("#errorMsg").html("");
		$("#auditOpinion").html("");
	}
	//提交
	function submitRefundData() {
		var password = funGetPassword();
		var bankCardId = $("#bankCardId").val();
		if (password != null) {

			$.post("bankCardBind_deleteBankCard.action", {
				'id' : bankCardId,
				tradePwd : password
			}, function(res) {

				if (res.STATE != "FAIL") {

					$("#" + bankCardId).remove();
					CloseDownDiv('#MyDiv');
					alert("银行卡删除成功");
				} else {
					$("#errorMsg").html(res.MSG);
				}
			}, "json");
		}
	}
</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<form action="bankCardBind_addSettbankCardUI.action" method="post"
				id="form">
				<div class="tabNav">
					<div id="tab" class="tabNavLine">
						<a class="tabMenuHover" style="margin-left: 50px;">提现银行卡列表</a> <a
							class="tabMenu">快捷银行卡列表</a>
					</div>
					<div class="clear"></div>
				</div>
				<ul id="tabContent">
					<li class="block">
						<table class="table table-border">
							<thead>
								<tr>
									<th>银行名称</th>
									<th>银行卡类</th>
									<th>银行卡号</th>
									<th>持卡人姓名</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listSettlementBankAccount}" var="v">
									<tr id="${v.id}">
										<td>${v.bankName }</td>
										<td>借记卡 <%--<c:forEach items="${bankCardTypeEnumList}" var="t">
								<c:if test="${t.value==v.cardType }">${t.desc}</c:if>
							</c:forEach>
							--%></td>
										<td>${v.tempBankAccountNo }</td>
										<td>${v.bankAccountName}</td>
										<td><a class="link-color mleft10"
											onclick="ChangeDiv('#MyDiv','${v.id}')" href="#foobar"
											class="boxy">解绑</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="btnDiv">

							<input id="btn_add" type="submit" value="新 增"
								class="btn btn-primary" />

						</div>
					</li>

					<li>
						<table class="table table-border">
							<thead>
								<tr>
									<th>银行名称</th>
									<th>银行卡类型</th>
									<th>银行卡号</th>
									<th>持卡人姓名</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listFastBankAccount}" var="v">
									<tr id="${v.id}">
										<td>${v.bankName }</td>
										<td><c:forEach items="${bankCardTypeEnumList}" var="t">
												<c:if test="${t.value==v.bankAccountType }">${t.desc}</c:if>
											</c:forEach>
										</td>
										<td>${v.tempBankAccountNo}</td>
										<td>${v.bankAccountName }</td>
										<td><a class="link-color mleft10"
											onclick="ChangeDiv('#MyDiv','${v.id}')" href="#foobar"
											class="boxy">解绑</a>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</li>
				</ul>
			</form>


			<div class="popupMask"></div>
			<div id="MyDiv" class="popupBox">
				<input type="hidden" id="trId" /> <input type="hidden"
					id="bankCardId" />
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

							<font id="hint-contents" class="font"></font>
						</p>

					</div>
					<%--


		<p style=" margin-top:10px;">
		<strong> &nbsp; </strong>
		<span class="font">解绑银行卡,为了确认您的身份，请输入支付密码：</span>
		</p>
		--%>
					<p class="clearfix" style="text-align: left;">
						<strong>支付密码：</strong>
						<c:choose>
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
						<span id="pwd" class="markRed"></span>
					</p>
					<p>
						<strong> &nbsp; </strong><span id="errorMsg" class="font markRed"></span>
					</p>
					<div class="btnDiv">
						<strong> &nbsp; </strong> <input class="submitBtn" id="btn1"
							type="button" value="确 定" onclick="submitRefundData();" /> <input
							class="cancelBtn" style=" margin-left:15px;" id="btn2"
							type="button" value="取 消" onclick="CloseDownDiv('#MyDiv');" />
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
				</div>
			</div>

			<div class="ht"></div>
			<div class="clear"></div>
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>


<script type="text/javascript">
	/*页面分类*/
	$(document).ready(function() {
		setPageType('.men-account', '.men-account-card ');
	})

	var tabs = document.getElementById("tab").getElementsByTagName("a");
	var divs = document.getElementById("tabContent").getElementsByTagName("li");
	for ( var i = 0; i < tabs.length; i++) {
		tabs[i].onclick = function() {
			change(this);
		}
	}
	function change(obj) {
		for ( var i = 0; i < tabs.length; i++) {
			if (tabs[i] == obj) {
				tabs[i].className = "tabMenuHover";
				divs[i].className = "block";
			} else {
				tabs[i].className = "tabMenu";
				divs[i].className = "";
			}
		}
	}
</script>
