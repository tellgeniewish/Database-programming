/*
기능: CartProduct
작성자: 장고은
마지막 수정일: 2024-11-10
*/
package model.domain;

public class CartProduct {
	private String userId;
	private int productId;
	private int quantity;
	private int cartItemPrice;
	
	public CartProduct() {}
	
	public CartProduct(String userId, int productId, int quantity, int cartItemPrice) {
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
		this.cartItemPrice = cartItemPrice;
	}
	
	public CartProduct(CartProduct cartProduct) {
	    if (cartProduct != null) {
	        this.userId = cartProduct.getUserId();
	        this.productId = cartProduct.getProductId();
	        this.quantity = cartProduct.getQuantity();
	        this.cartItemPrice = cartProduct.getCartItemPrice();
	    } else {
	        // null 입력에 대한 기본 처리
	        this.userId = null;
	        this.productId = 0;
	        this.quantity = 0;
	        this.cartItemPrice = 0;
	    }
	}
	
	public CartProduct(int productId, int quantity, int cartItemPrice) {
		this.productId = productId;
		this.quantity = quantity;
		this.cartItemPrice = cartItemPrice;
	}
	
	// setter & getter
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getCartItemPrice() {
		return cartItemPrice;
	}

	public void setCartItemPrice(int cartItemPrice) {
		this.cartItemPrice = cartItemPrice;
	}

	@Override
	public String toString() {
		return "CartProduct [userId=" + userId + ", productId=" + productId + ", quantity=" + quantity
				+ ", cartItemPrice=" + cartItemPrice + "]";
	}
}
