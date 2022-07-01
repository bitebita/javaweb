<%@ page import="bean.BookInfo" pageEncoding="utf-8"%>
<html>
<head>
<title>修改论文信息</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<%
		BookInfo bi = (BookInfo) request.getAttribute("bi");
	%>
	<form action="update.do" method="post">
		<input type="hidden" name="id" value="<%=bi.getId()%>">
		<table style="width: 50%">
			<caption>修改论文信息</caption>
			<tr>
				<th width="30%">标题：</th>
				<td width="70%"><input name="title" type="text"
					value="<%=bi.getTitle()%>"></td>
			</tr>
			<tr>
				<th>链接：</th>
				<td><input name="link" type="text"
					value="<%=bi.getLink()%>"></td>
			</tr>
			<tr>
				<th>摘要：</th>
				<td><input name="keywords" type="text"
					value="<%=bi.getKeywords()%>"></td>
			</tr>
			<tr>
				<th colspan="2"><input type="submit" value="修改"> <input
					type="reset" value="重置"></th>
			</tr>
		</table>
	</form>
</body>
</html>

