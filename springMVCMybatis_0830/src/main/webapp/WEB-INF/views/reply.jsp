<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="replyOK" method="post">
<table width="500" align="center" border="1">
	<tr><th colspan="2">게시판 원글 보기</th></tr>
	<tr>
		<td width="100">이름</td>
		<td>
			<c:set var="name" value="${fn:replace(vo.name, '<', '&lt')}"/>
			<c:set var="name" value="${fn:replace(name, '<', '&lt')}"/>
			${name}
			
			<input type="hidden" name="idx" value="${vo.idx}"/>
			<input type="hidden" name="ref" value="${vo.ref}"/>
			<input type="hidden" name="lev" value="${vo.lev}"/>
			<input type="hidden" name="seq" value="${vo.seq}"/>
			
		</td>
	</tr>
	<tr>
		<td>제목</td>
		<td colspan="3">
			<c:set var="subject" value="${fn:replace(vo.subject, '<', '&lt')}"/>
			<c:set var="subject" value="${fn:replace(subject, '<', '&lt')}"/>
			${subject}
 		</td>
	</tr>
	<tr>
		<td>내용</td>
		<td colspan="3">
			<c:set var="content" value="${fn:replace(vo.content, '<', '&lt')}"/>
			<c:set var="content" value="${fn:replace(content, '<', '&lt')}"/>
			<c:set var="content" value="${fn:replace(content, rn, '<br/>')}"/>
			${content}
		</td>
	</tr>
	<tr><th colspan="2">게시판 답글 달기</th></tr>
		<tr>
		<td width="100">이름</td>
		<td width="400"><input type="text" name="name"/></td>
	</tr>
	<tr>
		<td width="100">제목</td>
		<td width="400"><input type="text" name="subject"/></td>
	</tr>
	<tr>
		<td width="100">내용</td>
		<td width="400"><textarea rows="10" cols="60" name="content"></textarea></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" value="저장하기"/>
			<input type="reset" value="다시쓰기"/>
			<input type="button" value="돌아가기" onclick="location.href='contentView?idx=${vo.idx}'"/>
			<input type="button" value="목록보기" onclick="location.href='list'"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>