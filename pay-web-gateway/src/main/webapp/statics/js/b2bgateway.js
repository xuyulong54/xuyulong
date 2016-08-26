/**
 * 获取当前用户的证书列表 依赖 SignCertManager.js
 * 
 * @param snArray
 */
function getCurrentUserCA(snArray) {
	if ("" == snArray) {// 非数字证书用户
		$("#payDivId2").remove();
		return;
	}
	snArray = snArray.split(",");
	// 是否终止循环
	var isBreak = false;
	var currCAisExit = false;
	OnLoad();
	for ( var i = 0; i < snArray.length; i++) {
		if (SelectSignCert(snArray[i] + "")) {
			isBreak = true;
			currCAisExit = true;
			$.post("b2bgateway_setCASession.action", {
				SN : snArray[i]
			}, function(res) {
			}, 'json');
		}
		if (isBreak) {
			$("#payDivId2").remove();
			break;
		}
		if ((i + 1) == snArray.length && currCAisExit == false) {
			// 当前证书不存在，且用户是数字证书用户，则不能显示确认支付按钮
			$("#paySpanId").remove();
			$("#payDivId2")
					.html(
							'<span class="title"> 尊敬的用户：</span><div>尊敬的用户：你是数字证书用户，检测到本机并未安装数字证书! 如果你要继续支付操作，请在本机赋权安装数字证书!</div>');
		}
	}
}

function setValidateMsg(value) {
	if ((value.charAt(value.length - 1) == "!") == true) {
		document.getElementById("message").innerHTML = value;
	} else {
		document.getElementById('change').innerHTML = value;
	}
}

$(document).ready(function() {
	var merchantId = $("#merchantId").attr("value");
	var orderNo = $("#orderNo").attr("value");
	var frpCode = $("#frpcode").attr("value");
	var amount = $("#amount").attr("value");

	$("#gwPaySubmit").click(function() {
		if (!validate()) {
			return false;
		}
		var status = 0;
		var pwd_s = $("#keyboard").val();
		var ts = new Date().getTime() + "";
		var tradePwd = '';
		if (pwd_s == '1') {
			tradePwd = getPassInput("powerpass", ts, "errorMsg", "密码输入错误!");
			$("#password").val(tradePwd);
		} else {
			tradePwd = $("#password").val();
		}
		if (tradePwd == null || tradePwd == '') {
			return;
		}
		var username = document.getElementById('username').value;
		if (username == null) {
			return;
		}
		// $("#tradePwd").val(tradePwd);
		// 增加一个产品编号
		var payProductCode = $(frpCode).attr("value");
		var url = "b2bbalancePay_validateLogin.action";
		$("#payForm").submit();
		/*
		 * request(url, setValidateMsg, { 'username' : username, 'tradePwd' :
		 * tradePwd, 'orderNo' : orderNo, 'merchantId' : merchantId, 'frpCode' :
		 * frpCode, 'payProductCode' : payProductCode, 'amount' : amount },
		 * "POST", false);
		 */
	});

	// 判断是否有改变值
	var old_username = "";
	$("#username").bind("blur", function() {
		var username = $("#username").val();
		if (username != null && username != old_username) {
			old_username = username;
			$.post("b2bbalancePay_queryObligateInfo.action", {
				"username" : username
			}, function(res) {
				if (res.STATE == "SUCCESS") {
					$("#ObligateInfoDiv").show();
					$("#ObligateInfoDiv").find("div").html("预留信息:" + res.MSG);
				} else if (res.STATE == "FAIL") {
					$("#ObligateInfoDiv").show();
					$("#ObligateInfoDiv").find("div").html(res.MSG);
				} else {
					$("#ObligateInfoDiv").hide();
				}
			}, "json");
		}
	});

});

function submitorder() {
	$("#username").attr("disabled", "disabled");
	$("#infoForm").submit();
	/*
	 * $.ajax({ url : "b2bbalancePay_balancePay.action", type : "POST", error :
	 * function(data) { alert("抱歉，发送请求失败，请重试"); }, success : function(value) {
	 * location.href=value; } });
	 */
}
