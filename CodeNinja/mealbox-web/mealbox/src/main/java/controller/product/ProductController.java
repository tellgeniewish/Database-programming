package controller.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.domain.Product;
import model.domain.Review;
import model.service.ProductManager;
import model.service.ReviewManager;

import java.util.Comparator;
import java.util.List;

public class ProductController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductManager manager = ProductManager.getInstance();
		
		Product product = manager.getDetail(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("product", product);		
	    
		// 현진 ReadReviewController 코드
		ReviewManager reviewManager = new ReviewManager();
    	int productId = product.getId();
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
		
	    request.setAttribute("avrRev", reviewManager.calculateAverageScore(productId));
	    
		return "/product/detailPage.jsp";
	}
}