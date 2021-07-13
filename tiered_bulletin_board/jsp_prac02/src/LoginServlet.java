

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//직렬화
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		String id = request.getParameter("id"); //요청 id라는 이름으로 전달하는 내용을 id에 저장
		String passwd = request.getParameter("passwd");	//요정: passwd라는 이름으로 전달하는 내용을 저장
		//요청에 관한 처리
		
		//아래쪽 부분은 응답 화면 만들기
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); //화면을 뿌리기 위한 부분
		out.println("아이디=" + id + "<br>");
		//ln도 줄바꿈인데 br은 왜...?
		//출력 코드는 ln으로 했지만, 웹 브라우저에서는 <br> 태그를 주지 않으면 인식을 하지 못함
		out.println("비밀번호=" + passwd + "<br>");		//화면에 출력하는 내용
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
