package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.User;
/**
 * 사용자 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * USERINFO 테이블에 사용자 정보를 추가, 수정, 삭제, 검색 수행 
 */
public class UserDAO {
	private JDBCUtil jdbcUtil = null;
	
	public UserDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
	}
	//완료 코드
	/**
	 * 주어진 사용자 ID에 해당하는 사용자 정보를 데이터베이스에서 찾아 User 도메인 클래스에 
	 * 저장하여 반환.
	 */
	public User findUser(String userId) throws SQLException {
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT password, name, email, phone, address ");
		query.append("FROM MEAL_USER ");
		query.append("WHERE userid=?");          
		jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {userId});	// JDBCUtil에 query문과 매개 변수 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			if (rs.next()) {						// 학생 정보 발견
				User user = new User(		// User 객체를 생성하여 학생 정보를 저장
					userId,
					rs.getString("password"),
					rs.getString("name"),
					rs.getString("phone"),
					rs.getString("email"),
					rs.getString("address"));
				return user;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
	/**
	 * 사용자 관리 테이블에 새로운 사용자 생성.
	 */
	public int create(User user) throws SQLException {
		String sql = "INSERT INTO MEAL_USER (userid, password, name, phone, email, address, role, createdat, updatedat) VALUES (?, ?, ?, ?, ?, ?, 0, SYSDATE, SYSDATE)";		
		Object[] param = new Object[] {user.getId(), user.getPassword(), 
						user.getName(),  user.getPhone(), user.getEmail(), 
						user.getAddress() };				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil 에 insert문과 매개 변수 설정
						
		try {				
			int result = jdbcUtil.executeUpdate();	// insert 문 실행
			return result;
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
	 * 기존의 사용자 정보를 수정.
	 */
	public int update(User user) throws SQLException {
		String sql = "UPDATE MEAL_USER "
					+ "SET password=?, name=?, phone=?, email=?, address=?, updatedat=SYSDATE "
					+ "WHERE userid=?";
		Object[] param = new Object[] {user.getPassword(), user.getName(), 
					user.getPhone(), user.getEmail(),  user.getAddress(),
					user.getId()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil에 update문과 매개 변수 설정
			
		try {				
			int result = jdbcUtil.executeUpdate();	// update 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}

	/**
	 * 사용자 ID에 해당하는 사용자를 삭제.
	 */
	public int remove(String userId) throws SQLException {
		String sql = "DELETE FROM MEAL_USER WHERE userid=?";		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	// JDBCUtil에 delete문과 매개 변수 설정

		try {				
			int result = jdbcUtil.executeUpdate();	// delete 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}

	
	/**
	 * 전체 사용자 정보를 검색하여 List에 저장 및 반환
	 */
	public List<User> findUserList() throws SQLException {
        String sql = "SELECT u.userId, u.password, u.name, u.phone, u.address, u.email, COUNT(o.orderId) AS orderCount, COUNT(r.reviewId) AS reviewCount " 
        		   + "FROM MEAL_USER u LEFT OUTER JOIN MEAL_ORDER o ON u.userId = o.userId "
        		   + "LEFT OUTER JOIN MEAL_REVIEW r ON u.userId = r.userId "
        		   + "GROUP BY u.userId, u.password, u.name, u.phone, u.address, u.email "
        		   + "ORDER BY userId";
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil에 query문 설정
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query 실행			
			List<User> userList = new ArrayList<User>();	// User들의 리스트 생성
			while (rs.next()) {
				String userId = rs.getString("userId");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String address = rs.getString("address");
				
				User user = new User(userId, password, name, phone, email, address);// User 객체를 생성하여 현재 행의 정보를 저장
				user.setOrderCount(rs.getInt("orderCount"));
				user.setReviewCount(rs.getInt("reviewCount")); 
				
				if(userId.equals("admin")) {continue;} //admin은 List에 저장하지 않음.
				userList.add(user);				// List에 User 객체 저장
			}		
			return userList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
	/**
	 * 전체 사용자 정보를 검색한 후 현재 페이지와 페이지당 출력할 사용자 수를 이용하여
	 * 해당하는 사용자 정보만을 List에 저장하여 반환.
	 */
	public List<User> findUserList(int currentPage, int countPerPage) throws SQLException {
		String sql = "SELECT userId, name, email, NVL(commId, 0) AS commId, cName " 
					+ "FROM USERINFO u LEFT OUTER JOIN Community c ON u.commId = c.cId "
					+ "ORDER BY userId";
		jdbcUtil.setSqlAndParameters(sql, null,					// JDBCUtil에 query문 설정
				ResultSet.TYPE_SCROLL_INSENSITIVE,				// cursor scroll 가능
				ResultSet.CONCUR_READ_ONLY);						
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();				// query 실행			
			int start = ((currentPage-1) * countPerPage) + 1;	// 출력을 시작할 행 번호 계산
			if ((start >= 0) && rs.absolute(start)) {			// 커서를 시작 행으로 이동
				List<User> userList = new ArrayList<User>();	// User들의 리스트 생성
				do {
					User user = new User(			// User 객체를 생성하여 현재 행의 정보를 저장
						rs.getString("userId"),
						null,
						rs.getString("name"),
						rs.getString("email"),
						null,
						rs.getString("address"));
					userList.add(user);							// 리스트에 User 객체 저장
				} while ((rs.next()) && (--countPerPage > 0));		
				return userList;							
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}

	/**
	 * 주어진 사용자 ID에 해당하는 사용자가 존재하는지 검사 
	 */
	public boolean existingUser(String userId) throws SQLException {
		String sql = "SELECT count(*) FROM MEAL_USER WHERE userid=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	// JDBCUtil에 query문과 매개 변수 설정

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

}
