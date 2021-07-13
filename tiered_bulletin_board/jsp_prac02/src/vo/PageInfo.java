package vo;

public class PageInfo {

	private int page; // 현재 선택한 페이지
	private int maxPage; // 총 페이지
	private int startPage; // 한 화면에 보이는 시작 페이지
	private int endPage; // 한 화면에 보이는 끝 페이지
	private int listCount; // 총 게시물 수

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

}
