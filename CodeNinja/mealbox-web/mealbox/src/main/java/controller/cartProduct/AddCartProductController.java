package controller.cartProduct;

import model.domain.CartProduct;
import model.domain.Product;
import model.service.CartProductManager;
import model.service.ProductManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;

public class AddCartProductController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CartProductManager cartProductMan = CartProductManager.getInstance();
        ProductManager productManager = ProductManager.getInstance();

        try {
			HttpSession session = request.getSession();
			String userId = (String)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int cartItemPrice = Integer.parseInt(request.getParameter("cartItemPrice"));
            int result = -1;
            
         // 기존 장바구니 상품 확인 및 처리
            CartProduct cartProduct = new CartProduct(cartProductMan.getCartProduct(userId, productId));
            
            if (cartProduct.getUserId() == null) {
                // 데이터가 없으므로 새로 추가
                CartProduct newCartProduct = new CartProduct(userId, productId, quantity, cartItemPrice);
                result = cartProductMan.addCartProduct(newCartProduct);
            } else {
                // 데이터가 있으므로 수량 및 가격 업데이트
                int updatedQuantity = quantity + cartProduct.getQuantity();
                int updatedCartItemPrice = cartItemPrice + cartProduct.getCartItemPrice();

                cartProduct.setQuantity(updatedQuantity);
                cartProduct.setCartItemPrice(updatedCartItemPrice);

                result = cartProductMan.updateCartProduct(cartProduct);
            }

            // 결과 출력
            if (result > 0) {
                System.out.println("장바구니 상품 업데이트 성공!");
                System.out.println(cartProduct != null ? cartProduct.toString() : "새로운 장바구니 상품 추가 완료");
            } else {
                System.out.println("장바구니 상품 업데이트 실패!");
            }

            // 버튼 이름에 따른 처리
            String btnName = request.getParameter("btnName");
            if ("장바구니".equals(btnName)) {
                return "redirect:/cart/view";
            } else{
                // 바로 구매 처리
                List<Map<String, Object>> combinedProductDetails = new ArrayList<>();

                Map<String, Object> productDetail = new HashMap<>();
                productDetail.put("cartProduct", new CartProduct(userId, productId, quantity, cartItemPrice));
                Product product = productManager.getDetail(productId);
                productDetail.put("productDetail", product);
                combinedProductDetails.add(productDetail);

                session.setAttribute("cartProducts", combinedProductDetails);
                session.setAttribute("cartTotalPrice", cartItemPrice);
                return "redirect:/purchase/purchase";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "오류 발생: " + e.getMessage());
            return "redirect:/product";
        }
    }
}
