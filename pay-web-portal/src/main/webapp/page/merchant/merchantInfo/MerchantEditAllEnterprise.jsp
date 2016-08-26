<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户注册：2.设置商户信息</title>
</head>
<script type="text/javascript">
	//商户简称
	$("#shortName").formValidator({onShow:"",onFocus:"必填项，长度为2-10个字符"}).inputValidator({min:2,max:10,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-10个字符"});
	//营业执照号
	$("#licenseNo").formValidator({onShow:"",onFocus:"必填项，长度为15个字符"}).inputValidator({min:15,max:15,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为15个字符"});
	//企业法人姓名
	$("#legalPerson").formValidator({onShow:"",onFocus:"必填项，长度为2-10个中文"}).inputValidator({min:2,max:10,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-10个中文"}).regexValidator({regExp:"chinese",dataType:"enum",onError:"企业法人姓名必须为中文"});
</script>
<body>
<p class="clearfix">
	<strong>商户简称：</strong>
	<input type="text" class="ada-input"
		name="shortName" id="shortName" value="${shortName}" maxlength="10"> <span
		style="color: red">${shortNameMsg}</span>
	
</p>
<p class="clearfix">
	<strong>营业执照号：</strong>
	<input type="text" name="licenseNo"
		id="licenseNo" value="${licenseNo}" maxlength="15"> <span class="message">${licenseNoMsg}</span>
</p>
<p class="clearfix">
	<strong>企业法人姓名：</strong>
	<input type="text" name="legalPerson" id="legalPerson" value="${legalPerson}" maxlength="10"> 
</p>
</body>
</html>