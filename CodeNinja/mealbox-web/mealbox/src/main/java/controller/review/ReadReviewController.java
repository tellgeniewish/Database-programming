package controller.review;

import model.service.ProductManager;
import model.service.ReviewManager;
import model.domain.Product;
import model.domain.Review;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Comparator;
import controller.Controller;

public class ReadReviewController implements Controller {
    private ReviewManager reviewManager;

    public ReadReviewController() {
        reviewManager = new ReviewManager();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductManager manager = ProductManager.getInstance();
		Product product = manager.getDetail(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("product", product);
    	int productId = Integer.parseInt(request.getParameter("productId"));
        //int productId = 1010;
				
    	List<Review> reviews = reviewManager.findReviewsByProduct(productId);
        String sortStandard = request.getParameter("sort");

        if ("latest".equals(sortStandard)) {
            reviews.sort(Comparator.comparing(Review::getDate).reversed());
        } else if ("highRate".equals(sortStandard)) {
            reviews.sort(Comparator.comparing((Review r) -> r.getRating()).reversed());
        } else if ("lowRate".equals(sortStandard)) {
            reviews.sort(Comparator.comparing((Review r) -> r.getRating()));
        }

        request.setAttribute("reviews", reviews);
        return "/product/detailProductReviewPage.jsp";
    }
}