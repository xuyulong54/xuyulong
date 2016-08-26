<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
  <form id="pagerForm" onsubmit="return iframeCallback(this);" enctype="multipart/form-data" action="contract_editContract.action" method="post" class="pageForm required-validate">
    <div class="pageFormContent" layoutH="58">
        <input type="hidden" name="contracId" value="${contracId} "/>
      <p>
        <label>商户名或银行接口名：</label>
        <input type="text" name="userName" readonly="true" size="30" value="${userName }"/>
      </p>
      <p>
        <label>文件编号：</label>
        <input type="text" name="contractNo" value="${contractNo }" size="30" readonly="true"/>
      </p>
      <p>
        <label>源文件名：</label>
        <input type="text" name="fileName" value="${fileName }" size="30" readonly="true"/>
      </p>
      <p>
        <label>签约日期：</label>
        <input type="text" name="signTime" value="<s:date name="signTime" format="yyyy-MM-dd" />" size="30" readonly="true"/>
      </p>
      <p>
        <label>合同到期日期：</label>
        <input type="text" name="contractValid" value="<s:date name="contractValid" format="yyyy-MM-dd" />" size="30" readonly="true"/>
      </p>
      <p>
        <label>创建人：</label>
        <input type="text" name="creater" value="${creater }" size="30" readonly="true"/>
      </p>
      <p>
        <label>文件类型：</label>
        <select name="status" disabled="true">
        <c:forEach items="${contractTypeList }" var="v">
          <option value="100" <c:if test="${v.value eq contractType }">selected="selected"</c:if> >${v.desc }</option>
        </c:forEach>
        </select>
      </p>
      <p>
        <label>文件性质：</label>
           <select name="status" disabled="true">
        <c:forEach items="${filePropertiesList }" var="v">
          <option value="100" <c:if test="${v.value eq contractNo }">selected="selected"</c:if> >${v.desc }</option>
        </c:forEach>
        </select>
      </p>
      <p>
        <label>描述：</label>
        <input type="text" name="remark" value="${remark }" size="30" readonly="true"/>
      </p>
    </div>
    <div class="formBar">
      <ul>
        <li><div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">保存</button>
            </div>
          </div></li>
        <li><div class="button">
            <div class="buttonContent">
              <button type="button" class="close">取消</button>
            </div>
          </div></li>
      </ul>
    </div>
  </form>
</div>
