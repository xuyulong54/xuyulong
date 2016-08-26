<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实名认证</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript" src="<%=path%>/statics/js/YMDClass.js"></script>
<script type="text/javascript" src="<%=path %>/statics/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path %>/statics/js//datepicker/skin/WdatePicker.css"></script>
<script type="text/javascript">
	/*页面分类*/
	$(document).ready(function() {
		setPageType('.men-security', '.men-security-info ');
	})
	function changeCode(code) {
		code.src = 'randomCode.jpg?' + Math.floor(Math.random() * 100);
	}
	/** 表单数据校验 */
	$(document)
			.ready(
					function() {
						var myDate = new Date();
						var year = myDate.getFullYear();
						var month = myDate.getMonth() + 1;
						var day = myDate.getDate();
						$.formValidator.initConfig({
							formID : "subForm",
							submitOnce : "true",
							theme : "ArrowSolidBox",
							mode : "AutoTip",
							onError : function(msg) {
							//	alert(msg)
							},
							inIframe : true
						});
						//真实姓名
						$("#realName").formValidator({
							onShow : "",
							onFocus : "必填项，长度为2-10个中文"
						}).inputValidator({
							min : 2,
							max : 10,
							empty : {
								leftEmpty : false,
								rightEmpty : false,
								emptyError : "输入内容两边不能有空符号"
							},
							onError : "不能为空，长度为2-10个中文"
						}).regexValidator({
							regExp : "chinese",
							dataType : "enum",
							onError : "真实姓名必须为中文"
						});
						//身份证号
						$("#cardNo")
								.formValidator({
									onShow : "",
									onFocus : "必填项，长度为18个字符"
								})
								.inputValidator({
									min : 18,
									max : 18,
									empty : {
										leftEmpty : false,
										rightEmpty : false,
										emptyError : "输入内容两边不能有空符号"
									},
									onError : "不能为空，长度为18个字符"
								})
								.functionValidator({
									fun : isCardID
								});
						//职业
						$("#occupation").formValidator({
							onShow : "",
							onFocus : "必填项，长度为2-30个字"
						}).inputValidator({
							min : 2,
							max : 30,
							empty : {
								leftEmpty : false,
								rightEmpty : false,
								emptyError : "输入内容两边不能有空符号"
							},
							onError : "不能为空，长度为2-30个字"
						});
						$("#country").formValidator({
							onShow : "",
							onFocus : "请选择国籍"
						}).functionValidator({
							fun : function(val, elem) {
								return true;
							}
						});
						//验证码
						$("#randomCode").formValidator({
							onShow : "",
							onFocus : "输入左图中的字符,不区分大小写"
						}).inputValidator({
							min : 1,
							max : 4,
							empty : {
								leftEmpty : false,
								rightEmpty : false,
								emptyError : "输入内容两边不能有空符号"
							},
							onError : "验证码应为4个字符"
						}).ajaxValidator({
							dataType : "json",
							async : true,
							url : "ajaxvalidate_randomCodeValidate.action",
							success : function(data) {
								if (data.STATE != 'FAIL') {
									return true;
								}
								return data.MSG;
							},
							onError : "校验未通过，请重新输入",
							onWait : "正在校验，请稍候..."
						});
						//$.formValidator.reloadAutoTip();
						$('.next').bind('click', function() {
							$('.subform').submit();
						});
					});
	//删除图片
	function deleteImg(imgtype) {
		if (imgtype == 'cardFrontImg') {
			$(".cardFrontWrap").hide();
			$("#cardFrontPath").val("");
			//"#cardFront1").val("");
			document.getElementById('cardFront1').outerHTML = document
					.getElementById('cardFront1').outerHTML.replace(
					/(value=\").+\"/i, "$1\"");
			$("#form1Div").show();
			$("#cardMsg").html("");
		} else {
			$(".backImgWrap").hide();
			$("#cardBackPath").val("");
			//$("#cardFront2").val("");
			document.getElementById('cardFront2').outerHTML = document
					.getElementById('cardFront2').outerHTML.replace(
					/(value=\").+\"/i, "$1\"");
			$("#form2Div").show();
			$("#cardMsg").html("");
		}
	}
</script>
</head>
<style type="text/css">
.message {
	color: red;
}
</style>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<div class="realNameAuth">
				<div class="headline">
					<div class="title">实名认证</div>
				</div>
				<div class="memSecondSetpFlow setpFlow"></div>
				<div class='memFlowTex'>
					<ul>
						<li class='green'>实名认证说明</li>
						<li class='middle red'>填写申请信息</li>
						<li>提交申请，等待审核</li>
					</ul>
				</div>
				<div class="frm-comon">
					<form id="subForm" class="subform"
						action="realnameauth_realNameAuth.action" method="post">
						<p class="clearfix">
							<label> &nbsp; </label> <input type="hidden" name="cardFrontPath"
								id="cardFrontPath" value="${cardFrontPath}" />
						</p>
						<p class="clearfix">
							<label> &nbsp; </label> <input type="hidden" name="cardBackPath"
								id="cardBackPath" value="${cardBackPath}" />
						</p>
						<c:if test="${!empty  msg }">
							<p class="clearfix">
								<label>&nbsp;</label> <span class="markRed">${PAGE_ERROR_MSG}</span>
							</p>
						</c:if>
						<p class="clearfix">
							<label>真实姓名：</label> <input type="text" class="ada-input"
								value="${realName}" style="width: 350px;" id="realName"
								name="realName">
						</p>
						<p class="clearfix">
							<label>身份证号：</label> <input type="text" class="ada-input"
								value="${cardNo}" style="width: 350px;" id="cardNo"
								name="cardNo">
						</p>
						<p class="clearfix">
							<label>到期时间：</label> 
							<input name="cardNoValid" value="${cardNoValid}" type="text"   style="width: 80px;" class="timeinput"  onfocus="WdatePicker({minDate:'%y-%M-{%d}'})" id="cardValid" readonly="readonly" />
						<!-- 	<input name="cardNoValid"  type="text" style="width: 80px;" class="timeinput" onclick="calendar(this)" id="cardNoValid" readonly="readonly" /> -->
						（注：到期时间留空代表长期有效）
						</p>
						<p class="clearfix">
							<label>国籍：</label> <span class="select_border fl"> <span
								class="select_cont fl"> <select name="country"
									id="country" class="select">
										<option value="中国">中国</option>
										<option value="中国香港">中国香港</option>
										<option value="中国台湾">中国台湾</option>
										<option value="中国澳门">中国澳门</option>
										<option value="日本">日本</option>
										<option value="泰国">泰国</option>
										<option value="澳大利亚">澳大利亚</option>
										<option value="马来西亚">马来西亚</option>
										<option value="新加坡">新加坡</option>
										<option value="英国">英国</option>
										<option value="美国">美国</option>
										<option value="加拿大">加拿大</option>
										<option value="其他国家">其他国家</option>
								</select>
							</span>
							</span>
						</p>
						<p class="clearfix">
							<label>职业：</label> <span class="select_border fl"> <span
								class="select_cont fl"> <select name="profession"
									id="profession" class="select">
										<option value="国家机关、组织、企事业单位负责人">国家机关、组织、企事业单位负责人</option>
										<option value="专业技术人员、办事人员和相关人员">专业技术人员、办事人员和相关人员</option>
										<option value="商业、服务业人员">商业、服务业人员</option>
										<option value="农/林/牧/渔/水利业生产人员">农/林/牧/渔/水利业生产人员</option>
										<option value="生产、运输设备操作人员及有关人员">生产、运输设备操作人员及有关人员</option>
										<option value="军人">军人</option>
										<option value="其他从业人员">其他从业人员</option>
								</select>
							</span>
							</span>
						</p>
						<p class="clearfix">
							<label>验证码：</label> <input class="ada-input" style="float: left;"
								type="text" name="randomCode" id="randomCode"
								value="${randomCode}" size="8" /> <label style="width: 100px;"><img
								alt="验证码" src="randomCode.jpg" onclick="changeCode(this);"
								height="26" width="90px"></label>
						</p>

					</form>
					<div class="ImgFrm980">
						<div class="ImgFrm">
							<div id="cardMsg" style="color: red; text-indent: 125px;">

							</div>
							<c:if test="${empty cardFrontPath}">
								<div id="form1Div">
									<form id="form1" method="post" enctype="multipart/form-data">
										<p class="clearfix">
											<label> 身份证正反面：</label><input id="cardFront1" type="file"
												name="file" onchange="uploadCardFront('cardFrontImg');" />
											<div id="message"></div>
										</p>
									</form>
								</div>
							</c:if>
							<c:if test="${empty cardBackPath}">
								<div id="form2Div">
									<form id="form2" method="post" enctype="multipart/form-data">
										<p class="clearfix">
											<label>&nbsp; </label><input id="cardFront2" type="file"
												name="file" onchange="uploadCardFront('cardBackImg');" />
										</p>
										<div id="message"></div>
									</form>
								</div>
							</c:if>
							<div class='clear'></div>

							<div class='cardFrontWrap'>
								<p class="clearfix">
									<label>&nbsp;</label>
								</p>
								<p class="clearfix">
								<div id="showCardFrontImg" class="showCardFrontImg">
									<a href="javascript:;" class="ClostImgBtn"
										onclick="deleteImg('cardFrontImg');"></a> <img
										id="cardFrontImg" alt="" src="${cardFrontPath}"
										class="imgsize">
								</div>
								</p>
								<div class='clear'></div>
							</div>

						</div>
						<div class='clear'></div>
						<p class="clearfix">
						<div class='backImgWrap'>
						<p class="clearfix">
									<label>&nbsp;</label>
								</p>
							<p class="clearfix">
							<div id="showCardBackImg" class="showCardFrontImg">
								<a href="javascript:;" class="ClostImgBtn"
									onclick="deleteImg('cardBackImg');"></a> <img id="cardBackImg"
									alt="" src="${cardBackPath}" class="imgsize">
							</div>
							</p>
							<div class='clear'></div>
						</div>
						</p>
					</div>

					<div class="h10"></div>
					<div class="clear"></div>
					<p class="clearfix">
						<label> &nbsp; </label> <input type="button" onclick="submitForm();" value="下一步" class="btn btn-primary">
					</p>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<script type="text/javascript">
		function uploadCardFront(ImgType) {
			$(function() {
				var options = {
					url : "realnameauth_uploadFile.action", //跳转到相应的Action  
					type : "POST",//提交方式  
					dataType : "json",//数据类型  
					contentType : "application/x-www-form-urlencoded;",
					success : function(result) {//调用Action后返回过来的数据  
						if (result.STATE != 'FAIL') {
							if (ImgType == "cardFrontImg") {
								  setTimeout(function() {
									  $(".cardFrontWrap").show();
						        $("#cardFrontImg").attr("src", result.IMGURL);
						        $("#cardFrontPath").val(result.IMGURL);
						        $("#form1Div").hide();
						        $("#cardMsg").html("");}, 500);
								
							} else if (ImgType == "cardBackImg") {
								 setTimeout(function() { 
									 $(".backImgWrap").show();
						       $("#cardBackImg").attr("src", result.IMGURL);
						       $("#cardBackPath").val(result.IMGURL);
						       $("#form2Div").hide();
						       $("#cardMsg").html("");}, 500);
							}
						} else {
							var msg = "";
							if (result.MSG == 1) {
								msg = "请选择文件";
							} else if (result.MSG == 2) {
								msg = "证件大小不超过2M";
							} else if (result.MSG == 3) {
								msg = "上传证件格式为bmp、jpg、jpeg";
							}
							$("#cardMsg").html(msg);
						}
					}
				};
				if (ImgType == "cardFrontImg") {
					$("#form1").ajaxSubmit(options);
				} else if (ImgType == "cardBackImg") {
					$("#form2").ajaxSubmit(options);
				}
				return false;
			});
		}
		
		function submitForm(){
			$("#subForm").submit();			
		}
		
		<%--
		var myDate = new Date();
	    var year=myDate.getFullYear();
	    var month=myDate.getMonth()+1;
	    var day=myDate.getDate();
		new YMDselect('year','month','day',year,month,day);
		--%>
		new YMDselect('year', 'month', 'day');
		document.getElementById("year").options.add(new Option('长期', '2999'));
	</script>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
