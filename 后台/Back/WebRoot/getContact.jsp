<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.servlet.http.Cookie"%>
<%
    Cookie c = new Cookie("name", "Green");
    c.setMaxAge(10);
    c.setPath("127.0.0.1");
    response.addCookie(c);
%>
<a href="getCalls.jsp">跳转到获取cookie的页面</a>