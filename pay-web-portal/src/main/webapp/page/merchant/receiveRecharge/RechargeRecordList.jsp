<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>充值记录查询</title>
	<%@include file="/page/include/headScript.jsp"%>
	<script type="text/javascript" src="<%=path%>/statics/js/common.js"></script>
	<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
	<script type="text/javascript">
	/*页面分类*/
	$(document).ready(function() { setPageType('.mer-receivable', '.mer-receivable-recharge'); })
	$(function(){
		//时间控制
		 selectDate('${timeType}');
	})
	</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	  <div class="container">
	  	<div class="bd-container">
				<form action="merchantReceiveRecharge_listReceiveRechargeRecord.action" id="form" method="post">
				  <div class="query-head mtop10">
	          <div class="title">充值记录查询</div>
	        </div>
				  <div class="query-conditions">
				  	<div class="frm-horizontal">
					   	<div class="frm-group">
								<label>&nbsp;</label>
								<input type="hidden" name="type" value="${type}" id="type">
							</div>
						  <div class="frm-group">
								<span style="display:none;" class="ada-wronginfos" id="msg"></span>
							</div>

					    <div class="frm-group">
						    <div class="content-left">
						    	<jsp:include page="/page/include/tradingTime.jsp"></jsp:include>
						    </div>
						     <label>商户订单号：</label><input type="text" name="merchantOrderNo"  value="${merchantOrderNo}">
					    </div>

						  <div class="frm-group clearfix">
					      <div class="content-left">
					    		<label>交易状态：</label>
			            <div class="select_border">
			              <div class="select_cont">
			                <select name="status" class="select input-w170">
												<option value="">所有</option>
												<c:forEach items="${orderStatusList}" var="orderStatus">
													<option value="${orderStatus.value}"
														<c:if test="${status==orderStatus.value}">selected="selected"</c:if>>${orderStatus.desc}</option>
												</c:forEach>
											</select>
										</div>
									</div>
		          	</div>
		          </div>

		          <div class="frm-group">
		            <label>&nbsp;</label>
		            <input class="btn btn-primary" type="button" onclick="checkData('form');" value="查 询" />
		          </div>
							<div class="clear"></div>
						</div>
					</div>
				  <div class="search-top">
					  <a id="exportExecl" href="merchantReceiveRecharge_exportReceiveRechargeToExcel.action?beginDate=${beginDate}&endDate=${endDate}&merchantOrderNo=${merchantOrderNo}&status=${status}"
					 onclick="return exportExcel();" >导出EXCEL
					 </a>
		  		</div>
		    	<div class="search-sesult">
		        <img class="png" src="<%=path%>/statics/themes/default/images/ico_total.png"/>
		        <strong class="fl">合计结果：</strong>
		        <p>交易金额合计：<span class="text-warning"> <fmt:formatNumber value="${empty countResultMap.orderSum?0.00:countResultMap.orderSum}" pattern="#0.00"></fmt:formatNumber></span> 元
		      </div>
					<div class="search_list">
						<table class="table table-hover" cellpadding="0" cellpadding="0">
						 <thead>
								<tr>
									<th>创建时间</th>
									<th>商户订单号</th>
									<th>交易时间</th>
									<th>交易金额(元)</th>
									<th>交易渠道</th>
									<th>交易状态</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="recordList" status="st">
									<tr>
										<td >
											<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td >${merchantOrderNo}</td>
										<td >
											<fmt:formatDate value="${orderTime}" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td class="text-warning" >
											<fmt:formatNumber value="${orderAmount}" pattern="#0.00"></fmt:formatNumber>
										</td>
										<td >${payWayName}</td>
										<td >
											<c:forEach items="${orderStatusList}" var="orderStatusEnum">
												<c:if test="${orderStatusEnum.value==status}">${orderStatusEnum.desc}</c:if>
											</c:forEach>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<c:if test="${pageBean.totalCount>0}">
							<div class="pageCla">
								<z:page pageBean="${pageBean }"
									url="merchantReceiveRecharge_listReceiveRechargeRecord.action"
									currentPage="${pageNum }"
									parameter="&beginDate=${beginDate }&endDate=${endDate}&merchantOrderNo=${merchantOrderNo}&status=${status}&timeType=${timeType}"></z:page>
							</div>
						</c:if>
					</div>
				</form>
			</div>
		</div>
		<br style="clear: both;" />
		<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
