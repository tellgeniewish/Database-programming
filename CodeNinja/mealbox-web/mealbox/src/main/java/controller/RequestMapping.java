package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.*;
import controller.cartProduct.*;
import controller.order.*;
import controller.orderProduct.*;
import controller.product.*;
import controller.review.*;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	// 여기서부터 코드닌자 코드 추가
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/", new ForwardController("index.jsp"));
        
        // 리뷰 관련 매핑 추가
        //mappings.put("/review/items", new ForwardController("/review/reviewForm.jsp"));
        mappings.put("/review/form", new ReviewFormController());
        mappings.put("/review/create", new CreateReviewController());
        mappings.put("/review/read", new ReadReviewController());
        mappings.put("/review/delete", new DeleteReviewController());
        
        //영선-로그인, 회원가입, adminUserPage
        mappings.put("/user/login/form", new ForwardController("/user/login.jsp"));
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/logout", new LogoutController());
        mappings.put("/user/join/form", new ForwardController("/user/join.jsp"));
        mappings.put("/user/createUser", new CreateUserController());
        mappings.put("/user/updateUser", new UpdateUserController());
        mappings.put("/user/deleteUser", new DeleteUserController());
        mappings.put("/user/listUser", new ListUserController());
        
        // 윤지-main 및 admin, product Detail 페이지
        mappings.put("/product", new ListProductController());
        mappings.put("/admin", new ReadProductController());
        mappings.put("/admin/create", new CreateProductController());
        mappings.put("/admin/update", new UpdateProductController());
        mappings.put("/admin/delete", new DeleteProductController());
        mappings.put("/product/detail", new ProductController());
        
        // 고은 -myPage
        mappings.put("/user/readUser", new ForwardController("/user/myPage.jsp"));

        // 고은-장바구니 관련 request URI 추가
        mappings.put("/cart/add", new AddCartProductController());  
        mappings.put("/cart/update", new UpdateCartProductController());   
        mappings.put("/cart/remove", new RemoveCartProductController());  
        mappings.put("/cart/view", new ViewCartController());  
        
        // 고은-주문 관련 request URI 추가
        mappings.put("/order/create", new CreateOrderController());  
        mappings.put("/purchase/purchase", new ForwardController("/purchase/purchasePage.jsp")); 
        mappings.put("/purchase/purchaseList", new FindOrdersInUserController()); 
        mappings.put("/purchase/purchaseList/orderId", new FindOrderProductsInOrderController()); 
        
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	System.out.println("Request URI: " + uri); // 요청된 URI 출력
        if (mappings.get(uri) == null) {
            System.out.println("No controller found for URI: " + uri);
        }
        return  mappings.get(uri);
    }
}