<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="bank_listBankAgreement.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					接口名称：<input type="text" name="interfaceName" value="${interfaceName}" alt="精确查找" size="30" />
				</td>
				<td>
					银行序号：<input type="text" name="bankSequence" value="${bankSequence}" alt="精确查找" size="30" />
				</td>
				<td>
					商户编号：<input type="text" name="merchantNo" value="${merchantNo}" alt="精确查找" size="30" />
				</td>
			</tr>
			<tr>
				<td>
					业务类型：
					<select name="linkType">
						<option value="">全部</option>
						<option value="1" <c:if test="${linkType eq 1 }">selected="selected"</c:if>>B2B</option>
						<option value="2" <c:if test="${linkType eq 2 }">selected="selected"</c:if>>B2C</option>
						<option value="3" <c:if test="${linkType eq 3 }">selected="selected"</c:if>>快捷支付</option>
					</select>
				</td>
				<td>
					合同编号：<input type="text" name="agreementNo" value="${agreementNo}" alt="精确查找" size="30" />
				</td>
				<td colspan="4">
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
		<ul class="toolBar">
			<z:permission value="bank:agreement:add">
				<li><a class="add" href="bank_addBankAgreementUI.action" target="dialog" rel="input" title="新建银行协议"><span>新建银行协议</span></a></li>
			</z:permission>
			<z:permission value="boss:contract:download">
				<li>
					<a title="文件管理" href="contract_listContract.action" style="color:blue;" target="navTab"><span>下载文件</span></a>
				</li>
			</z:permission>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th>序号</th>
				<!-- <th>银行协议ID</th> -->
				<th>创建时间</th>
				<th>商户编号</th>
				<th>银行序号</th>
				<th>业务类型</th>
				<th>接口名称</th>
				<th>合同编号</th>
				<th>描述</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
				    <td><s:date name="createTime" format="yyyy-MM-dd" /></td>
					<td>${merchantNo }</td>
					<td>${bankSequence }</td>
					<td>
						<s:if test="linkType==1">B2B</s:if>
						<s:if test="linkType==2">B2C</s:if>
						<s:if test="linkType==3">快捷支付</s:if>
					</td>
					<td>${interfaceName }</td>
					<td>${agreementNo }</td>
					<td>${remark}</td>
					<td>
					<z:permission value="bank:agreement:view">
						<a href="bank_viewBankAgreement.action?id=${id}" title="查看银行协议" target="dialog" rel="input" style="color:blue" >【查看】</a>&nbsp;&nbsp;
					</z:permission>
					<z:permission value="bank:agreement:edit">
						<a href="bank_editBankAgreementUI.action?id=${id}" title="修改银行协议" target="dialog" rel="input" style="color:blue" >【修改】</a>&nbsp;&nbsp;
					</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    