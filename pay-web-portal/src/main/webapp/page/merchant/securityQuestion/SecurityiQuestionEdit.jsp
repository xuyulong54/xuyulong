<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户设置安全问题</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.mer-security', '.mer-security-info '); })

 function submitForm(){
  var text = "";
  document.getElementById("errorMsg").innerHTML=text;
  var textWarning= document.getElementById("textWarning");
  if (textWarning!=null){
    textWarning .innerHTML=text
  }  subForm();
}

$(function(){
	validatorFrom();
});

//------------------------表单验证------------------------
function validatorFrom(){
		$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
		$("#question").formValidator({onShow:"",onFocus:"请选择"}).functionValidator({
		    fun:function(val,elem){
		        if(val==""||val=="0"){
		         return "请选择"
			    }else{
				 return true;
			    }
			}
		});
		// 安全保护答案
		$("#answer").formValidator({onShow:"",onFocus:"必填项，长度为2-16个字符"}).inputValidator({min:2,max:16,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-16个字符"});
		$.formValidator.reloadAutoTip();
}
</script>
<body>
<div id="FakeCryptoAgent"></div>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
<div class="container">
  <div class="bd-container">
    <div class="headline">
	    <div class="title">修改安全问题</div>
    </div>
    <div class="frm-comon reg-inputW mtop15">
    	<form id="form1" action="merchantsecurityiquestion_editSecurityiQuestion.action" method="post">
    	  <input type="hidden" name="type" value="${type}"/>
        <p class="clearfix">
          <label for="">  安全问题：</label>
          <span class="select_border">
            <span class="select_cont">
    					<select class="select"  name="question"  id="question">
    					  <option value="">请选择</option>
    						<c:forEach items="${questions}" var="qu">
    							<option value="${qu.value}"
    								<c:if test="${qu.value == question}">selected="selected"</c:if>>${qu.desc}
                  </option>
    						</c:forEach>
    					</select>
    				</span>
          </span>
        </p>
        <p class="clearfix">
          <label>答案：</label>
          <input type="text" name="answer" id="answer" value="${answer}" maxlength="16"/>
        </p>
    		<jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>
    		<c:if test="${!empty  PAGE_ERROR_MSG }">
          <p class="clearfix">
            <label> &nbsp; </label>
            <span class="text-warning" id="textWarning">${PAGE_ERROR_MSG}</span>
          </p>
        </c:if>
        <p class="clearfix">
          <label> &nbsp; </label>
          <input type="button" class="btn btn-primary" value="提 交" onclick="submitForm();return false;">
        </p>
    	</form>
    	<div class="clear"></div>
    </div>
  </div>
</div>
<jsp:include page="../../foot.jsp" />

</body>
</html>
