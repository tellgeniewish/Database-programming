package repository.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.Comment;
import repository.mybatis.mapper.CommentMapper3;

// Using Mapper XML and Mapper Interface (CommentMapper3)
public class CommentMapperRepository2 {
	private SqlSessionFactory sqlSessionFactory;
	
	public CommentMapperRepository2() {
		String resource = "mybatis-config_MX3.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	public Comment findCommentByCommentNo(long commentNo) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(CommentMapper3.class).selectCommentByPrimaryKey(commentNo);			
		} finally {
			sqlSession.close();
		}
	}

	public List<Comment> findCommentByCondition(Map<String, Object> condition) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(CommentMapper3.class).selectCommentByCondition(condition);			
		} finally {
			sqlSession.close();
		}
	}
	
	public int insertComment(Comment comment) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(CommentMapper3.class).insertComment(comment);
			if (result > 0) {
				sqlSession.commit();
			} 
			return result;
		} finally {
			sqlSession.close();
		}
	}

	public int updateComment(Comment comment) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(CommentMapper3.class).updateComment(comment);
			if (result > 0) {
				sqlSession.commit();
			} 
			return result;
		} finally {
			sqlSession.close();
		}
	}

	public int deleteComment(long commentNo) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(CommentMapper3.class).deleteComment(commentNo);
			if (result > 0) {
				sqlSession.commit();
			} 
			return result;	
		} finally {
			sqlSession.close();
		}
	}
	
	public int deleteAllComments() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(CommentMapper3.class).deleteAllComments();
			if (result > 0) {
				sqlSession.commit();
			} 
			return result;		
		} finally {
			sqlSession.close();
		}
	}
}