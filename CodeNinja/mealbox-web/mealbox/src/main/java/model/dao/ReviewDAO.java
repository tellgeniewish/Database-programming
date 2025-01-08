package model.dao;

import model.domain.Review;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ReviewDAO {
	private JDBCUtil jdbcUtil = null;
	
	public ReviewDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
	}

	// 리뷰 찾기
	public Review findReview(int reviewId) throws SQLException {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * ");
		query.append("FROM MEAL_REVIEW ");
		query.append("WHERE reviewId=?");  
		jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {reviewId});	// JDBCUtil에 query문과 매개 변수 설정
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			if (rs.next()) {						// review 정보 발견
				Review review = new Review(		// review 객체를 생성하여 리뷰 정보를 저장
					rs.getInt("reviewid"),
					rs.getInt("productid"),
					rs.getInt("orderid"),
					rs.getInt("lineno"),
					rs.getString("userid"),
					rs.getString("reviewcreatedat"),
					rs.getFloat("rating"),
					rs.getString("reviewtext"),
					rs.getString("reviewimg"));
				return review;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
    // 리뷰 생성
    public boolean create(Review review) {
        // 데이터베이스에 리뷰를 추가하는 로직
    	StringBuffer query = new StringBuffer();
		query.append("INSERT INTO MEAL_REVIEW (productId, orderId, lineNo, userid, reviewCreateDat, rating, reviewText, reviewImg) VALUES (?, ?, ?, ?, SYSDATE, ?, ?, ?)");
		Object[] newReview = new Object[] {review.getProductId(), review.getOrderId(), review.getLineNo(), review.getNickname(), review.getRating(), review.getReviewText(), review.getReviewImg()};
		jdbcUtil.setSqlAndParameters(query.toString(), newReview);
		
		try {				
			int result = jdbcUtil.executeUpdate();	// insert 문 실행
			return true;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}	
		
        return false;
    }

    // 특정 제품의 리뷰 조회
    public List<Review> findReviewsByProduct(int productId) { // 제품 ID를 기준으로 리뷰 검색 로직 
    	List<Review> result = new ArrayList<>(); 
    	
    	StringBuffer query = new StringBuffer();
		query.append("SELECT * ");
		query.append("FROM MEAL_REVIEW ");
		query.append("WHERE productId=?");  
		jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {productId});
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			while (rs.next()) {						// review 정보 발견
				Review review = new Review(		// review 객체를 생성하여 리뷰 정보를 저장
					rs.getInt("reviewid"),
					rs.getInt("productid"),
					rs.getInt("orderid"),
					rs.getInt("lineno"),
					rs.getString("userid"),
					rs.getString("reviewcreatedat"),
					rs.getFloat("rating"),
					rs.getString("reviewtext"),
					rs.getString("reviewimg"));
					result.add(review);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		
    	return result; 
    }

    // 리뷰 아이디 찾기
    public int findReviewId(int productId, int orderId) {
    	StringBuffer query = new StringBuffer();
		query.append("SELECT reviewId ");
		query.append("FROM MEAL_REVIEW ");
		query.append("WHERE productId=? and orderId=?");  
		jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {productId, orderId});
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			while (rs.next()) {						// review 정보 발견
				int reviewId = rs.getInt("reviewid");
				return reviewId;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		
    	return 0;
    }

    // 리뷰 수정
    public boolean update(Review review) throws SQLException {
        // 데이터베이스에서 리뷰를 수정하는 로직
    	StringBuffer query = new StringBuffer();
        query.append("UPDATE MEAL_REVIEW ");
        query.append("SET rating = ?, reviewText = ?, reviewImg = ? ");
        query.append("WHERE reviewId = ?");
        
        Object[] updateParams = new Object[] {
            review.getRating(),
            review.getReviewText(),
            review.getReviewImg(),
            review.getReviewId()
        };

        jdbcUtil.setSqlAndParameters(query.toString(), updateParams);
            
        try {
            int result = jdbcUtil.executeUpdate(); // update 문 실행
            return result > 0; // 업데이트 성공 여부 반환
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close(); // resource 반환
        }

        return false;
    }

    // 리뷰 삭제
    public boolean delete(int reviewId) {
    	String sql = "DELETE FROM MEAL_REVIEW WHERE reviewId=?";	
		jdbcUtil.setSqlAndParameters(sql, new Object[] {reviewId});	// JDBCUtil에 delete문과 매개 변수 설정

		try {				
			int result = jdbcUtil.executeUpdate();	// delete 문 실행
			return result > 0;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return false;
    }
}