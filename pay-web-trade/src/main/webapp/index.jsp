<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
//response.sendRedirect("b2c_execute.action");
request.getRequestDispatcher("b2c_execute.action").forward(request,response);
 %>