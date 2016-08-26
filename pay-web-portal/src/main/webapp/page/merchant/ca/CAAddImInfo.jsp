
<%
	//1、验证用户是否已有权限生成密钥对 同 验证 证书链完整 安装证书安装控件 VerifyGenerateKeyPairPermission()
	// 2、 验证CPS 如果为空则安装exe 保险箱
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数字证书申请</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript" src="<%=path%>/statics/cfca/js/onload.js"></script>
<script type="text/javascript"
	src="<%=path%>/statics/cfca/js/install.js"></script>



<script type="text/javascript">

/*页面分类*/
$(document).ready(function() { setPageType('.mer-security', '.mer-security-info '); })


    //发送签名,到后端验证保存用户DN信息
	function certApplyFor(rands,sn){
	//alert(rands+"-"+sn);
	var randsPkcs7=rands;//????签名
	var addrId=$("#addrId").val();
		$.ajax({
			url: "merchantca_certInsert.action",
			type: "POST",
			data:{"randsPkcs7" : randsPkcs7,"sn":sn,"addrId":addrId},
			dataType: "json",
			error: function(data){
				alert("安装失败!");
			},
			success: function(data){
				alert("安装成功");
				window.location.href="merchantca_listCA.action";
			}
		});
	}
//申请证书 将 textareaCSPInfo值返回给客户端进行验证
	function cspTranInfo(p10Str){
		$.post("merchantca_certApplyFor.action",{"p10Str":p10Str},function(res){
			if(res.STATE=="SUCCESS"){
				$("#signCert").val(res.SIGNCERT);
				$("#rands").val(res.RANDS);//后台随机数生成
				$("#sn").val(res.SN);
				//安装证书
				InstallCert(1);
				//调用工具包对随机数进行签名 再交互到服务端
				certApplyFor(res.RANDS,res.SN);
			}else{
				alert(res.MSG);
			}
		},"json");
	}

	  function messageOut() {
	      var phoneCode = document.getElementById('phoneCode').value;
	         if(phoneCode != ""){
	           $("#phoneCodeMsg").html("");
	         }
	    }


 function VerifyGenerateKeyPairPermission() {
        try {
            var result = CryptoAgent.CFCA_HavePermissiontoGenerateKeyPair();
            if (result) {
                alert("您有权限生成密钥对");
            }
            else {
                alert("没有权限生成密钥对");
            }

        }
        catch (e) {
            var LastErrorDesc = CryptoAgent.CFCA_GetLastErrorDesc();
            alert(LastErrorDesc);
        }
    }


</script>

<script type="text/javascript">
	/** 表单数据校验 */
$(document).ready(function(){
	//发送短信验证码
	$('#buttonid').bind('click',function(){
		// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
      sendSms('${currentUser.bindMobileNo}', '${currentUser.loginName}', 'merchant', 'binding', '', 'buttonid','phoneCodeMsg');
	});


	$.formValidator.initConfig({formID:"form",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
// 手机验证码
	$("#phoneCode").formValidator({onShow:"",onFocus:"必填项，请输入6位的手机验证码"}).inputValidator({min:6,max:6,onError:"请输入6位的手机验证码"}).regexValidator({regExp:"num",dataType:"enum",onError:"手机验证码必须为数字"})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_phoneCodeValidate.action",
	success : function(data){
           if( data.STATE!='FAIL') {
           //$('.ada-ensure').bind('click',function(){
        	   installCa();
         //  });
           return true;}
           return data.MSG;

	},
	onError : "校验未通过，请重新输入",
	onWait : "正在校验，请稍候..."
	});
	$.formValidator.reloadAutoTip();
});
	getDownLoadPath();
</script>
</head>
<body>
	<div id="FakeCryptoAgent"></div>
	<div id="tr_DigestAlgorithm" style="display: none"></div>
	<!-- 不能删除这个DIV -->
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<div class="headline">
				<div class="title">数字证书</div>
			</div>

			<div class="memSecondSetpFlow mtop10"></div>
			<div class="memFlowTex">
				<ul>
					<li class="green">数字证书申请</li>
					<li class="red" style="width: 420px;">填写申请信息并验证身份</li>

					<li>申请成功</li>
				</ul>
			</div>
			<div class="h10"></div>
			<div class="clear"></div>
			<div class="frm-comon">
				<p class="clearfix">
					<label> &nbsp; </label>
					<c:if test="${isMobileAuth == 101}">
						<span class="markRed"> 请先绑定手机！</span>
					</c:if>
				</p>
				<c:if test="${isMobileAuth == 100 }">
					<div class="ht"></div>
					<form action="merchantca_addCAImInfo.action" method="post"
						id="form">
						<p class="clearfix">
							<label> &nbsp; </label> <span class="message markRed"
								id="phoneCodeMsg">${phoneCodeMsg}</span>
						</p>
						<p class="clearfix">
							<label>签约名：</label> <span>${merchantShortName}</span>
						</p>
						<p class="clearfix">
							<label>使用地点：</label> <span class="select_border"> <span
								class="select_cont"> <select class="select" id="addrId"
									name="addrId" style="width:240px;">
										<c:forEach items="${addrs }" var="addr">
											<option value="${addr.value }">${addr.desc }</option>
										</c:forEach>
								</select> </span>
							</span>
						</p>
						<p class="clearfix">
							<label>绑定手机：</label> ${currentUser.bindMobileNo} &nbsp &nbsp
							&nbsp;<a class="link-color" href="merchantmobilebind_bindingMobileUI.action">更改手机号</a>
						</p>
						<p class="clearfix">
							<label>短信验证码：</label> <input style="width:125px;" id="phoneCode"
								type="text" name="phoneCode" value="${phoneCode}" maxlength="6" onblur="messageOut()">

							<input type="button" class="btn btn-secondary" id="buttonid" value="获取验证码">

						</p>
						<p class="clearfix">
							<label> &nbsp; </label> <input type="button"
								class="btn btn-primary ada-ensure" value="确 定"> <span
								class="clearfix" style="display: none;" id="installCa"> <input
								id="Button2" class="btn btn-primary" onclick="installCa()"
								type="button" value="申 请" /> </span>
						</p>

					</form>


				</c:if>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<input id="textareaCSPInfo" name="textareaCSPInfo" height="20"
		type="hidden" />
	<input type="hidden" id="p10Str" value="" />
	<input type="hidden" id="rands" value="" />
	<input type="hidden" id="sn" value="" />
	<input type="hidden" id="signCert" value="" />
	<input id="TextContianerName" name="TextContianerName" height="20"
		type="hidden" />
	<div id="downloadCA">
		<span id="url"></span>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>

