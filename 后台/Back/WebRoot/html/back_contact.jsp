<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,com.testSSM.test.biz.Utils,com.testSSM.test.model.*" isELIgnored="false"%>

<%
    //keyword
    String keyword = request.getParameter("keyword");
    if(Utils.isEmpty(keyword)){
        keyword = (String)pageContext.getAttribute("keyword");
        if(Utils.isEmpty(keyword)){
           keyword = (String)request.getAttribute("keyword");
        }
    }
    pageContext.setAttribute("keyword",keyword);
    //pageNum
    String pageNum = request.getParameter("pageNum");
    if(Utils.isEmpty(pageNum)){
        pageNum = (String)pageContext.getAttribute("pageNum");
        if(Utils.isEmpty(pageNum)){
            pageNum = (String)request.getAttribute("pageNum");
        }
    }
    pageContext.setAttribute("pageNum",pageNum);
    //numPerPage
    String numPerPage = request.getParameter("numPerPage");
    if(Utils.isEmpty(numPerPage)){
       numPerPage = (String)pageContext.getAttribute("numPerPage");
       if(Utils.isEmpty(numPerPage)){
          numPerPage = (String)request.getAttribute("numPerPage");
       }
    }
    pageContext.setAttribute("numPerPage",numPerPage);

    System.out.println("params:::"+ keyword + " pageNum:"+ pageNum + " numPer:"+ numPerPage);
	ContactsJspResponse Contact = Utils.getContact("http://localhost:8080/Back/getContact",keyword,pageNum,numPerPage);
	request.setAttribute("Contact",Contact.getContacts());
	request.setAttribute("ContactCount",Contact.getSize());
	/**
	request.setAttribute("keyword",keyword);
	request.setAttribute("pageNum",pageNum);
	request.setAttribute("numPerPage",numPerPage);
	**/
%>

<form id="pagerForm" method="post" action="back_contact.jsp">
	<input type="hidden" name="keyword" value="${keyword}" />
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="back_contact.jsp" method="post" onreset="$(this).find('select.combox').comboxReset()">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					姓名：<input type="text" name="keyword" value="${keyword}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="50">ID</th>
				<th width="100">姓名</th>
				<th width="40">照片地址</th>
				<th width="40">号码类型</th>
				<th width="40">标识</th>
				<th>号码</th>
				<th>号码1</th>
				<th>号码2</th>
				<th width="120">姓</th>
				<th width="120">名</th>
				<th width="50">公司</th>
				<th width="50">部门</th>
				<th width="50">岗位</th>
				<th width="50">职责</th>
				<th width="50">邮编</th>
				<th width="50">邮箱名</th>
				<th width="150">备注</th>
				<th width="50">主页</th>
				<th width="50">昵称</th>
				<th width="50">关系</th>
				<th width="50">协议</th>
				<th width="50">定制协议</th>
				<th width="50">身份</th>
				<th width="50">名称空间</th>
				<th width="50">群ID</th>
			</tr>
		</thead>
		<tbody>

    <c:forEach var="con" items="${Contact}">
		<tr target="sid_user" rel="1">
            <td>${con.getContactId()}</td>
            <td>${con.getDisplayName()}</td>
            <td>${con.getPhotoUri()}</td>
            <td>${con.getNumberType()}</td>
            <td>${con.getLabel()}</td>
            <td>${con.getNumber()}</td>
            <td>${con.getNumber1()}</td>
            <td>${con.getNumber2()}</td>
            <td>${con.getFirstName()}</td>
            <td>${con.getLastName()}</td>
            <td>${con.getCompany()}</td>
            <td>${con.getDepartment()}</td>
            <td>${con.getJob()}</td>
            <td>${con.getJobDescription()}</td>
            <td>${con.getEmailAddress()}</td>
            <td>${con.getEmailAddressDisplayName()}</td>
            <td>${con.getNote()}</td>
            <td>${con.getWebUrl()}</td>
            <td>${con.getNickName()}</td>
            <td>${con.getRelationName()}</td>
            <td>${con.getProtocol()}</td>
            <td>${con.getCustomProtocol()}</td>
            <td>${con.getIdentity()}</td>
            <td>${con.getNamespace()}</td>
            <td>${con.getGroupId()}</td>
        </tr>
    </c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="50">50</option>
				<option value="100" <%="100".equals(numPerPage)?"selected":""%>>100</option>
				<option value="150" <%="150".equals(numPerPage)?"selected":""%>>150</option>
				<option value="200" <%="200".equals(numPerPage)?"selected":""%>>200</option>
				<option value="250" <%="250".equals(numPerPage)?"selected":""%>>250</option>
			</select>
			<span>条，共${ContactCount}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="${ContactCount}" numPerPage="${numPerPage==null?50:numPerPage}" pageNumShown="10" currentPage="${pageNum==null?1:pageNum}"></div>

	</div>
</div>
