package controller.cartProduct;

import model.service.CartProductManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;

public class RemoveCartProductController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CartProductManager cartProductMan = CartProductManager.getInstance();

        try {
			HttpSession session = request.getSession();
        	
			String userId = (String)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
            int productId = Integer.parseInt(request.getParameter("productId"));

            int result = cartProductMan.removeCartProduct(userId, productId);

            if (result > 0) {
                request.setAttribute("message", "장바구니 상품이 삭제되었습니다.");
                return "redirect:/cart/view";
            } else {
                request.setAttribute("message", "장바구니 삭제 실패.");
                return "redirect:/cart/view";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "오류 발생: " + e.getMessage());
            return "redirect:/cart/view";
        }
    }
}
