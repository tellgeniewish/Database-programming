package controller.orderProduct;

import model.domain.OrderProduct;
import model.domain.Product;
import model.service.OrderProductManager;
import model.service.ProductManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindOrderProductsInOrderController implements Controller   {
	OrderProductManager orderProductManager = OrderProductManager.getInstance();
    ProductManager productManager = ProductManager.getInstance();

	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
        	// POST 요청에서 orderId 추출
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            System.out.println("Received orderId: " + orderId);
            
            List<OrderProduct> orderProductList = orderProductManager.findOrderProductsInOrder(orderId);

	        // 각 주문별 상품 목록 조회
            List<Map<String, Object>> combinedProductDetails = new ArrayList<>();
            for (OrderProduct orderProduct : orderProductList) {
                Map<String, Object> productDetail = new HashMap<>();
                productDetail.put("orderProduct", orderProduct);
    			System.out.println("Logged-in OrderId ID: " + orderProduct.getOrderId());
                Product product = productManager.getDetail(orderProduct.getProductId());
                productDetail.put("productDetail", product);
                combinedProductDetails.add(productDetail);
            }
            request.setAttribute("combinedProductDetails", combinedProductDetails);

	        
            return "/purchase/orderProductDetailPage.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "오류 발생: " + e.getMessage());
            return "redirect:/purchase/purchaseList"; // 오류 발생 시 이동할 페이지
        }
    }
}
