/*
기능: Order
작성자: 장고은
마지막 수정일: 2024-11-10
*/
package model.domain;

import java.util.Date;

public class Order {
	private int orderId;
	private String userId;
	private Date orderAt;
	private String purchaser;
	private String purPhone;
	private String recipient;
	private String recPhone;
	private String deliveryAddress;
	private int totalPrice;
	private Date deliveryDate;
	
	public Order() {}
	
	public Order(int orderId, String userId, Date orderAt, String purchaser, String purPhone, String recipient, String recPhone, String deliveryAddress, int totalPrice, Date deliveryDate) {
		this.orderId = orderId;
		this.userId = userId;
		this.orderAt = orderAt;
		this.purchaser = purchaser;
		this.purPhone = purPhone;
		this.recipient = recipient;
		this.recPhone = recPhone;
		this.deliveryAddress = deliveryAddress;
		this.totalPrice = totalPrice;
		this.deliveryDate = deliveryDate;
	}
	
	// 특정 사용자가 주문한 주문 정보를 구하기 위한 생성자
	public Order(int orderId, Date orderAt, String purchaser, String purPhone, String recipient, String recPhone, String deliveryAddress, int totalPrice, Date deliveryDate) {
		this.orderId = orderId;
		this.orderAt = orderAt;
		this.purchaser = purchaser;
		this.purPhone = purPhone;
		this.recipient = recipient;
		this.recPhone = recPhone;
		this.deliveryAddress = deliveryAddress;
		this.totalPrice = totalPrice;
		this.deliveryDate = deliveryDate;
	}

	// setter & getter
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public String getPurPhone() {
		return purPhone;
	}

	public void setPurPhone(String purPhone) {
		this.purPhone = purPhone;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getRecPhone() {
		return recPhone;
	}

	public void setRecPhone(String recPhone) {
		this.recPhone = recPhone;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderAt() {
		return orderAt;
	}

	public void setOrderAt(Date orderAt) {
		this.orderAt = orderAt;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", userId=" + userId + ", orderAt=\" + orderAt + \", purchaser=" + purchaser + ", purPhone="
				+ purPhone + ", recipient=" + recipient + ", recPhone=" + recPhone + ", deliveryAddress="
				+ deliveryAddress + ", totalPrice=" + totalPrice + ", deliveryDate="
				+ deliveryDate + "]";
	}
}
