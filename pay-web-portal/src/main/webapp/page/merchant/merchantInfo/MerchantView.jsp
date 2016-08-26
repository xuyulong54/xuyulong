<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>商户信息</title>
			<%@include file="/page/include/headScript.jsp" %>
				<script type="text/javascript" src="<%=path%>/statics/cfca/js/SignCertManager.js"></script>
				<script type="text/javascript">
				/*页面分类*/
				$(document).ready(function() {setPageType('.mer-account', '.mer-account-bsinfo ');  })

	/*			function initMerchantInfo() {
					var tablename = document.getElementById("tbl");
					var li = tablename.getElementsByTagName("tr");
					for (var i = 0; i < li.length; i++) {
						if (i % 2 == 0) {
							li[i].style.backgroundColor = "#fafafa";
						} else
							li[i].style.backgroundColor = "#FFFFFF";
					}
				}*/


				</script>

		</head>

		<body>
			<!-- 用户加载证书控件的用的容器 -->
			<div id="FakeCryptoAgent"></div>
			<%-- top menu include --%>
				<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
				<div class="pageContent">
					<%-- page content --%>
						<div class="showinfo">
						<div class="headline">
							<div class="title">商户基本信息</div>
						</div>
							<div class="info-box">
								<table cellpadding="0" cellspacing="0" class="tbls" id="tbl">
									<tr>
										<th>账户名：</th>
										<td>${currentUser.loginName}</td>
									</tr>
									<tr>
										<th>商户状态：</th>
										<td>
											<c:forEach items="${MerchantOnlineStatusEnumList}" var="merchantStatus">
												<c:if test="${merchantStatus.value==bean.status}">${merchantStatus.desc}</c:if>
											</c:forEach>
										</td>
									</tr>
									<!-- 审核不通过时，显示“审核结果说明” -->
									<c:if test="${bean.status==MerchantOnlineStatusEnum.NOPASS.value}">
										<tr>
											<th>审核结果说明：</th>
											<td>${MerchantOnlineStatusEnum.NOPASS.desc}</td>
										</tr>
									</c:if>
									<tr>
										<th>商户编号：</th>
										<td>${bean.merchantNo}</td>
									</tr>
									<tr>
										<th>签约属性：</th>
										<td>
											<c:forEach items="${MerchantTypeEnumList}" var="v">
												<c:if test="${bean.merchantType == v.value}">${v.desc}</c:if>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<th>签约名：</th>
										<td>${bean.fullName}</td>
									</tr>
									<c:if test="${bean.merchantType==MerchantTypeEnum.MEMBER.value }">
										<tr>
											<th>身份证号：</th>
											<td>${bean.cardNo }</td>
										</tr>
									</c:if>
									<c:if test="${bean.merchantType!=MerchantTypeEnum.MEMBER.value }">
										<tr>
											<th>商户简称：</th>
											<td>${bean.shortName}</td>
										</tr>
										<tr>
											<th>营业执照号：</th>
											<td>${bean.licenseNo}</td>
										</tr>
										<tr>
											<th>企业法人姓名：</th>
											<td>${bean.legalPerson}</td>
										</tr>
									</c:if>
									<tr>
										<th>网站地址：</th>
										<td>${bean.url }</td>
									</tr>
									<tr>
										<th>经营范围：</th>
										<td>${bean.scope }</td>
									</tr>
									<tr>
										<th>ICP证备案号：</th>
										<td>${bean.icp}</td>
									</tr>
									<tr>
										<th>联系人姓名：</th>
										<td>${bean.busiContactName}</td>
									</tr>
									<tr>
										<th>联系人手机：</th>
										<td>${bean.busiContactMobileNo}</td>
									</tr>
									<tr>
										<th>通信地址：</th>
										<td>${showaddress}</td>
									</tr>
									<%--操作员必须为“管理员” --%>
									<c:if test="${userOperator.type==UserOperatorTypeEnum.ADMIN.value}">
									<%--状态为“激活”、“审核不通过”、“注册中”时可以对信息进行修改--%>
									<c:if test="${bean.status==MerchantOnlineStatusEnum.ACTIVE.value
												|| bean.status == MerchantOnlineStatusEnum.NOPASS.value
												|| bean.status == MerchantOnlineStatusEnum.SIGNING.value}">
										<tr>
										<th></th>
											<td>
												<input class="btn btn-primary" id="Button7" type="button" value="修 改" onclick="window.location.href='merchantinfo_editMerchantUI.action'" />
											</td>
										</tr>
									</c:if>
									</c:if>
								</table>
							</div>
							<div class="clear" style="float:left;margin-left: 50px;"></div>
						</div>
						<div class="clear"></div>
				</div>
				<jsp:include page="../../foot.jsp" />
		</body>

		</html>
