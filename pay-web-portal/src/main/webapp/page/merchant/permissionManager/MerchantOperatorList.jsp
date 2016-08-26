<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>

		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>操作员列表</title>
			<%@include file="/page/include/headScript.jsp" %>
				<script type="text/javascript" src="<%=path%>/statics/js/common.js"></script>
				<script>
					$(document).ready(function () {
					setPageType('.mer-system', '.mer-system-operator '); /*页面分类*/
					setSecondNav('.secondNav2');
					$('.pull').bind('click',function(){
					if($('.pWrap').css('display')=='block'){
					$(this).attr('src','<%=path %>/statics/themes/default/images/ada-icon1.gif');
					$('.pWrap').hide();
					}
					else{
					$(this).attr('src','<%=path %>/statics/themes/default/images/ada-icon2.gif');
					$('.pWrap').show();
					}
					});

					})
				</script>


		<script type="text/javascript">
			var ts = "<%=System.currentTimeMillis()%>";
			$(function () {
				validatorFrom();
			});

			function submitForm() {
			    var text = "";
		        document.getElementById("errorMsg").innerHTML=text;
		        var textWarning= document.getElementById("textWarning");
		        if (textWarning!=null){
		          textWarning .innerHTML=text
		        }
				var password = funGetPassword();
				if (password == null) {
					return;
				}
				var rolestr = "";
				var operstr = "";
				$("input[name='selectRole']:checked").each(function () {
					rolestr += $(this).attr("id");
					rolestr += ",";
				});

				$("input[name ='operId']:checked").each(function () {
					operstr += $(this).attr("id");
					operstr += ",";
				});
				$("#selectVal").val(rolestr);
				$("#selectOperatorVal").val(operstr);
// 				$("#form1").submit();
				subForm();
			}

			function checkAll() {
				var items = document.getElementsByName("operId");
				var itemall = document.getElementById("all");
				var len = items.length;
				for (var i = 0; i < len; i++) {
					items[i].checked = itemall.checked;
				}
			}

			function validatorFrom() {
				$.formValidator.initConfig({
					formID: "form1",
					submitOnce: "true",
					theme: "ArrowSolidBox",
					mode: "AutoTip",
					onError: function (msg) {
// 						alert(msg)
					},
					inIframe: true
				});
				$(":checkbox[name='operId']").formValidator({
				    tipID:"checktTip",
					onShow: "",
					onFocus: "请选择角色"
				}).inputValidator({
					min: 1,
					onError: "请选择角色"
				});
 				$(":checkbox[name='selectRole']").formValidator({
 				    tipID:"testTip",
					onShow: "",
					onFocus: "请选择角色"
				}).inputValidator({
					min: 1,
					onError: "请选择角色"
				});
				$.formValidator.reloadAutoTip();
			};
			 //弹出隐藏层
			function ChangeDiv(show_div, operationType, id, loginName, status) {

			    showAlert(show_div,'.popupMask');//弹出层。
				centerMask('.popupMask',show_div);//居中弹出层
				$("#operationType").val(operationType);
				$("#id").val(id);
				$("#status").val(status);
				if (operationType == "restLoginPwd") {
					$("#hint-contents").html("您确定要重置用户名为" + loginName + "的登录密码么？");
				} else {
					var statusVar = (status == 101) ? "冻结" : "激活";
					$("#hint-contents").html("您确定要" + statusVar + "用户名为" + loginName + "的操作员么？");
				}

			}

			 //重置密码,激活冻结操作员
			function editOperate() {
				var operationType = $("#operationType").val();
				var id = $("#id").val();
				var status = $("#status").val();
				<c:choose>
				<c:when test="${USE_KEYBOARD }">
				var password02 = getPassInput("powerpass02", ts, "errorMsg", "密码输入错误：");
				</c:when>
				<c:otherwise>
				var password02 = $("#password02").val();
				</c:otherwise>
				</c:choose>
				if (password02 != null) {
					var actionUrl = "permission_resetMerchantOperatorLoginPwd.action";
					if (operationType != "restLoginPwd") {
						actionUrl = "permission_updateMerchantOperatorStatus.action";
					}
					$.post(actionUrl, {
						id: id,
						tradePwd: password02,
						status: status
					}, function (res) {
						if (res.STATE != "FAIL") {
							$("#hint-contents").html(res.MSG);
							$("#p-tradePwds").remove();
							$("#p-errorMsgs").remove();
							$("#btn1").click(refreshPage);
							$("#btn2").remove();
						} else {
							$("#errorMsgs").html(res.MSG);
						}
					}, "json");
				}
			}
			 //刷新页面
			var refreshPage = function () {
			window.location.href="permission_listMerchantOperator.action";
					//window.location.reload();
				}
				//关闭弹出层

			function CloseDownDiv(show_div) {
				hideAlert(show_div,'.popupMask');//关闭弹出层。
				document.getElementById("powerpass02").clear();
			}
		</script>
