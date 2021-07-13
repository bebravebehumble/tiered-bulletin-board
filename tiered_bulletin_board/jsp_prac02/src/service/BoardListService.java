package service;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardListService {

	public ArrayList<BoardBean> getArticleList(int page, int limit) throws Exception {

		ArrayList<BoardBean> aList = null;
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		
		aList = bDAO.selectArticleList(page, limit);
		//괄호 속에 page, limit 추가
		JdbcUtil.close(con);

		return aList;
	}

	//int값으로 총 게시물 수 찾는 메소드
	public int getListCount() throws Exception {
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		
		int listCount = 0;
		listCount = bDAO.selectListCount();
		JdbcUtil.close(con);
		
		return listCount;
	}

}
