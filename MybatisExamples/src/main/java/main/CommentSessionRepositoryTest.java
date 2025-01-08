package main;

import java.util.*;

import model.Comment;
import model.User;
import repository.mybatis.CommentSessionRepository;

public class CommentSessionRepositoryTest {
	private static CommentSessionRepository commentDao = new CommentSessionRepository();
	
	public static void main(String[] args) {
		System.out.println("CommentSessionRepositoryTest starts...");		
		try {
			insertComment(20240001L, "user1", "comment #1");
			insertComment(20240002L, "user1", "comment #2");
			insertComment(20240003L, "user2", "comment #3");
			System.out.println();
			
			findCommentByPrimaryKey(20240001L);
			findCommentByPrimaryKey(20240002L);
			findCommentByCondition("user1");
			System.out.println();
	
			updateComment(20240001L, "comment #0");
			findCommentByCondition("user1");
			System.out.println();
			
			deleteComment(20240001L);
			findCommentByCondition("user1");
			System.out.println();			
		} finally {
			deleteAllComments();
			findCommentByCondition("user1");
		}
	}
	
	public static void findCommentByPrimaryKey(long commentNo) {		
		System.out.println("findCommentByPrimaryKey(" + commentNo + "): ");
		
		Comment comment = commentDao.findCommentByCommentNo(commentNo);
		System.out.println(comment);
	}

	public static void findCommentByCondition(String userId) {
        System.out.println("findCommentByCondition(" + userId + "): ");
        
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", userId);
		List<Comment> list = commentDao.findCommentByCondition(condition);		
		System.out.println(list);
	}
	
	public static void insertComment(long commentNo, String userId, String commentContent) {
        System.out.println("insertComment(" + commentNo + ", ...): ");
        
		User user = new User();
		user.setUserId(userId);
		user.setUserName("Scott");
		Date regDate = Calendar.getInstance().getTime();
		
		Comment comment = new Comment();
		comment.setCommentNo(commentNo);
		comment.setUserId(userId);
		comment.setCommentContent(commentContent);
		comment.setRegDate(regDate);
		comment.setUser(user);		
		int result = commentDao.insertComment(comment);
		System.out.println("return: " + result);
	}

	public static void updateComment(long commentNo, String commentContent) {
        System.out.println("updateComment(" + commentNo + ", " + commentContent + "): ");

        Comment comment = new Comment();
		comment.setCommentNo(commentNo);
		comment.setCommentContent(commentContent);		
		int result = commentDao.updateComment(comment);
        System.out.println("return: " + result);
	}

	public static void deleteComment(long commentNo) {
        System.out.println("deleteComment(" + commentNo + "): ");

        int result = commentDao.deleteComment(commentNo);
        System.out.println("return: " + result);
	}		
	
	public static void deleteAllComments() {		
        System.out.println("deleteAllComments(): ");
        
		int result = commentDao.deleteAllComments();
        System.out.println("return: " + result);
	}	
}
