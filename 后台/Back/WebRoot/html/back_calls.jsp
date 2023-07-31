<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,com.testSSM.test.biz.Utils,com.testSSM.test.model.*" isELIgnored="false"%>

<%
    //telNumber
    String telNumber = request.getParameter("telNumber");
    if(Utils.isEmpty(telNumber)){
        telNumber = (String)pageContext.getAttribute("telNumber");
        if(Utils.isEmpty(telNumber)){
           telNumber = (String)request.getAttribute("telNumber");
        }
    }
    pageContext.setAttribute("telNumber",telNumber);
    //keyword
    String keyword = request.getParameter("keyword");
    if(Utils.isEmpty(keyword)){
        keyword = (String)pageContext.getAttribute("keyword");
        if(Utils.isEmpty(keyword)){
           keyword = (String)request.getAttribute("keyword");
        }
    }
    pageContext.setAttribute("keyword",keyword);
    //startDate
    String startDate = request.getParameter("startDate");
    if(Utils.isEmpty(startDate)){
        startDate = (String)pageContext.getAttribute("startDate");
        if(Utils.isEmpty(startDate)){
            startDate = (String)request.getAttribute("startDate");
        }
    }
    pageContext.setAttribute("startDate",startDate);
    //endDate
    String endDate = request.getParameter("endDate");
    if(Utils.isEmpty(endDate)){
         endDate = (String)pageContext.getAttribute("endDate");
         if(Utils.isEmpty(startDate)){
             endDate = (String)request.getAttribute("endDate");
         }
    }
    pageContext.setAttribute("endDate",endDate);
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

    System.out.println("params:::"+telNumber + "  keyword:"+keyword + "  start:"+startDate + " end:"+endDate+ " pageNum:"+pageNum + " numPer:"+numPerPage);
	CallJspResponse Calls = Utils.getCalls("http://localhost:8080/Back/getCalls",telNumber,keyword,startDate,endDate,pageNum,numPerPage);
	request.setAttribute("Calls",Calls.getCalls());
	request.setAttribute("CallsCount",Calls.getSize());
	/**
	request.setAttribute("telNumber",telNumber);
	request.setAttribute("keyword",keyword);
	request.setAttribute("startDate",startDate);
	request.setAttribute("endDate",endDate);
	request.setAttribute("pageNum",pageNum);
	request.setAttribute("numPerPage",numPerPage);
	**/
%>

<form id="pagerForm" method="post" action="back_calls.jsp">
	<input type="hidden" name="telNumber" value="${telNumber}">
	<input type="hidden" name="keyword" value="${keyword}" />
	<input type="hidden" name="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" value="${endDate}" />
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="back_calls.jsp" method="post" onreset="$(this).find('select.combox').comboxReset()">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
<!--				<td>-->
<!--					关键字：<input type="text" name="keyword" value="${keyword}"/>-->
<!--				</td>-->
				<td>
					电话：<input type="text" name="telNumber" value="${telNumber}"/>
				</td>
				<td class="dateRange">
					时间选择:
					<input name="startDate" class="date readonly" readonly="readonly" type="text" value="${startDate}">
					<span class="limit">-</span>
					<input name="endDate" class="date readonly" readonly="readonly" type="text" value="${endDate}">
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
				<th width="40">ID</th>
				<th width="60">通话类型</th>
				<th>电话号码</th>
				<th width="140">通话日期</th>
				<th width="100">通话时长</th>
			</tr>
		</thead>
		<tbody>

    <c:forEach var="call" items="${Calls}">
		<tr target="sid_user" rel="1">
            <td>${call.getId()}</td>
            <td>${call.getType()}</td>
            <td>${call.getNumber()}</td>
            <td>${call.getDate()}</td>
            <td>${call.getDuration()}秒</td>
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
				<option value="500" <%="500".equals(numPerPage)?"selected":""%>>500</option>
			</select>
			<span>条，共${CallsCount}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="${CallsCount}" numPerPage="${numPerPage==null?50:numPerPage}" pageNumShown="10" currentPage="${pageNum==null?1:pageNum}"></div>

	</div>
</div>
