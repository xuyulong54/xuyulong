<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
		<div class="pageFormContent" layoutH="58">
			<table>
				<tr>
					<td class="n_td">设置键名：</td>
					<td><input id="setKey" name="setKey" type="text" disabled="disabled" value="${setKey}" size="76"/></td>
				</tr>
				<tr>
					<td class="t_td">设置内容：</td>
					<td style="text-align: left"><textarea id="setContent" name="setContent"
							style="width: 480px; height: 80px" disabled="disabled">${setContent}</textarea></td>
				</tr>
				<tr>
					<td class="n_td">描述：</td>
					<td><input id="description" name="description" type="text" disabled="disabled" value="${description}" size="76"/></td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
</div>