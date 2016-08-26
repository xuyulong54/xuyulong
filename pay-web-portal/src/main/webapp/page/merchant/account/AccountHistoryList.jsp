<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>

		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>商户账户明细</title>
			<%@include file="/page/include/headScript.jsp" %>
			<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
			<script type="text/javascript">

				/*页面分类*/
				 $(document).ready(function() { setPageType('.mer-account', '.mer-account-search'); })
				//时间控制
				 $(function(){selectDate('${timeType}');})
			</script>
		</head>

		<body onload="getNowDay()">
			<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
			<div class="container">
				<div class="bd-container">
					<form action="merchantaccount_listAccountHistory.action" method="post" id="form">
					  <div class="query-head mtop10">
	            <div class="title">
	               账户明细查询</div>
	        	</div>
					 	<div class="query-conditions">
					    <div class="frm-horizontal">
								<div class="frm-group">
									<label>&nbsp;</label>
									<span style="display:none;" class="ada-wronginfos" id="msg"></span>
								</div>
								<div class="frm-group">
							    <div class="content-left">
							    	<jsp:include page="/page/include/tradingTime.jsp"></jsp:include>
							    </div>
						    	<label>收入/支出：</label>
						    	<div class="select_border">
				            <div class="select_cont">
											<select name="fundDirection" class="select input-w170">
												<option value="">所有</option>
												<option value="123" <c:if test="${fundDirection == 123}">selected="selected"</c:if>>收入</option>
												<option value="321" <c:if test="${fundDirection == 321}">selected="selected"</c:if>>支出</option>
											</select>
										</div>
									</div>
							  </div>
								<div class="frm-group">
						    	<div class="content-left">
							   	  <label>交易流水号：</label>
										<input name="requestNo" value="${requestNo}" type="text" size="28"/>
						    	</div>
						    	<label>交易类型：</label>
					    	  <div class="select_border">
			              <div class="select_cont">
											<select name="trxType" class="select input-w170">
												<option value="">所有</option>
												<c:forEach items="${trxTypeList }" var="trxTypeEnum">
													<option value="${trxTypeEnum.value }" <c:if test="${trxTypeEnum.value ==trxType}">selected="selected"</c:if>>${trxTypeEnum.desc }
													</option>
												</c:forEach>
											</select>
										</div>
									</div>
						    </div>
						    <div class="frm-group">
				          <label>&nbsp;</label>
			            <input id="Button1" class="btn btn-primary" type="button" onclick="checkData('form');"   value="查 询" />
				        </div>
								<div class="clear"></div>
							</div>
						</div>
						<div class="search-top">
		   				<a id="exButton"  href="merchantaccount_exportExcel.action?beginDate=${beginDate }&endDate=${endDate}&fundDirection=${fundDirection}&trxType=${trxType}&requestNo=${requestNo}"
							onclick="return exportExcel();">导出EXCEL
							</a>
		    		</div>
					</form>
					<div class="search_list">
						<table class="table table-hover" cellpadding="0" cellpadding="0">
						 <thead>
							<tr>
								<th>创建时间</th>
								<th>交易流水号</th>
								<th>交易金额(元)</th>
								<th>账户余额(元)</th>
								<th>交易类型</th>
								<th>描述</th>
							</tr>
							</thead>
							<tbody>
							<s:iterator value="recordList" status="st">
								<tr>
									<td>
										<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>${requestNo}</td>
									<td class="text-warning">${fundDirection==123?'+':'-'}
										<fmt:formatNumber value="${amount}" pattern="#0.000000" />
									</td>
									<td class="text-success">
										<fmt:formatNumber value="${balance}" pattern="#0.000000" />
									</td>
									<td>
										<c:forEach items="${trxTypeList}" var="trxTypeMap">
											<c:if test="${trxType eq trxTypeMap.value}">${trxTypeMap.desc}</c:if>
										</c:forEach>
									</td>
									<td>${remark}</td>
								</tr>
							</s:iterator>
							</tbody>
						</table>
						<c:if test="${pageBean.totalCount>0}">
							<div class="pageCla">
								<z:page pageBean="${pageBean }" url="merchantaccount_listAccountHistory.action" currentPage="${pageNum }" parameter="&beginDate=${beginDate }&endDate=${endDate}&fundDirection=${fundDirection}&requestNo=${requestNo}&trxType=${trxType}&timeType=${timeType}"></z:page>
							</div>
						</c:if>
						<br style="clear: both;" />
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<jsp:include page="../../foot.jsp" />
		</body>
	</html>
