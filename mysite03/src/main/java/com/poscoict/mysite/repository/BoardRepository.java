package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.poscoict.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(BoardVo boardVo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			if(boardVo.getGroupNo() == null) {
				String sql = "insert  into board values(null, ?, ?, 0, (select ifnull(max( g_no ), 0 ) + 1 from board a), 1, 0, now(), ?)";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContents());
				pstmt.setLong(3, boardVo.getUserNo());
			} else {
				String sql = "insert  into board values(null, ?, ?, 0, ?, ?, ?, now(), ?)";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContents());
				pstmt.setInt(3, boardVo.getGroupNo());
				pstmt.setInt(4, boardVo.getOrderNo());
				pstmt.setInt(5, boardVo.getDepth());
				pstmt.setLong(6, boardVo.getUserNo());
			}
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		return result;
	}
	
	public List<BoardVo> findAllByPageAndKeword(String keyword, Integer page, Integer size) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();

			if(keyword == null || "".equals(keyword)) {
				String sql = 
					"     select a.no," + 
				    "              a.title," + 
					"              a.hit," + 
				    "              date_format(a.reg_date, '%Y-%m-%d %p %h:%i:%s') as regDate," +
					"              a.depth," + 
				    "              b.name as userName," +
					"              a.user_no as userNo" + 
				    "       from board a, user b" + 
					"     where a.user_no = b.no" + 
				    " order by g_no desc, o_no asc" + 
					"       limit ?, ?";
				pstmt = conn.prepareStatement(sql);
			
				pstmt.setInt(1, (page-1)*size);
				pstmt.setInt(2, size);
			} else {
				String sql = 
						"     select a.no," + 
					    "              a.title," + 
						"              a.hit," + 
					    "              date_format(a.reg_date, '%Y-%m-%d %p %h:%i:%s') as regDate," +
						"              a.depth," + 
					    "              b.name as userName," +
						"              a.user_no as userNo" + 
					    "       from board a, user b" + 
						"     where a.user_no = b.no" +
					    "        and (title like ? or contents like ?)" + 
					    " order by g_no desc, o_no asc" + 
						"       limit ?, ?";
				pstmt = conn.prepareStatement(sql);
			
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setInt(3, (page-1)*size);
				pstmt.setInt(4, size);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setHit(rs.getInt(3));
				vo.setRegDate(rs.getString(4));
				vo.setDepth(rs.getInt(5));
				vo.setUserName(rs.getString(6));
				vo.setUserNo(rs.getLong(7));

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int update(BoardVo boardVo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set title=?, contents=? where no=? and user_no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setLong(3, boardVo.getNo());
			pstmt.setLong(4, boardVo.getUserNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		return result;
	}
	
	public int delete(Long no, Long userNo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "delete from board where no = ? and user_no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		return result;
	}

	public BoardVo findByNo(Long no) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();

			String sql = "select	no, title, contents, g_no as groupNo, o_no as orderNo, depth, user_no as userNo from board where no = ?";
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = new BoardVo();
				
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				result.setContents(rs.getString(3));
				result.setGroupNo(rs.getInt(4));
				result.setOrderNo(rs.getInt(5));
				result.setDepth(rs.getInt(6));
				result.setUserNo(rs.getLong(7));
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public BoardVo findByNoAndUserNo(Long no, Long userNo) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();

			String sql = "select no, title, contents from board where no = ? and user_no = ?";
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);
			
			rs = pstmt.executeQuery();			
			if(rs.next()) {
				result = new BoardVo();
				
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				result.setContents(rs.getString(3));
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int updateHit(Long no) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set hit = hit + 1 where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		return result;
	}
	
	public int updateOrderNo(Integer groupNo, Integer orderNo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set o_no = o_no + 1 where g_no = ? and o_no >= ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, groupNo);
			pstmt.setInt(2, orderNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		return result;			
	}
	
	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.getTotalCount", keyword);
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}	
}