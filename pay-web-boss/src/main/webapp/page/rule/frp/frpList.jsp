<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="frp_listFrp.action" method="post">
	 <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>银行名称：</label>
					<input type="text" name="bankName" value="${param.bankName}" size="30" alt="模糊查询"  />
				</td>
				<td>
					<label>银行编号：</label>
					<input type="text" name="bankCode" value="${param.bankCode}" size="30" alt="精确查询"  />
				</td>
				<td>
					<label>支付渠道编号：</label>
					<input type="text" name="frpCode" value="${param.frpCode}" size="30" alt="精确查询"  />
				</td>
			</tr>
			<tr>
				<td>
					<label>类型：</label>
					
					<select name="busType" id="busType">
						<option value="">请选择</option>
						<c:forEach items="${bankBusTypeEnumList }" var="bankBusType">
							<option value="${bankBusType.value}" <c:if test="${busType eq bankBusType.value }">selected="selected"</c:if>>${bankBusType.desc}</option>
						</c:forEach>
					</select>
					
				</td>
				<td>
					<label>状态：</label>
					
					<select name="status">
						<option value="">请选择</option>
						<option value="100" <c:if test="${status eq 100 }">selected="selected"</c:if> >有效</option>
						<option value="101" <c:if test="${status eq 101 }">selected="selected"</c:if> >无效</option>
					</select>
					
				</td>
				<td>
					<label>支付类型：</label>
					
					<select name="payType">
						<option value="">请选择</option>
						<c:forEach items="${payTypeEnum }" var="item">
							<option value="${item.value }" <c:if test="${payType eq item.value }">selected="selected"</c:if>>${item.desc }</option>
						</c:forEach>
					</select>
					
				</td>
				<td>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">

	<div class="panelBar">
		<z:permission value="bank:frp:add">
		<ul class="toolBar">
			<li><a class="add" href="frp_addFrpUI.action" target="dialog" width="550" height="350" rel="addFrp" title="新增渠道"><span>新增支付渠道</span></a></li>
		</ul>
		</z:permission>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th>序号</th>
				<th>银行名称</th>
				<th>银行编号</th>
				<th>业务类型</th>
				<th>支付类型</th>
				<th>支付渠道编号</th>
				<th>状态</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
				    <td>${bankName }</td>
				    <td>${bankCode }</td>
				    <td>
				    	<c:forEach items="${bankBusTypeEnumList }" var="bankBusType">
							<c:if test="${busType eq bankBusType.value }">${bankBusType.desc}</c:if>
						</c:forEach>
				    </td>
				    <td>
				    	<c:forEach items="${payTypeEnum }" var="item">
							<c:if test="${payType eq item.value }">${item.desc }</c:if>
						</c:forEach>
				    </td>
				    <td>${frpCode }</td>
					<td>
						  <s:if test="status==100">有效</s:if>
						  <s:elseif test="status==101">无效</s:elseif>
						  <s:else>--</s:else>
					</td>
					<td>
					<z:permission value="bank:frp:edit">
						[<a href="frp_editFrpUI.action?frpCode=${frpCode}" title="修改支付渠道" target="dialog" style="color:blue">修改</a>]
					</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    