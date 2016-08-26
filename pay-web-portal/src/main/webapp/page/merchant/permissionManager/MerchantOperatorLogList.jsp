<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
		<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>日志列表</title>
			<%@include file="/page/include/headScript.jsp" %>
			<script type="text/javascript" src="<%=path%>/statics/js/common.js"></script>
			<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
		</head>
	<script type="text/javascript">
		 /*页面分类*/
		 $(document).ready(function() {
		  	setPageType('.mer-system', '.mer-system-operator ');
		  	setSecondNav('.secondNav3');
		  })
		  //时间控制
		$(function(){selectDate('${timeType}');})
			$(function () {
				if ('${proMap.operatorName}' == "" || '${proMap.operatorName}' == null) {
					$("#operatorName").val('${userOperator.loginName}');
				}
				getNowDay();
			})

			function submitForm() {
				var operatorName = $("#operatorName").val();
				if (operatorName == null || operatorName == "") {
					$("#msg").html("请输入操作员登录名");
				} else {
					var form = document.getElementById("form");
					form.submit();
				}
			}
	</script>
		<body>
			<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
			<div class="container">
				<div class="bd-container">
					<jsp:include page="/page/merchant/permissionManager/PmsMenu.jsp"></jsp:include>
					<form action="permission_listMerchantOperateLog.action" method="post" id="form">
						<div class="query-conditions query-reset">
						  <div class="frm-horizontal">
								<div class="frm-group">
									<label></label> <span id="msg" class="msg"> </span>
								</div>
								<div class="frm-group">
		   					  <div class="content-left">
		   						  <jsp:include page="/page/include/tradingTime.jsp"></jsp:include>
		  				    </div>
		              <label>操作员登录名：</label>
	                <input class="input-w170" type="text" name="operatorName" id="operatorName"  value="${proMap.operatorName}" size="15" alt="精确查询"/>
				  			</div>
						    <div class="frm-group">
									<div class="content-left">
										<label>IP地址：</label>
										<input class="input-w170" type="text" name="ip" value="${proMap.ip}" size="15" alt="精确查询" />
									</div>
									<label>操作状态：</label>
									<div  class="select_border">
	                  <div class="select_cont">
											<select name="operateStatus" class="selectW select "  id="status">
												<option value="">-请选择-</option>
												<option value="100" <c:if test="${proMap.operateStatus eq 100}">selected='selected'</c:if>>成功</option>
												<option value="101" <c:if test="${proMap.operateStatus eq 101}">selected='selected'</c:if>>失败</option>
											</select>
										</div>
									</div>
						    </div>
						    <z:permission value="Pms:OperatorLog:View">
						    <div class="frm-group">
									<label> &nbsp; </label>
								 	<input type="button" value="查 询" onclick="checkData('form');" class="btn btn-primary" />
								</div>
	              </z:permission>
								<div class="clear"></div>
							</div>
						</div>
					</form>
					<div class="h10"></div>
					<table class="table table-border">
            <thead>
							<tr >
								<th>序号</th>
								<th class="spacel-tdW">创建时间</th>
								<th>操作员</th>
								<th>操作内容</th>
								<th>操作状态</th>
								<th>IP地址</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="recordList" status="st">
								<tr target="sid_user" rel="${id}">
									<td>${st.index+1}</td>
									<td>
										<fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>${loginName }</td>
									<td>
										<c:choose>
											<c:when test="${fn:length(content) > 50}">
												${fn:substring(content, 0, 50) }...
											</c:when>
											<c:otherwise>${content}</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${operateStatus eq 100}">成功</c:when>
											<c:when test="${operateStatus eq 101}">失败</c:when>
											<c:otherwise>--</c:otherwise>
										</c:choose>
									</td>
									<td>${ip }</td>
									<td>
										<z:permission value="Pms:OperatorLog:View">
											[<a class="link-color" href="permission_viewMerchantOperateLog.action?id=${id }" target="dialog" rel="input" title="查看操作员操作日志详情"><span>查看详情</span> </a>]
										</z:permission>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<div class="clear"></div>
				</div>

				<c:if test="${pageBean.totalCount>0}">
					<div class="pageCla">
						<z:page pageBean="${pageBean }" url="permission_listMerchantOperateLog.action" currentPage="${pageNum}" parameter="&operatorName=${proMap.operatorName}&operateType=${proMap.operateType}&beginDate=${proMap.beginDate}&endDate=${proMap.endDate}&status=${proMap.status}&timeType=${timeType}"></z:page>
					</div>
				</c:if>
			</div>
				<div class="clear"></div>
			</div>
			<jsp:include page="../../foot.jsp" />
		</body>

</html>
