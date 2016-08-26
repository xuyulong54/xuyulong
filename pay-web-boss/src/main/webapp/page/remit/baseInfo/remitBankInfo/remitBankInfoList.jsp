<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="remitBankInfoForm" onsubmit="return navTabSearch(this);" action="remitBankInfo_remitBankInfoList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>银行行号：</label>
						<input type="text" name="bankChannelNo" value="${bankChannelNo }" size="25" alt="精确搜索"/>
					</td>
					<td>
						<label>银行名称：</label>
						<input type="text" name="bankName" value="${bankName }" size="25" alt="模糊搜索"/>
					</td>
					<td>
						<label>清算行号：</label>
						<input type="text" name="clearBankChannelNo" value="${clearBankChannelNo }" size="25" alt="精确搜索"/>
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
	<z:permission value="boss:remitBankInfo:add">
		<ul class="toolBar">
			<li><a href="remitBankInfo_remitBankInfoAdd.action" class="add" target="dialog" rel="input" width="541" height="360"  title="添加银行信息"><span>添加</span></a></li>
			<!-- <li><a href="remitBankInfo_remitBankInfoUpload.action" class="add" type="file" target="dialog" rel="input" width="800" height="242"  title="导入银行信息"><span>导入</span></a></li> -->
		</ul>
	</z:permission>
	</div>
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>银行行号</th>
				<th>银行名称</th>
				<th>类别</th>
				<th>清算行号</th>
				<th>地区</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${bankChannelNo}</td>
				    <td>${bankName}</td>
				    <td>
						<c:forEach items="${bankTypeEnumList }" var="bankTypeEnum">
							<c:if test="${bankTypeEnum.value eq bankType }">${bankTypeEnum.desc}</c:if>
						</c:forEach>
					</td>
				    <td>${clearBankChannelNo }</td>
				    <td>${province }${city }</td>
					<td>
						<z:permission value="boss:remitBankInfo:edit">
							<a  href="remitBankInfo_remitBankInfoEdit.action?id=${id }" title="修改银行信息" target="dialog" rel="input" width="541" height="360" style="color:blue">修改</a>
							&nbsp;|&nbsp;
						</z:permission>
						<z:permission value="boss:remitBankInfo:delete">
							<a  href="javascript:deleteRemitBankInfo('${id}')" title="删除银行信息" style="color:blue">删除</a>
							&nbsp;|&nbsp;
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
	function deleteRemitBankInfo(id){
		alertMsg.confirm("确定要删除该银行信息?", {
			okCall: function(){
				$.post("remitBankInfo_deleteRemitBankInfo.action",{"id":id},function(res){
					 if(res.STATE=="SUCCESS"){
						 alertMsg.correct(res.MSG);
						 $("form[name='remitBankInfoForm']").submit();
					 }else if(res.STATE == "FAIL"){
					 	alertMsg.error(res.MSG);
					 }
				 },"json");
			}
		});
	}
	
</script>