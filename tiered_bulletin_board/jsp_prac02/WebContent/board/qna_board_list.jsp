<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/common.css" />
</head>
<body>

	<section id="listForm">
		<h2>글 목록</h2>
		<table border="1" style="margin-left: auto; margin-right: auto;">
			<tr>
				<td colspan="5" align="right"><a href="boardWriteForm.bo">글쓰기</a>
				</td>
			</tr>
			<tr id="tr_top" align="center">
				<td>번호</td>
				<td width="200px">제목</td>
				<td>작성자</td>
				<td>날짜</td>
				<td>조회수</td>
			</tr>
			<c:set var="i" value="0" /><!-- 변수 i=0, 게시물 번호 관련 변수(글 번호 정리) -->
			<c:forEach var="article" items="${articleList }"><!-- 자바의 향상된 for 역할 -->
				<tr align="center">
					<td>
						<c:choose>
							<c:when test="${pageInfo.page==1 }">
								${pageInfo.listCount-i }
							</c:when>
							<c:otherwise>
								${pageInfo.listCount-((pageInfo.page-1)*10)-i }
							</c:otherwise>
						</c:choose>
					</td><!-- el을 사용하면, 해당 필드의 getter 자동 호출 -->
					<td align="left" style="padding-left: 10px;">
					<c:choose>
						<c:when test="${article.BOARD_RE_LEV !=0 }">
							<c:forEach var="a" begin="1" end="${article.BOARD_RE_LEV }" step="1">
							&nbsp;&nbsp;
							</c:forEach>└
						</c:when>
					</c:choose>
					
					<a href="boardDetail.bo?board_num=${article.BOARD_NUM}&page=${pageInfo.page }">
					${article.BOARD_SUBJECT }</a>
					
					
					</td>
					<td>${article.BOARD_NAME }</td>
					<td>${article.BOARD_DATE }</td>
					<td>${article.BOARD_READCOUNT }</td>
				</tr>
				<c:set var="i" value="${i+1 }" />
			</c:forEach>
		</table>
	</section>
	
	<section id="pageList">
		<c:if test="${pageInfo.page > 1 && pageInfo.startPage > 1 }">
			<a href=
			"boardList.bo?page=
			${pageInfo.page-((pageInfo.page-1)%5+1) }">◀</a>
		</c:if>


		<c:forEach var="a" begin="${pageInfo.startPage }"
			end="${pageInfo.endPage }" step="1">
			<c:choose>
				<c:when test="${a==pageInfo.page }">
					<b>${a }</b>
				</c:when>
				<c:otherwise>
					<a href="boardList.bo?page=${a }">${a } </a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		
		<c:if
			test="${(pageInfo.page<pageInfo.maxPage)
			&& (pageInfo.endPage<pageInfo.maxPage)
			|| (pageInfo.endPage != pageInfo.maxPage) }">
			<a href=
			"boardList.bo?page=
			${pageInfo.page+(6-((pageInfo.page-1)%5+1)) }">▶</a>
		</c:if>

	</section>

</body>
</html>