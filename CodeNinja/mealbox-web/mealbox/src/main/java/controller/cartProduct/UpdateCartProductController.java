package controller.cartProduct;

import model.domain.CartProduct;
import model.service.CartProductManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;

public class UpdateCartProductController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CartProductManager cartProductMan = CartProductManager.getInstance();

        try {
			HttpSession session = request.getSession();
        	
			String userId = (String)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int cartItemPrice = Integer.parseInt(request.getParameter("cartItemPrice"));
            String updateValue = request.getParameter("updateValue");
			
			System.out.println("Received Parameters:");
			System.out.println("User ID: " + userId);
			System.out.println("Product ID: " + productId);
			System.out.println("Quantity: " + quantity);
			System.out.println("Cart Item Price: " + cartItemPrice);
			System.out.println("Update Value: " + updateValue);
			
            int productPrice = cartItemPrice / quantity;
            
            if("increase".equals(updateValue)) {
            	cartItemPrice = cartItemPrice + productPrice;
            	quantity = quantity + 1;
            } else {
            	if(quantity - 1 == 0) {
            		return "redirect:cart/remove"; // 수량이 0 이라면 해당 장바구니 상품 삭제
            	} else {
	            	cartItemPrice = cartItemPrice - productPrice;
	            	quantity = quantity - 1;            	
            	}
            }


			System.out.println("Quantity: " + quantity);
			System.out.println("Cart Item Price: " + cartItemPrice);
			
            // CartProduct 객체 생성
            CartProduct cartProduct = new CartProduct();
            cartProduct.setUserId(userId);
            cartProduct.setProductId(productId);
            cartProduct.setQuantity(quantity);
            cartProduct.setCartItemPrice(cartItemPrice);
     
            int result =  cartProductMan.updateCartProduct(cartProduct);


            // 결과 출력
            if (result > 0) {
                System.out.println("장바구니 상품 업데이트 성공!");
                System.out.println(cartProduct.toString());
            } else {
                System.out.println("장바구니 상품 업데이트 실패!");
            }
            
            return "redirect:/cart/view";
            
        } catch (Exception e) {
        	System.out.println("오류 발생: " + e.getMessage());
        	e.printStackTrace(); // 자세한 오류 로그 출력
            request.setAttribute("message", "오류 발생: " + e.getMessage());
            return "redirect:/product";
        }
    }
}
