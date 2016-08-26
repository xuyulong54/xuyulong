<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
</head> 
<body>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<form id="treeForm1" onsubmit="return navTabSearch(this);" action="pmsMenu_listPmsMenu.action" method="post" >
</form>
<div class="pageContent" style="padding:5px">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>菜单管理</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
				<div class="panelBar" style="width:262px">
					<ul class="toolBar">
					<z:permission value="pms:menu:add">
						<li>
							<a id="addMenu" class="add" href="pmsMenu_addPmsMenuUI.action" target="dialog" rel="input" width="600" height="400" title="添加菜单"><span>添加</span></a>
						</li>
					</z:permission>
					<z:permission value="pms:menu:delete">
						<li>
							<a id="delMenu" class="delete" href="pmsMenu_delPmsMenu.action" callback="navTabAjax" target="ajaxTodo" rel="inputMenu"  title="确定执行该删除操作吗？"><span>删除</span></a>
						</li>
					</z:permission>
					<z:permission value="pms:menu:view">
						<li>
							<a id="updateMenu" class="edit" href="javascript:onscreach();" ><span>刷新</span></a>
						</li>
					</z:permission>
					</ul>
				</div>
				<div layoutH="78" style="float:left; display:block; overflow:auto; width:260px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    ${tree}
				</div>
				<div layoutH="78" id="jbsxBox" class="unitBox" style="margin-left:266px;">
				
				</div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	</div>
</body>
<script type="text/javascript">
	function  onClickMenuNode(id){
		$("#addMenu").attr("href","pmsMenu_addPmsMenuUI.action?pid="+id);
		$("#delMenu").attr("href","pmsMenu_delPmsMenu.action?id="+id);
	}
	function onscreach(){
		$("#treeForm1").submit();
	}
	
	// 删除后的回调函数，刷新树形菜单
	function navTabAjax(json){
	   //navTabAjaxDone(json);
	   navTab.reload();
	}
</script>
