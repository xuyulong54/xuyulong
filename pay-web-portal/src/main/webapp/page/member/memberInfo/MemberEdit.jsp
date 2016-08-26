<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员基本信息修改</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() {  setPageType('.men-account', '.men-account-bsinfo '); })

	/** 表单数据校验 */
	$(document).ready(function() {
		$.formValidator.initConfig({
			formID : "form1",
			submitOnce : "true",
			theme : "ArrowSolidBox",
			mode : "AutoTip",
			onError : function(msg) {
				alert(msg)
			},
			inIframe : true
		});
		//真实姓名
		$("#realName").formValidator({
			onShow : "",
			onFocus : "必填项，长度为2-10个字"
		}).inputValidator({
			min : 2,
			max : 10,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "不能为空，长度为2-10个字"
		}).regexValidator({
			regExp : "chinese",
			dataType : "enum",
			onError : "真实姓名必须为中文"
		});
		//身份证号
		$("#cardNo").formValidator({
			onShow : "",
			onFocus : "必填项，长度为18个字符"
		}).inputValidator({
			min : 18,
			max : 18,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "不能为空，长度为18个字符"
		}).functionValidator({
			fun : isCardID
		});
		$.formValidator.reloadAutoTip();
	});
</script>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">
	<div class="pageContent">
	<div class="ada-memberinfo">
	<div class="frm-comon">
		<div class="headline">
	<div class="title">修改基本信息</div>
</div>

		<p class="clearfix">
		<label> &nbsp; </label>	<span class="markRed" style="height: 28px; line-height: 28px;">${PAGE_ERROR_MSG}</span>
			</p>
			<form id="form1" action="memberinfo_editMember.action" method="post">
				<div class="editBoxSpecial" >
					<p class="clearfix">
						<label> &nbsp; </label>
						<span class="text-warning">${PAGE_ERROR_MSG} </span>
					</p>
					<p class="clearfix">
						<label>真实姓名：</label>
						<input type="text" class="ada-input" name="realName" id="realName" value="${realName}" />
					</p>
					<p class="clearfix">
						<label>身份证号：</label>
						<input type="text" class="ada-input" name="cardNo" id="cardNo" value="${cardNo}" />
					</p>
					<p class="clearfix">
						<label>&nbsp; </label>

						<input class="btn btn-primary" type="submit"
							value="提 交" >

					</p>
				</div>
			</form>

		</div>
	</div>
	<div class="clear"></div>
	</div>
	</div></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
