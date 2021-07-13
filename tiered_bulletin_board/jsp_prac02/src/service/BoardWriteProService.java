package service;

import java.sql.Connection;
//sql의 내용 import

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardWriteProService {

	public boolean registArticle(BoardBean bo) throws Exception {
		//객체를 받아서 db에 등록하는 역할
		//액션에서 전달해 준 게시물의 내용(boardBean bo)를 이용하여
		//db에 등록하기
		//처리 결과 성공 혹은 실패 리턴
		boolean isWriteSuccess = false;
		Connection con = JdbcUtil.getConnection();	//싱글톤: 객체 1개 생성 후 공유
		//오라클과 연결
		BoardDAO bDAO = BoardDAO.getInstance();	//싱글톤: 객체를 한 개만 만들어서 공유
		//dao: database access object
		//디비에 쿼리문 전달 insert...
		
		bDAO.setConnection(con);
		//위에서 얻어진 con를 받아서 초기화
		//서비스에서 생성된 커넥션을 boardDAO 객체에 전달
		int insertCount = bDAO.insertArtcle(bo);
		//BoardDAO에서 executeUpdate 결과, 처리된 개수를 리턴
		//즉 1 리턴
		
		if (insertCount > 0) {
			JdbcUtil.commit(con);	//최종 승인
			isWriteSuccess = true;
		} else {
			JdbcUtil.rollback(con);	//쿼리문 동작 이전으로 돌림
		}
		
		JdbcUtil.close(con);
		
		return isWriteSuccess;	//이걸 해야 값이 리턴돼서 화면에 쓰기 성공
	}

}
