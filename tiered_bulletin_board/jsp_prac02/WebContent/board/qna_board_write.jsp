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
		<h2>게시글 등록</h2>
		<form action="boardWritePro.bo" method="post" name="boardform">
			<table>
				<tr>
					<td class="td_left">글쓴이</td>
					<td class="td_right">
						<input type="text" name="BOARD_NAME"
						required="required">
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
						required="required">
					</td>
				</tr>
				<tr>
					<td class="td_left">내용</td>
					<td class="td_right">
						<textarea name="BOARD_CONTENT" cols="40"
						rows="15" required="required"></textarea>
					</td>
				</tr>
			</table>
			<section id="commandCell">
				<input type="submit" value="등록">&nbsp;&nbsp; <input
					type="reset" value="다시쓰기">
			</section>
		</form>
	</section>
</body>
</html>