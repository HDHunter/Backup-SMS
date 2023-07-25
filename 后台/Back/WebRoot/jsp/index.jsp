<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*"%>
<% String appPath = request.getContextPath(); %>
<html>
<head>
    <title>Back Test</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<h1>Back Test</h1>

<%
	out.println("Hello World!");
	out.println("<br/>");
	out.println("request.getContextPath()：" + appPath);
%>
<br>
<%=new Date().toLocaleString() %>
<%
    out.println(new Date().toLocaleString());
%>

<%! int day = 2; %>
<h3>IF...ELSE 实例</h3>
<% if (day == 1 | day == 7) { %>
      <p>今天是周末</p>
<% } else { %>
      <p>今天不是周末</p>
<% } %>
</body>
<html>