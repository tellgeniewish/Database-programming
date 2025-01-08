package model.domain;

import java.util.List;
//import java.util.ArrayList;

import model.dao.OrderDAO;

public class User {
	private String id;
	private String password;
	private String name;
	private String phone;
	private String email;
	private String address;
	private List<Order> orderHistory; //주문 목록
	private int orderCount = 0;//주문 갯수
	private int reviewCount = 0;//리뷰 갯수
	
	public User() {
		
	}
	public User(String id, String password, String name, String phone, String email, String address) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Order> getOrderHistory(){ 
		return orderHistory; 
	} 
	public void setOrderHistory(List<Order> orderHistory) { 
		this.orderHistory = orderHistory;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	 
	/*기능*/
	//사용자 정보
	public void printUserInfo() {
		String str="";
		str += "아이디: " + getId() + "\n";
		str += "비밀번호: " + getPassword() + "\n";
		str += "이름: " + getName() + "\n";
		str += "휴대폰: " + getPhone() + "\n";
		str += "이메일: " + getEmail() + "\n";
		str += "주소: " + getAddress() + "\n";
		System.out.print(str);
	}
	
	public boolean matchPassword(String password) {
		if(password == null) {
			return false;
		}
		return this.password.equals(password);
	}
	
	public boolean isSameUser(String userid) {
		return id.equals(userid);
	}
	
	
	 
}
