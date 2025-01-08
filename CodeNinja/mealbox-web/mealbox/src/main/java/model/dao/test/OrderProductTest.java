package model.dao.test;

import java.sql.SQLException;
import java.util.Scanner;

import model.domain.OrderProduct;
import model.service.OrderProductManager;

public class OrderProductTest {

    private static OrderProductManager orderProductManager = OrderProductManager.getInstance();

    public static void main(String[] args) {
        System.out.println("Order Product Test용 코드입니다.");
        Scanner sc = new Scanner(System.in);

        try {
            // 사용자로부터 OrderProduct 데이터 입력받기
            System.out.print("orderId를 입력하시오: ");
            int orderId = sc.nextInt();

            System.out.print("productId를 입력하시오: ");
            int productId = sc.nextInt();

            System.out.print("수량을 입력하시오: ");
            int quantity = sc.nextInt();

            System.out.print("상품 가격을 입력하시오: ");
            int orderItemPrice = sc.nextInt();

            // OrderProduct 객체 생성
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(orderId);
            orderProduct.setProductId(productId);
            orderProduct.setQuantity(quantity);
            orderProduct.setOrderItemPrice(orderItemPrice);

            // OrderProduct 추가 호출
            int result = orderProductManager.createOrderProduct(orderProduct);

            // 결과 출력
            if (result > 0) {
                System.out.println("주문 상품 추가 성공!");
                System.out.println(orderProduct.toString());
            } else {
                System.out.println("주문 상품 추가 실패!");
            }

        } catch (SQLException e) {
            System.out.println("데이터베이스 오류: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close(); // Scanner 리소스 반환
        }
    }
}
