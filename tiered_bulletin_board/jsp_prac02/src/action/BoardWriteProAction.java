package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardWriteProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ActionForward forward = null;
		BoardBean boardBean = null;
		// 게시물의 작성 내용(제목, 내용, 작성자, 작성일 등등)을 담아서 이동할 객체!
		boardBean = new BoardBean();
		boardBean.setBOARD_NAME(req.getParameter("BOARD_NAME"));
		boardBean.setBOARD_PASS(req.getParameter("BOARD_PASS"));
		boardBean.setBOARD_SUBJECT(req.getParameter("BOARD_SUBJECT"));
		boardBean.setBOARD_CONTENT(req.getParameter("BOARD_CONTENT"));
		// 각각 하면 이동하기 불편하므로 객체를 생성해서 내용을 저장을 시켜 한 번에 전달
		// 사용자가 입력한 내용으로 BoardBean 객체 초기화

		BoardWriteProService bo = new BoardWriteProService();
		// 폼에서 쓴 내용을 가져가서 전달하는 역할
		// BoardWriteProService에는 registArticle 메소드가 있어야 함
		boolean isWriteSuccess = bo.registArticle(boardBean);
		// 게시물 내용을 등록하기 위한 메소드
		// boolean isWriteSuccess = false;
		// 일부러 false를 리턴해서는 인 되는 상황 연출
		// isWriteSuccess: 잘 됐으면 true, 잘 안 됐으면 false

		if (!isWriteSuccess) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>");
			out.println("alert('쓰기 실패')");
			out.println("history.back();");
			out.println("</script>");
			//쓰기가 잘못됐다면, 안내창을 보이고 쓰기창으로 이동
		} else {
			//쓰기가 성공했다면,
			//forward 객체를 생성하여 리다이렉트 시키고,
			//이동할 주소는 게시판 목록 보기
			forward = new ActionForward();	//forward 객체 생성
			forward.setRedirect(true);	//리다이렉트
			forward.setPath("./boardList.bo");	//이동할 주소
			//controller에서의 from(입력창)만 띄울 때 코드와 비슷함
		}

		return forward;
		//성공했을 때 결과 리턴하기
	}

}
