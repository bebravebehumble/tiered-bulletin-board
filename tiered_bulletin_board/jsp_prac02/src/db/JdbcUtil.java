package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcUtil {
//	   Connection conn = null;
//	      Context init = new InitialContext();
//	      DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
//	      conn = ds.getConnection();

	// 앞으로 모델2 게시판에서 디비 연결 처리는 아래 메소드 사용
	// static으로 만들어서 여러 클래스에서 공유
	//getConnection()을 이용하여 연결할 예정
	public static Connection getConnection() throws Exception {
		Connection con = null;
		Context init = new InitialContext();
		//컨텍스트를 초키화시키겠다(initial)
		//context: 문맥, 흐름, 제어권, 프로그램 흐름상 제어권을 가진 객체
		//연결하려는 준비
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		//오라클 자원에 연결할 수 있는 객체 생성
		con = ds.getConnection();
		//conn: 그 객체 ds를 통해 오라클과 자바의 연결 다리를 생성
		con.setAutoCommit(false);
		//자동 커밋 하지 않음

		//한 번 쓰고 복붙할 곳

		return con; //참조타입 객체의 아무것도 없는 값 null을 먼저 넣어놓고
					 //코드를 만든 다음 null값을 conn으로 바꿔 줌
	}
	//데이터 베이스는 외부 자원으로 사용 후, 자원 반납을 해야 함
	//close 메소드는 사용한 연결(커넥션)을 닫아서 자원을 돌려주는 역할(연결한 데이터베이스 반납)
	public static void close(Connection con) {  //연결 끝나고 닫는 역할
		try {	//throws해 줘도 됨
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//import sql.Statement
	//Statement stmt: 쿼리문을 담아서 실행해 주는 객체
	//statement: 성명서
	//여러 처리를 하기 위해서 메소드로 만들어놓음(쿼리문을 닫아야 하는 상황마다 메소드로 처리)
	//안 그러면 쿼리문을 닫아야 할 때마다 아래 코드를 계속 작성해야 함
	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//실행된 쿼리문의 결과를 담는 ResultSet
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//쿼리문 실행 완료
	public static void commit(Connection con) {
		try {
			con.commit();	//sql에서 봤던 commit, 핵심(최종 승인)
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//commit의 반대, 전달된 쿼리문의 실행 취소
	public static void rollback(Connection con) {
		try {
			con.rollback();  //처음으로 돌아감
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
