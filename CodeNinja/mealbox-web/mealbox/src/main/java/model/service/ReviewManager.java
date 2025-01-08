package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.ReviewDAO;
import model.domain.User;
import model.domain.Review;
import java.util.ArrayList;
import java.util.List;

public class ReviewManager {
    private static ReviewManager reviewMan = new ReviewManager();
	private ReviewDAO reviewDAO;

	public ReviewManager() {
		try {
			reviewDAO = new ReviewDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}

    private List<Review> reviews;

    // 리뷰 생성 메서드 추가
    public boolean createReview(Review review) { 
    	return reviewDAO.create(review);
    }
    
    // reivewId 찾기
    public int findReviewId(int productId, int orderId) {
    	return reviewDAO.findReviewId(productId, orderId);
    }
    
    // reviewId로 리뷰 조회 메서드
    public Review getReviewById(int reviewId) throws SQLException {
    	return reviewDAO.findReview(reviewId);
    }

    // 특정 상품의 리뷰 조회 메서드
    public List<Review> findReviewsByProduct(int productId) { 
    	return reviewDAO.findReviewsByProduct(productId); 
    }

    // 모든 리뷰 조회 메서드
    public List<Review> getAllReviews() {
        return reviews;
    }

    // 리뷰 수정
    public boolean updateReview(Review review) throws SQLException { 
    	return reviewDAO.update(review);
    }
    
    // 리뷰 삭제
    public boolean deleteReview(int reviewId) { 
    	return reviewDAO.delete(reviewId);
    }

    // 평균 점수 계산 메서드
    public double calculateAverageScore(int productId) {
    	List<Review> allReviews = findReviewsByProduct(productId);
        int countReviews = 0;
        double totalScore = 0.0;

        for (Review review : allReviews) {
            if (review.getProductId() == productId) {
                totalScore += review.getRating();
                countReviews++;
            }
        }
        return countReviews > 0 ? totalScore / countReviews : 0.0;
    }
}