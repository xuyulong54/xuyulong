
/**
 * 获取当前用户的证书列表 依赖 SignCertManager.js
 * 
 * @param snArray
 */
function getCurrentUserCA(snArray) {
	OnLoad(); 
	if ("" == snArray) {
		return;
	}
	snArray = snArray.split(",");
	// 是否终止循环
	var isBreak = false;
	var currCAisExit = false;
	for ( var i = 0; i < snArray.length; i++) {
		if (SelectSignCert(snArray[i]+"")) {
			isBreak = true;currCAisExit = true;
			$.post("gateway_setCASession.action", {
				SN : snArray[i]
			}, function(res) {
			}, 'json');
		}
		if (isBreak) {
			$("#payDivId2").remove();
			break;
		}
		if((i+1)==snArray.length && currCAisExit ==false){
			//当前证书不存在，且用户是数字证书用户，则不能显示确认支付按钮
			$("#paySpanId").remove();
			$("#payDivId2").html('<span class="title"> 尊敬的用户：</span><div>尊敬的用户：你是数字证书用户，检测到本机并未安装数字证书! 如果你要继续支付操作，请在本机赋权安装数字证书!</div>');
		}
	}
}

//Cookie操作
function getCookie(name) {
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)){
		var cookieValue = unescape(arr[2]);
		return cookieValue.replace(/(^")|("$)/g,"")
	}else{
		return null;
	}
}
function setCookie(name,value)
{
    var Days = 365;
    var exp  = new Date();    //new Date("December 31, 9998");
        exp.setTime(exp.getTime() + Days*24*60*60);
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//选择最后一次使用的银行.
function selectLastBank(){
	var bank = getCookie('lastSelectedBank');
	
	if(bank!=null){
		$("#"+bank).attr("checked","checked");
		var bankId = $("input[name='frpCode']:checked").attr("id");
		loadBankQuotaMsg(bankId);
	}
	
}

function loadBankQuotaMsg(bankId) {
	//var target = "http://localhost:8080"+path+"/page/payment/bankInfo/"+bankId+".html";
	//var target = path+"/page/payment/bankInfo/"+bankId+".html";
	var target = "page/payment/bankInfo/"+bankId+".html";
	var paras = 'target='+target;
	var url ="bankPay_bankQuotaMsg.action";
	request(url, setBankQuotaMsg, paras, "GET", false);
}

function request(url, onSuccess, paras, method, isAsync) {
	$.ajax({
		type:method,
		url:url,
		async:isAsync,
		data:paras,
		dataType: "html",
		success:function(request){
			onSuccess(request);
		},
        error:function(e) {
        	alert("请求出现错误。");
		}
	});
}

function setBankQuotaMsg(data) {
	if(data != 'fail'){
		$("#bankInfo").html(data);    
	}
}

function setValidateMsg(value){
	 if ((value.charAt(value.length - 1) == "!") == true) {
		 document.getElementById("message").innerHTML = value;
	 } else {
	     document.getElementById('change').innerHTML = value;
	 }
}

$(document).ready(function(){
	
	//选择最后一个选中的银行
	selectLastBank();
	
	$("input[name='frpCode']").click(function(){
		setCookie("lastSelectedBank",$("input[name='frpCode']:checked").attr("id"));
		var bankId = $("input[name='frpCode']:checked").attr("id");
		loadBankQuotaMsg(bankId);
	});
	
	$("#frpPaySubmit").click(function () {
        createOrder_check();
    });
	
	
//	 var ts = "<%=System.currentTimeMillis()%>";
     var merchantId = $("#merchantId").attr("value");
     var orderNo = $("#orderNo").attr("value");
     var frpCode = $("#frpcode").attr("value");
     var amount = $("#amount").attr("value");

     $("#gwPaySubmit").click(function () {
         if (!validate()) {
             return false;
         }
         var status = 0;
         var pwd_s = $("#powerpass").val();
         var tradePwd = getPassInput(
             "powerpass", ts,
             "errorMsg", "密码输入错误!");
        // $("#password").val(password);
         if (tradePwd == null ||tradePwd=='') {
             return;
         }
         var username = document.getElementById('username').value;
         if (username == null) {
             return;
         }
         $("#tradePwd").val(tradePwd);
         // 增加一个产品编号
         var payProductCode = $(frpCode).attr("value");
 		 var url ="balancePay_validateLogin.action";
 		 $("#payForm").submit();
		 /*request(url, setValidateMsg, {
			'username' : username,
			'tradePwd' : tradePwd,
			'orderNo' : orderNo,
			'merchantId' : merchantId,
			'frpCode' : frpCode,
			'payProductCode' : payProductCode,
			'amount' : amount
		 }, "POST", false);*/
     });

     //判断是否有改变值
     var old_username = "";
     $("#username").blur(function () {
         var username = $("#username").val();
         if (username != null && username != old_username) {
             old_username = username;
             $.post("balancePay_queryObligateInfo.action", {
                 "username": username
             }, function (res) {
                 if (res.STATE == "SUCCESS") {
                	 var message = "预留信息:";
                	 if(res.MSG != null){
                		 message = message + res.MSG;
                	 }
                	 $("#ObligateInfoDiv").show();
                     $("#ObligateInfoDiv").find("div").html(message);
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
	 $("#username").attr("disabled","disabled");
	 $("#infoForm").submit();
	 /*$.ajax({
			url : "balancePay_balancePay.action",
			type : "POST",
			error : function(data) {
				alert("抱歉，发送请求失败，请重试");
			},
			success : function(value) 
			{
				location.href=value;
			}
		});*/
}