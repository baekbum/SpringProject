<%@page import="java.util.List"%>
<%@page import="kr.koreait.VO.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>답변형게시판 - 내용보기</title>
<script type="text/javascript">
	function commentChk() {
		f = document.commentform;
		if(!f.name.value || f.name.value.trim().length == 0) {
			alert('이름넣어!!!');
			f.name.value = "";
			f.name.focus();
			return false;
		}
		if(!f.password.value || f.password.value.trim().length == 0) {
			alert('암호넣어!!!');
			f.password.value = "";
			f.password.focus();
			return false;
		}
		if(!f.content.value || f.content.value.trim().length == 0) {
			alert('내용넣어!!!');
			f.content.value = "";
			f.content.focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<form method="post" name="form1">
	이름 : <input type="text" name="name" value="${vo.name }" readonly="readonly"/><br/>
	작성일 : <input type="text" name="wdate" value="${vo.wdate }" readonly="readonly"/><br/>
	IP : <input type="text" name="ip" value="${vo.ip }" readonly="readonly"/><br/>
	제목 : <input type="text" name="title" size="80" value="${vo.title }" readonly="readonly"/><br/>
	내용 : <textarea rows="10" cols="80" name="content" readonly="readonly">${vo.content }</textarea><br/>
</form>
	<input type="button" onclick="location.href='list?page=${pageNo}'" value="리스트로"/>
	<input type="button" onclick="location.href='reply?page=${pageNo}&idx=${vo.idx }'" value="답변달기"/>
	<input type="button" onclick="location.href='edit?page=${pageNo}&idx=${vo.idx }'" value="수정하기"/>
	<input type="button" onclick="location.href='delete?page=${pageNo}&idx=${vo.idx }'" value="삭제하기"/>
	<br/>
	<p/>
<c:if test="${list != null}">
	<div width='90%' style='border:1px solid gray;'>
		<form action="replyDelete" method="post">
		<c:forEach var="comment" items="${list.list }">
			<div width='95%' style='border:1px solid silver;'>
			<div width='90%' style='background-color:silver'>
			이름 : ${comment.name }(${comment.wdate }) <br/>
			</div>
			<c:set var="content" value="${fn:replace(comment.content, '<', '&lt;')}"/>
			<c:set var="content" value="${fn:replace(content, '>', '&gt;')}"/>
			<c:set var="content" value="${fn:replace(content, rn, '<br/>')}"/>
			${content }
			</div>
			<div align="center">
			<input type="button" onclick="location.href='replyEdit?idx=${comment.idx}'" value="수정하기"/>
			<input type="button" onclick="location.href='replyDelete?idx=${comment.idx }'" value="삭제하기"/>
			</div>			
		</c:forEach>		
		</form>
	</div>
</c:if> 
</body>
</html>