<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="bankBranch_list.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					支付渠道编号：
					<input type="text" name="frpCode" value="${frpCode }" size="30" alt="精确查询"/>
				</td>
				<td >
					默认渠道编号：
					<input type="text" name="defaultBankChannelCode" value="${defaultBankChannelCode }" size="30" alt="精确查询"/>
				</td>
				<td >
					备用渠道编号：
					<input type="text" name="spareBankChannelCode" value="${spareBankChannelCode }" size="30" alt="精确查询"/>
				</td>
				<td>
					<div class="subBar" >
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
			<z:permission value="bank:branch:add">
				<li><a class="add" href="bankBranch_toAdd.action" target="dialog" width="650" height="400" rel="addBranch" title="新建银行分流"><span>新建银行分流</span></a></li>
			</z:permission>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="133">
		<thead>
			<tr>
				<th>序号</th>
				<th>支付渠道编号</th>
				<th>默认银行渠道</th>
				<th>备用银行渠道</th>
				<th>描述</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${frpCode}</td>
					<td>${defaultBankChannelCode }</td>
					<td>
						${spareBankChannelCode }
					</td>
					<td>
						${remark }
					</td>
					<td>
						<z:permission value="bank:branch:edit">
							[<a title="修改" href="bankBranch_toEdit.action?id=${id }" style="color:blue" target="dialog" width="650" height="400" >修改</a>]
						</z:permission>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>