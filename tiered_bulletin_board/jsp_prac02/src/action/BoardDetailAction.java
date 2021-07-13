package action;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//글 읽기 처리는 게시물의 pk를 이용하여 디비에서 해당 게시물 정보를 가져오고,
		//그것을 boardBean 객체에 담은 후에, 글쓰기 폼과 비슷한 형식에 해당 객체를 보내서 내용 표시
		
		int board_num
		=Integer.parseInt(req.getParameter("board_num"));
		//게시물의 번호를 각 클래스로 전달하기 위해서 변수에 저장
		
		String page = req.getParameter("page");
		
		BoardDetailService bService = new BoardDetailService();
		//새로운 서비스 객체에 접근하기 위한 준비
		
		BoardBean article = bService.getArticle(board_num, "read");
		//서비스 객체를 통해서 getArticle 메소드를 호출하면서 게시물 번호 전달
		//vo.BoardBean import 필요
		
		//BoardModifyProAction에서 주소에 보내는 str에 관한 처리
		String str = null;
		if (req.getParameter("str") != null) {
			str = req.getParameter("str");
			String decodedString = URLDecoder.decode(str, "UTF-8");
			req.setAttribute("str", decodedString);
		}
		
		ActionForward fo = new ActionForward();
		req.setAttribute("article", article);	//게시물 한 개
		req.setAttribute("page", page);	//페이지 정보
		fo.setPath("/board/qna_board_view.jsp"); //이동할 주소
		
		return fo;
	}

}
