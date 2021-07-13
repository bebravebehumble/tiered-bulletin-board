package service;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardModifyProService {

	public boolean isArticleWriter(int board_num, String pass) throws Exception {
		//전달받은 값을 다시 전달해줌
		//게시물 번호와 패스워드를 받아서
		//디비에 게시물 번호가 일치하는 항목의 비밀번호를 가져와서
		//사용자가 입력한 비밀번호와 같은지 비교
		boolean isArticleWriter = false;
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		
		isArticleWriter = bDAO.isArticleBoardWriter(board_num, pass);
		JdbcUtil.close(con);
		
		return isArticleWriter;
	}
	//게시물에서 작성한 내용이 article로 넘어옴
	public boolean updateArticle(BoardBean article) throws Exception {
		boolean isModi = false;
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		//커넥션 얻어서 초기화해주고
		
		//db로 접근
		int upCnt = bDAO.modifyArticle(article);
		//게시물 정보를 전달해서 디비 처리해 주는 메소드
		//int로 받은 이유: executeUpdate를 할 때 int값으로 정해짐(레코드 개수)
		//처리가 됐는지 안 됐는지 확인
		
		if (upCnt > 0) {
			JdbcUtil.commit(con);
			isModi = true;
		} else {
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		
		return isModi;
		//작성 결과를 가지고 리턴
	}

}
