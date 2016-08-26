<%-- 权限模块:权限（功能点）管理:添加或修改页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form1" method="post" action="pms_addPmsAction.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
		    <input type="hidden" name="navTabId" value="listPmsAction">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<p>
				<label>权限名称：</label>
				<input type="text" name="actionName" class="required" minlength="3" maxlength="50" size="30" />
			</p>
			<p>
				<label>权限标识：</label>
				<input type="text" name="action" class="required" minlength="3" maxlength="50" size="30" />
				<span class="info"></span>
			</p>
			<p style="width:99%">
				<label></label>
				<span style="color:red;">提示：权限标识添加后将不可修改，请确保添加信息的准确性！</span>
			</p>
			<p style="height:50px;">
				<label>权限描述：</label>
				<textarea rows="3" cols="27" name="desc" class="required" minlength="3" maxlength="60"></textarea>
			</p>
			<p>
				<label>关联菜单：</label>
				<input type="hidden" id="mnId" name="menu.id" value="" />
				<input type="text" id="menuName" name="menu.name" readonly="true" size="30"/>
				<a class="btnLook" href="pms_pmsMenuLookUpUI.action" lookupgroup="menu" width="350">菜单模块</a>
			</p>
			<p style="width:99%">
				<label></label>
				<span style="color:red;">提示：关联菜单添加后将不可修改，请确保选择菜单的准确性！</span>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
function submitForm(){
	var mnId = $("#mnId").val();
	if (mnId == null || mnId == ""){
		$("#menuName").val(""); // 校验会提示出错
	}else{
		$("#menuId").val(mnId);
	}
	$("#form1").submit();
}


	// 查找带回的拓展功能
	$.extend({
		bringBackSuggest : function(args) {
			$("input[name='menu.id']").val(args["id"]);
			$("input[name='menu.name']").val(args["name"]);
		},
		bringBack : function(args) {
			$.bringBackSuggest(args);
			$.pdialog.closeCurrent();
		}
	});
</script>
