package service;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;

public class BoardDeleteProService {

	public boolean deleteArticle(int board_num) throws Exception {
		//dao 객체를 생성하고 게시물 삭제 처리를 하기 위한 메소드
		
		boolean isdel = false;
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		//커넥션 얻어서 초기화해주고
		
		//db로 접근
		int delCnt = bDAO.deleteArticle(board_num);
		//게시물 정보를 전달해서 디비 처리해 주는 메소드
		//int로 받은 이유: executeUpdate를 할 때 int값으로 정해짐(레코드 개수)
		//처리가 됐는지 안 됐는지 확인
		
		if (delCnt > 0) {
			JdbcUtil.commit(con);
			isdel = true;
		} else {
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		
		return isdel;
	}

}
