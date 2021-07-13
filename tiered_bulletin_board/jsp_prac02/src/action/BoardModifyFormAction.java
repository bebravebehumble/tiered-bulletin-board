package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int board_num=Integer.parseInt(req.getParameter("board_num"));
		//게시물의 번호를 각 클래스로 전달하기 위해서 변수에 저장
		
		String page = req.getParameter("page");
		
		BoardDetailService bService = new BoardDetailService();
		//새로운 서비스 객체에 접근하기 위한 준비
		
		BoardBean article = bService.getArticle(board_num, "read");
		//서비스 객체를 통해서 getArticle 메소드를 호출하면서 게시물 번호 전달
		//vo.BoardBean import 필요
		
		ActionForward fo = new ActionForward();
		req.setAttribute("article", article);	//게시물 한 개
		req.setAttribute("page", page);	//페이지 정보
		fo.setPath("/board/qna_board_modify.jsp"); //이동할 주소
		
		return fo;
	}

}
