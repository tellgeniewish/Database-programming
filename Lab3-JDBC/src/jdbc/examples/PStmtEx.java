package jdbc.examples;
import java.sql.*;

public class PStmtEx {	
	static final String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";		
	// static final String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
	static final String user = "scott";
	static final String passwd = "TIGER";
		
	public static void main(String args[]) {		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// 2. JDBC Driver 로딩 및 등록
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		String searchKeyword = "AR";				
	
		System.out.println("--- Test #1: execute testStatement1(\"AR\") ---");
		testStatement1(searchKeyword);

		try {
			Thread.sleep(1000); 		// 1초 대기
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("--- Test #2: execute testStatement2(\"AR\") ---");
		testStatement2(searchKeyword);
		
		System.out.println("--- Test #3: execute testPreparedStatement(\"AR\") ---");
		testPreparedStatement(searchKeyword);
	}
	
	public static void testStatement1(String keyword) {	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;	
		
		String pattern = "%" + keyword + "%";

		String query1 = "SELECT ename, job, dname "
				+ "FROM emp JOIN dept USING (deptno) "
				+ "WHERE ename like " + pattern;	// 작은 따옴표 누락됨!
		
		System.out.println(query1);
		System.out.println();

		try {
			conn = DriverManager.getConnection(url, user, passwd);				
			stmt = conn.createStatement();	
			rs = stmt.executeQuery(query1);			// Statement 객체를 사용하여 SQL문 실행
			
			System.out.println("name    job    deptName");
			while (rs.next()) {							// 실행 결과 레코드 fetch
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String dname = rs.getString("dname");
				System.out.println(ename + ", " + job + ", " + dname);
			}
			System.out.println();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {				// 자원 해제
			if (rs != null) {
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
			if (stmt != null) { 
				try { 
					stmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
		}
	}
	
	public static void testStatement2(String keyword) {	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;	
		
		String pattern = "%" + keyword + "%";

		String query2 = "SELECT ename, job, dname "
			  	+ "FROM emp JOIN dept USING (deptno) "
			  	+ "WHERE ename like '" + pattern + "'";	// 작은 따옴표 추가
		System.out.println(query2);
		System.out.println();
		
		try {			
			conn = DriverManager.getConnection(url, user, passwd);				
			stmt = conn.createStatement();	
			rs = stmt.executeQuery(query2);				// Statement 객체를 사용하여 SQL문 실행
			
			System.out.println("name    job    deptName");
			while (rs.next()) {							// 실행 결과 레코드 fetch
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String dname = rs.getString("dname");
				System.out.println(ename + ", " + job + ", " + dname);
			}
			System.out.println();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {				// 자원 해제
			if (rs != null) {
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
			if (stmt != null) { 
				try { 
					stmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
			if (conn != null) {
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
		}	
	}
	
	public static void testPreparedStatement(String keyword) {	
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;	
		
		String pattern = "%" + keyword + "%";

		// String concatenation(+)보다 StringBuffer나 StringBuilder 사용이 바람직함
		StringBuffer query3 = new StringBuffer();
		query3.append("SELECT ename, job, dname ");
		query3.append("FROM emp JOIN dept USING (deptno) ");
		query3.append("WHERE ename like ?");		// parameter가 포함된 SQL 질의 정의
					
		System.out.println(query3);
		System.out.println();

		try {			
			conn = DriverManager.getConnection(url, user, passwd);				
			pStmt = conn.prepareStatement(query3.toString());	// Connection으로부터 PreparedStatement 생성
			pStmt.setString(1, pattern);			// PreparedStatement에 파라미터 값 설정
			rs = pStmt.executeQuery();				// 질의 실행 (주의: parameter 없음!)
			
			System.out.println("name    job    deptName");
			while (rs.next()) {						// 실행 결과 레코드 fetch
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String dname = rs.getString("dname");
				System.out.println(ename + ", " + job + ", " + dname);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {				// 자원 해제
			if (rs != null) {
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
			if (pStmt != null) { 
				try { 
					pStmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
			if (conn != null) { 
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
		}	
	}	
}