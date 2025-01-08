package dbcp1;

import java.sql.*;

public class DbcpTest1 {
	
	private static ConnectionManager1 cm = new ConnectionManager1();	// connection manager 객체 생성

	public static void main(String args[]){
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		String query = "SELECT * FROM EMP0000";

		try {
			conn = cm.getConnection(); 					// DBCP로 부터 connection 획득
			pstmt = conn.prepareStatement(query);		
			rs = pstmt.executeQuery();
			
			System.out.println("EmpNo    EName");
			System.out.println("---------------");
			while (rs.next()) {					
				int no = rs.getInt("EMPNO");
				String name = rs.getString("ENAME");
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