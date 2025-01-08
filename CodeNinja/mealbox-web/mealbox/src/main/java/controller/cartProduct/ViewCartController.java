package controller.cartProduct;

import model.domain.CartProduct;
import model.domain.Product;
import model.service.CartProductManager;
import model.service.ProductManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCartController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CartProductManager cartProductMan = CartProductManager.getInstance();
        ProductManager productManager = ProductManager.getInstance();

        try {
            // 사용자 ID 추출
			HttpSession session = request.getSession();
			String userId = (String)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);

            List<CartProduct> cartProductList = cartProductMan.getCartProducts(userId);
            
            // 장바구니 상품 데이터
            List<Map<String, Object>> combinedProductDetails = new ArrayList<>();
            for (CartProduct cartProduct : cartProductList) {
                Map<String, Object> productDetail = new HashMap<>();
                productDetail.put("cartProduct", cartProduct);
                Product product = productManager.getDetail(cartProduct.getProductId());
                productDetail.put("productDetail", product);
                combinedProductDetails.add(productDetail);
            }
            
            int totalPrice = cartProductMan.calculateTotalCartPrice(userId);
            
            // 세션에 장바구니 상품 정보 저장
            session.setAttribute("cartProducts", combinedProductDetails);
            session.setAttribute("cartTotalPrice", totalPrice);
            
            // 요청에 데이터 전달
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("combinedProductDetails", combinedProductDetails);
            
            String page = "";
            if(request.getParameter("page") == null) {
            	page = (String) session.getAttribute("page");
            } else {
            	page = request.getParameter("page");
            	session.setAttribute("page", page);
            }
            
            if("cartPage".equals(page)) {
                return "/cart/cartPage.jsp"; 
            } else if("purchasePage".equals(page)) {
                return "/purchase/purchasePage.jsp";
            } else {
                return "/cart/cartPage.jsp"; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "오류 발생: " + e.getMessage());
            return "redirect:/product";
        }
    }
}
