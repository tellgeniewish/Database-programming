package main;


import java.util.*;

import model.Comment;
import model.User;
import repository.mybatis.CommentMapperRepository2;

public class CommentMapperRepository2Test {
	private static CommentMapperRepository2 commentDao = new CommentMapperRepository2();
	
	public static void main(String[] args) {
		System.out.println("CommentMapperRepository2Test starts...");
		
		try {
			insertComment(20240007L, "user1", "comment #7");
			insertComment(20240008L, "user1", "comment #8");
			insertComment(20240009L, "user2", "comment #9");
			System.out.println();
			
			findCommentByPrimaryKey(20240007L);
			findCommentByPrimaryKey(20240008L);
			findCommentByCondition("user1");
			System.out.println();
	
			updateComment(20240007L, "comment #0");
			findCommentByCondition("user1");
			System.out.println();
			
			deleteComment(20240007L);
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