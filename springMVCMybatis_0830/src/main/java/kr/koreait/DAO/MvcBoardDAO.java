package kr.koreait.DAO;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import kr.koreait.VO.MvcBoardVO;

public class MvcBoardDAO {
	
	JdbcTemplate template = null;

	public MvcBoardDAO() {
		template = Constant.template;
	}
	
//	InsertService 클래스에서 테이블에 저장할 이름, 제목, 내용을 넘겨받고 insert SQL 명령을 실행하는 메소드
	public void insert(final String name, final String subject, final String content) {
		System.out.println("MvcBoardDAO insert()");

		String sql = "insert into mvcboard (idx, name, subject, content, ref, lev, seq) " +
				"values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
		
//		update() : 테이블 내용이 갱신되는 sql 명령을 실행한다. insert, delete, update
//		query() : 테이블 내용이 갱신되는 않는 sql 명령을 실행한다. 실행 결과가 여러건인 select
//		queryForObject() : 테이블 내용이 갱신되지 않은 sql 명령을 실행한다. 실행 결과가 한 건인 select
		
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, name);
				pstmt.setString(2, subject);
				pstmt.setString(3, content);				
			}
		});
	}
	
//	SelectListService 클래스에서 호출되는 테이블에 저장된 글 전체를 얻어오는 select SQL 명령을 실행하는 메소드
	public ArrayList<MvcBoardVO> SelectListService(){
		System.out.println("MvcBoardDAO selectList()");
		
		String sql = "select * from MVCBOARD order by ref desc, seq asc";
		return (ArrayList<MvcBoardVO>) template.query(sql, new BeanPropertyRowMapper<MvcBoardVO>(MvcBoardVO.class));
		
	}
	
//	IncrementService 클래스에서 조회수를 증가시킬 글 번호를 넘겨받고 조회수를 증가시키는 update SQL 명령을 실행하는 메소드	
	public void increment(final int idx) {
		
		String sql = "update mvcboard set hit = hit + 1 where idx = ?";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, idx);				
			}
		});
	}
	
//	selectByIdxService 클래스에서 글번호를 넘겨받고 해당 글 한 건을 얻어오는 select SQL 명령을 실행하는 메소드
	public MvcBoardVO contentView(int no) {
		
		String sql = "select * from mvcboard where idx = " + no;
		MvcBoardVO vo = null;
		vo = template.queryForObject(sql, new BeanPropertyRowMapper<MvcBoardVO>(MvcBoardVO.class));
		return vo;
		
	}
	
	public void update(final int idx, final String subject, final String content) {

		String sql = "update mvcboard set subject = ?, content = ?, where idx = ?";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, subject);
				pstmt.setString(2, content);
				pstmt.setInt(3, idx);
			}
		});
		
	}
	
	public void delete(final int idx) {

		String sql = "delete from mvcboard where idx = ?";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {				
				pstmt.setInt(1, idx);
			}
		});
	}
	
	public void replyIncrement(final HashMap<String, Integer> hmap) {

		String sql = "update mvcboard set seq = seq + 1 where ref = ? and seq >= ?";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, hmap.get("ref"));
				pstmt.setInt(2, hmap.get("seq"));
			}
		});
		
	}
	
//	ReplyService 클래스에서 테이블에 저장할 답글이 저장된 객체를 넘겨받고 답글을 저장하는 insert SQL 명령을 실행하는 메소드
	public void replyInsert(final MvcBoardVO vo) {

		String sql = "insert into MVCBOARD(idx, name, subject, content, ref, lev, seq) values(mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getSubject());
				pstmt.setString(3, vo.getContent());
				pstmt.setInt(4, vo.getRef());
				pstmt.setInt(5, vo.getLev());
				pstmt.setInt(6, vo.getSeq());				
			}
		});		
	}
	
}
