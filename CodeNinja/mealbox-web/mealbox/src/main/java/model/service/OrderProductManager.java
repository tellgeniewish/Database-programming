package model.service;

import java.sql.SQLException;
import java.util.List;
import model.dao.OrderProductDAO;
import model.domain.OrderProduct;

/**
 * 주문 상품 관리 클래스 (비즈니스 로직 처리 및 DAO 호출)
 */
public class OrderProductManager {
    private static OrderProductManager orderProductMan = new OrderProductManager();
    private OrderProductDAO orderProductDAO;

    // Private 생성자로 싱글톤 패턴 구현
    private OrderProductManager() {
        try {
            orderProductDAO = new OrderProductDAO(); // DAO 객체 생성
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Singleton 인스턴스 반환
    public static OrderProductManager getInstance() {
        return orderProductMan;
    }

    /**
     * 새로운 주문 상품 생성
     */
    public int createOrderProduct(OrderProduct orderProduct) throws SQLException {
        return orderProductDAO.create(orderProduct);
    }

    /**
     * 특정 주문 ID의 모든 상품 조회
     */
    public List<OrderProduct> findOrderProductsInOrder(int orderId) throws SQLException {
        return orderProductDAO.findOrderProductInOrder(orderId);
    }
    
}
