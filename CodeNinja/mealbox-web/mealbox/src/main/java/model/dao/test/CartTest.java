package model.dao.test;

import java.sql.SQLException;
import java.util.Scanner;
import model.domain.CartProduct;
import model.service.CartProductManager;

public class CartTest {

    private static CartProductManager cartProductManager = CartProductManager.getInstance();

    public static void main(String[] args) {
        System.out.println("Cart Product Test용 코드입니다.");
        Scanner sc = new Scanner(System.in);

        try {
            // 사용자로부터 userId와 productId, quantity 입력받기
            System.out.print("userId를 입력하시오: ");
            String userId = sc.next();

            System.out.print("productId를 입력하시오: ");
            int productId = sc.nextInt();

            System.out.print("새로운 수량을 입력하시오: ");
            int quantity = sc.nextInt();

            System.out.print("새로운 cartItemPrice를 입력하시오: ");
            int cartItemPrice = sc.nextInt();

            // CartProduct 객체 생성
            CartProduct cartProduct = new CartProduct();
            cartProduct.setUserId(userId);
            cartProduct.setProductId(productId);
            cartProduct.setQuantity(quantity);
            cartProduct.setCartItemPrice(cartItemPrice);

            // updateCartProduct 호출
            int result = cartProductManager.updateCartProduct(cartProduct);

            // 결과 출력
            if (result > 0) {
                System.out.println("장바구니 상품 업데이트 성공!");
                System.out.println(cartProduct.toString());
            } else {
                System.out.println("장바구니 상품 업데이트 실패!");
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
