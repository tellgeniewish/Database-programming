package transaction;

import java.sql.*;

public class TransactionTest {
	static Connection conn = null;

	public static void main(String args[]) {
		String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";
		String user = "scott";
		String passwd = "TIGER";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			// conn.setAutoCommit(true);  --> auto-commit mode (default 설정)
		
			System.out.println("init accounts, 100000");
			init_accounts(100000);
			show();
			
			System.out.println("101 --> 200, 100000");
			transfer(101, 200, 100000);
			show();
			
			conn.setAutoCommit(false); 		// auto commit mode 해제
			System.out.println("102 --> 200, 100000");
			transfer(102, 200, 100000);
			show();
			
			conn.setAutoCommit(false); 		// auto commit mode 해제
			System.out.println("103 --> 104, 100000");
			transfer(103, 104, 100000);
			show();
			
			System.out.println("batch udpate1, 104 --> 200, 100000");
			transfer_batch1(104, 200, 100000);
			show();

			System.out.println("batch udpate1, 104 --> 103, 100000");
			transfer_batch1(104, 103, 100000);
			show();
			
			System.out.println("batch udpate2, 104 --> 103, 100000");
			transfer_batch2(104, 103, 100000);
			show();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
		}
	}	
		
	public static void transfer(int senderAccNo, int receiverAccNo, int amount) {
		PreparedStatement pStmt = null;		
		try {
			String sql = "UPDATE account SET balance = balance - ? WHERE accno = ?";
			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, amount);
			pStmt.setInt(2, senderAccNo);
			if (pStmt.executeUpdate() != 1) 
				throw new AppException();
			pStmt.close();
			
			sql = "UPDATE account SET balance = balance + ? WHERE accno = ?";
			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, amount);
			pStmt.setInt(2, receiverAccNo);
			if (pStmt.executeUpdate() != 1) 
				throw new AppException();
			
			conn.commit();
		} catch (Exception ex) {
			try { 
				conn.rollback();
			} catch (SQLException e) { e.printStackTrace(); }
			ex.printStackTrace();
		}
		finally { 
			try { 
				conn.setAutoCommit(true);		// auto-commit mode 설정
			} catch (SQLException ex) { ex.printStackTrace(); }
			if (pStmt != null) {
				try { 
					pStmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
		}
	}
	
	public static void transfer_batch1(int senderAccNo, int receiverAccNo, int amount) {
		Statement stmt = null;		 
		try {
			conn.setAutoCommit(false); 		// auto commit 해제 -> 트랜잭션 시작

			String sql1 = "UPDATE account SET balance = balance - " + amount +
					" WHERE accno = " + senderAccNo;
			String sql2 = "UPDATE account SET balance = balance + " + amount +
					" WHERE accno = " + receiverAccNo;
			stmt = conn.createStatement();
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			int[] cnt = stmt.executeBatch();

			if (cnt[0] != 1 || cnt[1] != 1) 
				throw new AppException();
			
			conn.commit();
		} catch (Exception ex) {
			try { 
				conn.rollback();
			} catch (SQLException e) { e.printStackTrace(); }
			ex.printStackTrace();
		}
		finally { if (stmt != null) 
			try { stmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
		}
	}
	
	public static void transfer_batch2(int senderAccNo, int receiverAccNo, int amount) {
		PreparedStatement pStmt = null;		 
		try {
			conn.setAutoCommit(false); 		// auto commit 해제 -> 트랜잭션 시작

			String sql1 = "UPDATE account SET balance = balance - ? WHERE accNo = ?";
			pStmt = conn.prepareStatement(sql1);
			pStmt.setInt(1, amount); 
			pStmt.setInt(2, senderAccNo);
			pStmt.addBatch();			
			
			pStmt.clearParameters();		// 질의 파라미터들을 삭제(optional)
			pStmt.setInt(1, -amount); 
			pStmt.setInt(2, receiverAccNo);
			pStmt.addBatch();

			int[] cnt = pStmt.executeBatch();
			for (int i=0; i < cnt.length; i++) {        
			    if (cnt[i] == Statement.SUCCESS_NO_INFO)
				    System.out.println("Execution " + i + 
				        ": unknown number of rows updated");
				else if (cnt[i] != 1) 
					throw new AppException();
			}
			
			conn.commit();
		} catch (Exception ex) {
			try { 
				conn.rollback();
			} catch (SQLException e) { e.printStackTrace(); }
			ex.printStackTrace();
		}
		finally { if (pStmt != null) 
			try { pStmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
		}
	}
	
	public static void init_accounts(int amount) {
		PreparedStatement pStmt = null;		
		try {
			pStmt = conn.prepareStatement("UPDATE account SET balance = ?");
			pStmt.setInt(1, amount);
			pStmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally { 
			if (pStmt != null) {
				try { 
					pStmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			}
		}
	}
	
	public static void show() {
		PreparedStatement pStmt = null;		
		ResultSet rs = null;
		try {
			String sql = "SELECT accno, balance FROM account";
			pStmt = conn.prepareStatement(sql);
			rs = pStmt.executeQuery();
			System.out.println("AccNo Balance");
			System.out.println("-------------");
			while (rs.next()) {
				System.out.println(rs.getInt("accno") + "  " + rs.getInt("balance"));
			}			
			System.out.println();
			System.out.println();
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
			if (rs != null) try { rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
			if (pStmt != null) try { pStmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
		}
	}
}
