<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商户基本信息</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() {setPageType('.mer-account', '.mer-account-bsinfo ');  })

$(function() {
	formValidator();
	//发送短信验证码
	$('#buttonid').bind('click',function(){
		// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
      sendSms('${currentUser.bindMobileNo}', '${currentUser.loginName}', 'merchant', 'binding', '', 'buttonid','phoneCodeMsg');
	});
	// 加载省事数据
	var provinces = "${userInfo.province}";
	var city = "${userInfo.city}";
	$.post("merchantRegister_getCityByProvince.action",{"province":provinces},function(result){
		$("#city").empty();
		$("#city").append("<option value=''>请选择</option>");
		for(var i=0;i<result.cityList.length;i++){
			if(city == result.cityList[i].city){
				$("#city").append("<option value='"+result.cityList[i].city+"' selected='selected'>"+result.cityList[i].city+"</option>");
			}else{
				$("#city").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
			}
		}
	},"json");

	$("#province").change(function(){
			var provinceValue = $(this).children('option:selected').val();
			$.post("merchantRegister_getCityByProvince.action",{"province":provinceValue},function(result){
				$("#city").empty();
				$("#city").append("<option value=''>请选择</option>");
				for(var i=0;i<result.cityList.length;i++){
					$("#city").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
				}
			},"json");
	});
})
function formValidator(){
	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
	//商户简称
	$("#shortName").formValidator({onShow:"",onFocus:"必填项，长度为2-10个字符"}).inputValidator({min:2,max:10,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-10个字符"});
	//联系人姓名
	$("#busiContactName").formValidator({onShow:"",onFocus:"必填项，长度为2-10个字符"}).inputValidator({min:2,max:10,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-10个字符"}).regexValidator({regExp:"chinese",dataType:"enum",onError:"联系人姓名必须为中文"});
	//联系人手机
	$("#busiContactMobileNo").formValidator({onShow:"",onFocus:"必填项，请输入11位的手机号码"}).inputValidator({min:11,max:11,onError:"手机号码必须是11位的"}).regexValidator({regExp:"mobile",dataType:"enum",onError:"手机号码格式不正确"});
	//省
	$("#province").formValidator({onShow:"",onFocus:"请选择"}).functionValidator({
	    fun:function(val,elem){
	        if(val==""||val=="0"){
	         return "请选择"
		    }else{
			 return true;
		    }
		}
	});
	//市
	$("#city").formValidator({onShow:"",onFocus:"请选择"}).functionValidator({
	    fun:function(val,elem){
	        if(val==""||val=="0"){
	         return "请选择"
		    }else{
			 return true;
		    }
		}
	});
	// 通信地址
	$("#address").formValidator({onShow:"",onFocus:"请输入具体街道地址"}).inputValidator({min:2,max:50,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-50个字符"});
	// 手机验证码
	$("#phoneCode").formValidator({onShow:"",onFocus:"必填项，请输入6位的手机验证码"}).inputValidator({min:6,max:6,onError:"请输入6位的手机验证码"}).regexValidator({regExp:"num",dataType:"enum",onError:"手机验证码必须为数字"})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_phoneCodeValidate.action",
	success : function(data){
		if( data.STATE!='FAIL') {
            return true;}
			return data.MSG;
	},
	onError : "校验未通过，请重新输入",
	onWait : "正在校验，请稍候..."
	});
	$.formValidator.reloadAutoTip();
}

function messageOut() {
	  var phoneCode = document.getElementById('phoneCode').value;
	     if(phoneCode != ""){
	       $("#phoneCodeMsg").html("");
	     }
	}
</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="pageContent">
	<div class="ada-memberinfo">

		<div class="column columnW850">
		<div class="title">修改基本信息</div>
		</div>
		<div class="ada-wrap">
			<c:if test="${isMobileAuth == 101}">
			     <div class="tipsBox infoWrap">
						<div class="tipsTitle">
							<ul>
								<li><i class="iconfont" >&#xe602;</i></li>
								<li class="tipTxt" >亲，先&nbsp;<a href="merchantmobilebind_bindingMobileUI.action">绑定手机</a>&nbsp;哦！</li>
							</ul>
						</div>

						<div class="clear"></div>
		</div>

			</c:if>
				<c:if test="${isMobileAuth == 100}">
						${PAGE_ERROR_MSG}
						<input type="hidden" name="provinceTemp" id="provinceTemp" value="${province}">
						<input type="hidden" name="cityTemp" id="cityTemp" value="${city}">
						<input type="hidden" name="areaTemp" id="areaTemp" value="${area}">
				<form id="form1" action="merchantinfo_editMerchant.action" method="post">
					<div class="h10"></div>
						<p class="clearfix">
							<strong >商户简称：</strong>
							<input type="text" class="ada-input" name="shortName" id="shortName" value="${shortName}" maxlength="10">
						</p>
						<p class="clearfix">
							<strong >联系人姓名：</strong>
							<input type="text" class="ada-input" id="busiContactName" name="busiContactName" value="${busiContactName}" maxlength="10">
						</p>
						<p class="clearfix">
							<strong >联系人手机：</strong>
							<input type="text" class="ada-input" id="busiContactMobileNo" name="busiContactMobileNo" value="${busiContactMobileNo}" maxlength="11">
						</p>
						<p class="clearfix">
							<strong >绑定手机号：</strong>
						<span style='display:block;width:168px;float:left;'>${showBindMobileNo}</span>
							<input type="button" id="buttonid" value="获取验证码"  class="btn btn-secondary" />

								 <span class="markRed" id="phoneCodeMsg"></span>

						</p>
						<p class="clearfix">
							<strong >短信验证码：</strong>
							<input type="text" name="phoneCode" id="phoneCode" value="${phoneCode}" maxlength="6" onblur="messageOut()">
						</p>
						<p>
							<strong >通信地址：</strong>
							<label style="width:20px">省</label>
							<span  class="select_border">
			                 <span class="select_cont">
							<select class="select" style="width:150px" name="province" id="province">
								<option value="">请选择</option>
								<c:forEach items="${provinceList }" var="v">
									<option value="${v.province }" <c:if test="${userInfo.province eq v.province }">selected="selected" </c:if> >${v.province}</option>
								</c:forEach>
							</select>
							</span></span>
						</p>
						<p class="clearfix">
							<strong > &nbsp; </strong>
							<label style="width:20px">市</label>
							<span  class="select_border">
			                            <span class="select_cont">
							<select class="select" style="width:150px" name="city" id="city">
									<option value="">请选择</option>
							</select>
							</span></span>
						</p>
						<p class="clearfix">
							<strong > &nbsp; </strong>

							 <input class="ada-input" class="ada-input"type="text" name="address" id="address" value="${address}" maxlength="50">
						</p>
						<p class="clearfix" style="height:10px">
							<strong></strong>
							<span style="color: red">${provinceMsg}</span>
						</p>
						<p class="clearfix">
							<strong> &nbsp; </strong>
				            <span class="commonBtn"><span class="btn_lfRed">
							<input class="btn_rtRed" id="Button7" type="submit" value="保 存" onclick="" />
							</span></span>
						</p>
				</form>
			</c:if>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
