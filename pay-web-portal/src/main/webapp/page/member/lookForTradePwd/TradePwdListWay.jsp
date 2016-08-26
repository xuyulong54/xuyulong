<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回支付密码方式</title>
<%@include file="/page/include/headScript.jsp"%>

</head>
<body>
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
       <div class="container">
<div class="bd-container">
		<div class="headline">
	<div class="title">找回支付密码</div>
</div>

		<div class="ht"></div>
		<div class="editBoxWrap">
				<div class="memFirstSetpFlow">
        </div>
        <div class="memFlowTex">
        <ul>
            <li class="red">
                 选择找回方式
            </li>
            <li style="width: 420px;">
                    验证身份,   重置密码
            </li>
           
            <li>
               重置成功
            </li>
            </ul>
        </div>
        
	<div class="ht"></div>
		<div class="tip">
			<strong>根据您的账户情况，请选择重置支付密码的方式：</strong>
		</div>
		<div class="ht"></div>
		<div class="name">
			<strong>帐户名：
			${userInfo.loginName}   
					</strong>
		</div>
		<div class="h10"></div>
		<div class="wayBack">
					<img src="<%=path%>/statics/themes/default/images/ico_phone.png" style="float:left;" />
					<div class="font wayBack-font" >
				    <a href="<%=path%>/memberlookfortradepwd_tradePwdSendPhoneCodeCheckUI.action">通过手机号+证件号验证</a>${bindMobileNoErrorMsg}
					</div>
							<span class="commonBtn"><span class="btn_lfRed">
							<a	class="btn_rtRed"  style="color:white;" href="<%=path%>/memberlookfortradepwd_tradePwdSendPhoneCodeCheckUI.action">
							立即找回</a>
							</span></span>
			</div>
        
        
        <div class="wayBack">
					<img src="<%=path%>/statics/themes/default/images/ico_mail.png" style="float:left;" />
					<div class="font wayBack-font" >
				    <a href="<%=path %>/memberlookfortradepwd_tradePwdSendMailCheckUI.action">通过邮箱+证件号验证</a>${bindMailErrorMsg}
					</div>
							<span class="commonBtn"><span class="btn_lfRed">
							<a	class="btn_rtRed"  style="color:white;" href="<%=path %>/memberlookfortradepwd_tradePwdSendMailCheckUI.action">
							立即找回</a>
							</span></span>
			</div>
		
		<div class="clear"></div>
	</div>
	<div class="ht"></div>
	</div></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info '); })
</script>