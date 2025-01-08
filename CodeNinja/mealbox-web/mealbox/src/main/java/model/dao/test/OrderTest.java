package model.dao.test;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import model.dao.OrderDAO;
import model.dao.OrderProductDAO;
import model.domain.Order;
import model.domain.OrderProduct;

public class OrderTest {

    private static OrderProductDAO orderProductDao = new OrderProductDAO();
    private static OrderDAO orderDao = new OrderDAO();

    public static void main(String[] args) {
        System.out.println("Order Test용 코드입니다.");
        Scanner sc = new Scanner(System.in);

        
//        try {
//            // 사용자로부터 orderId와 lineNo 입력받기
//            System.out.print("orderId를 입력하시오: ");
//            int orderId = sc.nextInt();
//
//            System.out.print("lineNo를 입력하시오: ");
//            int lineNo = sc.nextInt();
//
//            System.out.println("\n--- [" + orderId + ", " + lineNo + "]에 대한 주문 상품 정보 조회 ---");
//
//            // 입력받은 orderId와 lineNo로 주문 상품 조회
//            OrderProduct orderProduct = orderProductDao.findOrderProduct(orderId, lineNo);
//
//            // 결과 출력
//            if (orderProduct != null) {
//                System.out.println(orderProduct.toString());
//            } else {
//                System.out.println("해당하는 주문 상품 정보가 없습니다.");
//            }
//        } catch (SQLException e) {
//            System.out.println("데이터베이스 오류: " + e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("오류 발생: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            sc.close(); // Scanner 리소스 반환
//        }
        
        //findOrderProductInOrder test
//        try {
//            // 사용자 ID 입력받기
//            System.out.print("아이디를 입력하시오: ");
//            String userId = sc.next();
//            System.out.println("\n--- [" + userId + "] 사용자의 주문 목록 조회 ---");
//
//            // 사용자 ID로 주문 목록 조회
//            List<Order> orderList = orderDao.findOrdersInUser(userId);
//            if (orderList == null || orderList.isEmpty()) {
//                System.out.println("주문 내역이 없습니다.");
//            } else {
//                for (Order order : orderList) {
//                    System.out.println(order.toString());
//
//                    // 각 주문의 상품 목록 조회
//                    List<OrderProduct> orderProductList = orderProductDao.findOrderProductInOrder(order.getOrderId());
//                    if (orderProductList != null && !orderProductList.isEmpty()) {
//                        for (OrderProduct product : orderProductList) {
//                            System.out.println("  -> " + product.toString());
//                        }
//                    } else {
//                        System.out.println("  -> 주문 상품 정보가 없습니다.");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("데이터베이스 오류: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            sc.close(); // Scanner 리소스 반환
//        }
    }
}
