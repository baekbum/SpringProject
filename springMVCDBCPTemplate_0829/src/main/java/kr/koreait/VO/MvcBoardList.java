package kr.koreait.VO;

import java.util.ArrayList;

public class MvcBoardList {
	private ArrayList<MvcBoardVO> list = new ArrayList<MvcBoardVO>();

	public ArrayList<MvcBoardVO> getList() {
		return list;
	}

	public void setList(ArrayList<MvcBoardVO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "MvcBoardList [list=" + list + "]";
	}
	
	
}
