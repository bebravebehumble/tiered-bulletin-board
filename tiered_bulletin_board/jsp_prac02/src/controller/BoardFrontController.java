package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardDeleteProAction;
import action.BoardDetailAction;
import action.BoardListAction;
import action.BoardModifyFormAction;
import action.BoardModifyProAction;
import action.BoardReplyFormAction;
import action.BoardReplyProAction;
import action.BoardWriteProAction;
import vo.ActionForward;


@WebServlet("*.bo")
//무슨 메소드든 boardcontroller가 처리
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BoardFrontController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest req
			, HttpServletResponse response)
					throws ServletException, IOException {
				req.setCharacterEncoding("UTF-8");
				//한글 깨짐 방지
				String RequestURI = req.getRequestURI();
				//localhose:9090/HRD_36/home.jsp
				//localhost:9090/jsp.prac02/0000.000.bo
				//getRequestURL; 전송된 URI를 가져옴
				String contextPath = req.getContextPath();
				//localhost:8090/HRD_36/
				//localhost:9090/jsp.prac02/
				//contextpath로 가져옴
				String command
				= RequestURI.substring(contextPath.length());
				//request 문장을 읽어와서 0000.000.bo를 가져옴 (실제 전달되는 부분)
				//home.jsp
				
				//url에서 사용할 주소만 걸러내는 작업
				
				
				//요청 처리 부분
				ActionForward forward = null;
				Action action = null;
				
				//게시물 등록이라는 요청
				if (command.equals("/boardWriteForm.bo")) {
					forward = new ActionForward();
					//isRedirec = false, path = null
					//forward 객체 생성
					forward.setPath("/board/qna_board_write.jsp");
					//게시물 등록 양식만 보여줌(form)
				}
				//boardWritePro.bo
				else if (command.equals("/boardWritePro.bo")) {
					//게시물 쓰기 처리(process)
					action = new BoardWriteProAction();	//boardwriteproaction에 전송
					try {
						forward = action.execute(req, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	//모델 2 처리 순서
				
				//boardList.bo
				else if (command.equals("/boardList.bo")) {	//명령이 전달됐다면
					//목록 보여주기(process)
					action = new BoardListAction();
					try {
						forward = action.execute(req, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//boardDetail.bo(제목 부분에 링크 거는 처리)
				else if (command.equals("/boardDetail.bo")) {
					//게시물 읽기 처리(process)
					action = new BoardDetailAction();
					try {
						forward = action.execute(req, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//수정 부분(boardModifyForm.bo)
				else if (command.equals("/boardModifyForm.bo")) {
					action = new BoardModifyFormAction();
					try {
					forward = action.execute(req, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//boardModifypro.bo
				else if (command.equals("/boardModifypro.bo")) {
					action = new BoardModifyProAction();
					try {
					forward = action.execute(req, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//boardReplyForm.bo
				else if (command.equals("/boardReplyForm.bo")) {
					//게시물 답변 처리(폼 나타내기)
					action = new BoardReplyFormAction();
					try {
						forward = action.execute(req, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//boardReplyPro.bo
				else if (command.equals("/boardReplyPro.bo")) {
					//게시물 답변 처리
					action = new BoardReplyProAction();
					try {
						forward = action.execute(req, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//boardDeleteForm.bo
				else if (command.equals("/boardDeleteForm.bo")) {
					//게시물 삭제 처리폼
					//삭제하려는 사람이 비밀번호를 알고 있는가?
					int board_num = Integer.parseInt(req.getParameter("board_num"));
					//게시물의 번호를 각 클래스로 전달하기 위해서 변수에 저장
					String page = req.getParameter("page");
					req.setAttribute("board_num", board_num);	//게시물 번호 정보
					req.setAttribute("page", page);  //페이지 정보
					//사용자에게 비밀번호 입력을 유도
					//비밀번호 입력하는 창 띄우기
					forward = new ActionForward();
					//isRedirec = false, path = null
					//forward 객체 생성
					forward.setPath("/board/qna_board_delete.jsp"); //이동 주소
					//게시물 삭제 후에 보던 페이지로 이동
					//현재 페이지, 게시물 번호 값을 가져가야 함
					//어떤 게시물의 비밀번호인지 확인하기 위한 게시물 번호도 전달
				}
				
				//boardDeletePro.bo
				else if (command.equals("/boardDeletePro.bo")) {
					//게시물 삭제 처리
					action = new BoardDeleteProAction();
					try {
						forward = action.execute(req, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				
				if (forward != null) {
					if (forward.isRedirect()) {
						response.sendRedirect(forward.getPath());
						//전달값 없이 페이지 이동
						//다음 페이지에 값 유지 X
					} else {
						RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
						//해당 페이지로 이동할 준비(위의 setPath)
						//이동할 객체를 가져와서 객체 생성
						dispatcher.forward(req, response);
						//요청과 응답(공유할 값) 가지고 페이지 이동
						//다음 페이지까지 값을 유지하는 것
						//해당 주소로 공유할 값() 가지고 페이지 이동
						
					}
				}
	}

}
