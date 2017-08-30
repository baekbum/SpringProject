package kr.koreait.DAO;

import java.util.ArrayList;
import java.util.HashMap;

import kr.koreait.VO.MvcBoardVO;

public interface MybatisDAO {

//	DAO 인터페이스의 추상 메소드 이름이 SQL 명령의 id로 인식된다.
	
//	메소드의 인수가 여러개일 경우 parameterType은 넘겨받는 인수가 모두 멤버로 포함된 클래스를 써주면 된다.
//	SQL 명령을 실행하는 xml 파일에서는 넘어오는 순서에 따라 #{param1}, #{param2}, #{param3}, ... 과 같이 적는다.
//	void insert(String name, String subject, String content);
	void insert(MvcBoardVO vo);
	ArrayList<MvcBoardVO> selectList();
	void increment(int idx);
	MvcBoardVO contentView(int idx);
	void update(MvcBoardVO vo);
	void delete(int idx);
	void replyIncrement(HashMap<String, Integer> hmap);
	void replyInsert(MvcBoardVO vo);
	
}





