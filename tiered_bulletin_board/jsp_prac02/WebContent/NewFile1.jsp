<!-- 동적인 값(새로고침을 할 때마다 변경되는 값), 모델 1 방식 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> <!-- page 지시어 -->
<!-- 톰캣을 설치하면, 톰캣 내부에 servlet-api.jar 파일이 추가됨 -->
<!-- 자바의 기본 제공 클래스에는 웹에 관한 처리를 위한 클래스를 제공하지 않음
    그러므로, 해당 클래스를 build path로 추가하여 컴파일 시 이용하도록 알려줌-->
    <!-- 홈페이지가 어떻게 만들어졌는지 -->
<!DOCTYPE html>
<%@page import="java.util.Calendar"%>
<!-- 칼렌더 클래스 임포트 -->
<html>
<head>
<%
//scriptlet 스클립틀릿: 웹 페이지에 보여줄 정보 구현(구현부)
Calendar c = Calendar.getInstance();
int hour = c.get(Calendar.HOUR_OF_DAY);
int minute = c.get(Calendar.MINUTE);
int second = c.get(Calendar.SECOND);
%>
<meta charset="UTF-8">
<title>현재 시각</title>
</head>
<body>
	현재 시각은
	<!-- 표현부(표시부) -->
	<%=hour%>시
	<%=minute%>분
	<%=second%>초입니다.
</body>
</html>