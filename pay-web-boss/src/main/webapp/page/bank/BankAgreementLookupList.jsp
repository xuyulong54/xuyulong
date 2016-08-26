<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="bank_BankAgreementLookupList.action">
    <%@include file="/page/inc/pageForm.jsp"%>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="bank_lookupBankList.action" method="post">
	<!-- 
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					协议ID：<input type="text" name="shortName" value="" size="30" alt="精确搜索" />
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
	</div> -->
	</form>
</div>
<div class="pageContent">	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="86">
		<thead>
			<tr>
				<th>序号</th>
				<!-- <th>ID</th> -->
				<th>银行接口名称</th>
				<th>银行序号</th>
				<th>商户编码</th>
				<!-- <th>商户密钥</th> -->
				<th>合同编号</th>
				<!-- <th>描述</th> -->
				<th>创建时间</th>
				<th>应用产品</th>
				<th>操作</th> <!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}">
					<td>${st.index+1}</td>
					<!-- <td>${id}</td> -->
					<td>${interfaceName }</td>
					<td>${bankSequence}</td>
					<td>${merchantNo}</td>
				    <!-- <td>${key}</td> -->
				    <td>${agreementNo}</td>
					<!-- <td>${remark}</td> -->
					<td>
						<s:date name="createTime" format="yyyy-MM-dd" />
					</td>
					<td>
						<s:if test="linkType==1">B2B</s:if>
						<s:if test="linkType==2">B2C</s:if>
						<s:if test="linkType==3">快捷支付</s:if>
					</td>
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({bankAgreementId:'${id}',merchantNo:'${merchantNo }',bankSequence:'${bankSequence}',
						linkType:'<s:if test="linkType==1">_NET_B2B</s:if><s:if test="linkType==2">_NET_B2C</s:if><s:if test="linkType==3">_FAST</s:if>',
						bankChannelCode:'${bankCode }', 
						bankCode:'${bankCode }',
						bankChannelName:'<c:forEach items='${bankCodeWithName }' var='bankName'><c:if test="${bankCode eq bankName.key }">${bankName.value }</c:if></c:forEach>', 
						bankchannelNamePay:'分行<s:if test="linkType==1">B2B</s:if><s:if test="linkType==2">B2C</s:if><s:if test="linkType==3">快捷支付</s:if>银行支付渠道',
						bankName:'<c:forEach items="${bankCodeWithName }" var="bankName"><c:if test="${bankCode eq bankName.key }">${bankName.value }</c:if></c:forEach>', 
						province:'${province }', city:'${city }', area:'${area }' ,backFlag:'1'})" title="查找带回">选择</a>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
    <div class="panelBar">
	<div class="pages">
		<span>查到&nbsp;${totalCount}&nbsp;条记录，每页</span>
		<select class="combox" name="numPerPage" value="${numPerPage}" onchange="dwzPageBreak({targetType:dialog, numPerPage:this.value})">
		    <option value="15" <c:if test="${numPerPage eq 15 }">selected='selected'</c:if>>15</option>
		</select>
		<span>条，共&nbsp;${pageCount}&nbsp;页</span>
	</div>
	<div class="pagination" 
		targetType="dialog" 
		totalCount="${totalCount}" 
		numPerPage="${numPerPage}" 
		pageNumShown="5" 
		currentPage="${currentPage}">
	</div>
    </div>
</div>

    