package service;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardReplyProService {

	public boolean replyArticle(BoardBean article) throws Exception {
		boolean isRe = false;
		int insertCount = 0;
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		insertCount = bDAO.insertReplyArtcle(article);

		if (insertCount > 0) {
			JdbcUtil.commit(con);
			isRe = true;
		} else {
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);

		return isRe;
	}

}
