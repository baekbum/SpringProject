package kr.koreait.DAO;

import java.util.List;

import kr.koreait.VO.RBoardCommentVO;

public interface MybatisCommentDAO {

	int selectCommentCount(int ref);

	List<RBoardCommentVO> selectCommentList(int ref);

	void reply(RBoardCommentVO commentVO);

	void cascadeDelete(int ref);

	RBoardCommentVO replyEdit(int idx);


}
