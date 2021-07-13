<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@page import="javax.sql.*"%>
<%@page import="javax.naming.*"%>
<%@page import="java.sql.*"%>
<%
   Connection conn = null; //Connection: 연결, conn: Connection의 객체
   try {
      Context init = new InitialContext();
      //Initial: 초기화
      //Context는 xml 파일(경로: WebContent  >>  META-INF  >>  context.xml)
      //context: 문맥 이라는 뜻
      DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
      //ds: DataSource(데이터 소스)의 객체
      //init: Context의 InitialContext 타입 객체
      //괄호 안의 내용을 찾기
      conn = ds.getConnection();
      out.print("<h3>연결 되었습니다.</h3>");
   } catch (Exception e) {	//오류가 있다면
      out.print("<h3>연결에 실패하였습니다.</h3>");
   	  //연결에 실패하였습니다 문구 띄우기
      e.printStackTrace();
   }
%>
