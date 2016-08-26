<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headUrl.jsp" %>
<html>
<head>
<link rel="stylesheet" href="<%=path %>/statics/themes/default/css/base.css" />
<link rel="stylesheet" href="<%=path %>/statics/themes/default/css/comon.css" />
<link rel="stylesheet" href="<%=path %>/statics/themes/default/css/control.css" />
<link rel="stylesheet" href="<%=path %>/statics/themes/default/css/table.css" />

<style>
  .pageCla{ margin:0 auto; height:30px; margin-top:20px; margin-right:20px; text-align:right; line-height:30px; font-weight: bold;}
 .pageCla a{ margin-left: 15px;}
 .pageCla .onPnum{}
 .pageCla .selectPnum{ font-weight: bold; font-size: 16px; color:#e60707; }
</style>


<title>选择MCC</title>

<script type="text/javascript">
	function selectMcc(mcc){
		if(mcc==""||mcc==null){
			alert("请选择MCC码!");
			return;
		}
		var form = opener.document.setMerchantInfoForm;
		form.mccValue.value = mcc;
		window.close();
	}
</script>

</head>
<body style="width: 100%; overflow:-Scroll;overflow-y:hidden;overflow-x:hidden;">
	<div>
		<div class="frm-horizontal">
			<form class="frm-horizontal" action="merchantRegister_selectPosMccList.action" id="mccForm" name="mccForm" method="post">
				<div class="frm-group  clearfix">
					<label>MCC码：</label>
					<input class="input-w100" type="text" name="mcc" value="${mcc}"
										size="20" />
					<label>经营范围：</label>
					<input type="text" name="buiScope" value="${buiScope}" size="20" />
					<label>行业类型：</label>
					  <select name="typeName">
          <option value="">请选择</option>
        <c:forEach items="${mccTypeList }" var="v">
          <option value="${v.fullName }" <c:if test="${v.fullName eq typeName }">selected="selected"</c:if> >${v.fullName }</option>
        </c:forEach>
        </select>
					<button class="btn btn-primary " type="submit">查 询</button>
				</div>
			</form>
		</div>
	</div>
	<table class="table table-hover" cellpadding="0" cellpadding="0" layoutH="260">
		<thead>
			<tr>
				<th style="width:30px;">序号</th>
				<th style="width:50px;">行业类型</th>
				<th style="width:50px;">MCC码</th>
				<th style="width:50px;">MCC费率（%）</th>
				<th style="width:50px;">上限额度</th>
				<th style="width:120px;">行业范围</th>
				<th style="width:52px;">操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr>
					<td>${st.index+1}</td>
					<td>${mccType.name}</td>
					<td>${mcc }</td>
					<td><fmt:formatNumber value="${rate }" pattern="#,##0.00"/>%</td>
					<td>${upperLimit }</td>
					<td>${buiScope }</td>
					<td><a class="link-color" href="javascript:void(0);" onclick="selectMcc('${mcc}');" >选择</a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<div class="pageCla">
		<z:page pageBean="${pageBean}"
			url="merchantRegister_selectPosMccList.action"
			currentPage="${pageNum }"
			parameter="&beginDate=${beginDate }&endDate=${endDate }&typeName=${typeName }&buiScope=${buiScope }&mcc=${mcc }"></z:page>
	</div>
	<br style="clear: both;" />
	<div class="clear"></div>
</body>
</html>
