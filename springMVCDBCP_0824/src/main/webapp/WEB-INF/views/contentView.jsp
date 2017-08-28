<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 보기</title>
</head>
<body>
<form action="update" method="post">
<table width="600" align="center" border="1">
	<tr><th colspan="4">게시글 보기</th></tr>
	<tr>
		<td width="70">글번호</td>
		<td width="340">작성자</td>
		<td width="100">작성일</td>
		<td width="70">조회수</td>
	</tr>
	<tr>
		<td>
			${vo.idx}
			<input type="hidden" name="idx" value="${vo.idx}"/>
		</td>
		<td>
			<c:set var="name" value="${fn:replace(vo.name, '<', '&lt;')}"/>
			<c:set var="name" value="${fn:replace(name, '>', '&gt;')}"/>
			${name}
		</td>
		<td>
			<fmt:formatDate value="${vo.writeDate}" pattern="yyyy.MM.dd(E)"/>
		</td>
		<td>${vo.hit}</td>
	</tr>
	<tr>
		<td>제목</td>
		<td colspan="3">
			<%-- 
			<c:set var="subject" value="${fn:replace(vo.subject, '<', '&lt;')}"/>
			<c:set var="subject" value="${fn:replace(subject, '>', '&gt;')}"/>
			${subject} 
			--%>
			<input type="text" name="subject" value="${vo.subject }"/>
		</td>
	</tr>
	<tr>
		<td>내용</td>
		<td colspan="3">
			<%-- 
			<c:set var="content" value="${fn:replace(vo.content, '<', '&lt;')}"/>
			<c:set var="content" value="${fn:replace(content, '>', '&gt;')}"/>
			<c:set var="content" value="${fn:replace(content, rn, '<br/>')}"/>
			${content} 
			--%>
			<textarea rows="10" cols="60" name="content">${vo.content}</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="center">
			<input type="submit" value="수정하기" />
			<input type="button" value="삭제하기" 
				onclick="location.href='delete?idx=${vo.idx}'"/>
			<input type="button" value="답변하기" 
				onclick="location.href='reply?idx=${vo.idx}'"/>
			<input type="button" value="목록보기" 
				onclick="location.href='list'"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>