package action;
import java.util.ArrayList;

//목록 띄우기
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action {
	//액션에서 implements를 받음

	@Override
	public ActionForward execute(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		//다형성
		//request: 요청	(응답하는 페이지까지 값이 유지가 됨)
		//response: 응답
		//웹은 요청과 응답의 한 사이클로 작업 종료
		
		ArrayList<BoardBean> articleList = new ArrayList<>();
		//게시물 목록 한줄한줄을 담을 컬렉션 프레임 워크
		ActionForward forward = new ActionForward();
		String str = null;
		int page = 1;	//현재 페이지 초기값(처음으로 보여줄 페이지-최근 등록순)
		int limit = 10; //한 페이지당 보여줄 게시물 수
		
		if (req.getParameter("page") != null) {	//값의 유효 범위(jsp 2일차 내용)
			page = Integer.parseInt(req.getParameter("page"));
		}  //페이지가 널이 아니라면 변수 page에 값 할당
		//목록에는 폼(입력창)이 없음
		//qna_board_list에서 요청이 들어오면 이곳에서 응답함
		//페이지를 클릭했을 때 그 값을 page라는 이름으로 하고, 그 값이 있다면
		//page(초기화되어있는 값)을 그 값으로 변경함
		
		BoardListService bService = new BoardListService();
		articleList = bService.getArticleList(page, limit);
		//page, limit 값을 전달해줘야 페이지가 바뀜(매개변수 두 개)
		
		int listCount = bService.getListCount(); //총 게시물 수
		//총 게시물에 따라 페이지 수를 설정해야 하기 때문에 총 게시물 수가 필요함
		
		int maxPage = (int) ((double) listCount / limit + 0.9);	//페이지의 최대
		//예를 들어서 총 게시물이 161개가 있다면(17페이지까지 보여줘야 함 16.1 >> 17)
		//총 게시물 수(listCount)를 double로 소수점까지 표시한 후(우선 순위), limit로 나누고
		//그 값에 0.9를 더해서 일의 자리를 올려줌. 계산을 한 후에 정수값(int)으로 형변환
		//0.9를 더해주는 이유는 0.1부터 0.9까지의 소수점을 전부 남겨둘 수 없기 때문
		//총 페이지는 총 게시물 수(listCount)를 페이지당 게시물(limit)로 나눈 것의 반올림
		//이유는 소수점 페이지는 없고, 게시물 소수점은 반올림이 되어야 함
		//만약 총 게시물이 153개라면 15.3 >> 16
		//15페이지로 보여줄 수 있는 것 외에 3개가 더 있다는 뜻이므로,
		//페이지는 16개가 되어야 함(게시물 3개를 안 보여줄 수는 없으므로 무조건 반올림처리)
		
		int startPage = (((int) ((double) page / 5 + 0.9)) -1 ) * 5 + 1;
		//1.0 >> 1.1 >> 0 >> 1
		//시작 페이지를 구하는 공식
		//시작 페이지도 계속 변화하니까 공식 적용
		//page는 현재 페이지
		//위의 5(page 뒤)는 한 페이지에 보여줄 페이지수, 게시물 수 아님, 예) 1~5
		//그니까 총 페이지 수가 1832923813개가 있으면 한 페이지 아래에 다 표시할 수 없으므로
		//1~5까지만 보여준다는 뜻(게시물 목록 아래에 목록 페이지 1 2 3 4 5)(5개까지만 보여준다는 뜻)
		//161부터 보여줘야 함
		//선택 페이지가 변경돼도 시작 페이지를 일정하게 보여주기 위함
		
		int endPage = startPage + 5 - 1;	//1, 6, 11, ...
		//여기서의 5는 한 페이지에 보여줄 페이지 수
		//한 화면에 보여줄 마지막 번호이므로, 5라는 수를 바꾸면 한 페이지에 나타나는 숫자 범위가 달라짐
		//한 화면에 보이는 마지막 페이지 번호
		//maxPage와 endPage는 다르다.
		//int endPage = startPage + 4;
		//되기는 하지만, 보여지는 페이지 개수를 파악하기 번거로우므로,
		//위의 식을 사용하는 것이 바람직할 수 있음
		
		if (endPage > maxPage)	//endPage: 5, 10, 15, 20, ...
			endPage = maxPage;
		//만약 maxPage가 13일 때, endPage가 15라면, 2페이지는 비어있게 되므로
		//endPage를 maxPage로 바꿔줌
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setEndPage(endPage);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setPage(page);
		pageInfo.setStartPage(startPage);
		
		req.setAttribute("pageInfo", pageInfo);
		//pageInfo를 pageInfo라는 이름의 속성으로 보내겠다
		req.setAttribute("articleList", articleList);
		//ArrayList에 담긴 값을 articleList라는 이름으로 웹페이지에 전달
		forward.setPath("/board/qna_board_list.jsp");
		//목록 페이지 호출
		
		return forward;
	}
//페이지를 바꿀 때마다 DAO에서 보여줄 내용 찾기
}
