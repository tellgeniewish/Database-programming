package model.service;

import java.sql.SQLException;
import java.util.List;
import model.dao.OrderDAO;
import model.domain.Order;

/**
 * 주문 관리 클래스 (비즈니스 로직 처리 및 DAO 호출)
 */
public class OrderManager {
    private static OrderManager orderMan = new OrderManager();
    private OrderDAO orderDAO;

    private OrderManager() {
        try {
            orderDAO = new OrderDAO(); // DAO 객체 생성
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static OrderManager getInstance() {
        return orderMan;
    }

    /**
     * 새로운 주문 생성
     */
    public int create(Order order) throws SQLException, ExistingOrderException {
        if (orderDAO.existingOrder(order.getOrderId())) {
            throw new ExistingOrderException(order.getOrderId() + "는 존재하는 주문아이디입니다.");
        }
        int orderId = orderDAO.create(order); // 생성된 orderId 반환
        order.setOrderId(orderId); // Order 객체에 orderId 설정
        return orderId;
    }

    /**
     * 특정 사용자의 주문 목록 조회
     */
    public List<Order> findOrdersInUser(String userId) throws SQLException {
        return orderDAO.findOrdersInUser(userId);
    }
    
    /**
	 * 특정 사용자의 주문 건수 조회 
     */
    public int countOrder(String userId) throws SQLException {
        return orderDAO.count(userId);
    }
}
