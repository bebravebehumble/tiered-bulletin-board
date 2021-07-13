package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//쓰기창을 여는 게 1차 목표
		//쓰기창에서 작성하려는 글이 어느 글의 참조와 들여쓰기 깊이, 순서를 가지고 있느냐를 조회
		ActionForward fo = null;
		BoardBean article = new BoardBean();
		fo = new ActionForward();
		int board_num = Integer.parseInt(req.getParameter("board_num"));
		
		BoardDetailService bSer = new BoardDetailService();
		article = bSer.getArticle(board_num, "reply");
		//게시물 번호를 전달하면 게시물 1개의 내용을 객체로 전달
		//게시물 하나의 정보 전달
		//BoardDetailService의 getArticle에서는
		//게시물 하나를 만들기 위해 오라클과의 커넥션을 생성하고
		//싱글톤으로 dao 객체를 얻어온 후,
		//dao에서 쿼리문 처리를 할 수 있도록 커넥션을 전달함
		//게시물을 누르면 해당 게시물의 조회수를 1 증가시키고
		//게시물 내용을 불러옴

		req.setAttribute("article", article);
		fo.setPath("/board/qna_board_reply.jsp");
		//qna_board_view.jsp에는 제목, 내용, 파일 등의 정보만 남아있음
		//답변을 처리하기 위해서는 ref(참조), lev(들여쓰기 깊이), seq(순서) 값이 필요
		//다시 읽어서 해당 값을 hidden으로 처리
		return fo;
	}

}
