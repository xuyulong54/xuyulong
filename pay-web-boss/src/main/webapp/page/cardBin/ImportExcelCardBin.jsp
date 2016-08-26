<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<form id="cardBinFileForm" name="cardBinFileForm"
						action="cardBin_importCardBin.action" method="post"
						enctype="multipart/form-data" onsubmit="return iframeCallback(this,dialogAjaxDone);"  target="callbackframe">
						<input type="hidden" name="navTabId" value="list">
						<input type="hidden" name="callbackType" value="closeCurrent">
						<input type="hidden" name="forwardUrl" value="">
						<s:hidden id="selectVal" name="selectVal"></s:hidden>
						<table class="styled">
							<tr>
								<td>CardBin文件：</td>
								<td><input type="file" name="file" id="file"
									class="required" />(仅支持Excel 2003,即xls格式)
									<button type="submit">提交</button>
								</td>
							</tr>
						</table>
					</form>
				</li>
			</ul>
		</div>
	</div>
