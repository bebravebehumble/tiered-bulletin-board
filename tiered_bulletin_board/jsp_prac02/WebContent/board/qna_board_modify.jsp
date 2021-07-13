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
		<h2>게시글 수정</h2>
		<form action="boardModifypro.bo" method="post" name="boardform">
		
		<input type="hidden" name="BOARD_NUM" value="${article.BOARD_NUM }" />
		<input type="hidden" name="page" value="${page }">
		<!-- 특정 게시물 정보 hidden:폼에 전달하여 다음 페이지로 넘어가지만, 사용자에게 보이지 않는 값 -->
			<table>
				<tr>
					<td class="td_left">글쓴이</td>
					<td class="td_right">
						<input type="text" name="BOARD_NAME"
						value="${article.BOARD_NAME }" required="required">
					</td>
				</tr>
				<tr>
					<td class="td_left">비밀번호</td>
					<td class="td_right">
						<input type="password" name="BOARD_PASS"
						required="required">
					</td>
				</tr>
				<tr>
					<td class="td_left">제목</td>
					<td class="td_right">
						<input type="text" name="BOARD_SUBJECT"
						required="required" value="${article.BOARD_SUBJECT }">
					</td>
				</tr>
				<tr>
					<td class="td_left">내용</td>
					<td class="td_right">
						<textarea name="BOARD_CONTENT" cols="40"
						rows="15" required="required">${article.BOARD_CONTENT }</textarea>
					</td>
				</tr>
			</table>
			<section id="commandCell">
			<!-- 자바스크립트는 웹브라우저 기반의 언어로서 정적인 정보에 동적인 효과 표시 -->
				<a href="javascript:modifyboard()">수정</a>
				<!-- 해당 메소드 호출 -->
				&nbsp;&nbsp;
				<a href="javascript:history.go(-1)">뒤로</a>
				<!-- 히스토리 객체를 이용하여 이전 페이지로 이동 -->
			</section>
		</form>
	</section>
</body>
<script>
	function modifyboard() {
		boardform.submit();
	}
</script>
</html>