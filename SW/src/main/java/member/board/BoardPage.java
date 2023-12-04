package member.board;

public class BoardPage {
	public static String pagingStr(int totalCount, int pageSize, int blockPage, int pageNum, String reqUrl) {
		String pagingStr = "";
		
		//전체 페이지 수
		int totalPages = (int)(Math.ceil((double)totalCount / pageSize));
		
		/*이전 페이지 바로가기 블럭*/
		//시작 블럭
		int pageTmp = ((pageNum-1) / blockPage) * blockPage + 1;
		if(pageTmp != 1) {
			pagingStr += "<a href='" + reqUrl + "&pageNum=1'> &lt;&lt; </a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "&pageNum=" + (pageTmp - 1) + "'> &lt; </a>";
		}
		
		/*현재 페이지의 블럭*/
		int blockCount = 1;
		while(blockCount <= blockPage && pageTmp <= totalPages) {
			if(pageTmp == pageNum) {
				pagingStr += "&nbsp;<strong>" + pageTmp + "</strong>&nbsp;";
			}else {
				pagingStr += "&nbsp;<a href='"+ reqUrl + "&pageNum=" + pageTmp + "'>"+ pageTmp + "</a>&nbsp;";
			}
			pageTmp ++;
			blockCount ++;
		}
		
		/*다음 페이지 바로가기 블럭*/
		if(pageTmp <= totalPages) {
			pagingStr += "<a href='" + reqUrl + "&pageNum=" + pageTmp + "'> &gt; </a>";
			pagingStr += "<a href='" + reqUrl + "&pageNum=" + totalPages + "'> &gt;&gt; </a>";
		}
		
		return pagingStr;
	}
}
