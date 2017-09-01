<%@page import="kr.koreait.VO.RBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<table width="100%" border="1" align="center">
	<tr>
		<th colspan="4"><h2>게시판 목록보기</h2></th>
	</tr>
	<tr>
		<td colspan="4" align="right">
			(${board.currentPage} / ${board.totalPage})Page
		</td>
	</tr>
	<tr>
		<th>번호</th>
		<th>작성자</th>
		<th>제목</th>
		<th>조회수</th>
	</tr>
	<c:forEach var="vo" items="${board.list}">
	<tr>
		<td align="center">
			 ${vo.idx }
		</td>
		<td align="center">
			${vo.name }
		</td>
		<td align="center">
			<a href='increment?page=${pageNo}&idx=${vo.idx}'>${vo.title }</a>(${vo.wdate})
		</td>		
		<td align="center">
			${vo.hit }
		</td>				
	</tr>	
	</c:forEach>
	<tr>
		<td colspan="5" align="center">
		<c:if test="${board.startPage > 10}"> 
			<input type="button" value="이전" onclick="location.href='list?page=${board.currentPage - 1}'"/>
		</c:if>
		<c:forEach var="i" begin="${board.startPage}" end="${board.endPage}" step="1">
			<input type="button" value="${i}" onclick="location.href='list?page=${i}'"/>
		</c:forEach>
		<c:if test="${board.startPage > board.endPage}"> 
			<input type="button" value="다음" onclick="location.href='list?page=${board.currentPage + 1}'"/>
		</c:if>
		</td>
	</tr>
	<tr>
		<td colspan="5" align="right">
			<input type="button" onclick="location.href='insert'" value="쓰기"/>
		</td>
	</tr>
</table>
</body>
</html>