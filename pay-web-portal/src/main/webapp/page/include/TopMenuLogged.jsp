<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<c:choose>
  <c:when test="${not empty currentUser && currentUser.userType==2}">
    <jsp:include page="TopMenuMember.jsp" />
  </c:when>
  <c:when test="${not empty currentUser && currentUser.userType!=2}">
    <jsp:include page="TopMenuMerchant.jsp" />
  </c:when>
  <c:otherwise>
    <jsp:include page="../IndexHead.jsp" />
  </c:otherwise>
</c:choose>