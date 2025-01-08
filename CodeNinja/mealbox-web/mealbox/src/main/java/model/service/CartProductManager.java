package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.CartProductDAO;
import model.domain.CartProduct;

/**
 * 장바구니 상품 관리 클래스 (비즈니스 로직 처리 및 DAO 호출)
 */
public class CartProductManager {
    private static CartProductManager cartProductMan = new CartProductManager();
    private CartProductDAO cartProductDAO;

    private CartProductManager() {
        try {
            cartProductDAO = new CartProductDAO(); // DAO 객체 생성
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CartProductManager getInstance() {
        return cartProductMan;
    }

    /**
     * 새로운 장바구니 상품 생성
     */
    public int addCartProduct(CartProduct cartProduct) throws SQLException {
        return cartProductDAO.create(cartProduct);
    }

    /**
     * 기존 장바구니 상품 업데이트
     */
    public int updateCartProduct(CartProduct cartProduct) throws SQLException {
        return cartProductDAO.update(cartProduct);
    }

    /**
     * 특정 장바구니 상품 삭제
     */
    public int removeCartProduct(String userId, int productId) throws SQLException {
        return cartProductDAO.remove(userId, productId);
    }

    /**
     * 특정 사용자의 장바구니 상품 조회
     */
    public List<CartProduct> getCartProducts(String userId) throws SQLException {
        return cartProductDAO.findCartProductInUser(userId);
    }
    
    /**
     * 특정 사용자의 특정 장바구니 상품 조회
     */
    public CartProduct getCartProduct(String userId, int productId) throws SQLException {
        return cartProductDAO.findCartProductInUserAndProduct(userId, productId);
    }
    
    /**
     * 특정 사용자의 장바구니 총 금액 계산
     */
    public int calculateTotalCartPrice(String userId) throws SQLException {
        return cartProductDAO.calculateTotalCartPrice(userId);
    }
}
