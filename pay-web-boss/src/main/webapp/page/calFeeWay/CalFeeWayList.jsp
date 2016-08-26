<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name="calFeeWayForm" onsubmit="return navTabSearch(this);" action="calFeeWay_listCalFeeWay.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>计费维度：</label>
					<select name="dimensionId" id="dimensionId">
						<option value="">--请选择--</option>
						<c:forEach items="${calDimensionList }" var="calDimension">
								<option value="${calDimension.id }"
									<c:if test="${dimensionId eq calDimension.id }">selected="selected"</c:if>>${calDimension.calProduct}(${calDimension.calCostInterfaceCode})</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<label>计费方式名称：</label>
					<input type="text" name="wayName" value="${wayName }" size="20" alt="模糊搜索"/>
				</td>
				<td>
					<label>计费类型：</label>
					<select name="calType" id="calType">
							<option value="">请选择</option>
							<c:forEach items="${calTypeEnumList }" var="calTypeEnum">
								<option value="${calTypeEnum.value}" <c:if test="${calType eq calTypeEnum.value}">selected="selected"</c:if>>${calTypeEnum.desc}</option>
							</c:forEach>
					</select>
				</td>
				
			</tr>
			<tr>
				<td>
					<label>收费方式：</label>
					<select name="calPeriod" id="calPeriod">
							<option value="">请选择</option>
							<c:forEach items="${calPeriodeEnumList }" var="calPeriodeEnum">
								<option value="${calPeriodeEnum.value}" <c:if test="${calPeriod eq calPeriodeEnum.value}">selected="selected"</c:if>>${calPeriodeEnum.desc}</option>
							</c:forEach>
					</select>
				</td>
				<td>
					<label>计费角色：</label>
					<select name="calRole" id="calRole">
							<option value="">请选择</option>
							<c:forEach items="${calRoleEnumList }" var="calRoleEnum">
								<option value="${calRoleEnum.value}" <c:if test="${calRole eq calRoleEnum.value}">selected="selected"</c:if>>${calRoleEnum.desc}</option>
							</c:forEach>
					</select>
				</td>
				<td>
					<label>状态：</label>
					<select name="status" id="status">
							<option value="">请选择</option>
							<c:forEach items="${publicStatusEnumList }" var="publicStatusEnum">
								<option value="${publicStatusEnum.value}" <c:if test="${status eq publicStatusEnum.value}">selected="selected"</c:if>>${publicStatusEnum.desc}</option>
							</c:forEach>
					</select>
				</td>
				<td colspan="2" align="left">
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
	<z:permission value="boss:calFeeWay:add">
		<ul class="toolBar">
			<li><a class="add" href="calFeeWay_addCalFeeWayUI.action" target="dialog" rel="input" height="489" width="557" title="添加计费方式"><span>添加</span></a></li>
		</ul>
	</z:permission>
	</div>
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th >序号</th>
				<th >计费维度</th>
				<th >计费方式名称</th>
				<th >免计费金额(包含)</th>
				<th >计费类型</th>
				<th >生效日期</th>
				<th >失效日期</th>
				<th >创建时间</th>
				<th >状态</th>
				<th >操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>
						<c:forEach items="${calDimensionList }" var="v">
							<c:if test="${dimensionId eq v.id}">${v.calProduct}(${v.calCostInterfaceCode})</c:if>
						</c:forEach>
					</td>
					<td>${wayName}</td>
				    <td align="right">${feeFreeAmount}</td>
					<td>
						<c:forEach items="${calTypeEnumList }" var="calTypeEnum">
							<c:if test="${calType eq calTypeEnum.value}">${calTypeEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td align="center"><s:date name="beginDate" format="yyyy-MM-dd" /></td>
					<td align="center"><s:date name="endDate" format="yyyy-MM-dd" /></td>
					<td align="center"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td >
						<c:forEach items="${publicStatusEnumList }" var="publicStatusEnum">
							<c:if test="${status eq publicStatusEnum.value}">${publicStatusEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td >
					<z:permission value="boss:calFeeWay:edit">
						<%-- <a href="calFeeWay_editCalFeeWayUI.action?id=${id}" title="修改计费约束信息" target="dialog" style="color:blue">修改</a> --%>
						<c:if test="${status eq 100}">
							<a href="javascript:changeFeeWayStatus(${id},${status});" title="修改计费约束状态"  style="color:blue">设为无效</a>
							&nbsp;|&nbsp;
						</c:if>
						
						<c:if test="${status eq 101}">
							<a href="javascript:changeFeeWayStatus(${id},${status});" title="修改计费约束状态"  style="color:blue">设为有效</a>
							&nbsp;|&nbsp;
						</c:if>
						
					</z:permission>
					<z:permission value="boss:calFeeRateFormula:view">
						<a href="calFeeRateFormula_calFeeRateFormulaList.action?feeWayId=${id}&calType=${calType}" title="设置计费公式" rel="calFeeRateFormula" target="dialog" style="color:blue">设置计费公式</a>
						&nbsp;|&nbsp;
					</z:permission>
					<a href="calFeeWay_calFeeWayView.action?feeWayId=${id}" title="计费方式详情" rel="calFeeRateFormula" height="300" target="dialog" style="color:blue">详情</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
	function changeFeeWayStatus(id,status){
		var msg = "";
		if('100' == status){
			msg = "您确定要将该计费约束置为失效状态吗？";
			status = '101';//转换为要修改成的状态值，传到后台
		}else{
			msg = "您确定要将该计费约束置为有效状态吗？";
			status = '100';//转换为要修改成的状态值，传到后台
		}
		alertMsg.confirm(msg, {
			okCall: function(){
				$.post("calFeeWay_changeFeeWayStatus.action",{id:id,status:status},
				function(res){
					//$("#pagerForm").submit();
					$("form[name='calFeeWayForm']").submit();
					if(res.STATE=="SUCCESS"){
						alertMsg.correct(res.MSG);
					}else{
						alertMsg.error(res.MSG);
					}
			},"json");
		}
	});
	}
</script>