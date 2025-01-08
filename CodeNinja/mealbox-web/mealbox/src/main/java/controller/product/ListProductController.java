package controller.product;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import model.domain.Product;
import model.service.ProductManager;
import controller.Controller;

public class ListProductController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductManager manager = ProductManager.getInstance();
		
		List<Product> productList;
		String categoryType = request.getParameter("categoryType");
		String categoryValue = request.getParameter("categoryValue");
		String orderBy = request.getParameter("orderBy");
		
		// 기본 getAllProducts
		if(orderBy == null && categoryType == null && categoryValue != null) {
		    productList = manager.findProductList();

            request.setAttribute("productList", productList);
            return "/product/productsPage.jsp";
		}
		
		// 정렬 기준 없는 경우
		if (orderBy == null) {
			orderBy = "";
		}
		
		if(categoryType != null && categoryValue != null) {
			productList = manager.searchProduct(categoryType, categoryValue, orderBy);
		} else {
			productList = manager.searchProduct(null, null, orderBy);
		}
		
		request.setAttribute("productList", productList);
        request.setAttribute("categoryType", categoryType);
        request.setAttribute("categoryValue", categoryValue);
        request.setAttribute("orderBy", orderBy);
		return "/product/productsPage.jsp";
	}
}