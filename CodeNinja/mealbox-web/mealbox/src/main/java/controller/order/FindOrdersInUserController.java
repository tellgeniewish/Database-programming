package controller.order;

import model.domain.Order;
import model.domain.OrderProduct;
import model.service.OrderManager;
import model.service.OrderProductManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindOrdersInUserController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderManager orderMan = OrderManager.getInstance();
    	OrderProductManager orderProductManager = OrderProductManager.getInstance();

        try {
            // 사용자 ID 추출
			HttpSession session = request.getSession();
			String userId = (String)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
        	//String userId = "ninja1";
			//System.out.println("Logged-in User ID: " + userId);
			
			 // 주문 목록 조회
	        List<Order> orderList = orderMan.findOrdersInUser(userId);

	        // 각 주문별 상품 목록 조회
	        Map<Integer, List<OrderProduct>> orderProductMap = new HashMap<>();
	        for (Order order : orderList) {
	            List<OrderProduct> orderProducts = orderProductManager.findOrderProductsInOrder(order.getOrderId());
	            orderProductMap.put(order.getOrderId(), orderProducts);
	        }

	        // JSP에 데이터 전달
	        request.setAttribute("orderList", orderList);
	        request.setAttribute("orderProductMap", orderProductMap);

	        return "/purchase/orderListPage.jsp";
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("message", "오류 발생: " + e.getMessage());
	        return "/purchase/orderListPage.jsp";
	    }
    }
}

