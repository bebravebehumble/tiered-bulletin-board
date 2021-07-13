package vo;
//vo: value object

public class ActionForward {
	private boolean isRedirect = false;
	//redirect로 이동하느냐, 아니냐
	// 주소 이동 여부, 주소가 바뀔 때
	// 공유할 값이 있다면 dispatcher,
	//목록, 게시물의 내용을 보여주세요 하고 요청하면 그에 관한 피드백을 보여주는 것(전달할 값을 가지고 피드백함)
	// 공유할 값이 없다면 redirect,
	// 단순 주소 이동: 처리를 끝내고 피드백 없이 다음 페이지를 보여주는 것(전달값 X)
	private String path = null;
	// 어느 주소로 갈지 주소경로, url(문자열값)

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
