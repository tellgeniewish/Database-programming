/*
기능: OrderProduct
작성자: 장고은
마지막 수정일: 2024-11-10
*/

package model.domain;

// 주문이 완료된 상품 

public class OrderProduct{
	private int orderId;
	private int lineNo;
	private int productId;
	private int quantity;
	private int orderItemPrice;
	
	public OrderProduct() { }		
	

	public OrderProduct(int orderId, int lineNo, int productId, int quantity, int orderItemPrice) {
		this.orderId = orderId;
		this.lineNo = lineNo;
		this.productId = productId;
		this.quantity = quantity;
		this.orderItemPrice = orderItemPrice;
	}
	
//	public OrderProduct(int lineNo, int productId, int quantity, int orderItemPrice) {
//		this.lineNo = lineNo;
//		this.productId = productId;
//		this.quantity = quantity;
//		this.orderItemPrice = orderItemPrice;
//	}
	
	// setter & getter
	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public int getLineNo() {
		return lineNo;
	}


	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
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


	public int getOrderItemPrice() {
		return orderItemPrice;
	}


	public void setOrderItemPrice(int orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}


	@Override
	public String toString() {
		return "OrderProduct [orderId=" + orderId + ", lineNo=" + lineNo + ", productId=" + productId + ", quantity="
				+ quantity + ", orderItemPrice=" + orderItemPrice + "]";
	}		
		
	
}