<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<style>
 <!--
/* elm1.show({
    id : 'elm1',
    resizeMode : 1,
    shadowMode : false,
    allowPreviewEmoticons : false,
    urlType : 'absolute',
    allowUpload : true, //允许上传图片
    imageUploadJson : 'article_uploadImg.action' //服务端上传图片处理URI
}); */

-->
</style>

<script type="text/javascript">
/* $(pageInit);
function pageInit()
{
	$.extend(XHEDITOR.settings,{shortcuts:{'ctrl+enter':submitForm}});
	$('#elm1').xheditor({
		upLinkUrl:"article_uploadImg.action",
		upImgUrl:"article_uploadImg.action",
		upImgExt:"jpg,jpeg,gif,png",
		upFlashUrl:"article_uploadImg.action",
		upFlashExt:"swf",
		upMediaUrl:"article_uploadImg.action",
		upMediaExt:"wmv,avi,wma,mp3,mid,jpg,png",
		tools:"simple",
		 skin: "vista"
	});
	$("span a[name='About']").remove(); 
} 
$(document).ready(function(){  
        //初始化在线编辑器  
        //设置在线编辑器上传各类文件对应的url  
        $("#newGJFrom #gjnr").xheditor({  
            upLinkUrl:'article_uploadImg.action',
			upImgUrl:'article_uploadImg.action',
			upImgExt:'jpg,jpeg,gif,png',
			upFlashUrl:'article_uploadImg.action',
			upFlashExt:"swf",
			upMediaUrl:'article_uploadImg.action',
			upMediaExt:"wmv,avi,wma,mp3,mid,jpg,png", 
            tools:"simple",
            skin: "vista"  
        });  
        //删除编辑器的 关于图标  
        $("span a[name='About']").remove();  
    });  
    */
function submitForm(){
	$('#form').submit();
}
</script>

<div class="pageContent">
	<form id="form" method="post" enctype="multipart/form-data" action="article_doAdd.action" 
	class="pageForm required-validate" onsubmit="return iframeCallback(this,dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}" />
			<div class="unit">
				<label>文章类型：</label>
				<s:select list="articleTypeList" name="type" cssClass="request" listValue="desc" listKey="value" ></s:select>
			</div>
			<div class="unit">
				<label>文章标题：</label>
				<s:textfield name="title" id="title" cssClass="required" maxlength="300" size="30" />
			</div>
			<div class="unit">
				<label>所属系统：</label>
				<select name="articleType">
				<option value="1" <c:if test="${articleType == 1 }">selected="selected"</c:if> >门户系统</option>
				<option value="2" <c:if test="${articleType == 2 }">selected="selected"</c:if>>代理商系统</option>
				</select>
			</div>
			<div class="unit">
				<label>状态：</label>
				<select name="status">
					<option value="100" <c:if test="${status == 100 }">selected="selected"</c:if> >启用</option>
					<option value="101" <c:if test="${status == 101 }">selected="selected"</c:if> >停用</option>
				</select>
			</div>
			<div class="unit">
				<label>内容：</label>
				<%-- <textarea id="elm1" upImgUrl="article_uploadImg.action" upFlashExt="swf" skin="vista" name="content" class="editor"  style="width:600px;height:320px;visibility:hidden;">${content }</textarea> --%>
				<textarea class="editor" name="content" id="gjnr" name="elm1" rows="20" cols="83" 
				tools="mini"  upBtnText="上3传"
				upImgUrl="article_uploadImg.action" upImgExt="jpg,jpeg,gif,png" 
				html5Upload=false upMultiple=1
				skin="vista">${content }</textarea> 
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm();">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
