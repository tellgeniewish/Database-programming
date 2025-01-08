package repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Comment;

// Using JDBC API
public class CommentJdbcRepository {
	
	public CommentJdbcRepository() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");			
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	private Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb", 
					"dbp2024", "TIGER");
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public Comment findCommentByCommentNo(long commentNo) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT comment_no, user_id, comment_content, reg_date ");
			sql.append("FROM comments ");
			sql.append("WHERE comment_no = ?");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, commentNo);
			rs = stmt.executeQuery();

			if (rs.next()) {
				Comment comment = new Comment();
				comment.setCommentNo(rs.getLong("comment_no"));
				comment.setUserId(rs.getString("user_id"));
				comment.setCommentContent(rs.getString("comment_content"));
				comment.setRegDate(rs.getDate("reg_date"));
				return comment;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } 
				catch (SQLException e) { e.printStackTrace(); }
			try { stmt.close(); } 
				catch (SQLException e) { e.printStackTrace(); }
			try { conn.close(); }
				catch (SQLException e) { e.printStackTrace(); }
		}
		return null;
	}
	
	public List<Comment> findCommentByCondition(Map<String, Object> condition) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Comment> comments = new ArrayList<Comment>();

		try {
			conn = this.getConnection();

			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT comment_no, user_id, comment_content, reg_date ");
			sql.append("FROM comments ");
			if (condition.get("userId") != null) {
				sql.append("WHERE user_id = ?");
			}
			stmt = conn.prepareStatement(sql.toString());
			if (condition.get("userId") != null) {
				stmt.setString(1, (String)condition.get("userId"));
			}
			rs = stmt.executeQuery();
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setCommentNo(rs.getLong("comment_no"));
				comment.setUserId(rs.getString("user_id"));
				comment.setCommentContent(rs.getString("comment_content"));
				comment.setRegDate(rs.getDate("reg_date"));
				comments.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } 
				catch (SQLException e) { e.printStackTrace(); }
			try { stmt.close(); } 
				catch (SQLException e) { e.printStackTrace(); }
			try { conn.close(); }
				catch (SQLException e) { e.printStackTrace(); }
		}
		return comments;
	}
	
	public int insertComment(Comment comment) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = this.getConnection();

			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO comments(comment_no, user_id, comment_content, reg_date) ");
			sql.append("VALUES (?, ?, ?, ?) ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, comment.getCommentNo());
			stmt.setString(2, comment.getUserId());
			stmt.setString(3, comment.getCommentContent());
			stmt.setDate(4, new java.sql.Date(comment.getRegDate().getTime()));
			return stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { stmt.close(); } 
				catch (SQLException e) { e.printStackTrace(); }
			try { conn.close(); }
				catch (SQLException e) { e.printStackTrace(); }
		}
		return 0;
	}
	
	public int updateComment(Comment comment) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = this.getConnection();

			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE comments ");
			sql.append("SET comment_content = ? ");
			sql.append("WHERE comment_no = ? ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, comment.getCommentContent());
			stmt.setLong(2, comment.getCommentNo());
			return stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { stmt.close(); } 
				catch (SQLException e) { e.printStackTrace(); }
			try { conn.close(); }
				catch (SQLException e) { e.printStackTrace(); }
		}
		return 0;
	}
	
	public int deleteComment(long commentNo) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = this.getConnection();

			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE FROM comments ");
			sql.append("WHERE comment_no = ? ");
			stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, commentNo);
			return stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { stmt.close(); } 
				catch (SQLException e) { e.printStackTrace(); }
			try { conn.close(); }
				catch (SQLException e) { e.printStackTrace(); }
		}
		return 0;
	}
	
	public int deleteAllComments() {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = this.getConnection();		
			stmt = conn.prepareStatement("DELETE FROM comments");
			return stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { stmt.close(); } 
				catch (SQLException e) { e.printStackTrace(); }
			try { conn.close(); }
				catch (SQLException e) { e.printStackTrace(); }
		}
		return 0;
	}
}