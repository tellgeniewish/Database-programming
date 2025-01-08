package dbcp3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpDAO {
	private static ConnectionManager3 cm = new ConnectionManager3();  // connection manager 객체 생성
		
	public List<Employee> findEmpList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT empno, ename, job, sal FROM EMP0000";

		try {
			conn = cm.getConnection();			// DBCP로 부터 connection 획득
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			List<Employee> empList = new ArrayList<Employee>();
			while (rs.next()) {
				Employee emp = new Employee(
						rs.getInt("empno"),
						rs.getString("ename"),
						rs.getString("job"),
						rs.getDouble("sal"));
				empList.add(emp);
			}
			return empList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) {
				try { pstmt.close(); } 
				catch (SQLException ex) { ex.printStackTrace(); }
			}
			if (conn != null) { 
				try { conn.close(); } 
				catch (SQLException ex) { ex.printStackTrace(); }
			}
		}
		return null;
	}
}
