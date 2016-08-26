
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
<%@include file="/page/include/headScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数字证书申请</title>

<script type="text/javascript" src="<%=path%>/statics/cfca/js/onload.js"></script>
<script type="text/javascript"
	src="<%=path%>/statics/cfca/js/install.js"></script>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info ');
})


    //发送签名,到后端验证保存用户DN信息
	function certApplyFor(rands,sn){
	//alert(rands+"-"+sn);
	var randsPkcs7=rands;//????签名
	var addrId=$("#addrId").val();
		$.ajax({
			url: "memberca_certInsert.action",
			type: "POST",
			data:{"randsPkcs7" : randsPkcs7,"sn":sn,"addrId":addrId},
			dataType: "json",
			error: function(data){
				//alert(data);
				alert("安装失败!");
			},
			success: function(data){
				alert("安装成功");
				window.location.href="memberca_listCA.action";
			}
		});
	}
//申请证书 将 textareaCSPInfo值返回给客户端进行验证
	function cspTranInfo(p10Str){
		$.ajax({
			url: "memberca_certApplyFor.action",
			type: "POST",
			data:{"p10Str" : p10Str},
			dataType: "json",
			error: function(data){
				alert(data);
			},
			success: function(data){
				//alert(data[0].p10Str+"-"+data[0].rands);
				$("#signCert").val(data[0].signCert);
				var rands=data[0].rands;
				$("#rands").val(rands);//后台随机数生成
				var sn=data[0].sn;
				$("#sn").val(sn);//后台随机数生成
				//安装证书
				InstallCert(1);
				//调用工具包对随机数进行签名 再交互到服务端
				certApplyFor(rands,sn);
			}
		});
	}


	function messageOut() {
		  var phoneCode = document.getElementById('phoneCode').value;
		     if(phoneCode != ""){
		       $("#phoneCodeMsg").html("");
		     }
		}
 /* function VerifyGenerateKeyPairPermission() {
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
    } */

</script>

<script type="text/javascript">
	/** 表单数据校验 */
$(document).ready(function(){
	//发送短信验证码
	$('#buttonid').bind('click',function(){
		// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
      sendSms('${currentUser.bindMobileNo}','${currentUser.loginName}',  'member', 'binding', '', 'buttonid','phoneCodeMsg');
	});

	$.formValidator.initConfig({formID:"form",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
	// 手机验证码
	$("#phoneCode").formValidator({onShow:"",onFocus:"必填项，请输入6位的手机验证码"}).inputValidator({min:6,max:6,onError:"请输入6位的手机验证码"}).regexValidator({regExp:"num",dataType:"enum",onError:"手机验证码必须为数字"})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_phoneCodeValidate.action",
	success : function(data){
           if(  data.STATE!='FAIL') {
          // $('.ada-ensure').bind('click',function(){
        	   installCa();
        	 //$("#installCa").show();
          // });
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
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>

	<div class="pageContent">
		<div class="frm-comon">
			<div class="ada-memberinfo">
				<div class="headline">
					<div class="title">数字证书</div>
				</div>
				<div class="h10"></div>
				<div class="memSecondSetpFlow"></div>
				<div class="memFlowTex">
					<ul>
						<li class="green">数字证书申请</li>
						<li class="red" style="width: 420px;">填写申请信息并验证身份</li>

						<li>申请成功</li>
					</ul>
				</div>
				<div class="h10"></div>
				<div class="clear"></div>
				<div class="ada-wrap">
					<p class="clearfix">
						<strong></strong>
						<c:if test="${currentUser.isMobileAuth == 101}">
							<span class="text-warning">请先绑定手机！</span>
						</c:if>
					</p>
					<c:if test="${currentUser.isMobileAuth == 100}">

						<form action="memberca_addCAImInfo.action" method="post" id="form">

							<p class="text-warning marginL150" id="phoneCodeMsg">${phoneCodeMsg}</p>

							<p class="clearfix">
								<label>真实姓名：</label> <span>${membertInfoParam.realName}</span>
							</p>
							<p class="clearfix">
								<label>使用地点：</label> <span class="select_border"> <span
									class="select_cont"> <select id="addrId" name="addrId"
										class="select">
											<c:forEach items="${addrs }" var="addr">
												<option value="${addr.value }">${addr.desc }</option>
											</c:forEach>
									</select> </span>
								</span>
							</p>
							<p class="clearfix">
								<label>绑定手机：</label> <span> ${bindMobileNo}</span><a
									class="link-color mleft10"
									href="membermobilebind_bindingMobileUI.action">更改手机号</a>
							</p>
							<p class="clearfix">
								<label>短信验证码：</label> <input style="width:158px;" id="phoneCode"
									maxlength="6" type="text" name="phoneCode" value="${phoneCode}" onblur="messageOut()">
										<input type="button" class="btn btn-secondary" id="buttonid"
										value="获取验证码">
							</p>
							<p class="clearfix">
								<label> &nbsp; </label> <input type="button"
									class="btn btn-primary ada-ensure" value="确 定"> <span
									class="clearfix" style="display: none;" id="installCa">

									<input id="Button2" class="btn btn-primary"
									onclick="installCa()" type="button" value="申 请" /> </span>
							</p>

						</form>


					</c:if>
				</div>

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
