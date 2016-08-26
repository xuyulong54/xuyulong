<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>转账</title>
<style type="text/css">
.message {
	color: red;
}
</style>
<%@include file="/page/include/headScript.jsp"%>
<%-- <script type="text/javascript" src="<%=path %>/statics/js/writeObject.js"></script> --%>
<script type="text/javascript" src="<%=path%>/statics/cfca/js/SignCertManager.js"></script>
<script type="text/javascript">
/*页面分类*/
 function submitForm(){
    var text = "";
    document.getElementById("errorMsg").innerHTML=text;
      var textWarning= document.getElementById("textWarning");
      if (textWarning!=null){
        textWarning .innerHTML=text
      } subForm();
  }

	$(document).ready(function() {
	//充值金额只能输入少于2位的数字。
	var readOnly='';
	$('.transferM').bind('keydown ',function(){	
	var value=$(this).val();
	if(value.indexOf('.')!==-1){
	var l=value.indexOf('.');
	var str =value.substr(l);
	if(str.length==2){
	readOnly=$(this).val();
	}
	if(str.length>=2){
	$(this).val(readOnly);
	}
	}
	});
	setPageType('.mer-account', '.mer-account-info ');
		$("#transferDesc").focus(function(){
			if($("#transferDesc").val() == "付款"){
				$("#transferDesc").val("");
			}$("#transferDesc").val();});
		$("#transferDesc").blur(function(){
			if($("#transferDesc").val() == ""){
				$("#transferDesc").val("付款");
			}else{$("#transferDesc").val();}
		});
	
		$.formValidator.initConfig({formID:"form1",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){alert(msg)},inIframe:true});
		
	    $("#checkMobile").click(function() {
	        if (!!$("#checkMobile").attr("checked")) {
	        	$("#divMobile").show();//显示
	        	$("#bindingPhone").formValidator({onShow:"请输入11位的手机号码",onFocus:""}).inputValidator({min:11,max:11,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"手机号码必须是11位的"}).regexValidator({regExp:"mobile",dataType:"enum",onError:"手机号码格式不正确"}).show();
	        	//$("#transferDesc").formValidator({onShow: "必填项，请输入转账说明",onFocus:"最多100个字"}).inputValidator({min:1,max:100,onError:"转账说明为1-100个字"});
	        }else{
	            $("#divMobile").hide();//隐藏
	            $("#bindingPhone").val("");
	            $("#bindingPhone").formValidator();
	            //$("#transferDesc").formValidator({onShow: "",onFocus:""}).inputValidator({min:1,max:100,onError:""});
	           // $("#transferDescTip").hide();//隐藏

	        }
	    });	
		//转账金额
		$("#transferAmount").formValidator({onShow:"金额必须为整数或小数,小数点后不超过2位",onFocus:""}).regexValidator({regExp:"^(([0]\.[0-9]{1,2})|([1-9][0-9]*\.[0-9]{1,2})|([1-9][0-9]*))$",onError:"金额必须为整数或小数,小数点后不超过2位"})
		.ajaxValidator({
			dataType : "json",
			async : true,
			url : "ajaxvalidate_merchantTransferAmountValidate.action",
			success : function(data) {
				if( data.STATE!='FAIL') {
		            return true;}
					return data.MSG;
			},
			onError : "校验未通过，请重新输入",
			onWait : "正在校验，请稍候..."
		});
		$.formValidator.reloadAutoTip();
	    
		//转账说明
		$("#transferDesc").formValidator({onShow: "",onFocus:"最多100个字"}).inputValidator({min:0,max:100,onError:"最多100个字"});
	    //收款人
	    $("#payeeAccountNo").formValidator({onShow:"请输入收款人",onFocus:""}).inputValidator({min:8,max:50,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"收款人格式不正确"})
	    .regexValidator({regExp:"email",dataType:"enum",onError:"收款人格式不正确"})
	    .compareValidator({desID:"loginName",operateor:"!=",onError:"不能转账给自己"})
	    .ajaxValidator({
    		dataType : "json",
    		async : true,
            url : "ajaxvalidate_merchantGetUserNameByAccount.action",
            success : function(data) {
	         	if(data.STATE!='FAIL'){
	         	   if(data.MSG ==null||data.MSG == ""){
	         	   		$("#recodeAccountName").html(data.FULLNAME);
	         	   }else{
	         	   		$("#recodeAccountName").html(data.FULLNAME + "("+data.MSG+")");
	         	   }
	               $("#recodeName").val(data.FULLNAME);
	               return true;}
	 			return data.MSG;
	         },
    		onError : "校验未通过，请重新输入",
    		onWait : "正在校验，请稍候..."
	    });
		$.formValidator.reloadAutoTip();
		$("#Button1").removeAttr("disabled");
	});
		//获取服务器时间戳
	var ts="<%=System.currentTimeMillis()%>";
	
