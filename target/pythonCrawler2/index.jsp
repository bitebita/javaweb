<%@ page pageEncoding="utf-8" import="java.util.ArrayList,bean.BookInfo"%>
<html>
<head>
<title>论文</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<h1>论文</h1>
	<a href="addview.do">增加论文信息</a>
	<%--<a href="add.do">增加论文信息</a>--%>
<%--
	<a href="index.do">查询论文信息</a>
--%>
	<p />
	<table style="width: 50%">
		<tr>
			<th>标题</th>
			<th>链接</th>
			<th>摘要</th>
			<th>管理</th>
		</tr>
		<%
			@SuppressWarnings("unchecked")
			ArrayList<BookInfo> list = (ArrayList<BookInfo>) request.getAttribute("list"); 
			for (BookInfo bi : list) {
				String id = bi.getId();
		%>
		<tr>
			<td><%=bi.getTitle()%></td>
			<td><%=bi.getLink()%></td>
			<td><%=bi.getKeywords()%></td>
			<td><a href="update.do?id=<%=id%>">修改</a> <a
				href="delete.do?id=<%=id%>">删除</a></td>
		</tr>
		<%
			}
		%>
	</table>
	<br />
	<hr />
</body>
</html>
