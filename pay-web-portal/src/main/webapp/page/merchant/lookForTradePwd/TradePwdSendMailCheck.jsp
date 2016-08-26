<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>找回支付密码，邮箱发送</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<style type="text/css">
.message {
	color: red;
}
</style>
<body>
<script type="text/javascript">
$(document).ready(function(){
	
	/*页面分类*/
	 setPageType('.mer-security', '.mer-security-info '); 
	
	$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
	//身份证号
	$("#cardNo").formValidator({onShow:"",onFocus:"必填项，长度为18个字符"}).inputValidator({min:18,max:18,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为18个字符"}).functionValidator({fun:isCardID})
	.ajaxValidator({
	dataType : "json",
	async : true,
	url : "ajaxvalidate_cardNoValidate.action",
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

<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include><br />
        <div class="container">
<div class="bd-container">
	   <div class="headline">
	<div class="title">找回支付密码</div>
</div>

<c:choose>
<c:when test="${currentUserInfo.isEmailAuth == 101}">
<div class="ht">
    </div>
       <div class="tipsBox infoWrap"> 	
						<div class="tipsTitle">
							<ul>
								<li><i class="iconfont" >&#xe602;</i></li>
								<li class="tipTxt" >亲，先&nbsp;<a href="merchantemailbind_bindingEmailUI.action">绑定邮箱</a>&nbsp;哦!</li>
				
							</ul>
						</div>
						
						<div class="clear"></div>
		</div>		

    

	
	
</c:when>
<c:when test="${currentUserInfo.isRealNameAuth == 101}">
<div class="ht">
    </div>
         <div class="tipsBox infoWrap"> 	
						<div class="tipsTitle">
							<ul>
								<li><i class="iconfont" >&#xe602;</i></li>
								<li class="tipTxt" >亲，先&nbsp;<a href="#">实名认证</a>&nbsp;哦!</li>
				
							</ul>
						</div>
						
						<div class="clear"></div>
		</div>		
    
</c:when>


<c:otherwise>
<form id="form1" action="merchantlookfortradepwd_tradePwdSendMailCheck.action" method="post">
 
<div class="ht">
    </div>
   <div class=" editBox editBoxWrap frm-comon">
			<div class="merSecondSetpFlow">
        </div>
        <div class="merFlowTex">
         <ul>
            <li class="green">
                 选择找回方式
            </li>
            <li class="red" style="width: 220px">
               验证身份
            </li>
             <li  style="width: 220px">
               重置密码
            </li>
           
            <li >
               重置成功
            </li>
            </ul>
        </div>
<div class="ht"></div>
        	 <c:if test="${!empty  msg }">
        	<p class="clearfix" >
        		<label> &nbsp; </label>
        			<span class="text-warning">
						${PAGE_ERROR_MSG}
					</span>
        		
        		</p>
        	</c:if>
        	<p class="clearfix" >
	        	<label> &nbsp; </label>
	          		<span class="text-warning" id="markRed"  style="display: none;">
							${PAGE_ERROR_MSG}
					</span>
        	</p>
            <p class="clearfix" >
                <label>
                   绑定邮箱：
                </label>
                <span> 
                   ${bindEmail}
                </span>
            </p>    
               <p class="clearfix" >
                  <label>
                      身份证号：
                   </label>
                    
                       <input type="text" name="cardNo" value="${cardNo}" id="cardNo" maxlength="18"/>
                   
                </p>
       
        <p class="clearfix" >
      <strong> &nbsp; </strong>
				
            <input id="Button2" class="btn btn-primary" type="submit" value="确 定" />
            
        </p>
        
        <div  class="clear"></div>
    </div>
    <div class="ht">
    </div>
</form>
</c:otherwise>
</c:choose>
</div></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>