<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@include file="/page/inc/taglib.jsp" %>
        <div class="pageContent">
            <form id="form" method="post" action="amountLimitPack_addOrEditAmountLimitPack.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
                <div class="pageFormContent" layoutH="60">
                    <input type="hidden" name="navTabId" value="listAmountLimitPack">
                    <input type="hidden" name="callbackType" value="closeCurrent">
                    <input type="hidden" name="forwardUrl" value="">
                    <input type="hidden" name="id" value="${id}" />
                    <p>
                        <label>名称：</label>
                        <input type="text" value="${name}" size="30" name="name" class="required" minlength="1" maxlength="50" />
                    </p>

                  <div class="unit">
					<label>描述：</label> 
					<s:textarea cols="50" rows="4"
					name="description" cssClass="required" maxlength="200"></s:textarea>
					
			</div>

                </div>

                <div class="formBar">
                    <ul>
                        <li>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="submit">保存</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" class="close">取消</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </form>
        </div>