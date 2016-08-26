<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>验证用户名</title>
</head>
<script type="text/javascript">
	function changeCode(code) {
		document.getElementById(code).src = 'randomCode.jpg?'+ Math.floor(Math.random() * 100);
	}
	/** 表单数据校验 */
	$(document).ready(function(){
	   
		$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
        //用户名
		$("#loginName").formValidator({onShow:"",onFocus:"请输入正确的用户名"})
		.ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_lookforPwdloginNameValidate.action",
		success : function(data){
			if( data.STATE!='FAIL') {
	            return true;}
				return data.MSG;
		},
		onError : "校验未通过，请重新输入",
		onWait : "正在校验，请稍候..."
		});
		
		//验证码
		$("#randomCode").formValidator({onShow:"",onFocus:"输入左图中的字符,不区分大小写"}).inputValidator({min:1,max:4,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"验证码应为4个字符"})
		.ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_randomCodeValidate.action",
		success : function(data){
			if( data.STATE!='FAIL') {
	            return true;}
				return data.MSG;
		},
		onError : "校验未通过，请重新输入",
		onWait : "正在校验，请稍候..."
		});
		$.formValidator.reloadAutoTip(); 
	});
</script>



<body>
<jsp:include page="../../IndexHead.jsp" />


  	<div class="container">
<div class="bd-container">
 <div class="headline mbt20">
	<div class="title">找回登录密码</div>
</div>

			<div class="frm-comon">	
			
		<form id="form1" action="merchantLookForLoginPwd_loginPwdCheckLoginName.action" method="post">
			<div class="merFirstSetpFlow">
        </div>
			
        <div class="merFlowTex">
       <ul>
            <li class="red"> 验证身份</li>
            <li  style="width: 220px"> 选择找回方式 </li>
             <li style="width: 220px">重置密码</li>
            <li>重置成功</li>
         </ul>
        </div>
		
		<div class="ht"></div>
			 <p class="clearfix">
          <label> &nbsp; </label>
            <span class="text-warning"> 请输入您要找回登录密码的账户名称 </span>
            </p>
       <p class="clearfix">
            <label >&nbsp; </label>
            <span class="text-warning">${PAGE_ERROR_MSG}</span>			
            </p>
				<input type="hidden" name="type" value="merchant">
					<p class="clearfix">
						<label>用户名：</label>
						<input id="loginName" type="text" name="loginName" value="${loginName}" height="25px" maxlength="30" style="width:290px;"/>
					</p>
					<p class="clearfix">
						<label width="200px">验证码：</label>
						<input id="randomCode" type="text" name="randomCode" style="width:190px;float:left;"
							value="${randomCode}" size="8" class="itH" maxlength="4" /> 		
					<span style="width:110px; height:30px; ">	<img alt="验证码" src="randomCode.jpg" id="yzm"  style="width:100px; height:30px; "
							onclick="changeCode('yzm');" class="codeico" title="看不清,换一张">	</span>
					</p>
					<p class="clearfix">
						<label> &nbsp; </label>
				       
						<input class="btn btn-primary" type="submit"
							value="下一步" />
							
					</p>
				
<div class="clear"></div>
		</form>
		<div class="clear"></div>

		</div>
	<div class="clear"></div>
	
</div></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>