<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/common.css">
</head>
<body>
	<section id="writeForm">
		<h2>게시글 읽기</h2>
		<table>
			<tr>
				<td class="td_left">글쓴이</td>
				<td class="td_right">${article.BOARD_NAME }</td>
			</tr>
			<tr>
				<td class="td_left">제목</td>
				<td class="td_right">${article.BOARD_SUBJECT }</td>
			</tr>
			<tr>
				<td class="td_left">내용</td>
				<td class="td_right">
					<div align="left">
						<!-- pre 안의 공백 주의, 그대로 표시됨 -->
						<pre style="padding-left: 10px;">${article.BOARD_CONTENT }</pre>
					</div>
				</td>
			</tr>
		</table>
	</section>
	<section id="commandCell">
		<a
			href="boardReplyForm.bo?board_num=${article.BOARD_NUM }&page=${page }">[답변]</a>
		<a
			href="boardModifyForm.bo?board_num=${article.BOARD_NUM }&page=${page }">[수정]</a>
		<a
			href="boardDeleteForm.bo?board_num=${article.BOARD_NUM }&page=${page }">[삭제]</a>
		<a href="boardList.bo?page=${page }">[목록]</a>
	</section>
	${str }
</body>
</html>