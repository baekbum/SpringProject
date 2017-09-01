package kr.koreait.DAO;

import java.util.HashMap;
import java.util.List;

import kr.koreait.VO.RBoardVO;

public interface MybatisDAO {

	void insert(RBoardVO vo);

	List<RBoardVO> selectList(HashMap<String, Integer> hmap);

	int selectCount();

	void increment(int idx);

	RBoardVO selectByIdx(int idx);

	void edit(RBoardVO vo);

	void delete(int idx);

	void incrementSeq(HashMap<String, Integer> hmap);

	void reply(RBoardVO vo);	

}
