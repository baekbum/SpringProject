<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="insertOK" method="post">
<table width="500" align="center" border="1">
	<tr><th colspan="2">게시판 쓰기</th></tr>
	<tr>
		<td>이름</td>
		<td><input type="text" name="name"/></td>
	</tr>
	<tr>
		<td>제목</td>
		<td><input type="text" name="subejct"/></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea rows="20" cols="100" name="content"></textarea></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<input type="submit" name="submit" value="전송하기"/>
		<input type="reset" name="reset" value="다시쓰기"/>
		<input type="button" name="view" value="목록보기" onclick="location.href='list'"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>