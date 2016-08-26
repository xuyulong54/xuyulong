<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="bankAccount_listBankAccount.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					开户银行：<input type="text" name="openBank" value="${openBank}" alt="精确查询"  size="30" />
				</td>
				<td>
					银行账号：<input type="text" name="bankAccount" value="${bankAccount}" alt="精确查询"  size="30" />
				</td>
				<td>
					银行账户名称：<input type="text" name="userName" value="${userName}" alt="精确查询"  size="30" />
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
					合作方式：<select name="cooperationWay">
							<option value="">全部</option>
							<option value="1" <c:if test="${cooperationWay eq 1 }">selected="selected"</c:if> >存管银行</option>
							<option value="2" <c:if test="${cooperationWay eq 2 }">selected="selected"</c:if> >合作银行 </option>
						</select>
				</td>
				<td>
					账户性质：<select name="accountNature">
							<option value="">全部</option>
							<option value="1" <c:if test="${accountNature eq 1 }">selected="selected"</c:if> >备付金存管账户</option>
							<option value="2" <c:if test="${accountNature eq 2 }">selected="selected"</c:if> >自有资金账户</option>
							<option value="3" <c:if test="${accountNature eq 3 }">selected="selected"</c:if> >备付金收付账户</option>
							<option value="4" <c:if test="${accountNature eq 4 }">selected="selected"</c:if> >备付金汇缴账户</option>
						</select>
				</td>
				<td>
					账户状态：<select name="accountStatus">
							<option value="">全部</option>
							<option value="1" <c:if test="${accountStatus eq 1 }">selected="selected"</c:if> >正常</option>
							<option value="2" <c:if test="${accountStatus eq 2 }">selected="selected"</c:if> >待销户</option>
							<option value="3" <c:if test="${accountStatus eq 3 }">selected="selected"</c:if> >已销户</option>
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
		<z:permission value="bank:account:add">
			<li><a class="add" href="bankAccount_addBankAccountUI.action" target="dialog" rel="input" title="新建银行账户信息"><span>新建银行账户信息</span></a></li>
		</z:permission>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th>序号</th>
				<th>开户银行/地址</th>
				<th>合作方式</th>
				<th>账户性质</th>
				<th>银行账号</th>
				<th>银行账户名称</th>
				<th>开户日期</th>
				<th>账户状态</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
				    <td>${openBank }/${openBankAddress}</td>
				    <td>
					    <s:if test="cooperationWay==1">存管银行</s:if>
					    <s:if test="cooperationWay==2">合作银行</s:if>
				    </td>
				    <td>
				    	<s:if test="accountNature==1">备付金存管账户</s:if>
				    	<s:if test="accountNature==2">自有资金账户</s:if>
				    	<s:if test="accountNature==3">备付金收付账户</s:if>
				    	<s:if test="accountNature==4">备付金汇缴账户</s:if>
				    </td>
				    <td>${bankAccount }</td>
					<td>${userName }</td>
					<td><s:date name="opendate" format="yyyy-MM-dd" /></td>
					<td>
						<s:if test="accountStatus==1">正常</s:if>
						<s:if test="accountStatus==2">待销户</s:if>
						<s:if test="accountStatus==3">已销户</s:if>
					</td>
					<td>
					<z:permission value="bank:account:view">
						<a href="bankAccount_viewBankAccount.action?id=${id}" title="查看银行账户信息" target="dialog" rel="input" style="color:blue" >查看</a>&nbsp;&nbsp;
					</z:permission>
					<z:permission value="bank:account:edit">
						<a href="bankAccount_editBankAccountUI.action?id=${id}" title="修改银行账户信息" target="dialog" rel="input" style="color:blue" >修改</a>&nbsp;&nbsp;
					</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    