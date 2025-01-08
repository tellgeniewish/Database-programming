package dbcp1;

import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource; // DBCP 관련 클래스 import

public class ConnectionManager1 {
	
	private BasicDataSource ds = null;		// BasicDataSource 객체 생성

	// BasicDataSource로부터 connection을 구함
	public Connection getConnection() throws SQLException {
		if (ds == null) setupDataSource();
		return ds.getConnection(); 				
	}
	
	private void setupDataSource() {			
		String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";
		String className = "oracle.jdbc.driver.OracleDriver";
		String userName = "scott";
		String passWord = "TIGER";
		
		ds = new BasicDataSource();
		
		ds.setDriverClassName(className); 	// JDBC 드라이버 클래스 설정
		ds.setUrl(url); 					// DBMS 접속 URL 설정
		ds.setUsername(userName); 			// DBMS 사용자 설정
		ds.setPassword(passWord);			// DBMS 사용자 암호 설정
		
		ds.setMaxTotal(10);
		ds.setInitialSize(10);
		ds.setMinIdle(5);
		ds.setMaxIdle(10);
		ds.setMaxWaitMillis(5000);
		ds.setPoolPreparedStatements(true); // Statement pooling 사용
	}
	
	public void close() {
		BasicDataSource bds = (BasicDataSource) ds;
		try {
			bds.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// 현재 활성화 상태인 Connection 의 개수와 비활성화 상태인 Connection 개수 출력
	public void printDataSourceStats() {
		try {
			BasicDataSource bds = (BasicDataSource) ds;
			System.out.println("NumActive: " + bds.getNumActive());
			System.out.println("NumIdle: " + bds.getNumIdle());
		} catch (Exception ex) {
			ex.printStackTrace();
		}   
	}
}	