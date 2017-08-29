<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 목록</title>
</head>
<body>
<table width="800" align="center" border="1">
	<tr><th colspan="5">게시글 목록 보기</th></tr>
	<tr>
		<td width="80" align="center">글번호</td>
		<td width="100" align="center">이름</td>
		<td width="400" align="center">제목</td>
		<td width="140" align="center">작성일</td>
		<td width="80" align="center">조회수</td>
	</tr>
	<jsp:useBean id="date" class="java.util.Date"/>
	<c:if test="${list.list.size() != 0 }">
		<c:forEach var="vo" items="${list.list }">
		<tr>
			<td align="center">
				<c:if test="${vo.lev == 0}">
					${vo.idx }
				</c:if>				
				<c:if test="${vo.lev >= 1}">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ㄴRe
				</c:if>
			</td>
			<td>
				<c:set var="name" value="${fn:replace(vo.name, '<', '&lt;')}"/>
				<c:set var="name" value="${fn:replace(name, '>', '&gt;')}"/>
				${name }
			</td>
			<td>
				<c:set var="subject" value="${fn:replace(vo.subject, '<', '&lt;')}"/>
				<c:set var="subject" value="${fn:replace(subject, '>', '&gt;')}"/>
				<a href="increment?idx=${vo.idx}">${subject }</a>
				<c:if test="${date.year == vo.writeDate.year && date.month == vo.writeDate.month &&
					date.date == vo.writeDate.date}">
					<img src="./images/new.png"/>
				</c:if>
				<c:if test="${vo.hit >= 10}">
					<img src="./images/hot.gif"/>
				</c:if>
			</td>
			
			<td>
				<c:if test="${date.year == vo.writeDate.year && date.month == vo.writeDate.month &&
					date.date == vo.writeDate.date}">
					<fmt:formatDate value="${vo.writeDate}" pattern="HH:mm"/> 
				</c:if>
				<c:if test="${date.year != vo.writeDate.year || date.month != vo.writeDate.month ||
					date.date != vo.writeDate.date}">
					<fmt:formatDate value="${vo.writeDate}" pattern="yyyy.MM.dd(E)"/> 
				</c:if>
			</td>
			<td>${vo.hit }</td>		
		</tr>
		</c:forEach>
	</c:if>
	
	<c:if test="${list.list.size() == 0 }">
		<tr>
			<td colspan="5" align="center">
			게시글이 없습니다.
			</td>
		</tr>			
	</c:if>
	
	<tr>
		<td colspan="5" align="right">
			<input type="button" value="글쓰기" onclick="location.href='insert'"/>
		</td>
	</tr>
</table>
</body>
</html>