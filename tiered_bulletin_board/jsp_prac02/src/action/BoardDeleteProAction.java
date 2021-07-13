package action;

import java.io.PrintWriter;
//import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardDeleteProService;
import service.BoardModifyProService;
import vo.ActionForward;

public class BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//게시물 번호, 사용자 입력 비밀번호, 이동한 페이지 번호 필요
		ActionForward fo = null;	//나중에 이동할 페이지 주소와 전달값의 공유 유무 확인
		boolean isDel = false;   //삭제가 됐는지 체크
		int board_num = Integer.parseInt(req.getParameter("board_num"));
		String page = req.getParameter("page");
		String pass = req.getParameter("BOARD_PASS");	//교수님은 이거 따로 빼셨음
		
		//입력한 비밀번호가 일치하는지 확인
		BoardModifyProService bmps = new BoardModifyProService();
		//패스워드 전달
		boolean isRightUser = bmps.isArticleWriter(board_num, pass);
		//(게시물 번호, 사용자가 입력한 패스워드)
		//비밀번호 일치 여부
		//boolean으로 리턴, true=일치, false=불일치

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter(); //출력을 하기 위한 객체 out 생성

		if (!isRightUser) {
			//비밀번호가 틀리다면
			out.println("<script>");
			out.println("alert('삭제할 권한이 없습니다.');");
			out.println("history.back();");	//전 페이지로 돌아간다는 의미
			out.println("</script>");

		} else {
			//비밀번호가 맞다면
			BoardDeleteProService bdps = new BoardDeleteProService(); //난 밖에 빼놨었는데 교수님은 안에 넣으심
//			BoardBean article = new BoardBean();
//			article.setBOARD_NUM(board_num);
			//굳이 주석처리한 코드처럼 할 필요가 없었음 전달하는 값이 board_num 하나니까 그냥 바로 전달하면 되는 거엿음
			//해당 게시물을 삭제하기 위해 deleteArticle
			isDel = bdps.deleteArticle(board_num);
			//글 삭제 처리가 잘 됐는지 안 됐는지 체크

			if (!isDel) {
				//정상적으로 처리가 되지 않았다면
				out.println("<script>");
				out.println("alert('삭제 실패.');");
				out.println("history.back();");
				out.println("</script>");
			} else {
				fo = new ActionForward();
				fo.setRedirect(true);
				fo.setPath("boardList.bo?page=" + page);
				//읽기 페이지로 이동
			}
		}
		
		return fo;
	}

}
