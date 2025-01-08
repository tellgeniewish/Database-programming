package controller.product;
import controller.Controller;
import controller.user.UserSessionUtils;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.domain.Product;
import model.service.ProductManager;
import model.service.ReviewManager;
public class ReadProductController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProductManager manager = ProductManager.getInstance();
        ReviewManager reviewManager = new ReviewManager();
        
        try {
        	HttpSession session = request.getSession();
        	boolean isAdmin = ((String)session.getAttribute(UserSessionUtils.USER_SESSION_KEY)).equals("admin");
        
        	if(isAdmin) {
        		List<Product> productList = manager.findProductList();
        		
        		for(int i=0; i<productList.size(); i++) {
        			double avgRev = Math.round((reviewManager.calculateAverageScore(productList.get(i).getId())*10))/10.0;
        			productList.get(i).setAverageReview(avgRev);    		}
        		
        		request.setAttribute("productList", productList);
        		return "/admin/adminPage.jsp";
        	}
       	} catch(Exception e) {
       		e.printStackTrace();
       		request.setAttribute("message", "admin이 아니거나, 로그인 하지 않은 사용자입니다.");
       	}
        
        return "redirect:/product";
	}
}