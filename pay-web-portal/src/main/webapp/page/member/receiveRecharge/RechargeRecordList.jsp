<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>充值记录查询</title>
	<%@include file="/page/include/headScript.jsp"%>
	<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
	<script type="text/javascript">
	//页面加载时调用此函数给时间框赋值初始值当天
	$(document).ready(function(){ setPageType('.men-receivable', '.men-receivable-recharge ');});
	//时间控制
	$(function(){selectDate('${timeType}');})
	</script>
</head>
<body >
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<form action="memberReceiveRecharge_listReceiveRechargeRecord.action" id="form"
				method="post">
				<div class="query-head mtop10">
	        <div class="title">  充值记录查询</div>
	      </div>
	    	<div class="query-conditions">
			  	<div class="frm-horizontal">
				    <div class="frm-group">
				      <strong></strong>
					    <input type="hidden" name="type" value="${type}" id="type">
				    </div>
				    <div class="frm-group">
							 <span style="display:none;" class="ada-wronginfos" id="msg"></span>
						</div>

				    <div class="frm-group">
					    <div class="content-left">
					    	<jsp:include page="/page/include/tradingTime.jsp"></jsp:include>
					    </div>
					    <label>商户订单号：</label><input type="text" name="merchantOrderNo" value="${merchantOrderNo}"  />
					  </div>

				    <div class="frm-group clearfix">
					    <div class="content-left">
						    <label>交易状态：</label>
			          <div class="select_border">
			            <div class="select_cont">
			              <select name="status" class="select input-w170">
											<option value="">所有</option>
											<c:forEach items="${orderStatusList}" var="orderStatus">
												<option value="${orderStatus.value}" <c:if test="${status==orderStatus.value}">selected="selected"</c:if>>${orderStatus.desc}</option>
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
			 	  </div>
			  	<div class="clear"></div>
		 	  </div>
			  <div class="search-top">
		     <a  id="exportExecl" href="memberReceiveRecharge_exportReceiveRechargeToExcel.action?beginDate=${beginDate }&endDate=${endDate}&merchantOrderNo=${merchantOrderNo}&status=${status}" onclick="return exportExcel();" >导出EXCEL</a>
		    </div>

	    	<div class="search_list">
					<table class="table table-hover" cellpadding="0" cellpadding="0">
					  <thead>
							<tr >
								<th>创建时间</th>
								<th>商户订单号</th>
								<th>交易类型</th>
								<th>交易时间</th>
								<th>交易金额(元)</th>
								<th>交易渠道</th>
								<th>交易状态</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="recordList" status="st">
							<tr>
								<td ><fmt:formatDate
										value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td >${merchantOrderNo}</td>
								<td >充值</td>
								<td ><fmt:formatDate
										value="${orderTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td class='text-warning'><fmt:formatNumber
										value="${orderAmount}" pattern="#0.00"></fmt:formatNumber></td>

								<td >${payWayName}</td>
								<td >
									<c:forEach items="${orderStatusList}" var="v">
									<c:if test="${v.value==status}">${v.desc}</c:if>
									</c:forEach>
								</td>
							</tr>
						 </s:iterator>
						</tbody>
					</table>
					<c:if test="${pageBean.totalCount>0}">
						<div class="pageCla">
						<z:page pageBean="${pageBean }"
						url="memberReceiveRecharge_listReceiveRechargeRecord.action"
						currentPage="${pageNum }"
						parameter="&beginDate=${beginDate }&endDate=${endDate}&merchantOrderNo=${merchantOrderNo}&status=${status}&timeType=${timeType}">
						</z:page>
				   </div>
					</c:if>
				</div>
			</form>
	    <div class="clear"></div>
	  </div>
  </div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>




		<%--
			<p class="clearfix">
			<strong></strong>
			<input type="hidden" name="type" value="${type}" id="type">
			</p>

				<p class="ada-wronginfop">
			   <span style="display:none;" class="ada-wronginfos" id="msg"></span>
               </p>

				<p class="clearfix">..
					<strong>创建时间：</strong>
					<input class="timeinput"
						name="beginDate" value="${beginDate }" type="text"
						class="timeinput" onclick="calendar(this)" id="beginDate" />　至　<input class="timeinput"
						name="endDate" value="${endDate}" type="text" class="timeinput"
						onclick="calendar(this)" id="endDate" />
						&nbsp; &nbsp; &nbsp;<a href="#" id="a_Date_1" onclick="selectDate('toDay',this)">今天</a>
						&nbsp; <a href="#" id="a_Date_3"
						onclick="selectDate('currentMonth',this)" class="Fcurrent">本月</a>
						&nbsp; <a href="#" id="a_Date_2" onclick="selectDate('lastMonth',this)">上月</a>
				</p>
				<p class="clearfix">
					<strong>交易流水号：</strong>

						<input type="text" name="trxNo" value="${trxNo}" style="float:left" />
					<strong>交易状态：</strong>
					<select name="status">
							<option value="">所有</option>
							<c:forEach items="${orderStatusList}" var="orderStatus">
								<option value="${orderStatus.value}" <c:if test="${status==orderStatus.value}">selected="selected"</c:if>>${orderStatus.desc}</option>
							</c:forEach>
					</select>
				</p>
				<p class="clearfix">
				<strong> &nbsp; </strong>
				<span class="commonBtn"><span class="btn_lfRed">
					<input class="btn_rtRed" type="button" onclick="checkData();" value="查 询" />
					</span></span>
					<a  id="exButton" href="memberSourceRecharge_exportSourceRechargeToExcel.action?beginDate=${beginDate }&endDate=${endDate}&trxNo=${trxNo}&status=${status}" onclick="return exportExcel();" class="exportBtn">导出EXCEL</a>
				</p>
			<div class="clear"></div>
			</div>


			<div class="clear"></div>
			<div class="h10"></div>
	        <div class="search_list">
			<table class="tbl" cellpadding="0" cellpadding="0">
				<tr >
					<th>交易流水号</th>
					<th>创建时间</th>
					<th>交易类型</th>
					<th>交易时间</th>
					<th>交易金额</th>
					<th>交易渠道</th>
					<th>交易状态</th>
				</tr>

				<s:iterator value="recordList" status="st">
					<tr>
						<td class="Form_Content">${trxNo}</td>
						<td class="Form_Content"><fmt:formatDate
								value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td class="Form_Content">充值</td>
						<td class="Form_Content"><fmt:formatDate
								value="${paymentTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td class="Form_Content"><fmt:formatNumber
								value="${payAmount}" pattern="#0.00"></fmt:formatNumber></td>

						<td class="Form_Content">${bankName}</td>
						<td class="Form_Content">
							<c:forEach items="${orderStatusList}" var="v">
							<c:if test="${v.value==status}">${v.desc}</c:if>
							</c:forEach>
						</td>
					</tr>
				</s:iterator>
			</table>
			<c:if test="${pageBean.totalCount>0}">
			<div class="pageCla">
				<z:page pageBean="${pageBean }"
					url="memberSourceRecharge_listSourceRechargeRecord.action"
					currentPage="${pageNum }"
					parameter="&beginDate=${beginDate }&endDate=${endDate}&trxNo=${trxNo}&status=${status}"></z:page>
			</div>
			</c:if>
		</form>
		<br style="clear: both;" />

	  <div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
 --%>
