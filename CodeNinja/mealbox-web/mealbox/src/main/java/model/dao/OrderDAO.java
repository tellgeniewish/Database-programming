/*
기능: OrderDAO
작성자: 장고은
*/

package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Order;

/**
 * 주문 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * MEAL_ORDER 테이블에서 주문 정보를 추가, 수정, 삭제 수행 (검색은 추후에 추가될 수 있으므로 추가함) 
 */
public class OrderDAO {
	private JDBCUtil jdbcUtil = null;
	
	public OrderDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
	}
	
	/**
	 * MEAL_ORDER 테이블에 새로운 행 생성 (PK 값은 Sequence를 이용하여 자동 생성)
	*/
	public int create(Order order) throws SQLException {
	    String sql = "INSERT INTO MEAL_ORDER (orderId, userId, orderAt, purchaser, purPhone, recipient, recPhone, deliveryAddress, totalPrice, deliveryDate) "
	               + "VALUES (sequence_orderid.nextval, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?)";
	    
	    Object[] param = new Object[] {
	        order.getUserId(), 
	        order.getPurchaser(), 
	        order.getPurPhone(),
	        order.getRecipient(), 
	        order.getRecPhone(), 
	        order.getDeliveryAddress(), 
	        order.getTotalPrice(), 
	        new java.sql.Date(order.getDeliveryDate().getTime())
	    };
	    
	    jdbcUtil.setSqlAndParameters(sql, param); // JDBCUtil에 SQL과 매개 변수 설정
		
		try {				
			int result = jdbcUtil.executeUpdate();	// insert 문 실행
			if (result > 0) {
	            // 자동 생성된 orderId 가져오기
	            String getIdSql = "SELECT sequence_orderid.currval FROM DUAL";
	            jdbcUtil.setSqlAndParameters(getIdSql, null);
	            ResultSet rs = jdbcUtil.executeQuery();
	            if (rs.next()) {
	                return rs.getInt(1); // 생성된 orderId 반환
	            }
			}
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}
	
	/**
	 * 특정 사용자가 주문한 주문 정보들을 검색하여 List에 저장 및 반환
	 * 이후 추가될 수 있음
	 */
	public List<Order> findOrdersInUser(String userId) throws SQLException {
        String sql = "SELECT o.orderId, o.orderAt, o.purchaser, o.purPhone, o.recipient, o.recPhone, o.deliveryAddress, o.totalPrice, o.deliveryDate " 
      		   + "FROM MEAL_ORDER o LEFT OUTER JOIN MEAL_USER u ON o.userId = u.userId "
      		   + "WHERE u.userId = ? "
      		   + "ORDER BY o.orderId";                          
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	// JDBCUtil에 query문과 매개 변수 설정
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			List<Order> orderList = new ArrayList<Order>();	// 주문들의 리스트 생성
			while (rs.next()) {
				Order order = new Order(			// Order 객체를 생성하여 현재 행의 정보를 저장
						rs.getInt("orderId"),
						rs.getDate("orderAt"),
						rs.getString("purchaser"),
						rs.getString("purPhone"),
						rs.getString("recipient"),					
						rs.getString("recPhone"),
						rs.getString("deliveryAddress"),
						rs.getInt("totalPrice"),		
						rs.getDate("deliveryDate"));
				orderList.add(order);			// List에 Order 객체 저장
			}		
			return orderList;					
				
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
	/**
	 * 주어진 주문 ID에 해당하는 사용자가 존재하는지 검사 
	 */
	public boolean existingOrder(int orderId) throws SQLException {
		String sql = "SELECT count(*) FROM MEAL_ORDER WHERE orderId=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {orderId});	// JDBCUtil에 query문과 매개 변수 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return false;
	}
	
	/**
	 * 특정 사용자의 주문 건수 조회 
	 */
	public int count(String userId) throws SQLException {
		String sql = "SELECT count(*) FROM MEAL_ORDER WHERE userId=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	// JDBCUtil에 query문과 매개 변수 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			return rs.getInt(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return 0;
	}
}


