<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商户基本信息</title>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() {setPageType('.mer-account', '.mer-account-bsinfo ');  })

function formValidator(){
	$('.ada-input').bind('focus', function() {$(this).parent().next().css('display', 'block');});
	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
	//签约名
	$("#fullName").formValidator({onShow:"",onFocus:"必填项，长度为2-30个字符"}).inputValidator({min:2,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-30个字符"});
	//经营范围
	$("#scope").formValidator({onShow:"",onFocus:"必填项，长度为2-100个字符"}).inputValidator({min:2,max:100,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-100个字符"});
	//商城网址
	$("#url").formValidator({onShow:"",onFocus:"必填项，长度不于255个字符"}).inputValidator({min:0,max:255,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为少于255个字符"}).regexValidator({regExp:"url",dataType:"enum",onError:"请输入正确格式的网址"});
	//证备案号
	$("#icp").formValidator({onShow:"",onFocus:"必填项，长度为2-30个字符"}).inputValidator({min:2,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-30个字符"});
	//身份证号
	$("#cardNo").formValidator({onShow:"",onFocus:"必填项，长度为18个字符"}).inputValidator({min:18,max:18,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为18个字符"}).functionValidator({fun:isCardID});
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
	$("#address").formValidator({onShow:"",onFocus:"必填项，长度为2-50个字符"}).inputValidator({min:2,max:50,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-50个字符"});
	$.formValidator.reloadAutoTip();
}
$(function() {
	dynamicShow('${merchantType}');
	formValidator();
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

 function dynamicShow(value) {
	var str1="/page/merchant/merchantInfo/MerchantEditAllEnterpriseInclude.jsp";
	var str2="/page/merchant/merchantInfo/MerchantEditAllMemberInclude.jsp";	
	if (value == '${MerchantTypeEnum.MEMBER.value}') {
		$("#radio1").attr("checked", "checked");
		$("#dynamicShow").load("page/merchant/merchantInfo/MerchantEditAllMember.jsp",function(){
			
		});
		
	} else if (value == '${MerchantTypeEnum.INDIVIDUAL.value }') {
		$("#radio2").attr("checked", "checked");
		$("#dynamicShow").load(
				"page/merchant/merchantInfo/MerchantEditAllEnterprise.jsp",function(){
					$("#shortName").val('${shortName}');
					$("#licenseNo").val('${licenseNo}');
				});
	} else {
		$("#radio3").attr("checked", "checked");
		$("#dynamicShow").load(
				"page/merchant/merchantInfo/MerchantEditAllEnterprise.jsp",function(){
					$("#shortName").val('${shortName}');
					$("#licenseNo").val('${licenseNo}');
				});
	}
}
</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">
		
		<div class="headline">
	<div class="title">修改基本信息</div>
</div>

		<div class="ada-wrap frm-comon reg-inputW">
			<input type="hidden" name="provinceTemp" id="provinceTemp" value="${province}" /> 
			<input type="hidden" name="cityTemp" id="cityTemp" value="${city}" /> 
			<input type="hidden" name="areaTemp" id="areaTemp" value="${area}" />
			
			<form id="form1" action="merchantinfo_editMerchantAll.action" method="post">
				<div class="h10"></div>
				<p class="clearfix">
				<label>签约属性：</label><input id="radio1" type="radio"
					class="radio" name="merchantType" value="${MerchantTypeEnum.MEMBER.value}"
					<c:if test="${merchantType==MerchantTypeEnum.MEMBER.value}"> checked="checked"</c:if>
					onclick="dynamicShow(this.value);" /> <span for="radio1"
					class="lbl">个人</span> <input class="mleft10" id="radio2" type="radio"
					name="merchantType" value="${MerchantTypeEnum.INDIVIDUAL.value }" class="radio"
					<c:if test="${merchantType==MerchantTypeEnum.INDIVIDUAL.value}">checked="checked"</c:if>
					onclick="dynamicShow(this.value);" /> <span for="radio2"
					class="lbl">个体工商户</span> <input class="mleft10"  id="radio3" type="radio"
					name="merchantType" value="${MerchantTypeEnum.ENTERPRISE.value }" class="radio"
					<c:if test="${merchantType==MerchantTypeEnum.ENTERPRISE.value}">checked="checked"</c:if>
					onclick="dynamicShow(this.value);" /><span for="radio3"
					class="lbl">企业</span>
				</p>
				<p class="clearfix">
					<label>公司全称：</label> 
					<input type="text" name="fullName" id="fullName" value="${fullName}" maxlength="30"> 
				</p>
				<div id="dynamicShow"></div>
				<p class="clearfix">
					<label>商城网址：</label> 
					<input  type="text" id="url" name="url" value="${empty url?'http://':url}" maxlength="255">
				</p>
				<p class="clearfix">
					<label>ICP证备案号：</label> 
					<input type="text" name="icp" id="icp" value="${icp}" maxlength="30"> 
				</p>
				<p class="clearfix">
				<label>身份证号：</label>
				<input type="text" name="cardNo" id="cardNo" value="${userInfo.cardNo}" maxlength="18"> 
				</p>
				<p class="clearfix">
					<label>联系人姓名：</label> 
					<input type="text" class="ada-input" id="busiContactName" name="busiContactName" value="${busiContactName}" maxlength="10"> 
				</p>
				<p class="clearfix">
					<label>联系人手机：</label> 
					<input type="text" class="ada-input" id="busiContactMobileNo" name="busiContactMobileNo" value="${busiContactMobileNo}" maxlength="11"> 
				</p>
				<p class="clearfix">
					<label>经营范围：</label>
					<textarea id="scope" rows="3" cols="20" name="scope" maxlength="100">${scope }</textarea>
				</p>
				<p>
				<label>通信地址：</label> 
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
				<label> &nbsp; </label> 
				<label style="width:20px">市</label>
				<span  class="select_border">  
                            <span class="select_cont"> 
				<select class="select" style="width:150px" name="city" id="city">
						<option value="">请选择</option>
				</select> 
				</span></span>
			</p>
			<p class="clearfix">
				<label>具体街道地址：</label> 
				<input type="text" name="address" id="address"value="${address}"> 
			</p>
				<p class="clearfix">
					<label> &nbsp; </label> 
				
					<input class="btn btn-primary" id="Button7" type="submit" value="保 存" onclick="" />
					
				</p>
				<div class="h10"></div>
			</form>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>