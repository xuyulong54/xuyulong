<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	//response.sendRedirect("login_loginPage.action"); 
	request.getRequestDispatcher("login_loginPage.action").forward(request,response);
%>
