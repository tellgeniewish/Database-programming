package controller.review;

import model.service.ProductManager;
import model.service.ReviewManager;
import model.domain.Product;
import model.domain.Review;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;

public class ReviewFormController implements Controller {
    private ReviewManager reviewManager;

    public ReviewFormController() {
        reviewManager = new ReviewManager();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception { 		
//    	int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        //int productId = 1010;//Integer.parseInt(request.getParameter("productId"));
    	int productId = Integer.parseInt(request.getParameter("productId"));
    	int orderId = Integer.parseInt(request.getParameter("orderId"));
    	int lineNo = Integer.parseInt(request.getParameter("lineNo"));
    	request.setAttribute("orderId", orderId);
    	request.setAttribute("lineNo", lineNo);
        
    	ProductManager manager = ProductManager.getInstance();
        Product product = manager.getDetail(productId);
        request.setAttribute("product", product);
        System.out.println("\n form --> productId==="+productId);
        
        int reviewId = reviewManager.findReviewId(productId, orderId);
        System.out.println("\n form --> reviewId==="+reviewId);
		if (reviewId != 0) { // 리뷰 이미 있을 유
			Review foundReview = reviewManager.getReviewById(reviewId);
			System.out.println("찾은 리뷰 평점="+ foundReview.getRating());
			if (foundReview.getReviewText() == null) {
		        foundReview.setReviewText(""); // text 값이 null일 경우 기본값 설정
		    }
			request.setAttribute("foundReview", foundReview); // 리뷰 데이터를 JSP로 전달
			request.setAttribute("reviewId", reviewId);
		} else {
			request.setAttribute("foundReview", null);			
		}
		return "/review/reviewForm.jsp";		
    }
}