package dbcp2;

import java.sql.*;

public class DbcpTest2{
	
	private static ConnectionManager2 cm = new ConnectionManager2();  // connection manager 객체 생성
	
	public static void main(String args[]){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT DEPTNO, DNAME FROM DEPT0000";

		try {
			conn = cm.getConnection(); 					// DBCP로 부터 connection 획득
			pstmt = conn.prepareStatement(query);		
			rs = pstmt.executeQuery();
			
			System.out.println("DeptNo   DName");
			System.out.println("---------------");
			
			while (rs.next()) {					
				int no = rs.getInt("DeptNO");
				String name = rs.getString("DNAME");
				System.out.println(no + "    " + name);
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if(rs != null) try { rs.close(); } catch(Exception e) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
	}
}