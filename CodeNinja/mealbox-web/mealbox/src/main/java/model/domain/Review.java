// Review.java (자바빈즈 클래스)
package model.domain;

public class Review {
    private int reviewId;
    private int productId;
    
    private int orderId;
    private int lineNo;
    
    private String nickname;
    private String date;
    private double rating;
    private String reviewText;
    private String reviewImg;

    public Review() { // 기본 생성자
        this.reviewId = reviewId; // 리뷰 생성 시 자동으로 reviewId 할당
    }

    // 생성자
    public Review(int reviewId, int productId, int orderId, int lineNo, String nickname, String date, double rating, String reviewText, String reviewImg) {
        this.reviewId = reviewId; // 리뷰 생성 시 자동으로 reviewId 할당
        this.productId = productId;
        this.orderId = orderId;
        this.lineNo = lineNo;
        
        this.nickname = nickname;
        this.date = date;
        this.rating = rating;
        //this.rating = new Rating(stars, score); // Rating 객체 생성
        this.reviewText = reviewText;
        this.reviewImg = reviewImg;
    }
    public Review(int productId, int orderId, int lineNo, String nickname, double rating, String reviewText, String reviewImg) {
        this.productId = productId;
        this.orderId = orderId;
        this.lineNo = lineNo;
        
        this.nickname = nickname;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewImg = reviewImg;
    }

    // Getter와 Setter
    public int getReviewId() { return reviewId; }  // 리뷰 ID Getter

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    
    public int getLineNo() { return lineNo; }
    public void setLineNo(int lineNo) { this.lineNo = lineNo; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public String getReviewImg() { return reviewImg; }
    public void setReviewImg(String reviewImg) { this.reviewImg = reviewImg; }
}