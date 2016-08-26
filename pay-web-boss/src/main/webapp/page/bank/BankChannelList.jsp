<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form  id="pagerForm" onsubmit="return navTabSearch(this);" action="bank_listBankChannel.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					银行渠道编号：<input type="text" name="bankChannelCode" value="${bankChannelCode}" alt="精确查询" size="30"/>
				</td>
				<td>
					银行名称：<input type="text" name="bankName" value="${bankName}" alt="模糊查询" size="30"/>
				</td>
				<td>
					银行渠道名称：<input type="text" name="bankChannelName" value="${bankChannelName }" alt="精确查询" size="30"/>
				</td>
			</tr>
			<tr>
				<td>
					落地行名称：<input type="text" name="landingBankName" value="${landingBankName }" alt="模糊查询" size="30"/>
				</td>
				<td>
					银行序号：<input type="text" name="banksequence" value="${bankSequence }" alt="精确查询" size="30"/>
				</td>
				<td>
					状态：
						<select name="status" >
							<option value="">全部</option>
							<option value="100">有效</option>
							<option value="101">无效</option>
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
		<ul class="toolBar">
			<z:permission value="bank:channel:add">
				<li><a class="add" href="bank_addBankChannelUI.action" target="dialog" rel="input" title="新建银行渠道"><span>新建银行渠道</span></a></li>
			</z:permission>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160" nowrapTD="false">
		<thead>
			<tr>
				<th >序号</th>
				<!-- <th>银行序号</th> -->
				<th>银行渠道编号</th>
				<th>银行渠道名称</th>
				<th>银行名称</th>
				<th>银行编码</th>
				<th>落地行名称</th>
				<th >状态</th>
				<th >操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
				    <%-- <td>${bankSequence }</td> --%>
					<td>${bankChannelCode }</td>
					<td>${bankChannelName }</td>
					<td>${bankName }</td>
					<td>${bankCode }</td>
					<td>${landingBankName }</td>
					<td>
						<s:if test="status==100">有效</s:if>
						<s:else>无效</s:else>
					</td>
					<td style="word-break:break-all">
					
					<z:permission value="bank:channel:view">
						<a href="bank_viewBankChannelUI.action?bankChannelCode=${bankChannelCode}&bankAgreementId=${bankAgreementID}&bankChannelID=${id}" title="查看银行渠道" target="dialog" style="color:blue">【查看】</a>
					</z:permission>
					
					<z:permission value="bank:channel:edit">
						<a href="bank_editBankChannelUI.action?bankChannelID=${id}" title="修改【${bankChannelName }】银行渠道" target="dialog" rel="input" style="color:blue">【修改】</a>
					</z:permission>
					
					<z:permission value="bank:settlement:set">
						<a href="banksettlement_setBanksettlementUI.action?bankChannelCode=${bankChannelCode}&bankAgreementID=${bankAgreementID}" title="设置【${bankChannelName }】结算信息" target="dialog" rel="input"  style="color:blue" >【设置结算信息】</a><br>
					</z:permission>
					
					<%-- <z:permission value="bank:certificate:set">
						<a href="bankCertificate_setBankCertificateUI.action?bankChannelCode=${bankChannelCode}" title="设置【${bankChannelName }】证书信息" target="dialog" rel="input"  style="color:blue" >【设置证书信息】</a>
					</z:permission> --%>
					
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    