</script>
</head>
<body>
<div id="FakeCryptoAgent"></div>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	 <div class="bd-container ">
	 <div class="men-recharge">
	  <div class="panel-trade">
	   <div class="panel-head">
                    <div class="menu">
                        <span class="fixed-mid"></span>
                        <h4>
                            商户转账</h4>
                    </div>
                    <a class="link" href="merchantReceiveTransfer_listReceiveTransfer.action"><span class="fixed"></span>
                        <h4 class="fl">
                            转账记录查询</h4>
                    </a>
                    <div style="clear: both;">
                    </div>
                </div>
	   <div class="clear">
                </div>
                
		 <div class="panel-body">
	
<div class="frm-comon">	
<form id="form1" action="merchantReceiveTransfer_transfer.action" method="post">
		<input type="hidden" name="loginName" value="${currentUser.loginName}" id="loginName">
		<input type="hidden" name="balance" value="${balance}" id="balance">	
		<input type="hidden" name="recodeName" value="${recodeName}" id="recodeName">
        	<p class="clearfix">
					<label> 可用余额：</label>
					<fmt:formatNumber value="${balance}" pattern="#0.00"></fmt:formatNumber></span>元 
					</p>
					<p class="clearfix">
					<label > &nbsp; </label>
        	 <div id="divpayeeAccountNo" style="margin-left: 100px;"><span id="recodeAccountName" style="color: red;font-size: small;">${recodeName}</span></div>
        	</p>
        	<c:if test="${balance == '0' || balance == '0.0'}">
         	<p class="clearfix">
         	<label > &nbsp; </label>
             	<span class="text-warning">账户余额不足，请充值后继续完成转账操作，去</span>
              <a class="link-color" href="merchantReceiveRecharge_rechargePage.action"> 充值</a> 
         	</p>
        
         </c:if>
        	<p class="clearfix">
        	<label > 收款人账户：</label>
        	<input value="${payeeAccountNo}" type="text"  name="payeeAccountNo" id="payeeAccountNo" maxlength="30"/> 
		</p>
       	<p class="clearfix">
        	<label > 转账金额(元)：</label>
        	<input value="${transferAmount}" type="text" name="transferAmount" id="transferAmount" class='transferM' />
		</p> 	
        	<p class="clearfix">
        	<label > 转账说明：</label>
        	<input value="${transferDesc}" type="text"	name="transferDesc" id="transferDesc" maxlength="100"/>
		</p> 	
      <%--  <jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>	 --%>
       		<%@ include file="/page/include/merchantTradePwd.jsp"%>
         <p class="clearfix">
					<label > &nbsp; </label>
				<input type="checkbox" style="width:20px" id="checkMobile" name="checkMobile" <c:if test="${checkMobile=='on' }">checked="checked"</c:if> />免费通知收款人
					</p>
				 <c:if test="${not empty PAGE_ERROR_MSG}">
            <p class="clearfix">
              <label> &nbsp; </label><span class="text-warning" id="textWarning"> ${PAGE_ERROR_MSG}</span>
            </p>
          </c:if>
          <p class="clearfix">    
          <div id="divMobile" style="<c:if test="${empty checkMobile }">display: none</c:if>">
         <p class="clearfix"><label >手机号码：</label><input id="bindingPhone" value="${mobileNo}" type="text" name="mobileNo" id="mobileNo" maxlength="11"/></p></div>
         </p>
            <p class="clearfix">
            <label> &nbsp; </label>
        	<input id="Button1" class="btn btn-primary"  type="button" onclick="submitForm();return false;"  value="确认转账" />
        	</p>
	</form>
	</div>
	</div>
	
	<div class="recharge-foot">
                <h3>
                    新手帮助：</h3>
                <div class="help-transferbg">
                </div>
            </div>
	</div>
	</div>
	<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
<script type="text/javascript">

	function getAcountName(){
		var targetName=$("#payeeAccountNo").val();
			if(targetName!=null&&targetName!=""){
					 $.ajax({
                                          url : 'merchantReceiveTransfer_toMerchantGetUserNameByAccount.action',
                                          data : {'targetName':targetName},
                                          type : "POST",
                                          dateType:'json',
                                          /* beforeSend : function(XMLHttpRequest) {// 提交之前的处理代码放在此处,可空着
                                                            
                                          }, */
                                          success : function(data) {
                                               // var dd=eval('('+data+')');
                                                $("#recodeAccountName").html("("+data.msg+")");
                                                $("#recodeName").val(data.msg);
                                          },
                                          error : function(data) {
                                          }
                                    });
                        }
		
	}
</script>