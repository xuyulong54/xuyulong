<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户修改登录密码页面</title>
<%@include file="/page/include/headScript.jsp"%>

<script type="text/javascript">
/*页面分类*/
/*千万别加$(document).ready(function() { setPageType('.mer-security', '.mer-security-info '); })*/

$(document).ready(function(){
		$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
     	$("#lgoldPwd").formValidator({onShow:"",onFocus:"必填项，请输入原登录密码"}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"请输入原登录密码"})
     	.ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_oldLoginPwdValidate.action",
		success : function(data){
			if( data.STATE!='FAIL') {
	            return true;}
				return data.MSG;
		},
		onError : "校验未通过，请重新输入",
		onWait : "正在校验，请稍候..."
		});
		$("#lgnewPwd1").formValidator({onShow:"",onFocus:"必填项，请输入密码"})
		.functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     	}).ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_loginEqTradePwd.action",
		success : function(data){
			if( data.STATE!='FAIL') {
	            return true;}
				return data.MSG;
		},
		onError : "校验未通过，请重新输入",
		onWait : "正在校验，请稍候..."
		});
		$("#lgnewPwd2").formValidator({onShow:"",onFocus:"必填项，两次输入密码必须一致"})
		.functionValidator({
            fun: function (val, elem) {
            	var msg=validatePwd(val);
            	if(msg!=''){return msg;}return true;
            }
     	})
		.compareValidator({desID:"lgnewPwd1",operateor:"=",onError:"两次输入的密码不一致"})
		.ajaxValidator({
		dataType : "json",
		async : true,
		url : "ajaxvalidate_loginEqTradePwd.action",
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
<div class="pageContent">
<div class="ada-memberinfo">

		<div class="headline">
	<div class="title">修改登录密码</div>
</div>
		
	<div class="frm-comon reg-inputW mtop15">
		
	<form id="form1" action="merchantpwdedit_firstEditLoginPwd.action" method="post">
	<input type="hidden" name="type" value="${type}"/>
	
        <c:if test="${!empty  msg }">
        	<p class="clearfix">
        	<label> &nbsp; </label>
        		
        			<span class="markRed">
						${PAGE_ERROR_MSG }
					</span>
        		
        	</p>
        </c:if>
            <p class="clearfix">
                    <label>账户名：</label>

              
                
						${userOperator.loginName}

            </p>
          
            <p class="clearfix">
               <label>
                    原登录密码：
               </label>
               
                    <input type="password" name="lgoldPwd" id="lgoldPwd" value="${lgoldPwd}" maxlength="20"/>
               
            </p>
            <p class="clearfix">
               <label>
                    新登录密码：
               </label>
              
                    <input type="password" name="lgnewPwd1" id="lgnewPwd1" value="${lgnewPwd1}" maxlength="20"/>
               
            </p>
           <p class="clearfix">
                <label>
                    确认新登录密码：
               </label>
              
                   <input type="password" name="lgnewPwd2" id="lgnewPwd2" value="${lgnewPwd2}" maxlength="20"/>
                
            </p>
            <p class="clearfix">
              <label> &nbsp; </label>
				
                    <input id="Button2" class="btn btn-primary" type="submit" value="提 交" />
                   
               
            </p>
       
       
    <div class="ht">
    </div>
		</form>
		 <div class="clear"></div>
    </div>
    <div class="clear"></div>
    </div>
    <div class="clear"></div>
    </div>
    <jsp:include page="../../foot.jsp" />
</body>
</html>