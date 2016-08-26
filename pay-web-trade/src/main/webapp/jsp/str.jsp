<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String url = (String)request.getAttribute("str");
response.sendRedirect(url); 
%>