<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#lofinFormArea {
	text-align: center;
	width: 250px;
	margin: auto;
	border: 1px solid red
}

h1 {
	text-aligen: center;
}
</style>
</head>
<body>
	<h1>로그인</h1>
	<section id="loginFormArea">
		<form action = "login" method = "get">
			<!-- action 속성은 폼의 입력 결과를 처리할 페이지를 지정
			method는 전송 방식, post 방식도 있음 -->
			<label id="id">아이디 :</label>
			<input type = "text" name = "id" id="id"/><br>
			<label id="passwd">비밀번호: </label>
			<input type = "password" name="passwd" id="passwd"><br><br>
			<input type = "submit" value = "로그인" />
		</form>
	</section>
</body>
</html>