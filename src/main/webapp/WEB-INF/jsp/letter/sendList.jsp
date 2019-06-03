<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>보낸 편지</title>
<style type="text/css">
table {
	margin-top: 10px;
	border-collapse: collapse;
	border-top: 1px solid gray;
	border-bottom: 1px solid gray;
	width: 100%;
}
th, td {
	padding: 5px 0;
}
th {
	border-bottom: 1px solid gray;
}
</style>
</head>
<form action="./app/letter/sendList">
	</form>
	<table>
		<thead>
			<tr>
				<th>제목</th>
				<th>받은 사람</th>
				<th>등록일시</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="article" items="${articleList}">
				<tr>
					<td><a href="./app/letter/sendview?letterId=${letter.letterId }">${letter.title }</a></td>
					<td>${letter.receiverId }</td>
					<td>${letter.cdate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</head>
</html>