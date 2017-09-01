package kr.koreait.VO;

import java.util.ArrayList;
import java.util.List;

public class RBoardList {
	//**********************************************
	private List<RBoardVO> list = new ArrayList<RBoardVO>();
	private int totalCount;
	private int totalPage;
	private int currentPage;
	private int pageSize;
	private int startNo;
	private int endNo;
	private int currentBlock;
	private int startPage;
	private int endPage;
	
	public RBoardList() {}
	public RBoardList(int pageSize, int totalCount, int currentPage) {
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		calculator();
	}	
	private void calculator() {
		totalPage = (totalCount - 1) / pageSize + 1;
		currentPage = currentPage > totalPage ? totalPage : currentPage;
		startNo = (currentPage - 1) * pageSize + 1;
		endNo = startNo + pageSize - 1;
		endNo = endNo > totalCount ? totalCount : endNo;
		startPage = ((currentPage - 1) / 10) * 10 + 1;
		endPage = startPage + 9;
		endPage = endPage > totalPage ? totalPage : endPage;
	}
	///////////////////////////////////////////////////////////////
	// getter & setter
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public int getStartNo() {
		return startNo;
	}
	public int getEndNo() {
		return endNo;
	}
	public int getCurrentBlock() {
		return currentBlock;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}	
	
	public List<RBoardVO> getList() {
		return list;
	}
	public void setList(List<RBoardVO> list) {
		this.list = list;
	}
}
