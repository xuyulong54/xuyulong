<%-- 权限模块:操作员管理:添加或修改页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="editPmsMenu1" method="post" action="pmsMenu_editPmsMenu.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="150">    
		    <input type="hidden" name="navTabId" value="listPmsMenu">
			<input type="hidden" name="forwardUrl" value="">
			
			<input type="hidden" id="menuId" name="id"  value="${model.id }"/>
			<input type="hidden" name="version"  value="${model.version }"/>
			<input type="hidden" name="level"  value="${model.level }"/>
			<input type="hidden" name="isLeaf"  value="${model.isLeaf }"/>
			
			<p style="width:99%">
				<label>上级菜单：</label>
				<input type="text" name="parent.name"  value="${model.parent.name}" readonly="true" size="30" />
				<input type="hidden" name="parent.id"  value="${model.parent.id}" />
				<span class="info"></span>
			</p>
			<p style="width:99%">
				<label>菜单名称：</label>
				<input type="text" name="name" class="required" maxlength="90" value="${model.name }" size="30" />
			</p>
			<p style="width:99%">
				<label>菜单编号：</label>
				<input type="text" name="number" class="required number" maxlength="20" value="${model.number }" size="30"  />
			</p>
			<p style="width:99%">
				<label>请求URL：</label>
				<input type="text" name="url" maxlength="150" value="${model.url }" size="50" />
			</p>
			
			<p style="width:99%">
				<label>navTabId：</label>
				<input type="text" name="targetName" maxlength="50" value="${model.targetName}" size="30"  />
			</p>
			<z:permission value="pms:operator:edit">
			<div class="buttonActive" style="margin-left:130px;margin-top:30px;">
				<div class="buttonContent">
					<button type="submit">保存</button>
				</div>
			</div>
			</z:permission>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitForm2(){
		$("#editPmsMenu1").submit();
	}
</script>