</head>
		<body>
		<div id="FakeCryptoAgent"></div>
			<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
<div class="frm-comon">
			<div class="container">
             <div class="bd-container">
				<jsp:include page="/page/merchant/permissionManager/PmsMenu.jsp"></jsp:include>
					<form action="permission_listMerchantOperator.action" method="post">
					<div class="query-conditions query-reset">
					 <div class="frm-horizontal">
						<z:permission value="Pms:Operator:View">
							<div class="frm-group">
			   					<div class="content-left">
			   						 <label>姓名：</label>
			   						 	<input type="text" name="realName" title="模糊查询" value="${realName}" />
			  				    </div>
	                            <label>登录名：</label>
                                 <input type="text" name="loginName" title="精確查询" value="${loginName}"/>
			  				</div>

								<div class="frm-group">
			   					<div class="content-left">
			   					<label> &nbsp; </label>
			   					<input type="submit" class="btn btn-primary " value="查 询"  />
                                  <z:permission value="Pms:Role:Operation">
                               <a  class="link-color mleft10" href="permission_addMerchantOperatorUI.action">添加操作员
												</a>
										</z:permission>
			   					</div>
			   					</div>

								<div class="clear"></div>
						</z:permission>
                       </div>
                       </div>
					</form>
					<form action="permission_editMerchantOperatorsRole.action" id="form1" method="post">
						<table class="table table-border mbt10">
						<thead>
							<tr>
								<z:permission value="Pms:Role:Operation">
									<th style="width:26px">
										<input type="checkBox" name="all" id="all" onClick="checkAll()" />
										<input type="hidden" name="operIds" />
									</th>
								</z:permission>
								<th>创建时间</th>
								<th>登录名</th>
								<th>姓名</th>
								<th>手机号码</th>
								<th>状态</th>
								<th>类型</th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody>
							<s:iterator value="recordList" status="st">
								<tr>
									<z:permission value="Pms:Role:Operation">
										<td>
											<c:if test="${type==UserOperatorTypeEnum.USER.value }">
												<input type="checkBox" name="operId" id="${id}" />
											</c:if>
										</td>
									</z:permission>
									<td>&nbsp;<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>&nbsp;${loginName}</td>
									<td>&nbsp;${realName}</td>
									<td>&nbsp;${mobileNo}</td>
									<td>&nbsp;
										<c:forEach items="${operatorStatuslist}" var="operatorStatus">
											<c:if test="${status==operatorStatus.value}">${operatorStatus.desc}</c:if>
										</c:forEach>
										<td>&nbsp;
											<c:forEach items="${operatorTypeList}" var="operatorType">
												<c:if test="${type==operatorType.value}">${operatorType.desc}</c:if>
											</c:forEach>
										</td>
										<td>
											<z:permission value="Pms:Operator:Operation">
												<c:if test="${type==UserOperatorTypeEnum.USER.value }">
													<a class="link-color mleft10" href="permission_editMerchantOperatorUI.action?id=${id}">修改</a>
												</c:if>
											</z:permission>
											<z:permission value="Pms:Operator:Operation">
												<c:if test="${type==UserOperatorTypeEnum.USER.value }">
													<a href="javascript:;" class="link-color mleft10" onclick="ChangeDiv('#MyDiv2','restLoginPwd','${id}','${loginName}')">重置密码</a>
												</c:if>
											</z:permission>
											<c:if test="${status==OperatorStatusEnum.ACTIVE.value}">
												<c:if test="${type==UserOperatorTypeEnum.USER.value}">
													<z:permission value="Pms:Operator:Operation">
														<a href="javascript:;" class="link-color mleft10" title="点击冻结" onclick="ChangeDiv('#MyDiv2','updateStatus','${id}','${loginName}',101)">冻结</a>
													</z:permission>
												</c:if>
											</c:if>
											<c:if test="${status==OperatorStatusEnum.INACTIVE.value}">
												<c:if test="${type==UserOperatorTypeEnum.USER.value}">
													<z:permission value="Pms:Operator:Operation">
														<a href="javascript:;" class="link-color mleft10" title="点击激活" onclick="ChangeDiv('#MyDiv2','updateStatus','${id}','${loginName}',100)">激活</a>
													</z:permission>
												</c:if>
											</c:if>
										</td>
								</tr>
							</s:iterator>
							</tbody>
						</table>
						<div id="checktTip" style="float:left;line-height:25px; clear:both"></div>
						<div class="clear"></div>
						<h3 class='addRole clearfix'>批量给操作员赋角色</h3>
				<div class='operatorAccounts'>
					<h4>
						<i class="iconfont" style="color:#e60707;font-size:20px;">&#xe600;</i>说明
						<img src="<%=path%>/statics/themes/default/images/ada-icon1.gif"
							class='pull' />
					</h4>
					<div class='pWrap'>
						<p style="margin-top:15px;">
							<strong>使用场景</strong>如果您有多个操作员，有多个角色。
						<p class='clear'></p>
						</p>
						<p>
							<strong>作用</strong>为了快速给操作员赋予指定角色。
						<p class='clear'></p>
						</p>
						<p style="margin-bottom:15px;">
							<strong>操作说明</strong>只需勾选操作员后，再勾选需要赋予的角色后，输入支付密码验证身份即可
						<p class='clear'></p>
						</p>
					</div>
				</div>


				<div class="searchterms1 operator-box clearfix">
							<div class="contentW2">
								<z:permission value="Pms:Role:Operation">
									<input type="hidden" name="selectVal" id="selectVal" />
									<!-- 角色 -->
									<input type="hidden" name="selectOperatorVal" id="selectOperatorVal" />
									<!-- 操作员 -->

							  <div class="leftbox">分配角色：</div>
									<div class="rightcont">
										<c:if test="${hasRole eq 'false' }">
                    <span style="color: red;">没有可分配的角色，请先
                    <a class="link-color mleft10" href="permission_addMerchantRoleUI.action">添加角色</a></span></c:if>
										<s:iterator value="rolesList" status="st">
											<c:if test="${id!=0}">
												<label style='float:left;'>
													<input class="check" type="checkbox" name="selectRole" id="${id}" >&nbsp;&nbsp;${roleName}</label>
											</c:if>
										</s:iterator>
										<span id="testTip" style="float:left;line-height:25px;"></span>
									</div>

									<p class="clearfix">
									<div class="operW">
										<div id="tt"></div>
										<jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>
									</div>
                  </p>
                  <c:if test="${!empty msg }">
                  <p class="clearfix">
                   <label for="">&nbsp;</label>
                  	<span id="textWarning" class="markRed"> ${msg} </span>
                	</p>
                  </c:if>
									<p class="clearfix">
											<input  type="button" value="提 交" class="btn btn-primary fl" onclick="submitForm();" style="margin-left: 166px;">
										</p>

									</div>
								</z:permission>

								<c:if test="${pageBean.totalCount>0}">
									<div class="pageCla text-right" style="padding-right: 20px;">
										<z:page pageBean="${pageBean }" url="permission_listMerchantOperator.action" currentPage="${pageNum }" parameter="&realName=${realName}&loginName=${loginName}"></z:page>
									</div>
								</c:if>

								<div class="clear"></div>
							</div>
							</form>
							<div class="clear"></div>
						</div>


					<div class="clear"></div>
				</div>
				</div>
