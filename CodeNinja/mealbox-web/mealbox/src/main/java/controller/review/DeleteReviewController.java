package controller.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;
import model.service.ReviewManager;

public class DeleteReviewController implements Controller {
    private ReviewManager reviewManager;

    public DeleteReviewController() {
        reviewManager = new ReviewManager();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// DELETE 요청에서 파라미터로 reviewId를 받기
        String method = request.getMethod();
        if ("DELETE".equalsIgnoreCase(method)) {
        	System.out.println("\n\n delete 들어왔다!!!\n\n");

            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            System.out.println("\n\n delete review id===" + reviewId);
            
            // 리뷰 삭제 처리
            boolean isDeleted = reviewManager.deleteReview(reviewId);
            
            // 삭제 성공 시 응답
            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 서버 오류
            }
        }
        return null;  // 결과를 직접 반환하지 않고, AJAX를 통해 처리합니다.
    }
}