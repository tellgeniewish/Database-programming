/*
기능: OrderProductDAO
작성자: 장고은
마지막 수정일: 2024-11-10
*/

package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.OrderProduct;

/**
 * 주문 상품 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * MEAL_ORDER_PRODUCT 테이블에서 장바구니 정보를 추가, 수정, 삭제, 검색 수행 
 */
public class OrderProductDAO {
	private JDBCUtil jdbcUtil = null;
	
	public OrderProductDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
	}
	
	/**
	 * MEAL_ORDER_PRODUCT 테이블에 새로운 행 생성 (PK 값은 Sequence를 이용하여 자동 생성)
	*/
	public int create(OrderProduct orderProduct) throws SQLException {
	    String sql = "INSERT INTO MEAL_ORDER_PRODUCT (orderId, lineNo, productId, quantity, orderItemPrice) " +
	                 "VALUES (?, sequence_lineno.nextval, ?, ?, ?)";
	    
	    Object[] param = new Object[] {
	        orderProduct.getOrderId(),
	        orderProduct.getProductId(),
	        orderProduct.getQuantity(),
	        orderProduct.getOrderItemPrice()
	    };
	    
	    jdbcUtil.setSqlAndParameters(sql, param); // JDBCUtil에 insert문과 매개 변수 설정

	    try {				
	        int result = jdbcUtil.executeUpdate(); // insert 문 실행
	        return result;
	    } catch (Exception ex) {
	        jdbcUtil.rollback();
	        ex.printStackTrace();
	    } finally {		
	        jdbcUtil.commit();
	        jdbcUtil.close(); // resource 반환
	    }		
	    return 0;				
	}
	
	
	/**
	 * 특정 주문ID 값을 가지는 주문 상품 정보들을 검색하여 List에 저장 및 반환
	 */
	public List<OrderProduct> findOrderProductInOrder(int orderId) throws SQLException {
        String sql = "SELECT orderId, lineNo, productId, quantity, orderItemPrice " 
      		   + "FROM MEAL_ORDER_PRODUCT "
      		   + "WHERE orderId = ? "
      		   + "ORDER BY lineNo";                        
		jdbcUtil.setSqlAndParameters(sql, new Object[] {orderId});	// JDBCUtil에 query문과 매개 변수 설정
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();	// 주문들의 리스트 생성
			while (rs.next()) {
				OrderProduct orderProduct = new OrderProduct(			// Order 객체를 생성하여 현재 행의 정보를 저장
						rs.getInt("orderId"),
						rs.getInt("lineNo"),
						rs.getInt("productId"),
						rs.getInt("quantity"),
						rs.getInt("orderItemPrice"));
				orderProductList.add(orderProduct);			// List에 Order 객체 저장
			}		
			return orderProductList;					
				
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
}
