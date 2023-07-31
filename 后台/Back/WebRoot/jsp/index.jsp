<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" isELIgnored="false"%>
<html>
<head>
    <title>Back Test</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<h1>Back Home Page</h1>
<a href="../html/">跳转到html界面</a>
<a href="text.jsp">跳转到test.jsp界面</a>
<p/>



<%
	out.println("out.println Hello World!");
	out.println("<br/>");
	out.println("out.println <br/>");
	String appPath = request.getContextPath();
	out.println("request.getContextPath()：" + appPath);
	String[] products = new String[]{"aaa","bbb","ccc","ddd"};

    List< String> words = new ArrayList();
    words.add("aaaa");
    words.add("bbbb");
    words.add("ccccc");
    request.setAttribute("words", words);


	out.println("<br/>");
    out.println(new Date().toLocaleString());
%>
<br/>

<%! int day = 2; %>
<h3>%if语句：IF...ELSE 实例：是不是周末</h3>
<% if (day == 1 | day == 7) { %>
      <p>今天是周末</p>
<% } else { %>
      <p>今天不是周末</p>
<% } %>

<p/>

<table border="border" align="center">
    <caption>What's in the Fridge?</caption>
    <tr>
        <th>Product</th>
        <th>title</th>
    </tr>
    <c:forEach var="v" items="${words}">
        <tr>
            <th><c:out value="${v}"/></th>
            <th>abcde-gc</th>
        </tr>
    </c:forEach>
    <c:forEach var="prod" items="${products}">
        <tr>
           <td>${prod}</td>
            <th>abcde-gc</th>
        </tr>
    </c:forEach>
</table>

<%@ include file="index.html"%>
</body>
<html>