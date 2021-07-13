package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import db.JdbcUtil;
import vo.BoardBean;

public class BoardDAO {
	// 쿼리문을 생성하여 오라클에 전달하는 역할
	DataSource ds;
	Connection con;
	private static BoardDAO boardDAO;
	// 싱글톤으로 객체 생성
	// 여러 개 만들지 않는 이유: 디비 오라클은 서로 다른 시스템. 두 개가 서로 연결하기 위해서는 통로가 하나밖에 없음
	// 첫 번쩨 BoardDAO는 자료형, 두 번째 boardDAO는 객체 변수
	//BoardDAO 설계도로 만든 boardDAO 객체
	
	private BoardDAO() {		
	}
	
	public static BoardDAO getInstance() {
		//singleton 1개의 객체 공유
		if (boardDAO == null) {
			boardDAO = new BoardDAO();
		}
		return boardDAO;
	}
	
	public void setConnection(Connection con) {
		//connection을 받아서 초기화
		this.con = con;
	}
	
	//실제로 쿼리문을 만들어서 전달하는 부분
	//폼에서 작성한 게시물을 오라클에 저장하는 처리
	public int insertArtcle(BoardBean article) {
		PreparedStatement pstmt = null;
		//자바에서 생성된 쿼리문을 db로 전달할 수 있는 객체 변수(pstmt) 생성
		//객체 이름을 바꿀 수는 있지만 바꿀 일이 없음
		String sql = "insert into board values("
		+ "(select nvl(max(board_num),0)+1 from "
		+ "board),"
		+ "?,?,?,?,?,(select nvl(max(board_num),0)+1 "
		+ "from " + "board),?,?,?,sysdate)";
		
//		String sql = "insert into board values(" 
//			      + "seq_board.NEXTVAL,"
//			      + "?,?,?,?,?,seq_board.NEXTVAL,?,?,?,sysdate)";
		//시퀀스를 대신하는 서브쿼리 이용
		//sql은 문자열
		//nvl(max(board_num),0)+1: 시퀀스(일련번호)를 쓰지 않는 부분
		//한 줄로 써도 되지만 너무 길어서 나눠놓음
		//괄호와 따옴표 주의
		//+는 문장을 연결해주는 역할
		//?: 아래의 순서대로 ? 안에 넣는 것
		//System.out.print(sql); 로 검증해볼 수 있음
		int insertCount = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBOARD_NAME());
			pstmt.setString(2, article.getBOARD_PASS());
			pstmt.setString(3, article.getBOARD_SUBJECT());
			pstmt.setString(4, article.getBOARD_CONTENT());
			pstmt.setString(5, "");
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 0);
			//쿼리문 만들어주는 부분
			
			insertCount = pstmt.executeUpdate();	//insert
			//실제로 동작하는 부분
			//등록된 레코드 개수 리턴
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			//처리가 끝났을 시 종료
		}
		
		return insertCount;
	}

	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		PreparedStatement pstmt = null;	//쿼리문 전달을 위한 객체
		ResultSet rs = null;	//쿼리 수행 결과 얻어지는 여러 개의 집합을 가리킴 결과 집합(Resultset)
		String sql = "select * from (select p.*, row_number() "
		+ "over (order by board_re_ref desc, "
		+ "board_re_seq) as rnum from board p) "
		+ "where rnum between ? and ?";
		//rnum이 어디에서 어디까지 해당하는지
		//게시물의 추출이 조건이 없을 때는 모두 보여졌지만,
		//조건을 주므로 해서 일부만 표시하게 변

		//from절의 서브쿼리 게시물에서의 p의 모든 것과, 게시물 번호 값을 구하기 이전에 내림차순 오름차순 정렬
		//서브쿼리 내에서의 row_number() 게시물 행 번호
		//over(order by ...) 결과 표시 전, 주어진 조건으로 정렬 후 표시
		//답변형 게시물을 위한 쿼리문
		//답글의 참조(원글이 무엇인지), 답글의 순서(순서는 어떻게 처리할 것인지)
		
		//System.out.println("sql:"+sql);
		ArrayList<BoardBean> aList = new ArrayList<>();
		BoardBean bRow = null;
		int startRow = (page - 1) * 10 + 1;
		//읽기 시작할 row 번호
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow); //예) 2페이지, 11~20, 11
									   //page가 8이라면, 71(한 페이지에 보이는 시작 페이지)
			pstmt.setInt(2, page * limit);  //예 2*10=20
			rs = pstmt.executeQuery(); //select insert 시에는 update, select 시에는 query
			
			while (rs.next()) {
				bRow = new BoardBean();
				bRow.setBOARD_NUM(rs.getInt("board_num"));
				bRow.setBOARD_NAME(rs.getString("board_name"));
				bRow.setBOARD_SUBJECT(rs.getString("board_subject"));
				bRow.setBOARD_CONTENT(rs.getString("board_content"));
				bRow.setBOARD_FILE(rs.getString("board_file"));
				bRow.setBOARD_RE_REF(rs.getInt("board_re_ref"));
				bRow.setBOARD_RE_LEV(rs.getInt("board_re_lev"));
				bRow.setBOARD_RE_SEQ(rs.getInt("board_re_seq"));
				bRow.setBOARD_READCOUNT(rs.getInt("board_readcount"));
				bRow.setBOARD_DATE(rs.getDate("board_date"));
				//숫자는 int, 문자는 String, 날짜는 date
				
				aList.add(bRow);	//arrayList에 추가
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);	//먼저 닫고
			JdbcUtil.close(pstmt);	//그 다음에 닫음
		}
		
		return aList;
	}

	//총 게시물 수 구함
	public int selectListCount() {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(board_num) from board";
		//전체 게시물 개수를 찾는 쿼리문
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				listCount = rs.getInt(1);
				//값을 읽어올 때 첫 번째 값을 읽어온다는 뜻
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return listCount;
		//listCount값을 호출한 곳으로 보내기
	}

	public int updateReadCount(int board_num) {
		PreparedStatement pstmt = null;
		int cnt = 0;
		String sql = "update board set board_readcount="
		+ "board_readcount+1 where board_num=?";
		//해당 게시물로 찾아가서 1 증가
		//board_readcount의 기본값은 0(조회수)
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			cnt = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return cnt;
	}
	
	public BoardBean selectArticle(int board_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardBean bRow = null;
		String sql = "select * from board where board_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery(); //select이기 때문에 query

			if (rs.next()) {
			bRow = new BoardBean();	//객체를 담고
			bRow.setBOARD_NUM(rs.getInt("board_num"));
			bRow.setBOARD_NAME(rs.getString("board_name"));
			bRow.setBOARD_SUBJECT(rs.getString("board_subject"));
			bRow.setBOARD_CONTENT(rs.getString("board_content"));
			bRow.setBOARD_FILE(rs.getString("board_file"));
			bRow.setBOARD_RE_REF(rs.getInt("board_re_ref"));
			bRow.setBOARD_RE_LEV(rs.getInt("board_re_lev"));
			bRow.setBOARD_RE_SEQ(rs.getInt("board_re_seq"));
			bRow.setBOARD_READCOUNT(rs.getInt("board_readCount"));
			bRow.setBOARD_DATE(rs.getDate("board_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return bRow; //돌려줌
	}

	public boolean isArticleBoardWriter(int board_num, String pass) {
		//게시물과 패스워드를 받음
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select board_pass from board " + "where board_num=?";
		//sql에 전달할 쿼리문을 저장할 문자열 변수 sql
		//조회한 패스워드
		boolean isWriter = false;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			//쿼리문을 구동하여 결과를 rs(커서) 객체에 전달
			rs.next(); //실제값 가리키기
			
			if (pass.equals(rs.getString("board_pass"))) {
				//입력받은 pass와 조회한 board_pass 비교
				isWriter = true;
				//결과적으로 비밀번호가 맞으면 true, 틀리면 false 리턴
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return isWriter; //결과 service에 리턴
	}

	public int modifyArticle(BoardBean article) {
		int upCnt = 0;
		PreparedStatement pstmt = null;
		String sql = "update board set board_subject=?, "
		+ "board_content=? where board_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBOARD_SUBJECT());
			//첫 번째 물음표 안에 들어가는 것
			pstmt.setString(2, article.getBOARD_CONTENT());
			//두 번째 물음표 안에 들어가는 내용
			pstmt.setInt(3, article.getBOARD_NUM());
			//세 번째 물음표 안에 들어가는 정보
			upCnt = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}

		return upCnt;
	}

	public int insertReplyArtcle(BoardBean article) {
		//원글 답글, 답글 답글
		//새로 쓰기도 하지만, 끼워넣기도 함
		//폼화면에서 작성한 내용(article)
		PreparedStatement pstmt = null;
		String sql = "";
		int insertCount = 0;
		int re_ref = article.getBOARD_RE_REF();	//참조
		int re_lev = article.getBOARD_RE_LEV();	//들여쓰기
		int re_seq = article.getBOARD_RE_SEQ();	//정렬 순서
		
		try {
			sql = "update board set BOARD_RE_SEQ="
			+ "BOARD_RE_SEQ+1 where "
			+ "BOARD_RE_REF=? and "
			+ "BOARD_RE_SEQ>?";
			//이미 다른 답글이 있는 상태에서 참조가 같고
			//현재 선택한 게시물보다 순서가 크다면
			//순서값을 1씩 모두 증가
			//1, 2, 3, ...게시물이 있는데 1과 2 중간에 답글 게시물이 생기면
			//1, 2, 2, 3, ...이 되므로
			//끼어들기 이후의 숫자들은 +1씩 해 주어서
			//1, 2, 3, 4, ...처럼 번호가 매끄럽게 되도록 해 줌
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_seq);
			int updateCount = pstmt.executeUpdate();
			
			if (updateCount > 0) {
				JdbcUtil.commit(con); //디비에 변환값 반영
			}
			JdbcUtil.close(pstmt);
			//순서에 관한 정리

			re_seq = re_seq + 1; //선택한 글보다 순서 1 증가(답글은 원글에 +1한 번호로 들어감)
			re_lev = re_lev + 1; //선택한 글보다 1칸 더 들여쓰기
			sql = "insert into board values ("
			+ "(select nvl(max(board_num), 0)+1 from "
			+ "board),?,?,?,?,?,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, article.getBOARD_NAME());
			pstmt.setString(2, article.getBOARD_PASS());
			pstmt.setString(3, article.getBOARD_SUBJECT());
			pstmt.setString(4, article.getBOARD_CONTENT());
			pstmt.setString(5, "");	//답장은 파일 첨부 없음
			pstmt.setInt(6, re_ref);
			pstmt.setInt(7, re_lev);
			pstmt.setInt(8, re_seq);
			pstmt.setInt(9, 0);
			//각각 숫자에 해당하는 ?에 들어가는 내용
			insertCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}

		return insertCount;
	}

	public int deleteArticle(int board_num) {
		PreparedStatement pstmt = null;
		String board_delete_sql = "delete from board where BOARD_num=?"; //굳이 나눌 필요 없었음
		int deleteCount = 0;
		
		try {
			pstmt = con.prepareStatement(board_delete_sql);
			pstmt.setInt(1, board_num);
			//첫 번째 물음표 안에 들어가는 정보
			deleteCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}

		return deleteCount;
	}
}
