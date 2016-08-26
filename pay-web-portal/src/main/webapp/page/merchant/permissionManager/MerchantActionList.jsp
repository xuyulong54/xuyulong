<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>

		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>权限列表</title>
			<%@include file="/page/include/headScript.jsp" %>
		</head>

		<body>
			<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>

			<div id="tabs1">
				<jsp:include page="/page/merchant/permissionManager/PmsMenu.jsp"></jsp:include>
				<div class="main1box">
					<div class="main" id="main1">
						<ul class="block">
							<li>
								<form action="permission_listMerchantAction.action" method="post">
									<z:permission value="pms:action:view">
										<div class="searchterms">
											<div class="ada-wrap">
												<p class="clearfix" style="margin-top:10px;">
													<strong> 权限名：</strong>
													<input type="text" name="actionName" alt="精确查询" value="${actionName}" style="float:left;" />

													<strong style="margin-left: 10px;"> <input type="submit" class="btn_login" value="查 询" />
										</strong>
													<%-- <z:permission value="pms:action:edit">
														<a href="permission_addMerchantActionUI.action">添加权限</a>
									</z:permission>--%>
									</p>
									<div class="clear"></div>
									</div>
									</div>
									</z:permission>
									<div style="color:red; height:5px; line-height: 20px;">
										${msg }</div>
								</form>
								<table class="table table-border">
								<thead>
									<tr >
										<td>权限名</td>
										<!-- <td>权限</td> -->
										<!-- <td>备注</td> -->
										<!-- <td>创建时间</td> -->
										<!-- <td>操作</td> -->
									</tr>
									</thead>
									<tbody>
									<s:iterator value="recordList" status="st">
										<tr>
											<td>${actionName }</td>
											<%-- <td>${action }</td>--%>
											<%-- <td>${remark}</td>
													<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
													</td>
													<td>
														<z:permission value="pms:action:edit">
															<a href="permission_editMerchantActionUI.action?id=${id}">修改</a>
														</z:permission>&nbsp;
														<z:permission value="pms:action:delete">
															<a href="permission_deleteMerchantAction.action?id=${id}">删除</a>
														</z:permission>
													</td>--%>
										</tr>
									</s:iterator>
									</tbody>
								</table> <span class="message">${idMsg}${objMsg}</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<c:if test="${pageBean.totalCount>0}">
				<div class="pageCla">
					<z:page pageBean="${pageBean }" url="permission_listMerchantAction.action" currentPage="${pageNum }" parameter="&actionName=${actionName}"></z:page>
				</div>
			</c:if>
			<div class="ht1"></div>
			<jsp:include page="../../foot.jsp" />
		</body>

		</html>
		<script type="text/javascript">
/*页面分类*/
 $(document).ready(function() { setPageType('.mer-system', '.mer-system-operator '); })
 </script>
