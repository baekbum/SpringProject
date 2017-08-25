package kr.koreait.DAO;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.koreait.VO.MvcBoardVO;

public class MvcBoardDAO {

//	데이터베이스에 접속하기 위해서 DateSource 인터페이스의 객체를 생성한다.	
	DataSource dataSource;
//	생성자에서 DataSource 인터페이스 객체를 초기화 시킨다.
	public MvcBoardDAO() {
		try {
			System.out.println("연결 확인");
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("연결 실패");
		}
	}
	
//	InsertService 클래스에서 테이블에 저장할 이름, 제목, 내용을 넘겨받고 insert SQL 명령을 실행하는 메소드
	public void insert(String name, String subject, String content) {
		Connection conn = null;
		PreparedStatement pstmt = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into MVCBOARD(idx, name, subject, content, ref, lev, seq) values(mvcboard_idx_seq.nextval, ?, ?, ?,  mvcboard_idx_seq.currval, 0, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, subject);
			pstmt.setString(3, content);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			
		} finally {
			if(conn != null) {
				try {
					conn.close();					
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}	
		}
	}
	
//	SelectListService 클래스에서 호출되는 테이블에 저장된 글 전체를 얻어오는 select SQL 명령을 실행하는 메소드
	public ArrayList<MvcBoardVO> SelectListService(){
		System.out.println("MvcBoardDAO selectList()");
		ArrayList<MvcBoardVO> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String sql = "select * from MVCBOARD order by ref desc, seq asc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
//			테이블에서 얻어와 ResultSet 인터페이스 객체에 저장된 내용을 ArrayList에 저장시킨다.
			System.out.println(rs);
			list = new ArrayList<MvcBoardVO>();
			while(rs.next()) {
				int idx = rs.getInt("idx");
				String name = rs.getString("name");
				String subject = rs.getString("subject");
				String content = rs.getString("content");
				int ref = rs.getInt("ref");
				int lev = rs.getInt("lev");
				int seq = rs.getInt("seq");
				int hit = rs.getInt("hit");
				Date writeDate = rs.getTimestamp("writeDate");
				list.add(new MvcBoardVO(idx, name, subject, content, ref, lev, seq, hit, writeDate));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();					
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
		return list;
	}
	
//	IncrementService 클래스에서 조회수를 증가시킬 글 번호를 넘겨받고 조회수를 증가시키는 update SQL 명령을 실행하는 메소드	
	public void increment(int idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set hit = hit + 1 where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();					
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
	}
	
//	selectByIdxService 클래스에서 글번호를 넘겨받고 해당 글 한 건을 얻어오는 select SQL 명령을 실행하는 메소드
	public MvcBoardVO contentView(int no) {
		MvcBoardVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
						
			if(rs.next()) {
				/*
				vo = new MvcBoardVO();
				vo.setIdx(rs.getInt("idx"));
				vo.setName(rs.getString("name"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setRef(rs.getInt("ref"));
				vo.setLev(rs.getInt("lev"));
				vo.setSeq(rs.getInt("seq"));
				vo.setHit(rs.getInt("hit"));
				vo.setWriteDate(rs.getTimestamp("writeDate"));
				*/				
				
				int idx = rs.getInt("idx");
				String name = rs.getString("name");
				String subject = rs.getString("subject");
				String content = rs.getString("content");
				int ref = rs.getInt("ref");
				int lev = rs.getInt("lev");
				int seq = rs.getInt("seq");
				int hit = rs.getInt("hit");
				Date writeDate = rs.getTimestamp("writeDate");
				vo = new MvcBoardVO(idx, name, subject, content, ref, lev, seq, hit, writeDate);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			if(conn != null) {
				try {
					conn.close();					
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}		
		return vo;
		
	}
}
