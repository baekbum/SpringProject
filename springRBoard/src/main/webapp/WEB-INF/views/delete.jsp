<%@page import="kr.koreait.VO.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function chk() {
		f = document.form1;
		if(!f.password.value || f.password.value.trim().length == 0) {
			alert('암호넣어!!!');
			f.password.value = "";
			f.password.focus();
			return false;
		}
		return true;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>답변형게시판 - 삭제하기</title>
</head>
<body>
<form method="post" name="form1" action="deleteok?page=${pageNo}&idx=${vo.idx}">
	이름 : <input type="text" name="name" value="${vo.name}" readonly="readonly"/><br/>
	암호 : <input type="password" name="password"/><br/>
	제목 : <input type="text" name="title" size="80" value="${vo.title}" readonly="readonly"/> <br/>
	내용 : <textarea rows="10" cols="80" name="content" readonly="readonly">${vo.content}</textarea> <br/>
	<input type="submit" value="삭제하기" />
	<input type="button" value="돌아가기" onclick="location.href='view?page=${pageNo }&idx=${vo.idx}'"/>
</form>
</body>
</html>