</div>
			</div>
			<div class="popupMask"></div>
			<div id="MyDiv2" class="popupBox">
				<input type="hidden" id="id">
				<input type="hidden" id="operationType">
				<input type="hidden" id="status">
				<div class="top">
					<a href="#" class="btnClost" onclick="CloseDownDiv('#MyDiv2');"></a>
				</div>
				<div class="cont">
			<div class="left-icon">
				<i class="iconfont">&#xe600;</i>
			</div>
			<div class="right-box">
         <h3 id="hint-title">温馨提示：</h3>
         <p id="p-hint">

				<font id="hint-contents" class="font"></font>
			</p>

         </div>

     <p id="p-tradePwds" style="text-align: left;">
						<strong>支付密码：</strong>
						<c:choose>
							<c:when test="${USE_KEYBOARD }">
								<input type="hidden" name="tradePwd" id="password02"
									class="password" maxlength="20" />
								<script type="text/javascript">
									writePassObject("powerpass02", {
										"width" : 173,
										"height" : 35,
										" margin-top" : 10,
										"accepts" : "[:graph:]+",
										"x" : -50,
										"maxlength" : 20
									});
								</script>
							</c:when>
							<c:otherwise>
								<input type="password" name="tradePwd" id="password02"
									class="password" maxlength="20" />
							</c:otherwise>
						</c:choose>
					</p>
					<p id="p-errorMsgs"> <strong> &nbsp; </strong><span id="errorMsgs" class="font markRed"></span>
					</p>
					<div class="btnDiv">
						<strong> &nbsp; </strong>
						<input class="submitBtn" id="btn1" type="button" value="确 定" onclick="editOperate();" />
						<input class="cancelBtn" style=" margin-left:15px;" id="btn2" type="button" value="取 消" onclick="CloseDownDiv('#MyDiv2');" />
						<div class="clear"></div>
					</div>
					<div class="clear">
					</div>
				</div>
			</div>
			<div class="clear"></div>
			</div>

			<jsp:include page="../../foot.jsp" />
		</body>

		</html>
