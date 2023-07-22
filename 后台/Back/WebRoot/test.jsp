<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.servlet.http.Cookie"%>
<%
    Cookie c = new Cookie("name", "Green");
    c.setMaxAge(10);
    c.setPath("127.0.0.1");
    response.addCookie(c);
%>
<jsp:include page="index.jsp"></jsp:include>
request.getParameter();
<jsp:include page="getCalls">
    <jsp:param name="name" value=""/>
    <jsp:param name="age" value="<%=str%>"/>
</jsp:include>
<h2>跳转到获取cookie的页面</h2>

四大域范围：
page
    pageContext.setAttribute("","");
request
    request.setAttribute("","");
session
application

EL表达式：
<span>${expression}</span>

