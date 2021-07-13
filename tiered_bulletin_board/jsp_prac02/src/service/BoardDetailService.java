package service;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardDetailService {

	public BoardBean getArticle(int board_num, String mode) throws Exception {
		//게시물 하나를 불러오기 위해 
		BoardBean article = null;
		Connection con = JdbcUtil.getConnection();
		//db.JdbcUtil import 필요
		//오라클과의 커넥션 만들기
		
		BoardDAO bDAO = BoardDAO.getInstance();
		//싱글톤으로 dao 객체 얻어오기
		
		bDAO.setConnection(con);
		//dao에서 쿼리문 처리를 할 수 있도록 커넥션 전달
		
		if (mode.equals("read")) {	//글 읽기라면
			int cnt = bDAO.updateReadCount(board_num);
			//조회수에 대한 처리(조회수 증가) 1 업데이트
			//쿼리문을 작성하여 sql에 보낼 것이기 때문에 BoardDAO에서 처리
			if (cnt > 0) {
					JdbcUtil.commit(con);
			} else {
				JdbcUtil.rollback(con);
			}
		}
		
		article = bDAO.selectArticle(board_num);
		//게시물 내용 가져오기(pk에 해당하는 게시물)
		JdbcUtil.close(con);
		
		return article;
	}
	
}
