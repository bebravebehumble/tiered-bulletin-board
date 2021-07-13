package action;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardModifyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyProAction implements Action {
	//가져오는 게시물 번호에 해당하는 비밀번호가 입력한 비밀번호와 일치한다면,
	//해당 게시물 수정 처리
	//서비스를 호출하는 것이 모델 2의 기본 구성

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp)
	throws Exception {
		int board_num = Integer.parseInt(req.getParameter("BOARD_NUM"));
		//변수 board_num에 입력한 숫자를 req의 board_num에 저장?
		String page = req.getParameter("page");
		
		BoardModifyProService bmps = new BoardModifyProService();
		//패스워드 전달
		boolean isRightUser
		= bmps.isArticleWriter(board_num, req.getParameter("BOARD_PASS"));
		//(게시물 번호, 사용자가 입력한 패스워드)
		//비밀번호 일치 여부
		//boolean으로 리턴, true=일치, false=불일치
		ActionForward fo = null;
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter(); //출력을 하기 위한 객체 out 생성
		
//		System.out.println("isRightUser:" + isRightUser);

		if (!isRightUser) {
			//isRightUser가 bmps.isArticleWriter(board_num, rea.getParamaber("BOARD_PASS")가 아닐 때
			//비밀번호가 틀리다면
//			System.out.println("들어왔다");

			out.println("<script>");
//			System.out.println("넘어왔나");
			out.println("alert('수정할 권한이 없습니다.');"); //띄우는 창
			//아놔!!!! 괄호 빼먹엇음!!!
			out.println("history.back();");	//전 페이지로 돌아간다는 의미
			out.println("</script>");
		} else {
			//비밀번호가 맞다면
			BoardBean article = new BoardBean();
			article.setBOARD_NUM(board_num);
			article.setBOARD_SUBJECT(req.getParameter("BOARD_SUBJECT"));
			article.setBOARD_CONTENT(req.getParameter("BOARD_CONTENT"));
			//boardBean 객체에서 폼에서 작성한 값을 저장하고
			
			//디비에 반영하기 위해 updateArticle 호출
			//수정한 데이터를 다시 sql로 보내서 저장하는 역할
			boolean isModi = bmps.updateArticle(article);
			//글 작성이 됐는지 안 됐는지 체크
			

			if (!isModi) {
				//정상적으로 처리가 되지 않았다면
				out.println("<script>");
				out.println("alert('수정 실패.');");
				out.println("history.back();");
				out.println("</script>");
			} else {
				//정상적으로 처리가 되었다면
				//스크립트는 페이지에서만 동작
				//그러므로, 페이지를 이동하고 그 페이지에서 스크립트가 동작할 수 있도록
				//str을 전달하고 있음
				String str = "<script>" + "alert('수정 성공.');" + "</script>";
				String encodedString = URLEncoder.encode(str, "UTF-8");
				//바로 전송하면 한글의 경우 깨짐 발생
				//적절한 언어셋으로 변경하여 전송
				
				fo = new ActionForward();
				fo.setRedirect(true);
				fo.setPath("boardDetail.bo?page=" + page
					+ "&board_num=" + board_num + "&str=" + encodedString);
				//읽기 페이지로 이동
			}
		}
		
		return fo;
	}

}
