package action;
//메소드의 선언부를 기재
//인터페이스?
//1. 규격을 맞추려는 목적
//2. 다형성

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public interface Action {
	public ActionForward execute(HttpServletRequest req,
			HttpServletResponse resp) throws Exception;
	//execute 메소드 선언
	// 클라이언트의 요청을 받아 처리하고
	// 응답을 ActionForward 객체로 리턴
	// ActionForward는 이동 여부, 이동할 url을 가지고 있음
	//요청을 하면 응답하고 리턴을 할 때 ActionForward를 이용하여 작동함
}
