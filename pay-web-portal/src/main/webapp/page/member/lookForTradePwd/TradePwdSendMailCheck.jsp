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
<body>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info '); })

$(document).ready(function(){
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
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
<div class="container">
<div class="bd-container">
		<div class="headline">
	<div class="title">找回支付密码</div>
</div>

<c:choose>
<c:when test="${currentUser.isRealNameAuth == 101}">
<div class="ht">
    </div>


        <div class="tipsBox infoWrap">
						<div class="tipsTitle">
							<ul>
								<li><i class="iconfont" >&#xe602;</i></li>
								<li class="tipTxt" >亲，先进行&nbsp;<a href="realnameauth_consentAgreementUI.action">实名认证</a>&nbsp;哦!</li>

							</ul>
						</div>

						<div class="clear"></div>
						</div>




</c:when>
<c:when test="${currentUser.isEmailAuth == 101}">
<div class="ht">
    </div>


        <div class="tipsBox infoWrap">
						<div class="tipsTitle">
							<ul>
								<li><i class="iconfont" >&#xe602;</i></li>
								<li class="tipTxt" >亲，先&nbsp;<a href="memberemailbind_bindingEmailUI.action">绑定邮箱</a>&nbsp;哦！</li>

							</ul>
						</div>

						<div class="clear"></div>
						</div>





</c:when>
 <c:otherwise>
<form id="form1" action="memberlookfortradepwd_tradePwdSendMailCheck.action" method="post">
<div class="frm-comon">
	<div class="ht"></div>

			<div class="merSecondSetpFlow">
        </div>
        <div class="merFlowTex">
        <ul>
            <li class="green"> 选择找回方式</li>
            <li style="width: 220px" class="red"> 验证身份</li>
             <li style="width: 220px" >重置密码</li>
            <li>重置成功</li>
        </ul>
        </div>
    <div class="ht"></div>
              <p class="clearfix">
				<label> &nbsp; </label>
					<span class="markRed" id="phoneCodeMsg">${PAGE_ERROR_MSG} </span>

				</p>
        	 <c:if test="${!empty  PAGE_ERROR_MSG }">
        		<p class="clearfix">
					<label> &nbsp; </label>
        			<div class="text-warning">${PAGE_ERROR_MSG}</div>
        		</p>
        	</c:if>
            <p class="clearfix">
               	<label>  绑定邮箱：</label>
                <strong style=" text-align: left;">    ${bindEmail} </strong>
            </p>
           <p class="clearfix">
                <label> 身份证号：</label>
                <input type="text" name="cardNo" value="${cardNo}" id="cardNo" maxlength="18"/>
            </p>
      		<p class="clearfix">
               <label> &nbsp; </label>
                <input id="Button2" class="btn btn-primary" type="submit" value="确 定" />
            </p>
        <div class="clear"></div>

    <div class="ht">
    </div>
    </div>
</form>
</c:otherwise>
 </c:choose>
  </div></div>


	<jsp:include page="../../foot.jsp" />
</body>
</html>
